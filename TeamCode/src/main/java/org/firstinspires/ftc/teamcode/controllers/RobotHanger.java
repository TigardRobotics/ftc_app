package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.configuration.MotorConfigurationType;

/**
 * Created by Brandon on 10/2/2018.
 */

public class RobotHanger extends HardwareController {
    private DcMotor hangMotor;

    private double speed = 0.0;


    public RobotHanger(DcMotor hangMotor) {
        this.hangMotor = hangMotor;
    }

    @Override
    public void init() {
        hangMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hangMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        hangMotor.setPower(speed);
    }

    @Override
    public void stop() {
        hangMotor.setPower(0.0);
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }
}
