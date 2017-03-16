package org.firstinspires.ftc.teamcode;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Derek Williams of team 3965 on 10/9/2016.
 */

public abstract class RobotBase extends OpMode {
    public static final String RED = "red";
    public static final String BLUE = "blue";
    public static final String NO_COLOR = "NONE";


    private DcMotor leftDriveMotor;
    private DcMotor rightDriveMotor;

    @Override
    public void init() {
        telemetry.addLine("Basic Hardware Initialized");
        leftDriveMotor = hardwareMap.dcMotor.get("left_drive");
        rightDriveMotor = hardwareMap.dcMotor.get("right_drive");
        leftDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void stop() {
        stopDriveMotors();
    }

    public void setDrivePower(double power){
        leftDriveMotor.setPower(power);
        rightDriveMotor.setPower(-power);
    }

    public void stopDriveMotors(){
        setDrivePower(0.0);
    }

    public void setLeftDrivePower(double power){
        DbgLog.msg(String.format("Enabling Left Motor w/ power: %f", power));
        telemetry.addLine(String.format("Enabling Left Motor w/ power: %f", power));
        leftDriveMotor.setPower(power);
    }

    public void setRightDrivePower(double power){
        DbgLog.msg(String.format("Enabling Right Motor w/ power: %f", power));
        telemetry.addLine(String.format("Enabling Right Motor w/ power: %f", power));
        rightDriveMotor.setPower(-power);
    }

    public void setSquareLeftDrivePower(double power) {
        setLeftDrivePower(power*Math.abs(power));
    }

    public void setSquareRightDrivePower(double power) {
        setRightDrivePower(power*Math.abs(power));
    }

    public double getDrivePosition() {
        return Math.max(leftDriveMotor.getCurrentPosition(), rightDriveMotor.getCurrentPosition());
    }

    public double countsToCentimeters(double counts) {
        return 0.0;
    }

    protected double getDriveMotorDiameter() {
        return 0.0;
    }

    protected double getFullRotation() {
        return 0.0;
    }

    public double getTurnCircumference(){
        return Math.PI*getDriveMotorDiameter();
    }

    public SensorModule getSensorModule() {
        throw new RuntimeException("Sensor Module does not Exist");
    }
}
