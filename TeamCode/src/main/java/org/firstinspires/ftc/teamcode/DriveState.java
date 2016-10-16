package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams on 10/12/2016.
 */

public class DriveState extends State{
    private double power;
    private double InitialEncoderPosition;

    DriveState(String name, double power){
        this.name = name;
        this.power = power;
    }

    @Override
    public double getProgress() {
        return Math.abs(machine.robot.getDrivePosition() - InitialEncoderPosition);
    }

    @Override
    public void start() {
        InitialEncoderPosition = machine.robot.getDrivePosition();
        machine.robot.setDrivePower(power);
    }

    @Override
    public void loop() {
        machine.robot.telemetry.addData(name, String.format("Driven %f encoder counts", getProgress()));
    }

    @Override
    public void stop() {
        machine.robot.stopDriveMotors();
    }
}
