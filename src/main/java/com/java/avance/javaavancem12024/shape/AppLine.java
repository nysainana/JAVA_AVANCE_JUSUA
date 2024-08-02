package com.java.avance.javaavancem12024.shape;

import com.java.avance.javaavancem12024.Utility;

public class AppLine extends AppBaseLine {

    private AppLine before, after;

    public AppLine(double sx, double sy, double ex, double ey) {
        super(sx, sy, ex, ey);

        this.shape.startXProperty().addListener((observableValue, oldValue, newValue) -> { if(this.before != null) this.before.shape.setEndX(newValue.doubleValue()); });
        this.shape.startYProperty().addListener((observableValue, oldValue, newValue) -> { if(this.before != null) this.before.shape.setEndY(newValue.doubleValue()); });
        this.shape.endXProperty().addListener((observableValue, oldValue, newValue) -> { if(this.after != null) this.after.shape.setStartX(newValue.doubleValue()); });
        this.shape.endYProperty().addListener((observableValue, oldValue, newValue) -> { if(this.after != null) this.after.shape.setStartY(newValue.doubleValue()); });
    }

    @Override
    protected double getAngle() {
        double Ax = before != null ? this.before.shape.getStartX() : this.shape.getStartX();
        double Ay = before != null ? this.before.shape.getStartY() : this.shape.getStartY() + 100;
        double Bx = this.shape.getStartX();
        double By = this.shape.getStartY();
        double Cx = this.shape.getEndX();
        double Cy = this.shape.getEndY();
        return Utility.calculateAngleABCAndNorm(Ax, Ay, Bx, By, Cx, Cy)[2];
    }

    @Override
    public void onRemove() {
        if(before != null) before.setAfter(null);
        if(after != null) after.setBefore(null);
    }

    public AppLine getBefore() {
        return before;
    }

    public void setBefore(AppLine before) {
        this.before = before;
    }

    public AppLine getAfter() {
        return after;
    }

    public void setAfter(AppLine after) {
        this.after = after;
    }

}
