package org.firstinspires.ftc.teamcode.controllers;

/**
 * Abstract Driving Interface
 */

public interface IDrive {
    /**
     * Set amount of power to Drive
     * @param power -1=full reverse, +1=full forward
     */
    void setDrivePower(double power);

    /**
     * Stop the Drive Motors
     */
    void stopDriveMotors();

    /**
     * Set Drive Direction
     * @param direction (-1 to +1)  -1 = max left steer, 0 = no steer, +1 = max right steer
     */
    void setDriveDirection(double direction);

    /**
     * Set amount of power to Spin with
     * @param power -1=full counterclockwise, +1=full clockwise
     */
    void setRotationPower(double power);

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
