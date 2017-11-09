package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by Katrina on 11/8/2017.
 */

public class ColorController extends HardwareController {

    private ModernRoboticsI2cColorSensor color;

    public ColorController(ModernRoboticsI2cColorSensor color) {
        this.color = color;
        this.color.enableLight(true);
    }

    public double getRed() {
        return color.red();
    }

    public double getBlue() {
        return color.blue();
    }

}