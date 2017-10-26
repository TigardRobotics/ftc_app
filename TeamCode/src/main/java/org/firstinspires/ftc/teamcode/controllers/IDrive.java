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
    void setCountsPerCentimeter(double cpc);
    double PositionToCentimeters(double position);
    double getRotationPosition();
    void setCountsPerDegree(double cpd);
    double RotationToDegrees(double position);
}
