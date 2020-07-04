package com.michalgarnczarski;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Drawer {

    private int sashHeight;
    private int handleLocation;
    private Pull pull;
    private Lock lock;

    public Drawer(int sashHeight, int handleLocation, Pull pull, Lock lock) {
        this.sashHeight = sashHeight;
        this.handleLocation = handleLocation;
        this.pull = pull;
        this.lock = lock;
    }

    public Group createSashDrawing(double scale) {

        Group drawingGroup = new Group();

        double scaledSashHeight = scale * this.sashHeight;
        double scaledHandleLocation = scale * this.handleLocation;
        double scaledPullLength = scale * this.pull.getLength();
        double scaledFixingsSpacing = scale * this.pull.getFixingsSpacing();

        Rectangle sash = new Rectangle(0,0,scale * 900, scaledSashHeight);
        sash.setFill(Color.TRANSPARENT);
        sash.setStroke(Color.BLACK);

        int cassettesNumber = this.lock.getCassettes().length;
        Rectangle[] lockCassettes = new Rectangle[cassettesNumber];

        for (int i = 0; i < cassettesNumber; i++) {
            lockCassettes[i] = new Rectangle(0, (scaledSashHeight - scaledHandleLocation - scale * this.lock.getCassettes()[i].getOffset()), scale * 80, scale * this.lock.getCassettes()[i].getWidth());
            lockCassettes[i].setFill(Color.TRANSPARENT);
            lockCassettes[i].setStroke(Color.RED);
            drawingGroup.getChildren().add(lockCassettes[i]);
        }

        drawingGroup.getChildren().add(sash);

        return drawingGroup;
    }
}
