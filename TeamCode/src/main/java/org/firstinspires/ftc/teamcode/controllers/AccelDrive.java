package org.firstinspires.ftc.teamcode.controllers;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

/**
 * Created by Mark on 10/1/2017.
 */

public class AccelDrive extends TankDrive {

    private double targetLeftPower = 0;
    private double targetRightPower = 0;
    private ElapsedTime loopTimer;

    public double accel;    //Motor accel/time  1=0 to full power in 1 sec

    public AccelDrive(DcMotor right, DcMotor left, double accel) {
        super( right, left);
        targetLeftPower = 0;
        targetRightPower = 0;
        this.accel = accel;
    }

    public void setLeftDrivePower(double power) {
        targetLeftPower = power;
    }

    public void setRightDrivePower(double power) {
        targetRightPower = power;
    }

    public void start() {
        loopTimer.reset();
    }

    public void loop() {
        double loopTime=loopTimer.seconds();
        double power_change=accel*loopTime;    //power change for this loop
        //Adjust Left power based on the accel
        if(Math.abs(targetLeftPower-leftDrivePower)<=power_change) {
            super.setLeftDrivePower(targetLeftPower);
        }
        else if (leftDrivePower < targetLeftPower)
        {
            super.setLeftDrivePower(leftDrivePower + power_change);
        }
        else
        {
            super.setLeftDrivePower(leftDrivePower - power_change);
        }

        //Adjust Right power based on the accel
        if(Math.abs(targetRightPower-rightDrivePower)<=power_change) {
            super.setRightDrivePower(targetRightPower);
        }
        else if (rightDrivePower < targetRightPower)
        {
            super.setRightDrivePower(rightDrivePower + power_change);
        }
        else
        {
            super.setRightDrivePower(rightDrivePower - power_change);
        }
        super.loop();
    }
}
