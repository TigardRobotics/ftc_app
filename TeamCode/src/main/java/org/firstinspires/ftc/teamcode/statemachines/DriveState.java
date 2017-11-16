package org.firstinspires.ftc.teamcode.statemachines;

import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.controllers.SwerveDrive;
import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Drive the Robot Straight
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
        if(drive instanceof SwerveDrive) ((SwerveDrive)drive).setDirection(0.0, 0.0); //!WORKAROUND: Make sure we are not in spin mode
        drive.setDrivePower(speed);
    }

    @Override
    public void doState() {
        Robot.telemetry.addData(name, String.format("Driven %f", getProgress()));
        RobotBase.log(name+String.format("Driven %f", getProgress()));
    }

    @Override
    public void onExit() {
        super.onExit();
        drive.stopDriveMotors();
    }
}
