package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.RobotLog;

/**
 * Created by Derek Williams of team 3965 on 2/1/2017.
 */

public class DriveAndSweepState extends VelocityVortexState {
    protected double power;
    protected double initialEncoderPosition;

    DriveAndSweepState(String name, double power){
        this.name = name;
        this.power = power;
    }

    @Override
    public double getProgress() {
        return Math.abs(getRobot().getDrivePosition() - initialEncoderPosition);
    }

    @Override
    public void onEntry() {
        initialEncoderPosition = getRobot().getDrivePosition();
        getRobot().setDrivePower(power);
        getVelocityVortexRobotBase().enableCollector();
    }

    @Override
    public void doState() {
        getRobot().telemetry.addData(name, String.format("Driven %f encoder counts", getProgress()));
        RobotLog.i(name, String.format("Driven %f encoder counts", getProgress()));
    }

    @Override
    public void onExit() {
        getRobot().stopDriveMotors();
        getVelocityVortexRobotBase().disableCollector();
    }
}
