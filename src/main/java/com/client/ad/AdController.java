package com.client.ad;

import com.client.ClientAdapter;
import com.client.Mediator;
import com.client.createAd.CreateAdController;
import com.serviceLib.common.CityHandler;
import com.serviceLib.models.Ad;
import com.serviceLib.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;
import java.util.Optional;

public class AdController {

    @FXML
    public Text categoryField, dateOfRegistrationField, headingField, priceField, regionAndCityField, usernameField, createdAtField, viewsField;

    @FXML
    public TextArea descriptionField;

    @FXML
    public Button exitButton, minimizeButton, showPhoneAndEmailButton, backButton, editButton, deleteButton;

    @FXML
    public ImageView image;

    protected static Ad ad;

    protected User user;

    public static Mediator mediator;

    private ClientAdapter clientService = ClientAdapter.getInstance();

    public static void setAd(Ad ad) {
        AdController.ad = ad;
    }

    public void initializeFields() {
        user = clientService.getUser(ad.getAuthorId());
        usernameField.setText(user.getUsername());
        dateOfRegistrationField.setText(user.getCreatedAt().toString());
        regionAndCityField.setText(CityHandler.getRegion(user.getCityId()) + ", "
                + CityHandler.getCity(user.getCityId()));
        headingField.setText(ad.getHeading());
        priceField.setText((int)ad.getPrice() + " грн.");
        createdAtField.setText(ad.getCreatedAt().toString());
        descriptionField.setText(ad.getDescription());
        viewsField.setText(String.valueOf(ad.getViewsCount()));
        categoryField.setText(ad.getCategory());
    }

    public void initializeButtons() {

        backButton.setOnAction(event -> {
            backButton.getScene().getWindow().hide();
            mediator.setStage(mediator.browserController, "browser.fxml");
        });

        showPhoneAndEmailButton.setOnAction(event -> {
            ad.setTelephoneAndEmailCheckCount(ad.getTelephoneAndEmailCheckCount() + 1);
            clientService.updateAd(ad);
            showPhoneAndEmailButton.setText("+" + user.getPhoneNumber() + "    " + user.getEmail());
        });

        deleteButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setHeaderText("Delete ?");
            alert.setContentText("Are you sure you want to delete this ad?");
            Optional<ButtonType> result = alert.showAndWait();
            if(!result.isPresent() || result.get() != ButtonType.OK) {
                alert.close();
            } else {
                clientService.deleteAd(ad);
                deleteButton.getScene().getWindow().hide();
                mediator.setStage(mediator.browserController, "browser.fxml");
            }
        });

        editButton.setOnAction(event -> {
            CreateAdController.setAd(ad);
            editButton.getScene().getWindow().hide();
            mediator.setStage(mediator.createAdController, "createAd.fxml");
            CreateAdController.setAd(null);
        });

        minimizeButton.setOnAction(event -> mediator.setIconofied());

        exitButton.setOnAction(event -> System.exit(0));
    }

    public void initialize() {
        image.setImage(ad.getImage());
        double x = (580 - image.getImage().getWidth() * 580 / image.getImage().getHeight())/2;
        if(x > 0) {
            image.setX(x);
        }
        double y = (580 - image.getImage().getHeight() * 580 / image.getImage().getWidth())/2;
        if(y > 0) {
            image.setY(y);
        }

        initializeFields();
        initializeButtons();

        if(ad.getAuthorId() == mediator.getUser().getId()) {
            showPhoneAndEmailButton.setText("+" + mediator.getUser().getPhoneNumber() + "    " + mediator.getUser().getEmail());
            showPhoneAndEmailButton.setDisable(true);
            deleteButton.setDisable(false);
            editButton.setDisable(false);
            deleteButton.setVisible(true);
            editButton.setVisible(true);
        }
    }
}

