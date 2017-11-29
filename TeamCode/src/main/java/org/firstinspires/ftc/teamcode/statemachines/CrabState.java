package org.firstinspires.ftc.teamcode.statemachines;

import org.firstinspires.ftc.teamcode.controllers.IDrive;
import org.firstinspires.ftc.teamcode.controllers.SwerveDrive;
import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Drive the Robot Straight in any direction
 */

public class CrabState extends State {
    protected double direction;
    protected double speed;
    protected SwerveDrive swerve;

    protected double initialPos;

    public CrabState(String name, double direction, double speed, Transition... transitions) {
        super(name, transitions);
        this.speed = speed;
        this.direction = direction;
        swerve = (SwerveDrive)(RobotBase.findController(SwerveDrive.class));
    }

    @Override
    public double getProgress() {
        return Math.abs(swerve.getDrivePosition() - initialPos);
    }

    @Override
    public void onEntry() {
        super.onEntry();
        initialPos = swerve.getDrivePosition();
        swerve.setDriveDirection(direction);
        swerve.setDrivePower(speed);
    }

    @Override
    public void doState() {
        Robot.telemetry.addData(name, String.format("Driven %f", getProgress()));
        RobotBase.log(name+String.format("Driven %f", getProgress()));
    }

    @Override
    public void onExit() {
        super.onExit();
        swerve.stopDriveMotors();
    }
}
