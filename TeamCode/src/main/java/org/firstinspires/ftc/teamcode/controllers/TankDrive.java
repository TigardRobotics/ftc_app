package org.firstinspires.ftc.teamcode.controllers;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Drive Controller for a simple 2 motor Tank-style drive
 */

public class TankDrive extends HardwareController implements IDrive {

    private DcMotor leftDriveMotor;
    private DcMotor rightDriveMotor;

    protected double drivePower;
    protected double driveDirection;

    public TankDrive(DcMotor right, DcMotor left) {
        leftDriveMotor = left;
        rightDriveMotor = right;
        rightDriveMotor.setDirection(DcMotor.Direction.REVERSE);
        leftDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void setDrivePower(double power) {
        drivePower = power;
        //Determine wheel powers based in direction so that:
        //Average power = power
        //Direction = 0 => Same power to both wheels
        //Direction = -1 => All power to right wheel (steer left)
        //Direction = +1 => All power to left wheel (steer right)
        double right_power = Range.clip(drivePower*(1-driveDirection), -1.0, 1.0);
        double left_power = Range.clip(drivePower*(1+driveDirection), -1.0, 1.0);
        setLeftDrivePower(left_power);
        setRightDrivePower(right_power);
    }

    @Override
    public void stopDriveMotors(){
        setDrivePower(0.0);
        setDriveDirection(0.0);
    }

    @Override
    public void setRotationPower(double power) {
        setLeftDrivePower(power);
        setRightDrivePower(-power);
    }

    @Override
    public void setDriveDirection(double direction)
    {
        driveDirection = direction;
        setDrivePower(drivePower);  //Set the correct wheel powers for the new direction
    }

    @Override
    public double getDrivePosition() {
        return Math.max(leftDriveMotor.getCurrentPosition(), rightDriveMotor.getCurrentPosition()); //Max is used to account for slippage
    }

    @Override
    public double getRotationPosition() {
        return RotationToDegrees(leftDriveMotor.getCurrentPosition() - rightDriveMotor.getCurrentPosition());
    }

    public void setLeftDrivePower(double leftPower) {
        RobotBase.log(String.format("Enabling Left Motor w/ speed: %f", leftPower));
        Robot.telemetry.addLine(String.format("Enabling Left Motor w/ speed: %f", leftPower));
        leftDriveMotor.setPower(leftPower);
    }

    public void setRightDrivePower(double rightPower) {
        RobotBase.log(String.format("Enabling Right Motor w/ speed: %f", rightPower));
        Robot.telemetry.addLine(String.format("Enabling Right Motor w/ speed: %f", rightPower));
        rightDriveMotor.setPower(rightPower);
    }

    private double countsPerCentimeter = 45.0;    //estimate based on 4" tetrix wheels
    public double PositionToCentimeters(double counts) { return counts/countsPerCentimeter;};

    private double countsPerDegree = 7660.0 / 180.0; //emperical measurement from DriveCalibration
    public double RotationToDegrees(double counts) { return counts/countsPerDegree;}


}
