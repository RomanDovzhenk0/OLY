package com.server.dbHandler.repositories;

import com.serviceLib.common.CategoryChangerController;
import com.serviceLib.common.CityHandler;
import com.serviceLib.models.Ad;
import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdRepository extends Query {

    private final Connection CONNECTION;

    public AdRepository(Connection connection) {
        this.CONNECTION = connection;
    }

    public void addAd(Ad ad, int userId){
        try {
            PreparedStatement preparedStatement = this.CONNECTION.prepareStatement(AD_INSERT);
            constructAddStatement(preparedStatement, ad, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException | FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void updateAd(Ad ad) {
        try {
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(AD_UPDATE);
            constructUpdateStatement(preparedStatement, ad);
            preparedStatement.executeUpdate();
            if(ad.getImageFile() != null) {
                try {
                    preparedStatement = CONNECTION.prepareStatement("UPDATE ad SET image = ? WHERE id = ?");
                    FileInputStream fin = new FileInputStream(ad.getImageFile());
                    preparedStatement.setBinaryStream(1, fin, ad.getImageFile().length());
                    preparedStatement.setInt(2, ad.getId());
                    preparedStatement.executeUpdate();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAd(Ad ad) {
        try {
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(AD_DELETE);
            preparedStatement.setInt(1, ad.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Ad> getAds() {
        try{
            PreparedStatement preparedStatement = this.CONNECTION.prepareStatement(AD_GET_ALL);
            return getAdsFromStatement(preparedStatement);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<Ad> getAdsByAuthorId(int authorId) {
        try {
            PreparedStatement preparedStatement = this.CONNECTION.prepareStatement(AD_GET_BY_AUTHOR_ID);
            preparedStatement.setInt(1, authorId);
            return getAdsFromStatement(preparedStatement);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<Ad> getAdsByFilter(String heading, String category, int minPrice, int maxPrice, String cityName, String regionName, String orderType) {
        try {
            String headingSubStatement = heading.equals("") ? "%%" : "%" + heading + "%";
            String statement = "SELECT * FROM ad WHERE heading LIKE "
                    + "'" + headingSubStatement + "' AND price >= "
                    + (minPrice == -1 ? 0 : minPrice) + " AND price <= "
                    + (maxPrice == -1 ? 999999 : maxPrice) + " AND ("
                    + (category.equals("Any Category") ? "category = category " :
                    (category.startsWith("All in") ? getCategorySubStatement(category) : ("category = '" + category + "' ")));

            if(cityName != null) {
                statement += ") AND authorId IN (SELECT id FROM users WHERE cityId LIKE " +
                        CityHandler.getCityId(cityName, regionName) +
                        ") ORDER BY ";
            } else if (regionName != null) {
                statement += ") AND authorId IN (SELECT id FROM users WHERE cityId IN (SELECT id FROM cities WHERE region LIKE " +
                        "\"" + regionName + "\"" +
                        ")) ORDER BY ";
            } else {
                statement += ") ORDER BY ";
            }

            switch (orderType != null ? orderType : "") {
                case "Cheapest" -> statement += "price";
                case "Dearest" -> statement += "price DESC";
                case "Newest" -> statement += "createdAt DESC";
                case "Recommended" -> statement += "viewsCount DESC";
                default -> statement += "null";
            }

            PreparedStatement preparedStatement = this.CONNECTION.prepareStatement(statement);
            return getAdsFromStatement(preparedStatement);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    static void constructUpdateStatement(PreparedStatement preparedStatement, Ad ad) throws SQLException {
        preparedStatement.setString(1, ad.getHeading());
        preparedStatement.setString(2, ad.getDescription());
        preparedStatement.setString(3, ad.getCategory());
        preparedStatement.setDouble(4, ad.getPrice());
        preparedStatement.setInt(5, ad.getLikesCount());
        preparedStatement.setInt(6, ad.getViewsCount());
        preparedStatement.setInt(7, ad.getTelephoneAndEmailCheckCount());
        preparedStatement.setInt(8, ad.getId());
    }

    private String getCategorySubStatement(String category) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] categoryArray = CategoryChangerController.getArrayOfCategory(category);
        for (int i = 0; i < categoryArray.length; i++) {
            if(i != 0) {
                stringBuilder.append("OR category = '").append(categoryArray[i]).append("' ");
            } else {
                stringBuilder.append("category = '").append(categoryArray[i]).append("' ");
            }
        }
        return stringBuilder.toString();
    }

    private List<Ad> getAdsFromStatement(PreparedStatement preparedStatement) throws SQLException {
        List<Ad> list = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            Ad ad = new Ad(
                    resultSet.getInt("id"),
                    resultSet.getInt("authorId"),
                    resultSet.getString("heading"),
                    resultSet.getString("description"),
                    resultSet.getString("category"),
                    resultSet.getInt("price"),
                    resultSet.getInt("likesCount"),
                    resultSet.getInt("viewsCount"),
                    resultSet.getInt("telephoneAndEmailCheckCount"),
                    new Image(resultSet.getBinaryStream("image")),
                    resultSet.getDate("createdAt")
            );
            list.add(ad);
        }
        return list;
    }

    static void constructAddStatement(PreparedStatement preparedStatement, Ad ad, int userId) throws FileNotFoundException, SQLException {
        FileInputStream fin = new FileInputStream(ad.getImageFile());
        preparedStatement.setInt(1, userId);
        preparedStatement.setString(2, ad.getHeading());
        preparedStatement.setString(3, ad.getDescription());
        preparedStatement.setString(4, ad.getCategory());
        preparedStatement.setDouble(5, ad.getPrice());
        preparedStatement.setBinaryStream(6, fin, ad.getImageFile().length());
    }
}
