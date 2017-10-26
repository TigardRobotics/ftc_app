package org.firstinspires.ftc.teamcode.controllers;

/**
 * Created by Katrina on 10/26/2017.
 */

public interface IBlockLift {
    void clamp();
    void release();
    void lift(double power);
    void reset();
}