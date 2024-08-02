package com.java.avance.javaavancem12024;

import javafx.stage.Screen;

public class Utility {

    public static double[] calculateAngleABCAndNorm(double Ax, double Ay, double Bx, double By, double Cx, double Cy) {
        double[] vectorAB = calculeVectAB(Bx, By, Ax, Ay);
        double[] vectorAC = calculeVectAB(Bx, By, Cx, Cy);

        double dotProduct = vectorAB[0] * vectorAC[0] + vectorAB[1] * vectorAC[1];
        double normAB = calculeNorm(vectorAB[0], vectorAB[1]);
        double normAC = calculeNorm(vectorAC[0], vectorAC[1]);

        double cosTheta = dotProduct / (normAB * normAC);
        return new double[]{normAB, normAC, Math.toDegrees(Math.acos(cosTheta))};
    }

    public static double[] calculeVectAB(double Ax, double Ay, double Bx, double By) {
        return new double[]{Ax - Bx, Ay - By};
    }

    public static double calculeNorm(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }

    public static double calculeNorm(double Ax, double Ay, double Bx, double By) {
        double[] vector = calculeVectAB(Ax, Ay, Bx, By);
        return calculeNorm(vector[0], vector[1]);
    }

    public static double pixelsToCm(double pix) {
        double dpi = Screen.getPrimary().getDpi();
        return pix * 2.54 / dpi;
    }

    public static double cmToPixels(double cm) {
        double dpi = Screen.getPrimary().getDpi();
        return (int) (cm * dpi / 2.54);
    }

    public static double getValidAngle(double angle) {
        return (int)(angle / 15) * 15;
    }

}
