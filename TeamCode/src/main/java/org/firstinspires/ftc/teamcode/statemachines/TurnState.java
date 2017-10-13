package org.firstinspires.ftc.teamcode.statemachines;

import org.firstinspires.ftc.teamcode.controllers.SensorModule;

/**
 * Created by Derek Williams on 10/12/2016.
 */

public class TurnState extends MoveState {
    protected double initialEncoderPosition;

    public TurnState(String name, double speed, SensorModule sensors, Transition... transitions){
        super(name, speed, sensors, transitions);
    }

    public double getProgress() {
        return Math.abs(drive.getRotationPosition() - initialEncoderPosition);
    }

    @Override
    public void onEntry() {
        initialEncoderPosition = drive.getRotationPosition();
        drive.setRotationPower(speed);
    }

    @Override
    public void doState() {
        //telemetry.addData(name, String.format("Driven %f encoder counts", getProgress()));
    }

    @Override
    public void onExit() {
        drive.stopDriveMotors();
    }
}
