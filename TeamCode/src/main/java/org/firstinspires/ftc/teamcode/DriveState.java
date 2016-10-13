package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams on 10/12/2016.
 */

public class DriveState extends State{
    private double Power;
    private double TargetDistance;
    private double InitialEncoderPosition;

    DriveState(String name, RobotBase robot, double power, double distance, String nextstatename){
        Name = name;
        Robot = robot;
        Power = power;
        TargetDistance = distance;
        NextStateName = nextstatename;
    }

    protected double distanceDriven() {
        return Math.abs(Robot.getDrivePosition() - InitialEncoderPosition);
    }

    @Override
    public void start() {
        InitialEncoderPosition = Robot.getDrivePosition();
        Robot.setDrivePower(Power);
    }

    @Override
    public void loop() {
        Robot.telemetry.addData(Name, String.format("%f of %f", distanceDriven(), TargetDistance));
    }

    @Override
    public void checkComplete() {
        if(distanceDriven() >= TargetDistance){
            Complete = true;
        }
    }

    @Override
    public void stop() {
        Robot.stopDriveMotors();
    }
}
