package com.client.authenticator.login;

import com.client.Mediator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.shape.Line;

public class LoginController {

    @FXML
    protected Button minimizeButton, exitButton, logInButton, signUpButton;

    @FXML
    protected PasswordField passwordField;

    @FXML
    protected Line passwordLine, usernameLine;

    @FXML
    protected TextField usernameField;

    protected LoginModel loginModel = new LoginModel(mediator);
    protected LoginView loginView = new LoginView(this);
    public static Mediator mediator;

    public void initialize() {
        initializeButtons();
        initializeFields();
    }

    public void initializeFields() {
        passwordField.textProperty().addListener((observable, oldValue, newValue) ->
                loginModel.setPassword(passwordField.getText()));
        usernameField.textProperty().addListener((observable, oldValue, newValue) ->
                loginModel.setUsername(usernameField.getText()));
    }

    public void initializeButtons() {

        minimizeButton.setOnAction(event -> mediator.setIconofied());

        exitButton.setOnAction(event -> System.exit(0));

        logInButton.setOnAction(event -> {
            authorize();
        });

        signUpButton.setOnAction(event -> {
            signUpButton.getScene().getWindow().hide();
            mediator.setStage(mediator.registerController, "register.fxml");
        });
    }

    private void authorize() {
        if(loginModel.checkPassword()){
            signUpButton.getScene().getWindow().hide();
            mediator.setUser(loginModel.getUser());
            mediator.setStage(mediator.browserController, "browser.fxml");
        }
    }
}