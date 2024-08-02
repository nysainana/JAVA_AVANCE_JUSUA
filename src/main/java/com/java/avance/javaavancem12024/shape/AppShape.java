package com.java.avance.javaavancem12024.shape;

import com.java.avance.javaavancem12024.components.AppCanvas;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.shape.Shape;


public abstract class AppShape<T extends Shape> {

    protected final BooleanProperty selectedProperty = new SimpleBooleanProperty(Boolean.FALSE);
    protected AppCanvas canvas;
    protected final Label tooltip;
    protected T shape;

    public AppShape(T shape) {
        this.shape = shape;
        this.shape.setStroke(Paint.valueOf("#727272"));
        this.shape.setStrokeWidth(5);
        this.shape.setStrokeType(StrokeType.CENTERED);
        this.shape.setStrokeLineCap(StrokeLineCap.ROUND);
        this.shape.setStrokeLineJoin(StrokeLineJoin.ROUND);

        this.tooltip = new Label();
        this.tooltip.getStyleClass().add("tool-tip-label");
        this.moveTooltip();

        this.shape.setOnMousePressed(event -> {
            if(this.selectedProperty.get()) this.shape.setCursor(Cursor.MOVE);
            this.onMousePressed(event);
        });

        this.shape.setOnMouseDragged(event -> {
            if(this.selectedProperty.get()) this.onMouseDragged(event);
        });

        this.shape.setOnMouseReleased(event -> {
            this.shape.setCursor(Cursor.DEFAULT);
            this.onMouseReleased(event);
        });

        this.selectedProperty.addListener((observableValue, oldValue, newValue) -> {
            this.shape.setStroke(newValue ? javafx.scene.paint.Paint.valueOf("#0070C9") : Paint.valueOf("#727272"));
            this.onSelectChange(oldValue, newValue);
        });
    }

    protected abstract void onRemove();
    public abstract void moveTooltip();
    public abstract void onLeft();
    public abstract void onRight();
    public abstract void onUp();
    public abstract void onDown();
    public abstract void onSelectChange(boolean oldValue, boolean newValue);
    public abstract void onMousePressed(MouseEvent event);
    public abstract void onMouseDragged(MouseEvent event);
    public abstract void onMouseReleased(MouseEvent event);

    public void removeTooltip() { if(canvas != null) canvas.getChildren().remove(tooltip); }
    public void select() { this.selectedProperty.setValue(Boolean.TRUE); }
    public void unselect() { this.selectedProperty.setValue(Boolean.FALSE); }

    public void remove() {
        if(this.canvas != null) {
            this.onRemove();
            this.unselect();
            this.canvas.getChildren().remove(this.shape);
        }
    }

    public void setCanvas(AppCanvas canvas) {
        this.canvas = canvas;
        if(this.canvas != null) this.canvas.getChildren().add(this.shape);
    }

    public T getShape() {
        return shape;
    }

    protected Circle createCircle(){
        Circle circ = new Circle();
        circ.setRadius(3);
        circ.setStrokeWidth(1);
        circ.setFill(Color.WHITE);
        circ.setStroke(Color.valueOf("#666666"));
        circ.setCursor(Cursor.OPEN_HAND);
        circ.getStyleClass().add("circle-move");
        circ.visibleProperty().bind(selectedProperty);
        circ.addEventHandler(MouseEvent.MOUSE_PRESSED, (event) -> circ.setCursor(Cursor.CLOSED_HAND));
        circ.addEventHandler(MouseEvent.MOUSE_RELEASED, (event) -> circ.setCursor(Cursor.OPEN_HAND));
        return circ;
    }

}
