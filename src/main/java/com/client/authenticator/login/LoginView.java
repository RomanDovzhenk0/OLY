package com.client.authenticator.login;

import com.serviceLib.common.Validator;
import com.serviceLib.interfaces.Observer;
import javafx.scene.paint.Color;

public class LoginView implements Observer {

    private LoginController loginController;

    public LoginView(LoginController loginController) {
        this.loginController = loginController;
        loginController.loginModel.registerObserver(this);
    }

    @Override
    public void notification(String message) {
        switch (message) {
            case "validateUsername" ->
                    Validator.validateTextField(
                            loginController.usernameField,
                            loginController.usernameLine,
                            Validator.USERNAME_PATTERN);
            case "validatePassword" ->
                    Validator.validateTextField(
                            loginController.passwordField,
                            loginController.passwordLine,
                            Validator.PASSWORD_PATTERN);
            case "setCorrectUsernameStyle" -> {
                loginController.usernameField.setStyle("-fx-prompt-text-fill: #0078FF; -fx-border-color: white; -fx-background-color: white;");
                loginController.usernameLine.setStroke(Color.valueOf("#0078FF"));
            }
            case "setIncorrectUsernameStyle" -> {
                loginController.usernameField.clear();
                loginController.usernameField.setStyle("-fx-prompt-text-fill: red; -fx-border-color: white; -fx-background-color: white;");
                loginController.usernameField.setPromptText("User doesn't exists");
                loginController.usernameLine.setStroke(Color.RED);
            }
            case "setIncorrectPasswordStyle" -> {
                loginController.passwordField.clear();
                loginController.passwordField.setStyle("-fx-prompt-text-fill: red; -fx-border-color: white; -fx-background-color: white;");
                loginController.passwordField.setPromptText("Incorrect password");
                loginController.passwordLine.setStroke(Color.RED);
            }
        }
    }
}
