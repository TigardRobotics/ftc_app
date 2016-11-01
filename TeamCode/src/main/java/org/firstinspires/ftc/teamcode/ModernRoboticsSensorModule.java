package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.robotcontroller.internal.testcode.TestColorSensors;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by Derek Williams of team 3965 on 10/23/2016.
 */

public class ModernRoboticsSensorModule extends SensorModule {
    protected RobotBase robot;
    protected ModernRoboticsI2cRangeSensor frontRangeSensor;
    protected ColorSensor frontColorSensor;

    ModernRoboticsSensorModule(RobotBase robot) {
        this.robot = robot;
    }

    @Override
    public void init() {
        frontRangeSensor = robot.hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "front_range");
        frontColorSensor = robot.hardwareMap.colorSensor.get("front_color");
        frontColorSensor.enableLed(false);
    }

    public void activateFrontColorSensor(boolean active) {
        frontColorSensor.enableLed(active);
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

    @Override
    public double getFrontRed() {
        return frontColorSensor.red();
    }

    @Override
    public double getFrontGreen() {
        return frontColorSensor.green();
    }

    @Override
    public double getFrontBlue() {
        return frontColorSensor.blue();
    }

}
