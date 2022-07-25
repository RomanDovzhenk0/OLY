package com.client.myProfile;

import com.client.ClientAdapter;
import com.client.Mediator;
import com.client.ad.AdController;
import com.serviceLib.common.CityHandler;
import com.serviceLib.common.Validator;
import com.serviceLib.models.Ad;
import com.serviceLib.models.User;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MyProfileController {

    private final ObservableList<String> REGION_LIST = FXCollections.observableArrayList(
            "АВТОНОМНА РЕСПУБЛІКА КРИМ", "ВІННИЦЬКА ОБЛАСТЬ", "ВОЛИНСЬКА ОБЛАСТЬ", "ДНІПРОПЕТРОВСЬКА ОБЛАСТЬ",
            "ДОНЕЦЬКА ОБЛАСТЬ", "ЖИТОМИРСЬКА ОБЛАСТЬ", "ЗАКАРПАТСЬКА ОБЛАСТЬ", "ЗАПОРІЗЬКА ОБЛАСТЬ",
            "ІВАНО-ФРАНКІВСЬКА ОБЛАСТЬ", "КИЇВСЬКА ОБЛАСТЬ", "КІРОВОГРАДСЬКА ОБЛАСТЬ", "ЛУГАНСЬКА ОБЛАСТЬ",
            "ЛЬВІВСЬКА ОБЛАСТЬ", "МИКОЛАЇВСЬКА ОБЛАСТЬ", "ОДЕСЬКА ОБЛАСТЬ", "ПОЛТАВСЬКА ОБЛАСТЬ",
            "РІВНЕНСЬКА ОБЛАСТЬ", "СУМСЬКА ОБЛАСТЬ", "ТЕРНОПІЛЬСЬКА ОБЛАСТЬ", "ХАРКІВСЬКА ОБЛАСТЬ",
            "ХЕРСОНСЬКА ОБЛАСТЬ", "ХМЕЛЬНИЦЬКА ОБЛАСТЬ", "ЧЕРКАСЬКА ОБЛАСТЬ", "ЧЕРНІВЕЦЬКА ОБЛАСТЬ",
            "ЧЕРНІГІВСЬКА ОБЛАСТЬ", "М.СЕВАСТОПОЛЬ"
    );

    @FXML
    private Text birthDateText, cityText, createdAtText, emailText, firstnameText, lastnameText, usernameText, phoneText, username;

    @FXML
    private Button exitButton, goToBrowserButton, minimizeButton, adsButton, changeProfileButton, infoButton, saveChangesButton;

    @FXML
    private Line birthDateLine, confirmPasswordLine, emailLine, firstnameLine, lastnameLine, passwordLine, phoneNumberLine;

    @FXML
    private DatePicker birthDatePicker;

    @FXML
    private Group changeProfileGroup, infoGroup;

    @FXML
    private ComboBox<String> cityComboBox, regionComboBox;

    @FXML
    private PasswordField confirmPasswordField, passwordField;

    @FXML
    private TextField emailField, firstnameField, lastnameField, phoneNumberField;

    @FXML
    private GridPane gridPane;

    @FXML
    private ScrollPane scrollPane;

    private List<Ad> ads = new ArrayList<>();

    public static Mediator mediator;

    private ClientAdapter clientService = ClientAdapter.getInstance();

    private void showInfo() {

        User user = mediator.getUser();

        birthDateText.setText(user.getBirthDate().toString());

        cityText.setText(CityHandler.getCity(user.getCityId()));

        createdAtText.setText(user.getCreatedAt().toString());

        emailText.setText(user.getEmail());

        firstnameText.setText(user.getFirstname());

        lastnameText.setText(user.getLastname());

        username.setText(user.getUsername());

        phoneText.setText("+" + user.getPhoneNumber());
    }

    private void showAds() {

        scrollPane.setVisible(true);
        int columns = 0;
        int rows = 0;
        gridPane.getChildren().clear();
        for (Ad ad : ads) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("profileAd.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ProfileAdController adController = fxmlLoader.getController();
                adController.setData(ad);
                anchorPane.setOnMouseEntered(mouseEvent -> {
                    TranslateTransition translateTransition = new TranslateTransition(Duration.millis(70), anchorPane);
                    translateTransition.setFromX(0);
                    translateTransition.setByX(10);
                    translateTransition.playFromStart();
                });
                anchorPane.setOnMouseExited(mouseEvent -> {
                    TranslateTransition translateTransition = new TranslateTransition(Duration.millis(70), anchorPane);
                    translateTransition.setFromX(10);
                    translateTransition.setByX(-10);
                    translateTransition.playFromStart();
                });
                anchorPane.setOnMouseClicked(mouseEvent -> {
                    gridPane.getScene().getWindow().hide();
                    AdController.setAd(ad);
                    mediator.setStage(mediator.adController, "ad.fxml");
                });
                gridPane.add(anchorPane, columns, rows++);
                GridPane.setMargin(anchorPane, new Insets(10, 0, 10, 0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showChangeProfile() {

        User user = mediator.getUser();

        usernameText.setText(user.getUsername());

        birthDatePicker.setValue(LocalDate.parse(user.getBirthDate().toString()));

        phoneNumberField.setText(user.getPhoneNumber());

        emailField.setText(user.getEmail());

        firstnameField.setText(user.getFirstname());

        lastnameField.setText(user.getLastname());

        regionComboBox.setValue(CityHandler.getRegion(user.getCityId()));

        cityComboBox.setItems(FXCollections.observableArrayList(
                CityHandler.getCityListByRegion(regionComboBox.getValue()))
        );

        cityComboBox.setValue(CityHandler.getCity(user.getCityId()));
    }

    private boolean fieldsValidation() {
        if(!confirmPasswordField.getText().equals(passwordField.getText())) {
            confirmPasswordField.clear();
            confirmPasswordField.setStyle("-fx-prompt-text-fill: red; -fx-border-color: white; -fx-background-color: white;");
            confirmPasswordField.setPromptText("Passwords don't match");
            confirmPasswordLine.setStroke(Color.RED);
        } else {
            confirmPasswordField.setStyle("-fx-prompt-text-fill: #0078FF; -fx-border-color: white; -fx-background-color: white;");
            confirmPasswordLine.setStroke(Color.valueOf("#0078FF"));
        }

        return (passwordField.getText().equals("") || Validator.validateTextField(passwordField, passwordLine, Validator.PASSWORD_PATTERN))
                & Validator.validateTextField(emailField, emailLine, Validator.EMAIL_PATTERN)
                & Validator.validateTextField(phoneNumberField, phoneNumberLine, Validator.PHONENUMBER_PATTERN)
                & Validator.validateTextField(firstnameField, firstnameLine, Validator.FIRSTNAME_PATTERN)
                & Validator.validateTextField(lastnameField, lastnameLine, Validator.LASTNAME_PATTERN)
                & confirmPasswordField.getText().equals(passwordField.getText())
                & Validator.validationDatePicker(birthDatePicker, birthDateLine)
                & Validator.validationComboBox(regionComboBox)
                & Validator.validationComboBox(cityComboBox);
    }

    private void initializeFields() {

        passwordField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            Validator.validateTextField(passwordField, passwordLine, Validator.PASSWORD_PATTERN);
            if(newValue.equals("")) {
                Validator.setDefault(passwordField, passwordLine);
            }
        });

        emailField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            Validator.validateTextField(emailField, emailLine, Validator.EMAIL_PATTERN);
            if(newValue.equals("")) {
                Validator.setDefault(emailField, emailLine);
            }
        });

        firstnameField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            Validator.validateTextField(firstnameField, firstnameLine, Validator.FIRSTNAME_PATTERN);
            if(newValue.equals("")) {
                Validator.setDefault(firstnameField, firstnameLine);
            }
        });

        lastnameField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            Validator.validateTextField(lastnameField, lastnameLine, Validator.LASTNAME_PATTERN);
            if(newValue.equals("")) {
                Validator.setDefault(lastnameField, lastnameLine);
            }
        });

        phoneNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                phoneNumberField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            Validator.validateTextField(phoneNumberField, phoneNumberLine, Validator.PHONENUMBER_PATTERN);
            if(newValue.equals("")) {
                Validator.setDefault(phoneNumberField, phoneNumberLine);
            }
        });
    }

    private void initializeButtons() {

        infoButton.setOnAction(event -> {
            infoGroup.setVisible(true);
            scrollPane.setVisible(false);
            changeProfileGroup.setVisible(false);
            infoButton.setStyle("-fx-text-fill: #0078FF; -fx-background-color: white;");
            adsButton.setStyle("-fx-text-fill: white; -fx-background-color: #0078FF;");
            changeProfileButton.setStyle("-fx-text-fill: white; -fx-background-color: #0078FF;");
        });

        adsButton.setOnAction(event -> {
            infoGroup.setVisible(false);
            scrollPane.setVisible(true);
            changeProfileGroup.setVisible(false);
            infoButton.setStyle("-fx-text-fill: white; -fx-background-color: #0078FF;");
            adsButton.setStyle("-fx-text-fill: #0078FF; -fx-background-color: white;");
            changeProfileButton.setStyle("-fx-text-fill: white; -fx-background-color: #0078FF;");
        });

        changeProfileButton.setOnAction(event -> {
            showChangeProfile();
            infoGroup.setVisible(false);
            scrollPane.setVisible(false);
            changeProfileGroup.setVisible(true);
            infoButton.setStyle("-fx-text-fill: white; -fx-background-color: #0078FF;");
            adsButton.setStyle("-fx-text-fill: white; -fx-background-color: #0078FF;");
            changeProfileButton.setStyle("-fx-text-fill: #0078FF; -fx-background-color: white;");
        });

        goToBrowserButton.setOnAction(event -> {
            goToBrowserButton.getScene().getWindow().hide();
            mediator.setStage(mediator.browserController, "browser.fxml");
        });

        regionComboBox.setOnAction(event -> {
            cityComboBox.setValue("");
            cityComboBox.setItems(FXCollections.observableArrayList(
                    CityHandler.getCityListByRegion(regionComboBox.getValue()))
            );
        });

        saveChangesButton.setOnAction(event -> {
            if(fieldsValidation()) {
                User user = mediator.getUser();
                clientService.updateUser(new User(
                        user.getUsername(),
                        passwordField.getText().equals("") ? user.getPassword() : mediator.getHashCode(passwordField.getText()),
                        firstnameField.getText(),
                        lastnameField.getText(),
                        emailField.getText(),
                        phoneNumberField.getText().startsWith("38") ? phoneNumberField.getText() : "38" + phoneNumberField.getText(),
                        CityHandler.getCityId(cityComboBox.getValue(), regionComboBox.getValue()),
                        Date.valueOf(birthDatePicker.getValue())
                ));
                mediator.setUser(clientService.getUser(user.getUsername()));
                showInfo();
                infoGroup.setVisible(true);
                scrollPane.setVisible(false);
                changeProfileGroup.setVisible(false);
                infoButton.setStyle("-fx-text-fill: #0078FF; -fx-background-color: white;");
                adsButton.setStyle("-fx-text-fill: white; -fx-background-color: #0078FF;");
                changeProfileButton.setStyle("-fx-text-fill: white; -fx-background-color: #0078FF;");
            }
        });

        minimizeButton.setOnAction(event -> mediator.setIconofied());

        exitButton.setOnAction(event -> System.exit(0));
    }

    public void initialize() {

        regionComboBox.setItems(REGION_LIST);
        ads = clientService.getAdsByAuthorId(mediator.getUser().getId());

        showInfo();
        showAds();
        showChangeProfile();

        infoGroup.setVisible(true);
        scrollPane.setVisible(false);
        changeProfileGroup.setVisible(false);

        initializeFields();
        initializeButtons();

    }
}
