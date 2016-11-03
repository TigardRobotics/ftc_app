package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Robotics on 10/11/2016.
 */

public abstract class VelocityVortexRobotBase extends RobotBase {
    protected static final double DriveMotorDiameter = 35.56; // Centimeters
    protected static final double fullMotorRotation = 7660;

    // All hardware custom to velocity vortex defined here
    protected Servo rightButtonPusher;
    protected Servo leftButtonPusher;

    @Override
    public void init() {
        super.init();
        rightButtonPusher = hardwareMap.servo.get("right_button_pusher");
        leftButtonPusher = hardwareMap.servo.get("left_button_pusher");
        telemetry.addLine("Velocity Vortex Base Initialized");
    }

    @Override
    public double getDriveMotorDiameter () {
        return DriveMotorDiameter;
    }
}
