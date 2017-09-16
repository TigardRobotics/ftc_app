package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.RobotLog;

/**
 * Created by Derek Williams on 10/12/2016.
 */

public class DriveState extends State{
    protected double power;
    protected double initialEncoderPosition;

    DriveState(String name, double power, Transition... transitions){
        super(name, transitions);
    }

    @Override
    public double getProgress() {
        return Math.abs(getRobot().getDrivePosition() - initialEncoderPosition);
    }

    @Override
    public void onEntry() {
        initialEncoderPosition = getRobot().getDrivePosition();
        getRobot().setDrivePower(power);
    }

    @Override
    public void doState() {
        getRobot().telemetry.addData(name, String.format("Driven %f encoder counts", getProgress()));
        RobotLog.i(name, String.format("Driven %f encoder counts", getProgress()));
    }

    @Override
    public void onExit() {
        getRobot().stopDriveMotors();
    }
}
