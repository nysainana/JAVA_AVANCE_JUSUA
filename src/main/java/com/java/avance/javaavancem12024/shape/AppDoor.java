package com.java.avance.javaavancem12024.shape;

import com.java.avance.javaavancem12024.Utility;
import com.java.avance.javaavancem12024.components.AppCanvas;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;

public class AppDoor extends AppBaseLine{

    private final Line lineIn;
    private final Arc arc;

    public AppDoor(double x, double y) {
        super( x, y, x + 30, y);

        this.shape.setStrokeWidth(6);
        lineIn = new Line(this.shape.getStartX(), this.shape.getStartY(), this.shape.getEndX(), this.shape.getEndY());
        lineIn.setStrokeWidth(this.shape.getStrokeWidth()-2);
        lineIn.setStroke(Color.WHITE);
        lineIn.setMouseTransparent(Boolean.TRUE);
        lineIn.startXProperty().bind(this.shape.startXProperty());
        lineIn.startYProperty().bind(this.shape.startYProperty());
        lineIn.endXProperty().bind(this.shape.endXProperty());
        lineIn.endYProperty().bind(this.shape.endYProperty());

        arc = new Arc();
        arc.centerYProperty().bind(this.shape.startYProperty());
        arc.centerXProperty().bind(this.shape.startXProperty());
        arc.radiusYProperty().bind(arc.radiusXProperty());
        arc.strokeProperty().bind(this.shape.strokeProperty());
        arc.setMouseTransparent(Boolean.TRUE);
        arc.setType(ArcType.ROUND);
        arc.setFill(Color.TRANSPARENT);
        arc.setStrokeWidth(1);
        arc.setRadiusX(30);
        arc.setLength(90);

        this.shape.startXProperty().addListener((observableValue, oldValue, newValue) -> actualiseContent());
        this.shape.startYProperty().addListener((observableValue, oldValue, newValue) -> actualiseContent());
        this.shape.endXProperty().addListener((observableValue, oldValue, newValue) -> actualiseContent());
        this.shape.endYProperty().addListener((observableValue, oldValue, newValue) -> actualiseContent());
    }

    private void actualiseContent() {
        double[] data = Utility.calculateAngleABCAndNorm(this.shape.getEndX(), this.shape.getEndY(), this.shape.getStartX(), this.shape.getStartY(), this.shape.getStartX() + 50, this.shape.getStartY());
        arc.setStartAngle(this.shape.getStartY() < this.shape.getEndY() ? 360 - data[2] : data[2]);
        arc.setRadiusX(data[0]);
    }

    @Override
    public void setCanvas(AppCanvas canvas) {
        this.canvas = canvas;
        if(this.canvas != null) {
            this.canvas.getChildren().add(this.shape);
            this.canvas.getChildren().add(arc);
            this.canvas.getChildren().add(lineIn);
        }
    }

    @Override
    public void onRemove() {
        if(this.canvas != null) {
            this.canvas.getChildren().remove(lineIn);
            this.canvas.getChildren().remove(arc);
        }
    }
}
