package org.firstinspires.ftc.teamcode.controllers;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.RobotLog;

/**
 * Created by Mark on 10/1/2017.
 */

public class AccelDrive extends TankDrive {

    private double targetLeftPower = 0;
    private double targetRightPower = 0;

    public double Accel = 1;    //Max accel

    public AccelDrive(DcMotor right, DcMotor left) {
        super( right, left);
    }

    public void setLeftDrivePower(double power) {
        targetLeftPower = power;
    }

    public void setRightDrivePower(double power) {
        targetRightPower = power;
    }

    public void loop() {
        //Adjust Left power based on the accel
        if(Math.abs(targetLeftPower-leftDrivePower)<=Accel) {
            super.setLeftDrivePower(targetLeftPower);
        }
        else if (leftDrivePower < targetLeftPower)
        {
            super.setLeftDrivePower(leftDrivePower + Accel);
        }
        else
        {
            super.setLeftDrivePower(leftDrivePower - Accel);
        }

        //Adjust Right power based on the accel
        if(Math.abs(targetRightPower-rightDrivePower)<=Accel) {
            super.setRightDrivePower(targetRightPower);
        }
        else if (rightDrivePower < targetRightPower)
        {
            super.setRightDrivePower(rightDrivePower + Accel);
        }
        else
        {
            super.setRightDrivePower(rightDrivePower - Accel);
        }
        super.loop();
    }
}
