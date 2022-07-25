package com.server.dbHandler.repositories;

public class Query {
    public static final String USER_GET_BY_USERNAME = "SELECT * FROM users WHERE username = ?";
    public static final String USER_GET_BY_ID = "SELECT * FROM users WHERE id = ?";
    public static final String USER_INSERT = "INSERT INTO users(username, password, firstname, lastname, email, " +
            "phoneNumber, cityId, birthDate) VALUES (?,?,?,?,?,?,?,?)";
    public static final String USER_UPDATE = "UPDATE users SET password = ?, firstname = ?, lastname = ?, email = ?, phoneNumber = ?, cityId = ?, birthDate = ? WHERE username = ?";
    public static final String AD_INSERT = "INSERT INTO ad(authorId, heading, description, category, price, image) VALUES (?,?,?,?,?,?)";
    public static final String AD_GET_ALL = "SELECT * FROM ad";
    public static final String AD_DELETE = "DELETE FROM ad WHERE id = ?";
    public static final String AD_UPDATE = "UPDATE ad SET heading = ?, description = ?, category = ?, price = ?, likesCount = ?, viewsCount = ?, telephoneAndEmailCheckCount = ? WHERE id = ?";
    public static final String AD_GET_BY_AUTHOR_ID = "SELECT * FROM ad WHERE authorId = ?";
    public static final String AD_GET_BY_FILTER = "SELECT * FROM ad WHERE heading LIKE ? AND price >= ? AND price <= ? AND ";
}
