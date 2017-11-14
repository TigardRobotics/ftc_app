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

    /**
     * Get drive distance
     * @return position in cm
     */
    double getDrivePosition();

    /**
     * Get rotation angle
     * @return rotation in degrees (not truncated, clockwise increases)
     */
    double getRotationPosition();
}
