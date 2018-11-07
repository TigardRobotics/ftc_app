package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Color;
import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Created by Katrina on 11/8/2017.
 */

public class ColorController extends HardwareController {

    private ModernRoboticsI2cColorSensor color;

    public ColorController(ColorSensor color) {
        this.color = (ModernRoboticsI2cColorSensor)color;
    }

    @Override
    public void init() {
        super.init();
        RobotBase.instance.telemetry.addLine("Enabling Light Sensor Light");
        RobotBase.log("Enabling Light Sensor Light");
        color.enableLight(true);
    }

    public double getRed() {
        return color.red();
    }

    public double getBlue() {
        return color.blue();
    }


    public Color getColor() {
        if(getRed() > getBlue()) {
            return Color.RED;
        }
        else if(getBlue() > getRed()) {
            return Color.BLUE;
        }
        return Color.NEITHER;
    }
}