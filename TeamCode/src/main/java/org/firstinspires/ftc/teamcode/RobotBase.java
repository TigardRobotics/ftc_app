package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Derek Williams of team 3965 on 10/9/2016.
 */

public abstract class RobotBase extends OpMode {
    private DcMotor leftMotor = null;
    private DcMotor rightMotor = null;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        leftMotor = hardwareMap.dcMotor.get("motor_l");
        rightMotor = hardwareMap.dcMotor.get("motor_r");
    }

    public void setDrivePower(double power){
        leftMotor.setPower(power);
        rightMotor.setPower(power);
    }

    public void stopDriveMotors(){
        setDrivePower(0);
    }

    public void setLeftDrivePower(double power){
        leftMotor.setPower(power);
    }

    public void setRightDrivePower(double power){
        rightMotor.setPower(power);
    }

    public double getDrivePosition() {
        return Math.max(leftMotor.getCurrentPosition(), rightMotor.getCurrentPosition());
    }

    protected double getDriveMotorDiameter() {
        return 0.0;
    }

    public double getTurnCircumference(){
        return Math.PI*getDriveMotorDiameter();
    }
}

