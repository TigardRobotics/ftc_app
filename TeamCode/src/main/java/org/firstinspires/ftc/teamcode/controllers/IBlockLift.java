package org.firstinspires.ftc.teamcode.controllers;

/**
 * Created by Katrina on 10/26/2017.
 */

public interface IBlockLift {
    void acquire();
    void hold();
    void release();
    void lift(double power);
    void reset();
}