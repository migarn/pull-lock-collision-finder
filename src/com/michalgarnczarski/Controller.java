package com.michalgarnczarski;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;

public class Controller {
    @FXML
    private GridPane gridPane;
    @FXML
    private Label outputLabel;

    public void initialize() {
        gridPane.setAlignment(Pos.TOP_LEFT);

        // wywalić
        outputLabel.setText("Przykładowy wynik");
    }
}
