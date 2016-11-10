package org.firstinspires.ftc.teamcode;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Derek Williams of team 3965 on 10/9/2016.
 */

public abstract class RobotBase extends OpMode {
    private DcMotor leftDriveMotor;
    private DcMotor rightDriveMotor;

    @Override
    public void init() {
        telemetry.addLine("Basic Hardware Initialized");
        leftDriveMotor = hardwareMap.dcMotor.get("motor_l");
        rightDriveMotor = hardwareMap.dcMotor.get("motor_r");
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
        leftDriveMotor.setPower(-power);
    }

    public void setRightDrivePower(double power){
        rightDriveMotor.setPower(power);
    }

    public double getDrivePosition() {
        return Math.max(leftDriveMotor.getCurrentPosition(), rightDriveMotor.getCurrentPosition());
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
