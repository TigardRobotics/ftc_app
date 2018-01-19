package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.hardware.motors.TetrixMotor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Created by Katrina on 12/30/2017.
 */

public class BlockRolling extends HardwareController implements IBlockLift {
    // Declare hardware objects
    private DcMotor liftMotor;
    private Servo rightClamp;
    private Servo leftClamp;

    // set servo positions
    private static final double servoHold = 0.5;
    private static final double servoRelease = 0.0;
    private static final double servoAcquire = 1.0;

    private double initialEncoderPos;

    private static final double maxEncPos = 4700.0;

    private BlockControlMode blockControlMode = BlockControlMode.hold;

    /* Preset Positions:

     */


    /**
     * Constructor
     * @param liftMotor
     * @param rightClamp
     * @param leftClamp
     */
    public BlockRolling(DcMotor liftMotor, Servo rightClamp, Servo leftClamp) {
        setBlockControlMode(BlockControlMode.hold);
        this.liftMotor = liftMotor;
        this.rightClamp = rightClamp;
        this.leftClamp = leftClamp;
    }

    public void setBlockControlMode(BlockControlMode mode) {
        blockControlMode = mode;
    }

    @Override
    public void init() {
        initialEncoderPos = liftMotor.getCurrentPosition();
        this.liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setBlockControlMode(BlockControlMode.hold);
        super.init();
    }

    @Override
    public void loop() {
        switch(blockControlMode) {
            case acquire:
                rightClamp.setPosition(servoAcquire);
                leftClamp.setPosition(servoRelease);
                break;
            case release:
                rightClamp.setPosition(servoRelease);
                leftClamp.setPosition(servoAcquire);
                break;
            case clockwise:
                rightClamp.setPosition(servoAcquire);
                leftClamp.setPosition(servoAcquire);
                break;
            case counterclockwise:
                rightClamp.setPosition(servoRelease);
                leftClamp.setPosition(servoRelease);
                break;
            case hold:
                rightClamp.setPosition(servoHold);
                leftClamp.setPosition(servoHold);
                break;
            default:
                break;
        }
        super.loop();
        Robot.telemetry.addData("lift pos", getLiftPos());
    }

    @Override
    public void stop() {
        setBlockControlMode(BlockControlMode.hold);
    }

    public void setPos() {
        //liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //liftMotor.setTargetPosition(4000+(int)initialEncoderPos);
        //Robot.telemetry.addData("Lift RTP", 4000+(int)initialEncoderPos);
    }

    public void lift(double power) {
        //liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Robot.telemetry.addData("Lift RTV", power);

        if (!(power < 0.0 && getLiftPos() >= maxEncPos) && !(power > 0.0 && getLiftPos() <= 0.0)) {
            liftMotor.setPower(0.5*power);
        }
        else {
            liftMotor.setPower(0.0);
        }

    }

    public void reset() {
        //liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        liftMotor.setPower(0.0);
    }

    private double getLiftPos() {
        return Math.abs(liftMotor.getCurrentPosition()-initialEncoderPos);
    }
}
