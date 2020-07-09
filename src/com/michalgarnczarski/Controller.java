package com.michalgarnczarski;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.Group;

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
    private Pane drawingPane;
    @FXML
    private Label collisionLabel;

    // Some parameters set as class fields. More? Less?

    private int sashHeight;
    private int pullLength;
    private int fixingsSpacing;
    private PullLocationCalculator pullLocationCalculator;
    private LocksList locksList;
    private Lock lock;
    private int handleLocation;

    public void initialize() {

        // Method sets appearance, values and behaviour of controllers.

        this.gridPane.setAlignment(Pos.TOP_LEFT);
        setDefaultFieldsValues();
        setLocksComboBox("locks.dat");
        setPullLengthFieldListener();
        setFixingSpacingFieldListener();
        setFixingLocationFieldsListeners();
    }

    @FXML
    private void calculate() {

        // Parse parameters from FXML fields to pure Java fields.

        setParameters();

        // Clear pane from previous drawing.

        this.drawingPane.getChildren().clear();

        // Calculate location mode for sash.

        int locationMode = this.pullLocationCalculator.getLocationMode();

        // Initialize variable for printing message.

        StringBuilder output = new StringBuilder();

        // Compare calculated fixing locations with inserted fixing locations and print location mode message.

        int lowerFixingLocation = Integer.parseInt(this.lowerFixingLocationField.getText());
        int upperFixingLocation = Integer.parseInt(this.upperFixingLocationField.getText());

        if (lowerFixingLocation != this.pullLocationCalculator.getLowerFixingLocation()
                || upperFixingLocation != this.pullLocationCalculator.getUpperFixingLocation()) {
            int pullOffset = Math.abs(lowerFixingLocation - this.pullLocationCalculator.getLowerFixingLocation());

            output.append("Montaż niestandardowy (pochwyt ");
            output.append((lowerFixingLocation > this.pullLocationCalculator.getLowerFixingLocation()) ? "podwyższony o " : "obniżony o ");
            output.append(pullOffset);
            output.append("mm względem położenia ");
            output.append(locationMode == 1 ? "standardowego)." : "symetrycznego).");

        } else if (locationMode == 1) {
            output.append("Montaż standardowy.");
        } else if (locationMode == 0) {
            output.append("Montaż symetryczny.");
        }

        // Print fixings location message.

        output.append("\nOdległość dolnej nóżki pochwytu od dołu skrzydła: ");
        output.append(lowerFixingLocation);
        output.append("mm.");
        output.append("\nOdległość dolnej nóżki pochwytu od dołu skrzydła: ");
        output.append(upperFixingLocation);
        output.append("mm.");

        // Parse selected lock to Lock object.

        String selectedLock = this.locksComboBox.getSelectionModel().getSelectedItem().toString();

        for (Lock lockInList : this.locksList.getLocks()) {
            if (lockInList.getName().equals(selectedLock)) {
                this.lock = lockInList;
            }
        }

        // Draw sash.

        Drawer drawer = new Drawer(this.sashHeight, this.handleLocation, lowerFixingLocation, upperFixingLocation,
                new Pull(this.pullLength, this.fixingsSpacing), this.lock);
        Group drawingGroup = drawer.createSashDrawing(0.2);
        this.drawingPane.getChildren().add(drawingGroup);

        // Find collision

        CollisionFinder collisionFinder = new CollisionFinder(this.lock, this.handleLocation, lowerFixingLocation,
                upperFixingLocation);
        double[] collision = collisionFinder.findCollision(30);

        // Create message for collision

        String collisionOutput = "";

        if (collision[0] == 0 && collision[1] == 0) {
            collisionOutput = "Brak kolizji zamka z pochwytem.";
        } else if (collision[0] != 0 && collision[1] == 0) {
            collisionOutput = "Kolizja zamka z dolną nóżką pochwytu!\nSpróbuj zmienić wysokość pochwytu lub ";

            if (collision[0] < 0) {
                collisionOutput += "przesunąć pochwyt w dół o " + (int) collision[0] * (-1) + "mm.";
            } else {
                collisionOutput += "przesunąć pochwyt do góry o " + (int) collision[0] + "mm.";
            }

        } else if (collision[0] == 0 && collision[1] != 0) {
            collisionOutput = "Kolizja zamka z górną nóżką pochwytu!\nSpróbuj zmienić wysokość pochwytu lub ";

            if (collision[1] < 0) {
                collisionOutput += "przesunąć pochwyt w dół o " + (int) collision[1] * (-1) + "mm.";
            } else {
                collisionOutput += "przesunąć pochwyt do góry o " + (int) collision[1] + "mm.";
            }

        } else {
            collisionOutput = "Kolizja zamka z dolną i górną nóżką pochwytu!\nSpróbuj zmienić wysokość pochwytu lub ";

            int greaterCollision = Math.abs(collision[0]) > Math.abs(collision[1]) ? (int) collision[0] : (int) collision[1];

            if (greaterCollision < 0) {
                collisionOutput += "przesunąć pochwyt w dół o " + greaterCollision * (-1) + "mm.";
            } else {
                collisionOutput += "przesunąć pochwyt do góry o " + greaterCollision + "mm.";
            }
        }

        // Print messages.

        this.outputLabel.setText(output.toString());
        this.collisionLabel.setText(collisionOutput);
    }

    private void onlyNumbers(TextField textField) {

        // Method protects a text field from entering non numerical symbols.

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) return;
            textField.setText(newValue.replaceAll("[^\\d]", ""));
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

        this.sashHeight = Integer.parseInt(this.sashHeightField.getText()); // posortować
        this.pullLength = Integer.parseInt(this.pullLengthField.getText());
        this.fixingsSpacing = Integer.parseInt(this.fixingsSpacingField.getText());
        this.pullLocationCalculator = new PullLocationCalculator(this.sashHeight, new Pull(this.pullLength, this.fixingsSpacing));
        this.handleLocation = Integer.parseInt(this.handleLocationField.getText());
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
