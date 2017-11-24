package org.firstinspires.ftc.teamcode.statemachines;

import org.firstinspires.ftc.teamcode.controllers.SwerveDrive;
import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Spin the robot
 */

public class SpinState extends State {
    protected double initialPosition;

    protected double speed;
    protected SwerveDrive swerve;

    public SpinState(String name, double speed, Transition... transitions) {
        super(name, transitions);
        this.speed = speed;
        swerve = (SwerveDrive)RobotBase.findController(SwerveDrive.class);
    }

    @Override
    public double getProgress() {
        return Math.abs(swerve.getRotationPosition() - initialPosition);
    }

    @Override
    public void onEntry() {
        super.onEntry();
        swerve.spinMode();
        swerve.setDrivePower(speed);
        initialPosition = swerve.getRotationPosition();
    }

    @Override
    public void doState() {
        Robot.telemetry.addData(name, String.format("Driven %f ", getProgress()));
        RobotBase.log(name+String.format("Driven %f ", getProgress()));
    }

    @Override
    public void onExit() {
        super.onExit();
        //swerve.setDirection(0.0, 0.0); //!Doing this will exit spin; but, will mmess us up if we want to spin again
        swerve.stopDriveMotors();
    }
}
