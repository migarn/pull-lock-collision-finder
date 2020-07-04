package com.michalgarnczarski;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Cell;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    // FXML fields:

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
    @FXML
    private ComboBox locksComboBox;


    @FXML
    private Pane mypane;

    // Some parameters set as class fields. More? Less?

    private int sashHeight;
    private int pullLength;
    private int fixingsSpacing;
    private Pull pull;
    private PullLocationCalculator pullLocationCalculator;
    private LocksList locksList;

    public void initialize() {

        // Method sets appearance, values and behaviour of controllers.

        this.gridPane.setAlignment(Pos.TOP_LEFT);
        setDefaultFieldsValues();
        setLocksComboBox("locks.dat");
        setPullLengthFieldListener();
        setFixingSpacingFieldListener();
        setFixingLocationFieldsListeners();



//        Path path = new Path();
        Group group = new Group();
//        //MoveTo moveTo = new MoveTo(0, 0);
        Rectangle  rectangle = new Rectangle(0, 0, 100, 140);
//        LineTo line2 = new LineTo(126,232);
//        //path.getElements().add(moveTo);
//        path.getElements().setAll(line1, line2);
        group.getChildren().add(rectangle);
        mypane.getChildren().add(group);

        //this.mycell.setItem(group);

//        this.cell = new StackPane();
//        cell.getChildren().add(line1);
    }

    @FXML
    private void calculate() {

        // do metody kalkulator?

        // TODO

        setParameters();
        int locationMode = this.pullLocationCalculator.getLocationMode();

        String output = "";

        if (Integer.parseInt(this.lowerFixingLocationField.getText()) != this.pullLocationCalculator.getLowerFixingLocation()
                || Integer.parseInt(this.upperFixingLocationField.getText()) != this.pullLocationCalculator.getUpperFixingLocation()) {
            output += "Montaż niestandardowy";
        } else if (locationMode == 1) {
            output += "Montaż standardowy";
        } else if (locationMode == 0) {
            output += "Montaż Symetryczny";
        }

        output += "\nDolna nóżka: " + this.pullLocationCalculator.getLowerFixingLocation() +
                "\nGórna nóżka: " + this.pullLocationCalculator.getUpperFixingLocation();

        String selectedLock = this.locksComboBox.getSelectionModel().getSelectedItem().toString();

        Lock lock = null;

        // zabezpieczyć, żeby nie mogło być dwóch takich samych zamków, zabezpieczyć, żeby nie mogło być pustej listy zamków

        for (Lock lockInList : locksList.getLocks()) {
            if (lockInList.getName().equals(selectedLock)) {
                lock = lockInList;
            }
        }

        output += "\nZamek: " + lock.getName() + ", liczba punktów: " + lock.getCassettes().length;

        this.outputLabel.setText(output);

        // Nie zapomnieć o porówaniu przyjętych punktów mocowań z obliczonymi
    }

    private void onlyNumbers(TextField textField) {

        // Method protects a text field from entering non numerical symbols.

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) return;
            textField.setText(newValue.replaceAll("[^\\d]",""));
        });
    }

    private void setDefaultFieldsValues() {

        // Method sets fields as only numeric and then sets default values for all fields.

        onlyNumbers(this.sashHeightField);
        onlyNumbers(this.handleLocationField);
        onlyNumbers(this.pullLengthField);
        onlyNumbers(this.fixingsSpacingField);
        onlyNumbers(this.lowerFixingLocationField);
        onlyNumbers(this.upperFixingLocationField);

        this.sashHeightField.setText("0");
        this.handleLocationField.setText("1040");
        this.pullLengthField.setText("0");
        this.fixingsSpacingField.setText("0");
        this.lowerFixingLocationField.setText("0");
        this.upperFixingLocationField.setText("0");
    }

    private void setLocksComboBox(String filename) {

        // Method parses locks from file, passes their names into LocksComboBox and selects first lock as default.

        LocksParser locksParser = new LocksParser();
        ArrayList<String> locksNames = new ArrayList<>();

        try {
            this.locksList = locksParser.parseLocksFromFile(filename);

            for (Lock lock : this.locksList.getLocks()) {
                locksNames.add(lock.getName());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.locksComboBox.getItems().addAll(locksNames);
        this.locksComboBox.getSelectionModel().selectFirst();
    }

    private void setParameters() {

        // Method passes values from fields to the parameters and calculates pull fixings location.

        this.sashHeight = Integer.parseInt(this.sashHeightField.getText());
        this.pullLength = Integer.parseInt(this.pullLengthField.getText());
        this.fixingsSpacing = Integer.parseInt(this.fixingsSpacingField.getText());
        this.pull = new Pull(this.pullLength, this.fixingsSpacing);
        this.pullLocationCalculator = new PullLocationCalculator(this.sashHeight, this.pull);
    }

    private void setPullLengthFieldListener() {

        // Changing value of pullLengthField causes change of fixingsSpacingField, lowerFixingLocationField and
        // upperFixingLocationField.

        this.pullLengthField.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue) {

                // Minimal pull length set as 300mm.

                if (Integer.parseInt(this.pullLengthField.getText()) < 300) {
                    this.pullLengthField.setText("300");
                }

                // Protection from entering pull longer than sash height.

                if (Integer.parseInt(this.pullLengthField.getText()) > Integer.parseInt(this.sashHeightField.getText())) {
                    this.pullLengthField.setText(this.sashHeightField.getText());
                }

                // Default fixings spacing is 200mm lower than pull's length.

                this.fixingsSpacingField.setText(String.valueOf(Integer.parseInt(this.pullLengthField.getText()) - 200));

                // Calling method calculating all sash and pull parameters based on the sash entered values.

                setParameters();

                // Setting calculated fixings locations.

                this.lowerFixingLocationField.setText(String.valueOf(this.pullLocationCalculator.getLowerFixingLocation()));
                this.upperFixingLocationField.setText(String.valueOf(this.pullLocationCalculator.getUpperFixingLocation()));
            }
        }));
    }

    private void setFixingSpacingFieldListener() {

        // Changing value of fixingsSpacingField causes change of lowerFixingLocationField and
        // upperFixingLocationField.

        this.fixingsSpacingField.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue) {
                this.upperFixingLocationField.setText(String.valueOf(Integer.parseInt(this.upperFixingLocationField.getText())
                        - (this.fixingsSpacing - Integer.parseInt(this.fixingsSpacingField.getText())) / 2));
                this.lowerFixingLocationField.setText(String.valueOf(Integer.parseInt(this.lowerFixingLocationField.getText())
                        + (this.fixingsSpacing - Integer.parseInt(this.fixingsSpacingField.getText())) / 2));
            }
        }));
    }

    private void setFixingLocationFieldsListeners() {

        // Changing value of lowerFixingLocationField causes change of upperFixingLocationField.

        this.lowerFixingLocationField.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue) {
                this.upperFixingLocationField.setText(String.valueOf(Integer.parseInt(this.lowerFixingLocationField.getText())
                        + Integer.parseInt(this.fixingsSpacingField.getText())));
            }
        }));

        // Changing value of upperFixingLocationField causes change of lowerFixingLocationField.

        this.upperFixingLocationField.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue) {
                this.lowerFixingLocationField.setText(String.valueOf(Integer.parseInt(this.upperFixingLocationField.getText())
                        - Integer.parseInt(this.fixingsSpacingField.getText())));
            }
        }));
    }
}
