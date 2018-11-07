package org.firstinspires.ftc.teamcode.controllers;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

/**
 * Tank Drive but with limit on accelearation/decelleration (to prevent slipping)
 */

public class AccelDrive extends TankDrive {

    private double targetLeftPower = 0;
    private double targetRightPower = 0;

    private double actualLeftPower = 0;
    private double actualRightPower = 0;

    private ElapsedTime loopTimer;

    public double accel;    //Motor accel/time  1=0 to full power in 1 sec

    public AccelDrive(DcMotor right, DcMotor left, double accel) {
        super(right, left);
        targetLeftPower = 0;
        targetRightPower = 0;
        this.accel = accel;
        loopTimer = new ElapsedTime();
    }

    @Override
    public void setLeftDrivePower(double power) {
        targetLeftPower = power;
    }

    @Override
    public void setRightDrivePower(double power) {
        targetRightPower = power;
    }

    public void start() {
        loopTimer.reset();
    }

    public void loop() {
        double loopTime = loopTimer.seconds();
        double power_change = accel*loopTime;    //power change for this loop
        loopTimer.reset();

        //Adjust Left power based on the accel
        if(Math.abs(targetLeftPower-actualLeftPower) <= power_change) {
            actualLeftPower = targetLeftPower;
        }
        else if (actualLeftPower < targetLeftPower) {
            actualLeftPower = (actualLeftPower + power_change);
        }
        else {
            actualLeftPower = (actualLeftPower - power_change);
        }
        super.setLeftDrivePower(actualLeftPower);

        //Adjust Right power based on the accel
        if(Math.abs(targetRightPower-actualRightPower) <= power_change) {
            actualRightPower = targetRightPower;
        }
        else if (actualRightPower < targetRightPower) {
            actualRightPower = (actualRightPower + power_change);
        }
        else {
            actualRightPower = (actualRightPower - power_change);
        }
        super.setRightDrivePower(actualRightPower);

        super.loop();
        Robot.telemetry.addLine(String.format("Left: %f of %f Right: %f of %f", actualLeftPower, targetLeftPower, actualRightPower, targetRightPower));
    }
}
