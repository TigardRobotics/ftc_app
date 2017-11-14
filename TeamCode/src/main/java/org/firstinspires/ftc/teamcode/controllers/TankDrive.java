package org.firstinspires.ftc.teamcode.controllers;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Created by Mark on 10/1/2017.
 */

public class TankDrive extends HardwareController implements IDrive {

    private DcMotor leftDriveMotor;
    private DcMotor rightDriveMotor;

    protected double leftDrivePower;
    protected double rightDrivePower;

    public TankDrive(DcMotor right, DcMotor left) {
        leftDriveMotor = left;
        rightDriveMotor = right;
        rightDriveMotor.setDirection(DcMotor.Direction.REVERSE);
        leftDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setDrivePower(double power) {
        setLeftDrivePower(power);
        setRightDrivePower(power);
    }

    public void stopDriveMotors(){
        setDrivePower(0.0);
    }

    public void setRotationPower(double power) {
        setLeftDrivePower(power);
        setRightDrivePower(-power);
    }

    public void setLeftDrivePower(double power) {
        leftDrivePower = power;
        RobotBase.log(String.format("Enabling Left Motor w/ speed: %f", leftDrivePower));
        Robot.telemetry.addLine(String.format("Enabling Left Motor w/ speed: %f", leftDrivePower));
        leftDriveMotor.setPower(leftDrivePower);
    }

    public void setRightDrivePower(double power) {
        rightDrivePower = power;
        RobotBase.log(String.format("Enabling Right Motor w/ speed: %f", rightDrivePower));
        Robot.telemetry.addLine(String.format("Enabling Right Motor w/ speed: %f", rightDrivePower));
        rightDriveMotor.setPower(rightDrivePower);
    }

    public double getDrivePosition() {
        return Math.max(leftDriveMotor.getCurrentPosition(), rightDriveMotor.getCurrentPosition()); //Max is used to account for slippage
    }

    public double getRotationPosition() {
        return RotationToDegrees(leftDriveMotor.getCurrentPosition() - rightDriveMotor.getCurrentPosition());
    }

    private double countsPerCentimeter = 45.0;    //estimate based on 4" tetrix wheels
    public double PositionToCentimeters(double counts) { return counts/countsPerCentimeter;};

    private double countsPerDegree = 7660.0 / 180.0; //emperical measurement from BasicAutonomous
    public double RotationToDegrees(double counts) { return counts/countsPerDegree;}
}
