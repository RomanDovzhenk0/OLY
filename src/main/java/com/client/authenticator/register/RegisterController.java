package com.client.authenticator.register;

import com.client.ClientAdapter;
import com.client.Mediator;
import com.serviceLib.common.CityHandler;
import com.serviceLib.common.Validator;
import com.serviceLib.models.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.shape.Line;
import java.sql.Date;

public class RegisterController {

    @FXML
    protected Line birthDateLine, confirmPasswordLine, emailLine, firstnameLine, lastnameLine, passwordLine, phoneNumberLine, usernameLine;

    @FXML
    protected DatePicker birthDatePicker;

    @FXML
    protected ComboBox<String> cityComboBox, regionComboBox;

    @FXML
    protected PasswordField confirmPasswordField, passwordField;

    @FXML
    protected TextField emailField, firstnameField, lastnameField, phoneNumberField, usernameField;

    @FXML
    protected Button exitButton, goToSignInButton, minimizeButton, signUpButton;

    protected RegisterModel registerModel = new RegisterModel(mediator);
    protected RegisterView registerView = new RegisterView(this);
    public static Mediator mediator;
    private ClientAdapter clientService = ClientAdapter.getInstance();

    private boolean fieldsValidation() {

        return Validator.validateTextField(usernameField, usernameLine, Validator.USERNAME_PATTERN)
                & Validator.validateTextField(passwordField, passwordLine, Validator.PASSWORD_PATTERN)
                & Validator.validateTextField(emailField, emailLine, Validator.EMAIL_PATTERN)
                & Validator.validateTextField(phoneNumberField, phoneNumberLine, Validator.PHONENUMBER_PATTERN)
                & Validator.validateTextField(firstnameField, firstnameLine, Validator.FIRSTNAME_PATTERN)
                & Validator.validateTextField(lastnameField, lastnameLine, Validator.LASTNAME_PATTERN)
                & registerModel.setConfirmPassword(confirmPasswordField.getText())
                & Validator.validationDatePicker(birthDatePicker, birthDateLine)
                & Validator.validationComboBox(regionComboBox)
                & Validator.validationComboBox(cityComboBox);
    }

    private void initializeFields() {

        usernameField.textProperty().addListener((observable, oldValue, newValue) ->
                registerModel.setUsername(usernameField.getText()));

        passwordField.textProperty().addListener((observable, oldValue, newValue) ->
                registerModel.setPassword(passwordField.getText()));

        emailField.textProperty().addListener((observable, oldValue, newValue) ->
                registerModel.setEmail(emailField.getText()));

        firstnameField.textProperty().addListener((observable, oldValue, newValue) ->
                registerModel.setFirstname(firstnameField.getText()));

        lastnameField.textProperty().addListener((observable, oldValue, newValue) ->
                registerModel.setLastname(lastnameField.getText()));

        phoneNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                phoneNumberField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            registerModel.setPhoneNumber(phoneNumberField.getText());
        });
    }

    private void initializeButtons() {

        minimizeButton.setOnAction(event -> mediator.setIconofied());

        exitButton.setOnAction(event -> System.exit(0));

        signUpButton.setOnAction(event -> {
            if(fieldsValidation()) {
                if(registerModel.getUser() == null) {
                    clientService.addUser(new User(
                            registerModel.getUsername(),
                            mediator.getHashCode(registerModel.getPassword()),
                            registerModel.getFirstname(),
                            registerModel.getLastname(),
                            registerModel.getEmail(),
                            registerModel.getPhoneNumber().startsWith("38") ? registerModel.getPhoneNumber() : "38" + registerModel.getPhoneNumber(),
                            CityHandler.getCityId(cityComboBox.getValue(), regionComboBox.getValue()),
                            Date.valueOf(birthDatePicker.getValue())
                    ));
                    signUpButton.getScene().getWindow().hide();
                    mediator.setStage(mediator.loginController, "login.fxml");
                }
            }
        });

        goToSignInButton.setOnAction(event -> {
            goToSignInButton.getScene().getWindow().hide();
            mediator.setStage(mediator.loginController, "login.fxml");
        });
    }

    public void initialize() {

        regionComboBox.setItems(CityHandler.REGION_LIST);

        regionComboBox.setOnAction(event -> {
            cityComboBox.setItems(FXCollections.observableArrayList(
                    CityHandler.getCityListByRegion(regionComboBox.getValue()))
            );
            cityComboBox.setDisable(false);
        });

        initializeFields();
        initializeButtons();
    }
}