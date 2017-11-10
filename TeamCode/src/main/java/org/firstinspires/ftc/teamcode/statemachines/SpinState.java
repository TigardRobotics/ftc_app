package org.firstinspires.ftc.teamcode.statemachines;

import org.firstinspires.ftc.teamcode.controllers.SwerveDrive;
import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Created by Derek on 11/9/2017.
 */

public class SpinState extends State {

    private double speed;
    private SwerveDrive swerve;

    public SpinState(String name, double speed, Transition... transitions) {
        super(name, transitions);
        this.speed = speed;
        swerve = (SwerveDrive)RobotBase.findController(SwerveDrive.class);
    }

    @Override
    public void onEntry() {
        super.onEntry();
        swerve.spinMode();
        swerve.setDrivePower(speed);
    }

    @Override
    public void doState() {
        Robot.telemetry.addData(name, String.format("Driven %f encoder counts", getProgress()));
        RobotBase.log(name+String.format("Driven %f encoder counts", getProgress()));
    }

    @Override
    public void onExit() {
        super.onExit();
        swerve.crabMode();
        swerve.stopDriveMotors();
    }
}
