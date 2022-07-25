package com.client.browser;

import com.serviceLib.models.Ad;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class BrowserAdController {

    @FXML
    private Text createdAt;

    @FXML
    private Text heading;

    @FXML
    private ImageView image;

    @FXML
    private Text price;

    @FXML
    private Text views;

    public void setData(Ad ad) {
        createdAt.setText(ad.getCreatedAt().toString());
        heading.setText(ad.getHeading());
        image.setImage(ad.getImage());
        double x = (200 - image.getImage().getWidth() * 130 / image.getImage().getHeight())/2;
        if(x > 0) {
            image.setX(x);
        }
        price.setText((int)ad.getPrice() + " грн.");
        views.setText(String.valueOf(ad.getViewsCount()));
    }

}
