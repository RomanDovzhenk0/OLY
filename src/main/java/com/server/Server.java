package com.server;

import com.server.dbHandler.DBHandler;
import com.serviceLib.services.ServerService;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.webserver.WebServer;
import java.io.IOException;
import java.sql.SQLException;

public class Server {
    public static void main(String[] args) {

        WebServer webServer = new WebServer(80);

        try {

            DBHandler dbHandler = new DBHandler();

            ServerService serverService = new ServerService(dbHandler);

            ServerAdapter.serverService = serverService;

            PropertyHandlerMapping propertyHandlerMapping = new PropertyHandlerMapping();

            try {

                propertyHandlerMapping.addHandler("ServerService", ServerAdapter.class);

            } catch (XmlRpcException e) {
                System.out.println("XmlRpcException.\n" + e.getMessage());
                e.printStackTrace();
            }

            webServer.getXmlRpcServer().setHandlerMapping(propertyHandlerMapping);

            webServer.start();

        } catch (IOException e) {

            System.out.println("Can't start server at port: " + webServer.getPort());

        } catch (ClassNotFoundException e) {

            System.out.println("Can't find com.mysql.cj.jdbc.Driver.\n" + e.getMessage());
            e.printStackTrace();

        } catch (SQLException e) {

            System.out.println("SQLException.\n" + e.getMessage());
            System.out.println(e.getMessage());
            e.printStackTrace();

        }
    }
}
