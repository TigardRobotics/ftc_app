package org.firstinspires.ftc.teamcode.controllers;

import org.firstinspires.ftc.teamcode.controllers.HardwareController;

/**
 * Created by Derek on 11/8/2017.
 */

public abstract class DirectionController extends HardwareController {
    /**
     * Used to get direction robot is facing
     * @return Direction robot is facing (0 to <360)
     */
    public abstract double getDirection();
}
