package com.java.avance.javaavancem12024.shape;

import com.java.avance.javaavancem12024.Utility;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class AppRectangle extends AppShape<Rectangle> {

    private double dx, dy;
    private final Circle circleResizer;

    public AppRectangle(double x, double y) {
        super(new Rectangle());
        this.shape.setX(x);
        this.shape.setY(y);
        this.shape.setFill(Color.TRANSPARENT);

        this.circleResizer = createCircle();
        this.circleResizer.centerXProperty().addListener((observableValue, oldValue, newValue) -> this.shape.setWidth(Math.max(newValue.doubleValue() - this.shape.getX(), 0)));
        this.circleResizer.centerYProperty().addListener((observableValue, oldValue, newValue) -> this.shape.setHeight(Math.max(newValue.doubleValue() - this.shape.getY(), 0)));
        this.circleResizer.centerXProperty().addListener((observableValue, oldValue, newValue) -> this.moveTooltip());
        this.circleResizer.centerXProperty().addListener((observableValue, oldValue, newValue) -> this.moveTooltip());
        this.shape.widthProperty().addListener((observableValue, oldValue, newValue) -> this.circleResizer.setCenterX(this.shape.getX() + newValue.doubleValue()));
        this.shape.heightProperty().addListener((observableValue, oldValue, newValue) -> this.circleResizer.setCenterY(this.shape.getY() + newValue.doubleValue()));
        this.shape.xProperty().addListener((observableValue, oldValue, newValue) -> this.circleResizer.setCenterX(newValue.doubleValue() + this.shape.getWidth()));
        this.shape.yProperty().addListener((observableValue, oldValue, newValue) -> this.circleResizer.setCenterY(newValue.doubleValue() + this.shape.getHeight()));

        this.circleResizer.setOnMouseDragged((event) -> {
            this.circleResizer.setCenterX(event.getX());
            this.circleResizer.setCenterY(event.getY());
        });
    }

    @Override
    protected void onRemove() {
        if(this.shape != null) this.canvas.getChildren().remove(this.circleResizer);
    }

    @Override
    public void moveTooltip() {
        this.tooltip.setText(String.format("Longeur: %.1fcm \n Largeur: %.1fcm", Utility.pixelsToCm(this.shape.getWidth()), Utility.pixelsToCm(this.shape.getHeight())));
        this.tooltip.setLayoutX(this.shape.getX() + this.shape.getWidth() + 10);
        this.tooltip.setLayoutY(this.shape.getY() + this.shape.getHeight() + 10);
        if(this.canvas != null && !this.canvas.getChildren().contains(this.tooltip)) this.canvas.getChildren().add(this.tooltip);
    }

    @Override
    public void onLeft() { this.shape.setX(this.shape.getX() - 1); }

    @Override
    public void onRight() { this.shape.setX(this.shape.getX() + 1); }

    @Override
    public void onUp() { this.shape.setY(this.shape.getY() - 1); }

    @Override
    public void onDown() { this.shape.setY(this.shape.getY() + 1); }

    @Override
    public void onSelectChange(boolean oldValue, boolean newValue) {
        if(newValue && this.canvas != null) {
            if(!this.canvas.getChildren().contains(this.circleResizer)) this.canvas.getChildren().addAll(this.circleResizer);
            this.moveTooltip();
        }

        if(!newValue && this.canvas != null) {
            this.canvas.getChildren().removeAll(this.tooltip);
        }
    }

    @Override
    public void onMousePressed(MouseEvent event) {
        if(this.selectedProperty.get()) {
            this.dx = MouseInfo.getPointerInfo().getLocation().x - this.shape.getX();
            this.dy = MouseInfo.getPointerInfo().getLocation().y - this.shape.getY();
        }
    }

    @Override
    public void onMouseDragged(MouseEvent event) {
        if(this.selectedProperty.get()) {
            this.shape.setX(MouseInfo.getPointerInfo().getLocation().x - this.dx);
            this.shape.setY(MouseInfo.getPointerInfo().getLocation().y - this.dy);
        }
    }

    @Override
    public void onMouseReleased(MouseEvent event) {}

}
