package com.client.browser;

import com.client.ClientAdapter;
import com.client.Mediator;
import com.client.ad.AdController;
import com.serviceLib.common.CategoryChangerController;
import com.serviceLib.common.CityHandler;
import com.serviceLib.models.Ad;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BrowserController {

    @FXML
    protected ContextMenu categoryContextMenu;

    @FXML
    protected Button categorySetButton;

    @FXML
    protected ComboBox<String> cityComboBox, regionComboBox;

    @FXML
    protected TextField maxPriceTextField, minPriceTextField, searchTextField;

    @FXML
    protected Button minimizeButton, myProfileButton, searchButton, submitButton, exitButton;

    @FXML
    protected ChoiceBox<String> sortByChoiceBox;

    @FXML
    protected GridPane gridPane;

    @FXML
    private ScrollPane scrollPane;

    private int minPrice = -1, maxPrice = -1;

    private List<Ad> ads = new ArrayList<>();

    public static Mediator mediator;

    private ClientAdapter clientService = ClientAdapter.getInstance();

    private void showAds() {
        int columns = 0;
        int rows = 0;
        gridPane.getChildren().clear();
        for (Ad ad : ads) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("browserAd.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                BrowserAdController adController = fxmlLoader.getController();
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
                    submitButton.getScene().getWindow().hide();
                    AdController.setAd(ad);
                    ad.setViewsCount(ad.getViewsCount() + 1);
                    clientService.updateAd(ad);
                    mediator.setStage(mediator.adController, "ad.fxml");
                });
                gridPane.add(anchorPane, columns, rows++);
                GridPane.setMargin(anchorPane, new Insets(10, 0, 10, 0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeFields() {

        minPriceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() > 6) {
                minPriceTextField.setText(oldValue);
                minPrice = Integer.parseInt(oldValue);
            } else {
                if (!newValue.matches("0(\\d){0,5}") && minPrice <= maxPrice) {
                    minPriceTextField.setStyle("-fx-text-fill: black; -fx-border-color: white; -fx-background-color: white;");
                    minPriceTextField.setText(newValue.replaceAll("[^\\d]", ""));
                } else {
                    minPriceTextField.setStyle("-fx-text-fill: red; -fx-border-color: white; -fx-background-color: white;");
                }
                try{
                    minPrice = Integer.parseInt(minPriceTextField.getText());
                } catch (Exception ex) { minPrice = -1; }
            }
        });

        maxPriceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() > 6) {
                maxPriceTextField.setText(oldValue);
                maxPrice = Integer.parseInt(oldValue);
            } else {
                if (!newValue.matches("0(\\d){0,5}") && maxPrice >= minPrice) {
                    maxPriceTextField.setStyle("-fx-text-fill: black; -fx-border-color: white; -fx-background-color: white;");
                    maxPriceTextField.setText(newValue.replaceAll("[^\\d]", ""));
                } else {
                    maxPriceTextField.setStyle("-fx-text-fill: red; -fx-border-color: white; -fx-background-color: white;");
                }
                try{
                    maxPrice = Integer.parseInt(maxPriceTextField.getText());
                } catch (Exception ex) { maxPrice = -1; }
            }
        });
    }

    private void initializeButtons() {

        submitButton.setOnAction(event -> {
            submitButton.getScene().getWindow().hide();
            mediator.setStage(mediator.createAdController, "createAd.fxml");
        });

        myProfileButton.setOnAction(event -> {
            myProfileButton.getScene().getWindow().hide();
            mediator.setStage(mediator.myProfileController, "myProfile.fxml");
        });

        searchButton.setOnAction(event -> {
            if(maxPrice >= minPrice) {
                ads.clear();
                ads.addAll(clientService.getAdsByFilter(searchTextField.getText(), categorySetButton.getText(),
                        minPrice, maxPrice, cityComboBox.getValue(), regionComboBox.getValue(),
                        sortByChoiceBox.getValue()));
                showAds();
            }
        });

        minimizeButton.setOnAction(event -> mediator.setIconofied());

        exitButton.setOnAction(event -> System.exit(0));
    }

    public void initialize() {

        ads = clientService.getAds();

        showAds();

        sortByChoiceBox.setItems(FXCollections.observableArrayList("Cheapest", "Dearest", "Newest", "Recommended"));

        CategoryChangerController.addCategories(categorySetButton, categoryContextMenu);

        regionComboBox.setItems(CityHandler.REGION_LIST);

        regionComboBox.setOnAction(event -> {
            cityComboBox.setItems(FXCollections.observableArrayList(
                    CityHandler.getCityListByRegion(regionComboBox.getValue()))
            );
            cityComboBox.setDisable(false);
        });

        initializeButtons();
        initializeFields();

    }
}
