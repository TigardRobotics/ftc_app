package org.firstinspires.ftc.teamcode.controllers;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Created by Mark on 10/1/2017.
 */

public class SimDrive extends HardwareController implements IDrive {

    private static final double COUNTS_PER_WHEEL_ROTATION = 1440;         //Tetrix Encoder
    private static final double CM_PER_WHEEL_ROTATION = 4.0*Math.PI*2.54; //4" Tetrix Wheel
    private static final double CM_PER_ROBOT_TURN = 17.0*Math.PI*2.54;    //17" Turn Radius

    private double leftPower = 0;
    private double rightPower = 0;

    private double leftPosition = 0;
    private double rightPosition = 0;

    private ElapsedTime driveTimer;
    private double driveTime = 1;   //1 second timer

    public double cmPerSecond = 30.48;  //1 ft/sec full speed

    public SimDrive() {
        driveTimer = new ElapsedTime();
        driveTimer.reset();
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
        leftPower = power;
    }

    public void setRightDrivePower(double power) {
        rightPower = power;
    }

    public void loop() {
        if (driveTimer.seconds() > driveTime) {
            driveTimer.reset();

            leftPosition += (cmPerSecond*countsPerCentimeter)*leftPower;
            rightPosition += (cmPerSecond*countsPerCentimeter)*rightPower;

            String msg = String.format("Pwr,Pos Left=%f,%f Right=%f,%f ", leftPower,leftPosition,rightPower,rightPosition);
            RobotBase.log(msg);
            Robot.telemetry.addLine(msg);
        }
    }

    public double getDrivePosition() {
        return Math.max(leftPosition, rightPosition); //Max is used to account for slippage
    }

    private double countsPerCentimeter = COUNTS_PER_WHEEL_ROTATION / CM_PER_WHEEL_ROTATION;     //Simple default
    public void setCountsPerCentimeter( double cpc) {countsPerCentimeter=cpc;};
    public double PositionToCentimeters(double counts) { return counts/countsPerCentimeter;};

//!! This probably doesn't work.  Need to find math from last year
    public double getRotationPosition() {
        return leftPosition - rightPosition;
    }

    private double countsPerDegree = countsPerCentimeter * CM_PER_ROBOT_TURN / 360;     //Simple default
    public void setCountsPerDegree( double cpd) {countsPerDegree=cpd;};
    public double RotationToDegrees(double counts) { return counts/countsPerDegree;};

}
