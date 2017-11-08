package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by Derek on 11/7/2017.
 */

public class RangeController extends HardwareController {
    private ModernRoboticsI2cRangeSensor range;

    public RangeController(ModernRoboticsI2cRangeSensor range) {
        this.range = range;
    }

    public double getRangeCm() {
        return range.getDistance(DistanceUnit.CM);
    }

    public double getRangeIn() {
        return range.getDistance(DistanceUnit.INCH);
    }
}
