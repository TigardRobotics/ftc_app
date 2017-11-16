package org.firstinspires.ftc.teamcode.statemachines;

/**
 * Turn the Robot
 */

public class TurnState extends MoveState {
    protected double initialPosition;

    public TurnState(String name, double speed, Transition... transitions){
        super(name, speed, transitions);
    }

    public double getProgress() {
        return Math.abs(drive.getRotationPosition() - initialPosition);
    }

    @Override
    public void onEntry() {
        initialPosition = drive.getRotationPosition();
        drive.setRotationPower(speed);
    }

    @Override
    public void doState() {
        Robot.telemetry.addData(name, String.format("Driven %f", getProgress()));
    }

    @Override
    public void onExit() {
        drive.stopDriveMotors();
    }
}
