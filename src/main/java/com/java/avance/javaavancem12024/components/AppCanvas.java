package com.java.avance.javaavancem12024.components;

import com.java.avance.javaavancem12024.shape.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Cursor;
import javafx.scene.layout.*;

public class AppCanvas extends AnchorPane {

    private final ObjectProperty<State> state = new SimpleObjectProperty<>(State.SELECTION);
    private final ObjectProperty<AppShape> selectedShape = new SimpleObjectProperty<>();

    public AppCanvas() {
        super();

        state.addListener((observableValue, oldValue, newValue) -> {
            if(newValue == State.SELECTION) this.setCursor(Cursor.DEFAULT);
            else this.setCursor(Cursor.CROSSHAIR);
        });

        this.setOnMouseClicked(event -> {
            AppShape shape;

            switch (state.get()){
                case READY_LINE:
                case DRAWING_LINE:
                    AppLine line = selectedShape.isNotNull().get() ? (AppLine) selectedShape.get() : null;
                    double x = line != null ? line.getShape().getEndX() : event.getX();
                    double y = line != null ? line.getShape().getEndY() : event.getY();
                    shape = new AppLine(x, y, x, y);

                    if(selectedShape.isNotNull().get() && selectedShape.get() instanceof AppLine) {
                        ((AppLine) selectedShape.getValue()).setAfter((AppLine) shape);
                        ((AppLine) shape).setBefore(((AppLine) selectedShape.getValue()));
                    }

                    state.setValue(State.DRAWING_LINE);
                    break;
                case READY_RECT:
                    shape = new AppRectangle(event.getX(), event.getY());
                    state.setValue(State.DRAWING_RECT);
                    break;
                case DRAWING_RECT:
                    shape = null;
                    state.setValue(State.READY_RECT);
                    break;
                case WINDOW:
                    shape  = new AppWindow(event.getX(), event.getY());
                    state.setValue(State.SELECTION);
                    break;
                case DOOR:
                    shape  = new AppDoor(event.getX(), event.getY());
                    state.setValue(State.SELECTION);
                    break;
                default:
                    shape = null;
            }

            if(shape != null){
                shape.getShape().setOnMouseClicked(evt -> {
                    if(state.get() == State.SELECTION)
                        selectedShape.setValue(shape);
                });

                shape.setCanvas(this);
                selectedShape.setValue(shape);
            }
        });

        this.setOnMouseMoved(event -> {
            if(state.get() == State.DRAWING_LINE && selectedShape.isNotNull().get() && selectedShape.get() instanceof AppLine line) {
                line.moveTooltip();
                line.getShape().setEndX(event.getX());
                line.getShape().setEndY(event.getY());
            }

            if(state.get() == State.DRAWING_RECT && selectedShape.isNotNull().get() && selectedShape.get() instanceof AppRectangle rectangle) {
                rectangle.moveTooltip();
                double w = event.getX() - rectangle.getShape().getX();
                double h = event.getY() - rectangle.getShape().getY();
                rectangle.getShape().setWidth(w < 0 ? 0 : w);
                rectangle.getShape().setHeight(h < 0  ? 0 :h);
            }
        });

        this.selectedShape.addListener((observableValue, oldValue, newValue) -> {
            if(oldValue != null) {
                oldValue.unselect();
                oldValue.removeTooltip();
            }

            if(newValue != null
                    && state.getValue() != State.DRAWING_LINE && state.getValue() != State.READY_LINE
                    && state.getValue() != State.DRAWING_RECT && state.getValue() != State.READY_RECT) newValue.select();
        });

    }

    public ReadOnlyObjectProperty<State> stateProperty() {
        return state;
    }

    public void setState(State state) {
        this.selectedShape.setValue(null);
        this.state.setValue(state);
    }

    public void annuler() {
        if(state.getValue() == State.DRAWING_LINE || state.getValue() == State.DRAWING_RECT) removeSelected();
        if(state.getValue() == State.DRAWING_LINE) state.setValue(State.READY_LINE);
        if(state.getValue() == State.DRAWING_RECT) state.setValue(State.DRAWING_RECT);
    }

    public void unselect() {
        selectedShape.setValue(null);
    }

    public void removeSelected() {
        if(this.selectedShape.isNotNull().get()){
            this.selectedShape.getValue().remove();
            this.selectedShape.setValue(null);
        }
    }

    public void up() { if(selectedShape.isNotNull().get()) selectedShape.get().onUp(); }
    public void down() { if(selectedShape.isNotNull().get()) selectedShape.get().onDown(); }
    public void left() { if(selectedShape.isNotNull().get()) selectedShape.get().onLeft(); }
    public void right() { if(selectedShape.isNotNull().get()) selectedShape.get().onRight(); }

    public enum State {READY_LINE, DRAWING_LINE, SELECTION, WINDOW, DOOR, READY_RECT, DRAWING_RECT}

}
