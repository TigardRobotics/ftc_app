package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Robotics on 10/11/2016.
 */

public abstract class VelocityVortexRobotBase extends RobotBase {
    private ModernRoboticsSensorModule sensorModule = new ModernRoboticsSensorModule(this);

    protected static final double DRIVE_DIAMETER = 35.56; // Centimeters
    protected static final double FULL_TURN_ROTATION = 7660;

    // All hardware custom to velocity vortex defined here
    protected Servo rightButtonPusher;
    protected Servo leftButtonPusher;

    // Hardware constants
    final static double RIGHT_BUTTON_PUSHER_MAX = 0.7;
    final static double RIGHT_BUTTON_PUSHER_MIN = 0.3;
    final static double LEFT_BUTTON_PUSHER_MAX = 0.7;
    final static double LEFT_BUTTON_PUSHER_MIN = 0.3;

    @Override
    public void init() {
        super.init();
        //sensorModule.init();
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
}
