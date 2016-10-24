package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams of 3965 on 10/23/2016.
 */

public abstract class SensorModule {
    public double getRange() {
        return 0.0;
    }

    public double getOdsRange() {
        return 0.0;
    }

    public double getUsRange() {
        return 0.0;
    }

    public double getFrontColor() {
        return 0.0;
    }

    public double getBottomColor() {
        return 0.0;
    }

    public boolean isLineDetected() {
        return false;
    }
}
