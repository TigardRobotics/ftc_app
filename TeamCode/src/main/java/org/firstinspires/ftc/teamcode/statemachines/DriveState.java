package org.firstinspires.ftc.teamcode.statemachines;

import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Created by Derek Williams on 10/12/2016.
 */

public class DriveState extends MoveState {
    protected double initialPos;

    public DriveState(String name, double speed, Transition... transitions) {
        super(name, speed, transitions);
    }

    @Override
    public double getProgress() {
        return Math.abs(drive.getDrivePosition() - initialPos);
    }

    @Override
    public void onEntry() {
        super.onEntry();
        initialPos = drive.getDrivePosition();
        drive.setDrivePower(speed);
    }

    @Override
    public void doState() {
        Robot.telemetry.addData(name, String.format("Driven %f encoder counts", getProgress()));
        RobotBase.log(name+String.format("Driven %f encoder counts", getProgress()));
    }

    @Override
    public void onExit() {
        super.onExit();
        drive.stopDriveMotors();
    }
}
