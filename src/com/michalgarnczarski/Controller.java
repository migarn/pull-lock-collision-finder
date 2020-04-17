package com.michalgarnczarski;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;

public class Controller {
    @FXML
    private GridPane gridPane;
    @FXML
    private Label outputLabel;
    @FXML
    private TextField sashHeightField;
    @FXML
    private TextField handleLocationField;
    @FXML
    private TextField pullLengthField;
    @FXML
    private TextField fixingsSpacingField;
    @FXML
    private TextField lowerFixingLocationField;
    @FXML
    private TextField upperFixingLocationField;

    public void initialize() {
        gridPane.setAlignment(Pos.TOP_LEFT);

        sashHeightField.setText("0");
        handleLocationField.setText("1040");
        pullLengthField.setText("0");
        fixingsSpacingField.setText("0");

        onlyNumbers(sashHeightField);
        onlyNumbers(handleLocationField);
        onlyNumbers(pullLengthField);
        onlyNumbers(fixingsSpacingField);
        onlyNumbers(lowerFixingLocationField);
        onlyNumbers(upperFixingLocationField);

        pullLengthField.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (Integer.parseInt(pullLengthField.getText()) < 500) {
                    pullLengthField.setText("500");
                }
                fixingsSpacingField.setText(String.valueOf(Integer.parseInt(pullLengthField.getText()) - 200));
                // tu wywołać metodę obliczania wysokości nóżek
            }
        }));

        lowerFixingLocationField.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue) {
                upperFixingLocationField.setText(String.valueOf(Integer.parseInt(lowerFixingLocationField.getText()) + Integer.parseInt(fixingsSpacingField.getText())));
            }
        }));

        upperFixingLocationField.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue) {
                lowerFixingLocationField.setText(String.valueOf(Integer.parseInt(upperFixingLocationField.getText()) - Integer.parseInt(fixingsSpacingField.getText())));
            }
        }));
    }

    private void onlyNumbers(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) return;
            textField.setText(newValue.replaceAll("[^\\d]",""));
        });
    }

    @FXML
    private void calculate() {
        int sashHeight = Integer.parseInt(sashHeightField.getText());
        int pullLength = Integer.parseInt(pullLengthField.getText());
        int fixingsSpacing = Integer.parseInt(fixingsSpacingField.getText());
        Pull pull = new Pull(pullLength, fixingsSpacing);
        PullLocationCalculator pullLocationCalculator = new PullLocationCalculator(sashHeight, pull);

        int locationMode = pullLocationCalculator.getLocationMode();

        String output = "";

        if (locationMode == 1) {
            output += "Montaż standardowy";
        } else if (locationMode == 0) {
            output += "Montaż Symetryczny";
        }

        output += "\nDolna nóżka: " + pullLocationCalculator.getLowerFixingLocation() +
                "\nGórna nóżka: " + pullLocationCalculator.getUpperFixingLocation();

        outputLabel.setText(output);
    }
}
