package org.firstinspires.ftc.teamcode.controllers;

/**
 * Created by Derek on 9/18/17.
 */

public interface IDrive {
    void setDrivePower(double power);
    void stopDriveMotors();
    void setRotationPower(double power);
    void setLeftDrivePower(double power);
    void setRightDrivePower(double power);
    double getDrivePosition();
    double PositionToCentimeters( double position );
    double getRotationPosition();
    double RotationToDegrees( double position );
}
