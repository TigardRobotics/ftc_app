package org.firstinspires.ftc.teamcode.controllers;

/**
 * Created by Katrina on 10/26/2017.
 */

public interface IBlockLift {
    void setBlockControlMode(BlockControlMode mode);
    void lift(double power);
    void reset();

    enum BlockControlMode {
        acquire,
        release,
        hold,
        clockwise,
        counterclockwise
    }
}