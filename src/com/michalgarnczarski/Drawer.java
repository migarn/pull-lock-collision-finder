package com.michalgarnczarski;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Drawer {

    private int sashHeight;
    private int handleLocation;
    private int lowerFixingLocation;
    private int upperFixingLocation;
    private Pull pull;
    private Lock lock;

    public Drawer(int sashHeight, int handleLocation, int lowerFixingLocation, int upperFixingLocation, Pull pull,
                  Lock lock) {
        this.sashHeight = sashHeight;
        this.handleLocation = handleLocation;
        this.lowerFixingLocation = lowerFixingLocation;
        this.upperFixingLocation = upperFixingLocation;
        this.pull = pull;
        this.lock = lock;
    }

    public Group createSashDrawing(double scale) {

        // Method creates drawing of sash with pull and lock envelope. Method returns drawing as group to be added to
        // the pane.

        Group drawingGroup = new Group();

        // All dimension are scaled.

        double scaledSashHeight = scale * this.sashHeight;
        double scaledHandleLocation = scale * this.handleLocation;
        double scaledLowerFixingLocation = scale * this.lowerFixingLocation;
        double scaledUpperFixingLocation = scale * this.upperFixingLocation;
        double scaledPullLength = scale * this.pull.getLength();

        // First shape: sash. Width has been hardcoded as 900mm.

        Rectangle sash = new Rectangle(0,0,scale * 900, scaledSashHeight);
        sash.setFill(Color.GRAY);
        drawingGroup.getChildren().add(sash);

        // Second shape: glass. Profile width has been hardcoded as 120mm.

        Rectangle glass = new Rectangle(scale * 100,scale * 100,scale * (900 - 200),
                scaledSashHeight - scale * 200);
        glass.setFill(Color.AZURE);
        drawingGroup.getChildren().add(glass);

        // Third shape: pull. Pull is combined with three shapes - two fixings and a "main" pull. Pull's thickness
        // has been hardcoded as 40mm.

        double fixingFromEndDistance = (scaledPullLength - (scaledUpperFixingLocation - scaledLowerFixingLocation)) / 2;
        Rectangle mainPull = new Rectangle(scale * 150, scaledSashHeight - scaledUpperFixingLocation - fixingFromEndDistance,
                scale * 40, scaledPullLength);
        mainPull.setFill(Color.SILVER);

        Rectangle lowerFixing = new Rectangle(scale * 20, scaledSashHeight - scaledLowerFixingLocation - scale * 20,
                scale * 150, scale * 40);
        lowerFixing.setFill(Color.SILVER);

        Rectangle upperFixing = new Rectangle(scale * 20, scaledSashHeight - scaledUpperFixingLocation - scale * 20,
                scale * 150, scale * 40);
        upperFixing.setFill(Color.SILVER);

        drawingGroup.getChildren().add(mainPull);
        drawingGroup.getChildren().add(lowerFixing);
        drawingGroup.getChildren().add(upperFixing);

        // Fourth shape: lock. Lock is shown as envelope only. Cassettes width has been hardcoded as 60mm.

        int cassettesNumber = this.lock.getCassettes().length;
        Rectangle[] lockCassettes = new Rectangle[cassettesNumber];

        for (int i = 0; i < cassettesNumber; i++) {
            lockCassettes[i] = new Rectangle(0, (scaledSashHeight - scaledHandleLocation -
                    scale * this.lock.getCassettes()[i].getOffset() - scale * this.lock.getCassettes()[i].getWidth() / 2.0),
                    scale * 60, scale * this.lock.getCassettes()[i].getWidth());

            lockCassettes[i].setFill(Color.TRANSPARENT);
            lockCassettes[i].setStroke(Color.YELLOW);

            drawingGroup.getChildren().add(lockCassettes[i]);
        }

        return drawingGroup;
    }
}
