package com.java.avance.javaavancem12024.shape;

import com.java.avance.javaavancem12024.components.AppCanvas;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class AppWindow extends AppBaseLine{

    private final Line lineIn;

    public AppWindow(double x, double y) {
        super( x, y, x, y + 30);

        this.shape.setStrokeWidth(6);
        lineIn = new Line(this.shape.getStartX(), this.shape.getStartY(), this.shape.getEndX(), this.shape.getEndY());
        lineIn.setStrokeWidth(this.shape.getStrokeWidth()-2);
        lineIn.setStroke(Color.WHITE);
        lineIn.setMouseTransparent(Boolean.TRUE);
        lineIn.startXProperty().bind(this.shape.startXProperty());
        lineIn.startYProperty().bind(this.shape.startYProperty());
        lineIn.endXProperty().bind(this.shape.endXProperty());
        lineIn.endYProperty().bind(this.shape.endYProperty());
    }

    @Override
    public void setCanvas(AppCanvas canvas) {
        this.canvas = canvas;
        if(this.canvas != null) {
            this.canvas.getChildren().add(this.shape);
            this.canvas.getChildren().add(lineIn);
        }
    }

    @Override
    public void onRemove() {
        if(this.canvas != null) {
            this.canvas.getChildren().remove(lineIn);
        }
    }

}
