package com.serviceLib.interfaces;

import com.serviceLib.common.CategoryChangerController;
import com.serviceLib.models.Ad;
import com.serviceLib.models.User;
import javafx.scene.image.Image;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public interface Service {

    User getUser(String username);

    User getUser(int id);

    void addUser(User user);

    void updateUser(User user);

    void addAd(Ad ad, int userId);

    void updateAd(Ad ad);

    void deleteAd(Ad ad);

    List<Ad> getAds();

    List<Ad> getAdsByAuthorId(int authorId);

    List<Ad> getAdsByFilter(String heading, String category, int minPrice, int maxPrice, String cityName, String regionName, String orderType);

    static Object deserialize(Object object) {
        try {
            byte [] data = Base64.getDecoder().decode((String) object);
            ObjectInputStream ois = null;
            ois = new ObjectInputStream(
                    new ByteArrayInputStream(data));
            Object o  = ois.readObject();
            ois.close();
            return o;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    static String serialize(Object o) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream( baos );
            oos.writeObject(o);
            oos.close();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    default User getUserFromStatement(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        User user = null;
        while (resultSet.next()) {
            user = new User(
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("firstname"),
                    resultSet.getString("lastname"),
                    resultSet.getString("email"),
                    resultSet.getString("phoneNumber"),
                    resultSet.getInt("cityId"),
                    resultSet.getDate("birthDate"));
            user.setCreatedAt(resultSet.getDate("createdAt"));
            user.setId(resultSet.getInt("id"));
        }
        return user;
    }

    default void constructAddStatement(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getFirstname());
        preparedStatement.setString(4, user.getLastname());
        preparedStatement.setString(5, user.getEmail());
        preparedStatement.setString(6, user.getPhoneNumber());
        preparedStatement.setInt(7, user.getCityId());
        preparedStatement.setDate(8, user.getBirthDate());
    }

    default void constructUpdateStatement(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getPassword());
        preparedStatement.setString(2, user.getFirstname());
        preparedStatement.setString(3, user.getLastname());
        preparedStatement.setString(4, user.getEmail());
        preparedStatement.setString(5, user.getPhoneNumber());
        preparedStatement.setInt(6, user.getCityId());
        preparedStatement.setDate(7, user.getBirthDate());
        preparedStatement.setString(8, user.getUsername());
    }

    default String getCategorySubStatement(String category) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] categoryArray = CategoryChangerController.getArrayOfCategory(category);
        for (int i = 0; i < categoryArray.length; i++) {
            if (i != 0) {
                stringBuilder.append("OR category = '").append(categoryArray[i]).append("' ");
            } else {
                stringBuilder.append("category = '").append(categoryArray[i]).append("' ");
            }
        }
        return stringBuilder.toString();
    }

    default List<Ad> getAdsFromStatement(PreparedStatement preparedStatement) throws SQLException {
        List<Ad> list = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
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

    default void constructUpdateStatement(PreparedStatement preparedStatement, Ad ad) throws SQLException {
        preparedStatement.setString(1, ad.getHeading());
        preparedStatement.setString(2, ad.getDescription());
        preparedStatement.setString(3, ad.getCategory());
        preparedStatement.setDouble(4, ad.getPrice());
        preparedStatement.setInt(5, ad.getLikesCount());
        preparedStatement.setInt(6, ad.getViewsCount());
        preparedStatement.setInt(7, ad.getTelephoneAndEmailCheckCount());
        preparedStatement.setInt(8, ad.getId());
    }

    default void constructAddStatement(PreparedStatement preparedStatement, Ad ad) throws FileNotFoundException, SQLException {
        FileInputStream fin = new FileInputStream(ad.getImageFile());
        preparedStatement.setInt(1, ad.getAuthorId());
        preparedStatement.setString(2, ad.getHeading());
        preparedStatement.setString(3, ad.getDescription());
        preparedStatement.setString(4, ad.getCategory());
        preparedStatement.setDouble(5, ad.getPrice());
        preparedStatement.setBinaryStream(6, fin, ad.getImageFile().length());
    }
}
