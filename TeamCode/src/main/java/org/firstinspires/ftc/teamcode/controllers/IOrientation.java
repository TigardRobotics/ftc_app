package org.firstinspires.ftc.teamcode.controllers;

/**
 * Abstract Orientation Interface (like for a Gyro)
 */

public interface IOrientation {

    /**
     * Reset orientation
     * @param bearing direction we are now facing (0-360 deg)
     */
    void setBearing( double bearing );

    /**
     * Get rotation angle
     * @return absolute bearing in degrees (0-360 deg)
     */
    double getBearing();
}
