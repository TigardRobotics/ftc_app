package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by Derek Williams of team 3965 on 10/23/2016.
 */

public class ModernRoboticsSensorModule extends SensorModule {
    protected RobotBase robot;
    protected ModernRoboticsI2cRangeSensor frontRangeSensor;
    protected ColorSensor frontColorSensor;
    protected OpticalDistanceSensor bottomLineSensor;
    protected ModernRoboticsI2cGyro gyro;
    protected static final double LINE_SENSOR_LIGHT_THRESHOLD = 0.2;

    ModernRoboticsSensorModule(RobotBase robot) {
        this.robot = robot;
    }

    @Override
    public void init() {
        frontRangeSensor = robot.hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "front_range");
        frontColorSensor = robot.hardwareMap.colorSensor.get("front_color");
        bottomLineSensor = robot.hardwareMap.opticalDistanceSensor.get("bottom_ods");
        gyro = (ModernRoboticsI2cGyro) robot.hardwareMap.gyroSensor.get("gyro");
        frontColorSensor.enableLed(false);
        robot.telemetry.addLine("Calibrating Gyro, DO NOT MOVE!");
        gyro.calibrate();   //!! We need to have a way to wait for calibration to complete
        robot.telemetry.addLine("Sensor Module Initialized");
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
        return (frontColorSensor==null)? 0.0: frontColorSensor.blue();
    }

    @Override
    public String getFrontColor() {
        if (getFrontBlue() > getFrontRed()) {
            return RobotBase.BLUE;
        }
        if (getFrontRed() > getFrontBlue()) {
            return RobotBase.RED;
        }
        return "NONE";
    }

    private double LastDetect = 0.0;

    @Override
    public double getLastDetect() {
        return LastDetect;
    }

    @Override
    public double getLineDetectorLightLevel() {
        LastDetect = (bottomLineSensor==null) ? 0.0 : bottomLineSensor.getRawLightDetected();
        return LastDetect;
    }

    @Override
    public boolean isLineDetected() {
        return getLineDetectorLightLevel() > LINE_SENSOR_LIGHT_THRESHOLD;
    }

    @Override
    public int getHeading() {
        return gyro.getHeading();
    }

    @Override
    public int getHeadingError(int targetHeading) {
        return (getHeading()-targetHeading+180)%360-180;
    }
}
