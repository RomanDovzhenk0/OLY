package com.client;

import com.serviceLib.interfaces.*;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.xmlrpc.XmlRpcException;
import java.io.IOException;
import java.net.MalformedURLException;

public class Main extends Application {

    private Mediator mediator = new Mediator();

    public static void main(String[] args) throws MalformedURLException, XmlRpcException {

        //Create connection to the server (singleton class)
        Service clientAdapter = ClientAdapter.getInstance();

        //launch UI
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        mediator.setStage(mediator.loginController, "login.fxml");
    }
}