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

    private int sashHeight;
    private int pullLength;
    private int fixingsSpacing;
    private Pull pull;
    private PullLocationCalculator pullLocationCalculator;
    // rozważyć reszę pól

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
                if (Integer.parseInt(pullLengthField.getText()) > Integer.parseInt(sashHeightField.getText())) {
                    sashHeightField.setText(pullLengthField.getText());
                }
                fixingsSpacingField.setText(String.valueOf(Integer.parseInt(pullLengthField.getText()) - 200));
                setParameters();
                lowerFixingLocationField.setText(String.valueOf(this.pullLocationCalculator.getLowerFixingLocation()));
                upperFixingLocationField.setText(String.valueOf(this.pullLocationCalculator.getUpperFixingLocation()));
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
        setParameters();
        int locationMode = this.pullLocationCalculator.getLocationMode();

        String output = "";

        if (locationMode == 1) {
            output += "Montaż standardowy";
        } else if (locationMode == 0) {
            output += "Montaż Symetryczny";
        }

        output += "\nDolna nóżka: " + this.pullLocationCalculator.getLowerFixingLocation() +
                "\nGórna nóżka: " + this.pullLocationCalculator.getUpperFixingLocation();

        outputLabel.setText(output);

        // Nie zapomnieć o porówaniu przyjętych punktów mocowań z obliczonymi
    }

    private void setParameters() {
        this.sashHeight = Integer.parseInt(sashHeightField.getText());
        this.pullLength = Integer.parseInt(pullLengthField.getText());
        this.fixingsSpacing = Integer.parseInt(fixingsSpacingField.getText());
        this.pull = new Pull(pullLength, fixingsSpacing);
        this.pullLocationCalculator = new PullLocationCalculator(sashHeight, pull);
    }
}
