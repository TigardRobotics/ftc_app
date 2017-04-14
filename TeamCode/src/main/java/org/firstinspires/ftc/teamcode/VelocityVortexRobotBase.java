package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Derek Williams of team 3965 on 10/11/2016.
 */

public abstract class VelocityVortexRobotBase extends RobotBase {
    private ModernRoboticsSensorModule sensorModule = new ModernRoboticsSensorModule(this);
    protected StateMachine stateMachine = new StateMachine(this);

    protected static final double FULL_TURN_ROTATION = 6000; // Encoder counts
    private static final double COUNTS_PER_ROTATION = 1440.0;
    private static final double INCHES_PER_ROTATION = 15.0;
    protected static final double COUNTS_PER_INCH = COUNTS_PER_ROTATION/INCHES_PER_ROTATION;

    protected double driveSpeed = 0.80;     //0.70  //.75
    protected double turnSpeed = 0.25;      //0.20
    protected double followSpeed = 0.25;

    protected double rangeToBeacon = 4.5; //2.0  //6.0

    // All hardware custom to velocity vortex defined here
    protected Servo rightButtonPusher;
    protected Servo leftButtonPusher;

    protected DcMotor particleFlicker;
    protected DcMotor particleCollecter;

    protected DcMotor ballLift;
    protected Servo ballGripper;

    // Hardware constants
    final static double RIGHT_BUTTON_PUSHER_EXTENDED = 0.0/128.0;
    final static double RIGHT_BUTTON_PUSHER_RETRACTED = 50.0/128.0;
    final static double LEFT_BUTTON_PUSHER_EXTENDED = 128.0/128.0;
    final static double LEFT_BUTTON_PUSHER_RETRACTED = 20.0/128.0;
    final static double PARTICLE_FLICKER_SPEED = 1.0;
    final static double PARTICLE_LIFTER_SPEED = 1.0;
    final static double BALL_LIFT_SPEED = 1.0;

    final static double BALL_GRIPPER_OPEN = 1.0;
    final static double BALL_GRIPPER_CLOSE = 0.0;
    final static double BALL_GRIPPER_STOP = 0.5;

    @Override
    public void init() {
        super.init();
        sensorModule.init();
        rightButtonPusher = hardwareMap.servo.get("r_pusher");
        leftButtonPusher = hardwareMap.servo.get("l_pusher");
        particleFlicker = hardwareMap.dcMotor.get("launch");
        particleCollecter = hardwareMap.dcMotor.get("belt");
        ballLift = hardwareMap.dcMotor.get("liftMotor");
        ballGripper = hardwareMap.servo.get("spreader");

        telemetry.addLine("Velocity Vortex Base Initialized");

        stopBallGripper();
        retractLeftPusher();
        retractRightPusher();

    }

    @Override
    public void init_loop() {
        sensorModule.init_loop();
    }

    @Override
    public SensorModule getSensorModule() {
        return sensorModule;
    }


    public void extendRightPusher() {
        rightButtonPusher.setPosition(RIGHT_BUTTON_PUSHER_EXTENDED);
        telemetry.addLine("Extending Right Button Pusher");
    }

    public void retractRightPusher() {
        rightButtonPusher.setPosition(RIGHT_BUTTON_PUSHER_RETRACTED);
        telemetry.addLine("Retracting Right Button Pusher");
    }

    public void extendLeftPusher() {
        leftButtonPusher.setPosition(LEFT_BUTTON_PUSHER_EXTENDED);
        telemetry.addLine("Extending Left Button Pusher");
    }

    public void retractLeftPusher() {
        leftButtonPusher.setPosition(LEFT_BUTTON_PUSHER_RETRACTED);
        telemetry.addLine("Retracting Left Button Pusher");
    }

    public void forwardFlicker() {
        particleFlicker.setPower(-PARTICLE_FLICKER_SPEED);
    }

    public void reverseFlicker() {
        particleFlicker.setPower(PARTICLE_LIFTER_SPEED);
    }

    public void disableFlicker() {
        particleFlicker.setPower(0.0);
    }

    public void forwardCollecter() {
        particleCollecter.setPower(PARTICLE_LIFTER_SPEED);
    }

    public void reverseCollecter() {
        particleCollecter.setPower(-PARTICLE_LIFTER_SPEED);
    }

    public void disableCollecter() {
        particleCollecter.setPower(0.0);
    }

    public void raiseBallLift() { ballLift.setPower(BALL_LIFT_SPEED); }
    public void lowerBallLift() { ballLift.setPower(-BALL_LIFT_SPEED); }
    public void disableBallLift() { ballLift.setPower(0.0); }

    public void openBallGripper() {
        ballGripper.setPosition(BALL_GRIPPER_OPEN);
    }
    public void closeBallGripper() { ballGripper.setPosition(BALL_GRIPPER_CLOSE); }
    public void stopBallGripper() { ballGripper.setPosition(BALL_GRIPPER_STOP); }

    protected double degToEnc(double deg) {
        return deg*FULL_TURN_ROTATION/360.0;
    }

    protected double inToEnc(double dist) {
        return dist*COUNTS_PER_INCH;
    }
}
