package org.firstinspires.ftc.teamcode;

/**
 * Created by Robotics on 10/11/2016.
 */

public abstract class VelocityVortexRobotBase extends RobotBase {
    protected static final double DriveMotorDiameter = 35.56; // Centimeters
    protected static final double fullMotorRotation = 7660;

    // All hardware custom to velocity vortex defined here

    @Override
    public double getDriveMotorDiameter () {
        return DriveMotorDiameter;
    }
}
