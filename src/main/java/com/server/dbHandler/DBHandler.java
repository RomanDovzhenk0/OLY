package com.server.dbHandler;

import com.server.dbHandler.repositories.AdRepository;
import com.server.dbHandler.repositories.UserRepository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHandler extends DBConfig {

    private Connection connection;

    public UserRepository userRepository;

    public AdRepository adRepository;
    

    public DBHandler() throws ClassNotFoundException, SQLException {

        String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");

        this.connection = DriverManager.getConnection(url, dbUser, dbPass);

        userRepository = new UserRepository(this.connection);
        adRepository = new AdRepository(this.connection);
    }
}
