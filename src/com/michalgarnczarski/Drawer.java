package com.michalgarnczarski;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Drawer {

    private int sashHeight;
    private int handleLocation;
    private int lowerFixingLocation;
    private int upperFixingLocation;
    private Pull pull;
    private Lock lock;

    public Drawer(int sashHeight, int handleLocation, int lowerFixingLocation, int upperFixingLocation, Pull pull, Lock lock) {
        this.sashHeight = sashHeight;
        this.handleLocation = handleLocation;
        this.lowerFixingLocation = lowerFixingLocation;
        this.upperFixingLocation = upperFixingLocation;
        this.pull = pull;
        this.lock = lock;
    }

    public Group createSashDrawing(double scale) {

        Group drawingGroup = new Group();

        double scaledSashHeight = scale * this.sashHeight;
        double scaledHandleLocation = scale * this.handleLocation;
        double scaledLowerFixingLocation = scale * this.lowerFixingLocation;
        double scaledUpperFixingLocation = scale * this.upperFixingLocation;
        double scaledPullLength = scale * this.pull.getLength();
        double scaledFixingsSpacing = scale * this.pull.getFixingsSpacing();

        Rectangle sash = new Rectangle(0,0,scale * 900, scaledSashHeight);
        //sash.setFill(Color.TRANSPARENT);
        //sash.setStroke(Color.BLACK);
        sash.setFill(Color.GRAY);
        drawingGroup.getChildren().add(sash);

        Rectangle glass = new Rectangle(scale * 120,scale * 120,scale * (900 - 240), scaledSashHeight - scale * 240);
        glass.setFill(Color.WHEAT);
        drawingGroup.getChildren().add(glass);

        int cassettesNumber = this.lock.getCassettes().length;
        Rectangle[] lockCassettes = new Rectangle[cassettesNumber];

        for (int i = 0; i < cassettesNumber; i++) {
            lockCassettes[i] = new Rectangle(0, (scaledSashHeight - scaledHandleLocation - scale * this.lock.getCassettes()[i].getOffset()), scale * 80, scale * this.lock.getCassettes()[i].getWidth());
            lockCassettes[i].setFill(Color.RED);
            drawingGroup.getChildren().add(lockCassettes[i]);
        }

        Circle lowerFixing = new Circle(scale * 40, scaledSashHeight - scaledLowerFixingLocation, scale * 20);
        Circle upperFixing = new Circle(scale * 40, scaledSashHeight - scaledUpperFixingLocation, scale * 20);
        lowerFixing.setFill(Color.BLUE);
        upperFixing.setFill(Color.BLUE);

        drawingGroup.getChildren().add(lowerFixing);
        drawingGroup.getChildren().add(upperFixing);

        return drawingGroup;
    }
}
