package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Derek Williams of team 3965 on 10/11/2016.
 */

public abstract class VelocityVortexRobotBase extends RobotBase {
    private ModernRoboticsSensorModule sensorModule = new ModernRoboticsSensorModule(this);
    protected StateMachine stateMachine = new StateMachine(this);

    protected static final double DRIVE_DIAMETER = 35.56; // Centimeters
    protected static final double FULL_TURN_ROTATION = 4843; // Encoder counts
    private static final double COUNTS_PER_ROTATION = 1440.0;
    private static final double CENTIMETERS_PER_ROTATION = 31.928;
    protected static final double COUNTS_PER_CENTIMETER = COUNTS_PER_ROTATION/CENTIMETERS_PER_ROTATION;

    protected double driveSpeed = 0.50;
    protected double gyroDriveSpeed = 0.54;
    protected double turnSpeed = 0.25;
    protected double followSpeed = 0.25;

    protected double rangeToBeacon = 5.0; // was 4.0 on 2/17

    // All hardware custom to velocity vortex defined here
    protected Servo rightButtonPusher;
    protected Servo leftButtonPusher;

    protected DcMotor particleFlicker;
    protected DcMotor particleLifter;
    protected DcMotor ballLift;

    // Hardware constants
    final static double RIGHT_BUTTON_PUSHER_EXTENDED = 0.563;
    final static double RIGHT_BUTTON_PUSHER_RETRACTED = 0.226;
    final static double LEFT_BUTTON_PUSHER_EXTENDED = 0.471;
    final static double LEFT_BUTTON_PUSHER_RETRACTED = 0.797;
    final static double PARTICLE_FLICKER_SPEED = 1.0;
    final static double PARTICLE_LIFTER_SPEED = 1.0;
    final static double BALL_LIFT_SPEED = 1.0;

    @Override
    public void init() {
        super.init();
        sensorModule.init();
        rightButtonPusher = hardwareMap.servo.get("r_pusher");
        leftButtonPusher = hardwareMap.servo.get("l_pusher");
        particleFlicker = hardwareMap.dcMotor.get("launch");
        particleLifter = hardwareMap.dcMotor.get("belt");
        ballLift = hardwareMap.dcMotor.get("liftMotor");
        telemetry.addLine("Velocity Vortex Base Initialized");

        retractLeftPusher();
        retractRightPusher();

    }

    @Override
    public void init_loop() {
        sensorModule.init_loop();
    }

    @Override
    public double getDriveMotorDiameter () {
        return DRIVE_DIAMETER;
    }

    @Override
    public SensorModule getSensorModule() {
        return sensorModule;
    }

    @Override
    public double countsToCentimeters(double counts) {
        return COUNTS_PER_CENTIMETER*counts;
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

    public void enableFlicker() {
        particleFlicker.setPower(-PARTICLE_FLICKER_SPEED);
    }

    public void disableFlicker() {
        particleFlicker.setPower(0.0);
    }

    public void enableLifter() {
        particleLifter.setPower(PARTICLE_LIFTER_SPEED);
    }

    public void disableLifter() {
        particleLifter.setPower(0.0);
    }

    public void raiseBallLift() {
        ballLift.setPower(BALL_LIFT_SPEED);
    }
    public void lowerBallLift() { ballLift.setPower(-BALL_LIFT_SPEED); }
    public void disableBallLift() { ballLift.setPower(0.0); }

    protected double rotsToEnc (double rots) {
        return rots*FULL_TURN_ROTATION;
    }

    protected double cmToEnc(double dist) {
        return dist*COUNTS_PER_CENTIMETER;
    }
}
