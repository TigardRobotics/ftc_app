package org.firstinspires.ftc.teamcode.controllers;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.RobotLog;

/**
 * Created by Mark on 10/1/2017.
 */

public class TankDrive extends HardwareController implements IDrive{

    private DcMotor leftDriveMotor;
    private DcMotor rightDriveMotor;

    public TankDrive(DcMotor right, DcMotor left)
    {
        leftDriveMotor = left;
        rightDriveMotor = right;
        leftDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setDrivePower(double power){
        leftDriveMotor.setPower(power);
        rightDriveMotor.setPower(power);
    }

    public void stopDriveMotors(){
        setDrivePower(0.0);
    }

    public void setRotationPower(double power){
        leftDriveMotor.setPower(power);
        rightDriveMotor.setPower(-power);
    }
    public void setLeftDrivePower(double power){
        RobotLog.i(String.format("Enabling Left Motor w/ speed: %f", power));
        Robot.telemetry.addLine(String.format("Enabling Left Motor w/ speed: %f", power));
        leftDriveMotor.setPower(power);
    }

    public void setRightDrivePower(double power){
        RobotLog.i(String.format("Enabling Right Motor w/ speed: %f", power));
        Robot.telemetry.addLine(String.format("Enabling Right Motor w/ speed: %f", power));
        rightDriveMotor.setPower(-power);
    }

    public double getDrivePosition() {
        return Math.max(leftDriveMotor.getCurrentPosition(), rightDriveMotor.getCurrentPosition()); //Max is used to account for slippage
    }

    private double countsPerCentimeter;
    public void setCountsPerCentimeter( double cpc) {countsPerCentimeter=cpc;};
    public double PositionToCentimeters(double counts) { return counts/countsPerCentimeter;};

//!! This probably doesn't work.  Need to find math from last year
    public double getRotationPosition() {
        return leftDriveMotor.getCurrentPosition() - rightDriveMotor.getCurrentPosition();
    }

    private double countsPerDegree;
    public void setCountsPerDegree( double cpd) {countsPerDegree=cpd;};
    public double RotationToDegrees(double counts) { return counts/countsPerDegree;};

}
