package com.client.createAd;

import com.client.ClientAdapter;
import com.client.Mediator;
import com.serviceLib.common.CategoryChangerController;
import com.serviceLib.models.Ad;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import java.io.File;

public class CreateAdController {

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Button exitButton, goToBrowserButton, minimizeButton, selectCategoryButton, selectImageButton, submitButton;

    @FXML
    private Line headingLine, priceLine;

    @FXML
    private ImageView image;

    @FXML
    private ContextMenu categoryContextMenu;

    @FXML
    private TextField headingTextField, priceTextField;

    private File file;

    private static Ad ad;

    public static Mediator mediator;

    private ClientAdapter clientService = ClientAdapter.getInstance();

    public static void setAd(Ad ad) {
        CreateAdController.ad = ad;
    }

    private void initializeFields() {

        priceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() > 6) {
                priceTextField.setText(oldValue);
            } else {
                if (!newValue.matches("0(\\d){0,5}")) {
                    priceTextField.setStyle("-fx-text-fill: black; -fx-border-color: white; -fx-background-color: white;");
                    priceLine.setStroke(Color.valueOf("#0078FF"));
                    priceTextField.setText(newValue.replaceAll("[^\\d]", ""));
                } else {
                    priceTextField.setStyle("-fx-text-fill: red; -fx-border-color: white; -fx-background-color: white;");
                    priceLine.setStroke(Color.RED);
                }
            }
        });

        headingTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() > 58) {
                headingTextField.setText(oldValue);
            } else {
                if (newValue.length() >= 10) {
                    headingTextField.setStyle("-fx-text-fill: black; -fx-border-color: white; -fx-background-color: white;");
                    headingLine.setStroke(Color.valueOf("#0078FF"));
                } else {
                    headingTextField.setStyle("-fx-text-fill: red; -fx-border-color: white; -fx-background-color: white;");
                    headingLine.setStroke(Color.RED);
                }
            }
        });
    }

    private void initializeButtons() {
        Ad ad = CreateAdController.ad;
        selectImageButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            file = fileChooser.showOpenDialog(selectCategoryButton.getScene().getWindow());
            if(file != null) {
                Image newImage = new Image(file.toURI().toString());
                image.setImage(newImage);
                double x = (380 - image.getImage().getWidth() * 250 / image.getImage().getHeight())/2;
                if(x > 0) {
                    image.setX(x);
                } else {
                    image.setX(0);
                }
                image.setFitWidth(380);
            }
        });

        submitButton.setOnAction(event -> {
            if(descriptionTextArea.getText().length() >= 30
                    && headingTextField.getText().length() >= 10
                    && !priceTextField.getText().matches("0(\\d){0,5}")) {
                if(ad != null) {
                    clientService.updateAd(new Ad(
                            ad.getId(),
                            ad.getAuthorId(),
                            headingTextField.getText(),
                            descriptionTextArea.getText(),
                            selectCategoryButton.getText(),
                            Integer.parseInt(priceTextField.getText()),
                            ad.getLikesCount(),
                            ad.getViewsCount(),
                            ad.getTelephoneAndEmailCheckCount(),
                            file,
                            ad.getCreatedAt()
                    ));
                } else {
                    if(file == null || file.length()/1024/1024 >= 16) {
                        file = new File("com/oly/browser/no-product-image.jpeg");
                    }
                    clientService.addAd(new Ad(
                            headingTextField.getText(),
                            descriptionTextArea.getText(),
                            selectCategoryButton.getText(),
                            Integer.parseInt(priceTextField.getText()),
                            file
                    ), mediator.getUser().getId());
                }
                goToBrowserButton.getScene().getWindow().hide();
                mediator.setStage(mediator.browserController, "browser.fxml");
            }
        });

        goToBrowserButton.setOnAction(event -> {
            goToBrowserButton.getScene().getWindow().hide();
            mediator.setStage(mediator.browserController, "browser.fxml");
        });

        minimizeButton.setOnAction(event -> mediator.setIconofied());

        exitButton.setOnAction(event -> System.exit(0));
    }

    private void fillFields() {
        headingTextField.setText(ad.getHeading());
        descriptionTextArea.setText(ad.getDescription());
        selectCategoryButton.setText(ad.getCategory());
        priceTextField.setText("" + ad.getPrice());
        image.setImage(ad.getImage());
    }

    public void initialize() {

        CategoryChangerController.addCategories(selectCategoryButton, categoryContextMenu);

        descriptionTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() > 1500) {
                descriptionTextArea.setText(oldValue);
            } else {
                if (newValue.length() >= 30) {
                    descriptionTextArea.setStyle("-fx-text-fill: black; -fx-border-color: white; -fx-background-color: white;");
                } else {
                    descriptionTextArea.setStyle("-fx-text-fill: red; -fx-border-color: white; -fx-background-color: white;");
                }
            }
        });

        initializeFields();
        initializeButtons();
        if(ad != null) {
            fillFields();
        }
    }
}
