package com.java.avance.javaavancem12024.shape;

import com.java.avance.javaavancem12024.Utility;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;

import java.awt.*;

public abstract class AppBaseLine extends AppShape<Line>{

    private double x1m, y1m, x2m, y2m;
    private final Circle circleDebut, circleFin;

    public AppBaseLine(double sx, double sy, double ex, double ey) {
        super(new Line(sx, sy, ex, ey));

        this.circleDebut = createCircle();
        this.circleDebut.centerXProperty().bindBidirectional( this.shape.startXProperty());
        this.circleDebut.centerYProperty().bindBidirectional( this.shape.startYProperty());

        this.circleFin = createCircle();
        this.circleFin.centerXProperty().bindBidirectional( this.shape.endXProperty());
        this.circleFin.centerYProperty().bindBidirectional( this.shape.endYProperty());

        this.circleDebut.setOnMouseDragged((event) -> {
            this.circleDebut.setCenterX( event.getX());
            this.circleDebut.setCenterY( event.getY());
        });
        this.circleFin.setOnMouseDragged((event) -> {
            this.circleFin.setCenterX( event.getX());
            this.circleFin.setCenterY( event.getY());
        });

        this.circleDebut.onMousePressedProperty().bind(this.shape.onMousePressedProperty());
        this.circleFin.onMousePressedProperty().bind(this.shape.onMousePressedProperty());
        this.circleDebut.onMouseReleasedProperty().bind(this.shape.onMouseReleasedProperty());
        this.circleFin.onMouseReleasedProperty().bind(this.shape.onMouseReleasedProperty());

        this.shape.startXProperty().addListener((observableValue, oldValue, newValue) -> { if(this.selectedProperty.get()) this.moveTooltip(); });
        this.shape.startYProperty().addListener((observableValue, oldValue, newValue) -> { if(this.selectedProperty.get()) this.moveTooltip(); });
        this.shape.endXProperty().addListener((observableValue, oldValue, newValue) -> { if(this.selectedProperty.get()) this.moveTooltip(); });
        this.shape.endYProperty().addListener((observableValue, oldValue, newValue) -> { if(this.selectedProperty.get()) this.moveTooltip(); });
    }

    @Override
    public void moveTooltip() {
        this.tooltip.setText(String.format("Longeur: %.1fcm \n Angle: %.1f°", Utility.pixelsToCm(getNorm()), getAngle()));
        this.tooltip.setLayoutX(this.shape.getEndX() + 10);
        this.tooltip.setLayoutY(this.shape.getEndY() + 10);
        if(this.canvas != null && !this.canvas.getChildren().contains(this.tooltip)) this.canvas.getChildren().add(this.tooltip);
    }

    @Override
    public void onLeft() {
        this.shape.setStartX(this.shape.getStartX() - 1);
        this.shape.setEndX(this.shape.getEndX() - 1);
    }

    @Override
    public void onRight() {
        this.shape.setStartX(this.shape.getStartX() + 1);
        this.shape.setEndX(this.shape.getEndX() + 1);
    }

    @Override
    public void onUp() {
        this.shape.setStartY(this.shape.getStartY() - 1);
        this.shape.setEndY(this.shape.getEndY() - 1);
    }

    @Override
    public void onDown() {
        this.shape.setStartY(this.shape.getStartY() + 1);
        this.shape.setEndY(this.shape.getEndY() + 1);
    }

    @Override
    public void onSelectChange(boolean oldValue, boolean newValue) {
        if(newValue && this.canvas != null) {
            this.canvas.getChildren().addAll(this.circleDebut, this.circleFin);
            moveTooltip();
        }

        if(!newValue && this.canvas != null) {
            this.canvas.getChildren().removeAll(this.circleDebut, this.circleFin, this.tooltip);
        }
    }

    @Override
    public void onMousePressed(MouseEvent event) {
        if(this.selectedProperty.get()) {
            double x = MouseInfo.getPointerInfo().getLocation().x;
            double y = MouseInfo.getPointerInfo().getLocation().y;
            this. x1m = x - this.shape.getStartX();
            this.y1m = y - this.shape.getStartY();
            this.x2m = x - this.shape.getEndX();
            this.y2m = y - this.shape.getEndY();
        }
    }

    @Override
    public void onMouseDragged(MouseEvent event) {
        if(this.selectedProperty.get()) {
            double x = MouseInfo.getPointerInfo().getLocation().x;
            double y = MouseInfo.getPointerInfo().getLocation().y;

            double X1 = x - this.x1m;
            double Y1 = y - this.y1m;
            double X2 = x - this.x2m;
            double Y2 = y - this.y2m;

            this.shape.setStartX(X1);
            this.shape.setStartY(Y1);
            this.shape.setEndX(X2);
            this.shape.setEndY(Y2);
        }
    }

    @Override
    public void onMouseReleased(MouseEvent event) {}

    protected double getAngle() {
        return Utility.calculateAngleABCAndNorm(this.shape.getStartX(), this.shape.getStartY()+100, this.shape.getStartX(), this.shape.getStartY(), this.shape.getEndX(), this.shape.getEndY())[2];
    }

    protected double getNorm() {
        return Utility.calculeNorm(this.shape.getStartX(), this.shape.getStartY(), this.shape.getEndX(), this.shape.getEndY());
    }

}
