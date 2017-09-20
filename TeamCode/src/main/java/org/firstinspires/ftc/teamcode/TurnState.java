package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams on 10/12/2016.
 */

public class TurnState extends MoveState {
    protected double power;
    protected double initialEncoderPosition;

    TurnState(String name, double power, SensorModule sensors, Transition... transitions){
        super(name, power, sensors, transitions);
    }

    public double getProgress() {
        return Math.abs(driveSys.getPos() - initialEncoderPosition);
    }

    @Override
    public void onEntry() {
        initialEncoderPosition = driveSys.getPos();
        driveSys.setSpeed(power);
        driveSys.setSpeed(-power);
    }

    @Override
    public void doState() {
        //telemetry.addData(name, String.format("Driven %f encoder counts", getProgress()));
    }

    @Override
    public void onExit() {
        driveSys.stop();
    }
}
