package com.serviceLib.common;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Validator {
    public static final String USERNAME_PATTERN = "[\\w|\\d]{8,16}";
    public static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$";
    public static final String PHONENUMBER_PATTERN = "((380|0)(\\d){9})";
    public static final String EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String FIRSTNAME_PATTERN = "[A-Z][a-z]*";
    public static final String LASTNAME_PATTERN = "[A-Z][a-z]*( |'|-)*[a-zA-Z]+";
    public static final String PRICE_PATTERN = "0(\\d){0,5}";
    public static final String HEADING_PATTERN = ".{10,58}";
    public static final String DESCRIPTION_PATTERN = ".{30,255}";

    public static boolean validateTextField(TextField textField, Line line, String pattern) {
        if(textField.getText().matches(pattern)) {
            textField.setStyle("-fx-text-fill: black; -fx-border-color: white; -fx-background-color: white;");
            line.setStroke(Color.valueOf("#0078FF"));
            return true;
        } else {
            textField.setStyle("-fx-text-fill: red; -fx-border-color: white; -fx-background-color: white;");
            line.setStroke(Color.RED);
            return false;
        }
    }

    public static boolean validationComboBox(ComboBox<String> comboBox) {
        if(comboBox.getValue() == null) {
            comboBox.setStyle("-fx-border-color: red; -fx-background-color: white;");
            return false;
        } else {
            comboBox.setStyle("-fx-border-color: #0078FF; -fx-background-color: white;");
            return true;
        }
    }

    public static boolean validationDatePicker(DatePicker datePicker, Line line) {
        if(datePicker.getValue() == null) {
            line.setStroke(Color.RED);
            return false;
        } else {
            line.setStroke(Color.valueOf("#0078FF"));
            return true;
        }
    }

    public static void setDefault(TextField textField, Line line) {
        textField.setStyle("-fx-text-fill: black; -fx-border-color: white; -fx-background-color: white;");
        line.setStroke(Color.valueOf("#0078FF"));
    }

}
