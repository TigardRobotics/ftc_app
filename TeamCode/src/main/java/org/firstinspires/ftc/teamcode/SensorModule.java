package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams of 3965 on 10/23/2016.
 */

public abstract class SensorModule {
    public abstract void init();

    public double getRangeSensor() {
        return 0.0;
    }

    public double getOdsRange() {
        return 0.0;
    }

    public double getUsRange() {
        return 0.0;
    }

    public double getRangeCm() {
        return 0.0;
    }

    public double getFrontRed() {
        return 0.0;
    }

    public double getFrontGreen() {
        return 0.0;
    }

    public double getFrontBlue() {
        return 0.0;
    }

    public String getFrontColor() { return "NONE"; }

    public double getLineDetectorLightLevel() {
        return 0.0;
    }

    public boolean isLineDetected() {
        return false;
    }
}
