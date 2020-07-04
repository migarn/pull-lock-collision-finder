package com.michalgarnczarski;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class Drawer {

    Path path = new Path();
    MoveTo moveTo = new MoveTo(0, 0);
    LineTo line1 = new LineTo(321, 161);
    LineTo line2 = new LineTo(126,232);

    public void draw() {
        path.getElements().add(moveTo);
        path.getElements().setAll(line1, line2);

        //Group root = new Group(path);
    }

}
