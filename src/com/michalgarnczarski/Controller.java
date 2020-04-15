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
    private TextField pullLengthField;
    @FXML
    private TextField fixingsDistanceField;

    public void initialize() {
        gridPane.setAlignment(Pos.TOP_LEFT);
        sashHeightField.setText("0");
        pullLengthField.setText("0");
        fixingsDistanceField.setText("0");
        onlyNumbers(sashHeightField);
        onlyNumbers(pullLengthField);
        onlyNumbers(fixingsDistanceField);

        // wywalić
        outputLabel.setText("Przykładowy wynik");
    }

    private void onlyNumbers(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) return;
            textField.setText(newValue.replaceAll("[^\\d]",""));
        });
    }
}
