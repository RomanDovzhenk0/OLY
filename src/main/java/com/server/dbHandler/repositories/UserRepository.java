package com.server.dbHandler.repositories;

import com.serviceLib.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository extends Query {

    private Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    public User getUser(String username) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(USER_GET_BY_USERNAME);
            preparedStatement.setString(1, username);
            return getUserFromStatement(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUser(int id) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(USER_GET_BY_ID);
            preparedStatement.setInt(1, id);
            return getUserFromStatement(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int addUser(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(USER_INSERT);
            constructAddStatement(preparedStatement, user);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void updateUser(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(USER_UPDATE);
            constructUpdateStatement(preparedStatement, user);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User getUserFromStatement(PreparedStatement preparedStatement) throws SQLException {
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

    static void constructAddStatement(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getFirstname());
        preparedStatement.setString(4, user.getLastname());
        preparedStatement.setString(5, user.getEmail());
        preparedStatement.setString(6, user.getPhoneNumber());
        preparedStatement.setInt(7, user.getCityId());
        preparedStatement.setDate(8, user.getBirthDate());
    }

    static void constructUpdateStatement(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getPassword());
        preparedStatement.setString(2, user.getFirstname());
        preparedStatement.setString(3, user.getLastname());
        preparedStatement.setString(4, user.getEmail());
        preparedStatement.setString(5, user.getPhoneNumber());
        preparedStatement.setInt(6, user.getCityId());
        preparedStatement.setDate(7, user.getBirthDate());
        preparedStatement.setString(8, user.getUsername());
    }
}
