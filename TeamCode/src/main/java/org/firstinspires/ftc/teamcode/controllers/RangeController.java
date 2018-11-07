package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Created by Derek on 11/7/2017.
 */

public class RangeController extends HardwareController {
    private ModernRoboticsI2cRangeSensor range;
    private boolean report;

    public RangeController(ModernRoboticsI2cRangeSensor range, boolean report) {
        this.range = range;
        this.report = report;
    }

    @Override
    public void loop() {
        if(report) {
            Robot.telemetry.addData("range (cm)", getRangeCm());
        }
    }

    public double getRangeCm() {
        double r  = range.getDistance(DistanceUnit.CM);
        RobotBase.log("range (cm) = "+r);
        return r;
    }

    public double getRangeIn() {
        double r  = range.getDistance(DistanceUnit.INCH);
        RobotBase.log("range (in) = "+r);
        return r;
    }
}
