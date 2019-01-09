package org.firstinspires.ftc.teamcode.controllers;

import android.graphics.Path;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Created by Derek on 11/7/2017.
 */

public class LineDetector extends HardwareController {
    private OpticalDistanceSensor odsLeft;
    private OpticalDistanceSensor odsRight;
    private Side detected;

    public static final double THRESHOLD = 0.2; //! This is just a guess

    public enum Side {
        RIGHT,
        LEFT,
        BOTH,
        NEITHER
    }


    public LineDetector(OpticalDistanceSensor odsLeft, OpticalDistanceSensor odsRight) {
        this.odsLeft = odsLeft;
        this.odsRight = odsRight;
        this.detected = Side.NEITHER;
    }

    @Override
    public void loop() {
        boolean left = odsLeft.getLightDetected() >= THRESHOLD;
        boolean right = odsRight.getLightDetected() >= THRESHOLD;

        if(left && right) {
            detected = Side.BOTH;
        }
        else if(left) {
            detected = Side.LEFT;
        }
        else if(right) {
            detected = Side.RIGHT;
        }
        else {
            detected = Side.NEITHER;
        }
    }

    public Side get() {
        return detected;
    }

}
