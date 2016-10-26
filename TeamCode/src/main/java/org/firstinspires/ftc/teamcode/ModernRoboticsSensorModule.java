package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by Derek Williams of team 3965 on 10/23/2016.
 */

public class ModernRoboticsSensorModule extends SensorModule {
    protected RobotBase robot;
    protected ModernRoboticsI2cRangeSensor frontRangeSensor;

    ModernRoboticsSensorModule(RobotBase robot) {
        this.robot = robot;
    }

    @Override
    public void init() {
        frontRangeSensor = robot.hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "front_range");
    }

    @Override
    public double getOdsRange() {
        return frontRangeSensor.rawOptical();
    }

    @Override
    public double getUsRange() {
        return frontRangeSensor.rawUltrasonic();
    }

    @Override
    public double getRangeCm() {
        return frontRangeSensor.getDistance(DistanceUnit.CM);
    }
}
