package org.firstinspires.ftc.teamcode.statemachines;

import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.controllers.SensorModule;

/**
 * Created by Derek Williams on 10/12/2016.
 */

public class DriveState extends MoveState {
    protected double speed;
    protected double initialPos;

    public DriveState(String name, double speed, SensorModule sensors, Transition... transitions) {
        super(name, speed, sensors, transitions);
    }

    @Override
    public double getProgress() {
        return Math.abs(Drive.getDrivePosition() - initialPos);
    }

    @Override
    public void onEntry() {
        initialPos = Drive.getDrivePosition();
        Drive.setDrivePower(speed);
    }

    @Override
    public void doState() {
        Robot.telemetry.addData(name, String.format("Driven %f encoder counts", getProgress()));
        RobotLog.i(name, String.format("Driven %f encoder counts", getProgress()));
    }

    @Override
    public void onExit() {
        super.onExit();
        Drive.stopDriveMotors();
    }
}
