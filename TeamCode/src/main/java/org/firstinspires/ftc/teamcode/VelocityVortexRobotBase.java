package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Derek Williams of team 3965 on 10/11/2016.
 */

public abstract class VelocityVortexRobotBase extends RobotBase {
    private ModernRoboticsSensorModule sensorModule = new ModernRoboticsSensorModule(this);

    protected static final double DRIVE_DIAMETER = 35.56; // Centimeters
    protected static final double FULL_TURN_ROTATION = 4843; // Encoder counts
    private static final double COUNTS_PER_ROTATION = 1440.0;
    private static final double CENTIMETERS_PER_ROTATION = 31.928;
    protected static final double COUNTS_PER_CENTIMETER = COUNTS_PER_ROTATION/CENTIMETERS_PER_ROTATION;

    protected double driveSpeed = 0.5;

    // All hardware custom to velocity vortex defined here
    protected Servo rightButtonPusher;
    protected Servo leftButtonPusher;

    // Hardware constants
    final static double RIGHT_BUTTON_PUSHER_EXTENDED = 0.75;
    final static double RIGHT_BUTTON_PUSHER_RETRACTED = 0.98;
    final static double LEFT_BUTTON_PUSHER_EXTENDED = 0.69;
    final static double LEFT_BUTTON_PUSHER_RETRACTED = 0.54;

    @Override
    public void init() {
        super.init();
        sensorModule.init();
        rightButtonPusher = hardwareMap.servo.get("right_button_pusher");
        leftButtonPusher = hardwareMap.servo.get("left_button_pusher");
        telemetry.addLine("Velocity Vortex Base Initialized");
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

    protected double rotsToEnc (double rots) {
        return rots*FULL_TURN_ROTATION;
    }

    protected double cmToEnc(double dist) {
        return dist*COUNTS_PER_CENTIMETER;
    }
}
