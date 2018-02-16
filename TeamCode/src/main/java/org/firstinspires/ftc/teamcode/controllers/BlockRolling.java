package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.hardware.motors.TetrixMotor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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

    private static final double maxEncPos = 7300.0;
    private static final double minEncPos = 0.0;
    private boolean disableMinLimit = false;

    private BlockControlMode blockControlMode = BlockControlMode.hold;

    /* Preset Positions:

     */

    private double power;
    private double comPower;
    private int row = -1; //-1 means set power not row

    private static final double[] rowPositions = {120.0, 2700.0, 5100.0, 7200.0};
    private static final double rowThreshold = 100.0;

    /**
     * Constructor
     * @param liftMotor
     * @param rightClamp
     * @param leftClamp
     */
    public BlockRolling(DcMotor liftMotor, Servo rightClamp, Servo leftClamp) {
        setBlockControlMode(BlockControlMode.hold);
        this.liftMotor = liftMotor;
        liftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.rightClamp = rightClamp;
        this.leftClamp = leftClamp;
    }

    public void setBlockControlMode(BlockControlMode mode) {
        blockControlMode = mode;
    }

    @Override
    public void init() {
        initialEncoderPos = liftMotor.getCurrentPosition();
        RobotBase.log("Resetting lift encoder in init");
        Robot.telemetry.addLine("Resetting lift encoder in init");
        this.liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        power = 0.0;
        rightClamp.setPosition(servoHold);
        leftClamp.setPosition(servoHold);
        setBlockControlMode(BlockControlMode.hold);
        super.init();
    }

    @Override
    public void start() {
        initialEncoderPos = liftMotor.getCurrentPosition();
        RobotBase.log("Resetting lift encoder in start");
        Robot.telemetry.addLine("Resetting lift encoder in start");
        super.start();
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

        // Calculate and log lift speed and pos
        double liftPos = getLiftPos();

        // If row commanded
        if(row != -1) {
            double target = rowPositions[row];
            if(liftPos < target-rowThreshold) power = 0.9;
            else if (liftPos > target+rowThreshold) power = -0.4;
            else power = 0.0;
        }

        // Don't allow lift to move out of range
        if ((!(power > 0.0 && liftPos >= maxEncPos) && !(power < 0.0 && liftPos <= minEncPos))
             || disableMinLimit) {
            liftMotor.setPower(power);
        }
        else {
            liftMotor.setPower(0.0);
        }

        Robot.telemetry.addData("lift pos, power, row", liftPos +", "+power+", "+row);
        RobotBase.log("lift pos="+liftPos+", power="+power+", row="+row);
        prevPos = liftPos;
        prevTime = System.currentTimeMillis();
    }

    @Override
    public void stop() {
        rightClamp.setPosition(servoHold);
        leftClamp.setPosition(servoHold);
        setBlockControlMode(BlockControlMode.hold);
    }

    public void setRow(int rowNum) {
        //Robot.telemetry.addData("Lift RTP", 4000+(int)initialEncoderPos);
        if(rowNum > 3 || rowNum < -1) throw new RuntimeException("row set to "+rowNum);
        row = rowNum;
    }

    public void nextRow() {
        for(int r = 0; r < 4; r++) {
            if (getLiftPos() < rowPositions[r] - rowThreshold) {
                setRow(r);
                return;
            }
        }
        setRow(3);
    }

    public void lastRow() {
        for(int r = 3; r >= 0; r--) {
            if (getLiftPos() > rowPositions[r] + rowThreshold) {
                setRow(r);
                return;
            }
        }
        setRow(0);
    }

    public void lift(double power) {
        //liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //Robot.telemetry.addData("Lift RTV", power);
        if(power != comPower) {
            row = -1;
            this.power = 0.95*power;
        }
        comPower = power;
    }

    public void reset() {
        //liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        liftMotor.setPower(0.0);
    }

    public void overrideMinLimit(boolean override) {
        if(override) {
            Robot.telemetry.addLine("Overriding lift Min");
            RobotBase.log("Overriding lift Min");
        }
        if (!override && disableMinLimit) {
            //When Min Limit is re-enabled, reset it to the current position
            initialEncoderPos = liftMotor.getCurrentPosition();
            RobotBase.log("Resetting lift encoder in loop");
            Robot.telemetry.addLine("Resetting lift encoder in loop");
        }
        disableMinLimit = override;
    }

    private double getLiftPos() {
        return liftMotor.getCurrentPosition()-initialEncoderPos;
    }

    private double prevPos = 0.0;
    private long prevTime = 0L;
    public double getLiftSpeed() {
        return (getLiftPos() - prevPos) / (System.currentTimeMillis() - prevTime);
    }
}
