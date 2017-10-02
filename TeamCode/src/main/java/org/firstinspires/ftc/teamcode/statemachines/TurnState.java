package org.firstinspires.ftc.teamcode.statemachines;

import org.firstinspires.ftc.teamcode.controllers.SensorModule;

/**
 * Created by Derek Williams on 10/12/2016.
 */

public class TurnState extends MoveState {
    protected double power;
    protected double initialEncoderPosition;

    public TurnState(String name, double power, SensorModule sensors, Transition... transitions){
        super(name, power, sensors, transitions);
    }

    public double getProgress() {
        return Math.abs(Drive.getRotationPosition() - initialEncoderPosition);
    }

    @Override
    public void onEntry() {
        initialEncoderPosition = Drive.getRotationPosition();
        Drive.setRotationPower(power);
    }

    @Override
    public void doState() {
        //telemetry.addData(name, String.format("Driven %f encoder counts", getProgress()));
    }

    @Override
    public void onExit() {
        Drive.stopDriveMotors();
    }
}
