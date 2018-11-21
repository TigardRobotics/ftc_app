package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Brandon on 10/2/2018.
 */

public class RobotHanger extends HardwareController {
    private DcMotor hangMotor;

    private double speed = 0.95;
    private boolean ignoreLimit = false;

    private static final double LIMIT = 20.0;

    public RobotHanger(DcMotor hangMotor) {
        this.hangMotor = hangMotor;
    }

    @Override
    public void init() {
        //! TODO: Add homing by stalling
        hangMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hangMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        // If motor position is beyond limit and power is in the direction to beyond the limit
        if(Math.abs(hangMotor.getCurrentPosition()) > LIMIT && speed*hangMotor.getCurrentPosition() > 0) {
            hangMotor.setPower(0.0);
        }
        else {
            hangMotor.setPower(speed);
        }
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

    public double getPos() {
        return hangMotor.getCurrentPosition();
    }
}
