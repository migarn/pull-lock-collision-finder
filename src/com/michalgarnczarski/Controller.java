package com.michalgarnczarski;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class Controller {
    @FXML
    private GridPane gridPane;

    public void initialize() {
        gridPane.setAlignment(Pos.TOP_LEFT);
    }
}
