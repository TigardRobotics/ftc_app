package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams on 2/15/2017.
 */

public final class Tools {
    public static int sign(int x) {
        return x / Math.abs(x);
    }

    public static double sign(double x) {
        return x / Math.abs(x);
    }

    public static double sqr(double x) {
        return Math.pow(x, 2);
    }

    public static double timesabs(double x) {
        return x*Math.abs(x);
    }
}
