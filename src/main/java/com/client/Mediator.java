package com.client;

import com.client.ad.AdController;
import com.client.authenticator.login.LoginController;
import com.client.authenticator.register.RegisterController;
import com.client.browser.BrowserController;
import com.client.createAd.CreateAdController;
import com.client.myProfile.MyProfileController;
import com.serviceLib.models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Mediator {
    private Stage stage;
    private static Scene scene;
    private User user;

    public LoginController loginController = new LoginController();
    public RegisterController registerController = new RegisterController();
    public BrowserController browserController = new BrowserController();
    public AdController adController = new AdController();
    public CreateAdController createAdController = new CreateAdController();
    public MyProfileController myProfileController = new MyProfileController();

    public Mediator() {
        loginController.mediator = this;
        registerController.mediator = this;
        browserController.mediator = this;
        adController.mediator = this;
        createAdController.mediator = this;
        myProfileController.mediator = this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setIconofied() {
        stage.setIconified(true);
    }

    public void setStage(Object object, String filePath) {
        FXMLLoader fxmlLoader = new FXMLLoader(object.getClass().getResource(filePath));
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        scene.setOnMousePressed(pressEvent -> scene.setOnMouseDragged(dragEvent -> {
            stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
            stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
        }));
        stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("OLY!");
        stage.show();
    }

    public static String getHashCode(String s){
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA3-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        byte[] hash = digest.digest(s.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
