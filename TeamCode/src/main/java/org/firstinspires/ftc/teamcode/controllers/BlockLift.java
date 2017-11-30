package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Katrina on 10/26/2017.
 */

public class BlockLift extends HardwareController implements IBlockLift {
    // Declare hardware objects
    private DcMotor liftMotor;
    private Servo rightClamp;
    private Servo leftClamp;

    // set servo positions
    private static final double rightClampPos = 0.098;
    private static final double rightReleasePos = 0.5;
    private static final double leftClampPos = 0.508;
    private static final double leftReleasePos = 0.078;

    /**
     * Constructor
     * @param liftMotor
     * @param rightClamp
     * @param leftClamp
     */
    public BlockLift(DcMotor liftMotor, Servo rightClamp, Servo leftClamp) {
        this.liftMotor = liftMotor;
        this.rightClamp = rightClamp;
        this.leftClamp = leftClamp;
    }

    @Override
    public void init() {
        release();
        super.init();
    }

    public void clamp() {
        rightClamp.setPosition(rightClampPos);
        leftClamp.setPosition(leftClampPos);
    }

    public void release() {
        rightClamp.setPosition(rightReleasePos);
        leftClamp.setPosition(leftReleasePos);
    }

    public void lift(double power) {
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor.setPower(power);
    }

    public void reset() {
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        liftMotor.setPower(0.0);
    }
}
