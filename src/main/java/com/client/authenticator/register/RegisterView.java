package com.client.authenticator.register;

import com.serviceLib.common.Validator;
import com.serviceLib.interfaces.Observer;
import javafx.scene.paint.Color;

public class RegisterView implements Observer {

    private RegisterController registerController;

    public RegisterView(RegisterController registerController) {
        this.registerController = registerController;
        registerController.registerModel.registerObserver(this);
    }

    @Override
    public void notification(String message) {

        switch (message) {
            case "validateUsername" ->
                    Validator.validateTextField(registerController.usernameField,
                            registerController.usernameLine, Validator.USERNAME_PATTERN);
            case "validatePassword" ->
                    Validator.validateTextField(registerController.passwordField,
                            registerController.passwordLine, Validator.PASSWORD_PATTERN);
            case "validateEmail" ->
                    Validator.validateTextField(registerController.emailField,
                            registerController.emailLine, Validator.EMAIL_PATTERN);
            case "validateFirstname" ->
                    Validator.validateTextField(registerController.firstnameField,
                            registerController.firstnameLine, Validator.FIRSTNAME_PATTERN);
            case "validateLastname" ->
                    Validator.validateTextField(registerController.lastnameField,
                            registerController.lastnameLine, Validator.LASTNAME_PATTERN);
            case "validatePhone" ->
                    Validator.validateTextField(registerController.phoneNumberField,
                            registerController.phoneNumberLine, Validator.PHONENUMBER_PATTERN);
            case "setIncorrectConfirmPasswordStyle" -> {
                registerController.confirmPasswordField.setStyle("-fx-prompt-text-fill: red; -fx-border-color: white; -fx-background-color: white;");
                registerController.confirmPasswordField.setPromptText("Passwords don't match");
                registerController.confirmPasswordLine.setStroke(Color.RED);
            }
            case "setCorrectConfirmPasswordStyle" -> {
                registerController.confirmPasswordField.setStyle("-fx-prompt-text-fill: #0078FF; -fx-border-color: white; -fx-background-color: white;");
                registerController.confirmPasswordLine.setStroke(Color.valueOf("#0078FF"));
            }
            case "setUserAlreadyExistStyle" -> {
                registerController.usernameField.clear();
                registerController.usernameField.setStyle("-fx-prompt-text-fill: red; -fx-border-color: white; -fx-background-color: white;");
                registerController.usernameField.setPromptText("Username is already in use");
                registerController.usernameLine.setStroke(Color.RED);
            }
        }
    }
}
