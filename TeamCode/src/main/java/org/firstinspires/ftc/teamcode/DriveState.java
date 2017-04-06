package org.firstinspires.ftc.teamcode;

import com.qualcomm.ftccommon.DbgLog;

/**
 * Created by Derek Williams on 10/12/2016.
 */

public class DriveState extends State{
    protected double power;
    protected double initialEncoderPosition;

    DriveState(String name, double power){
        this.name = name;
        this.power = power;
    }

    @Override
    public double getProgress() {
        return Math.abs(getRobot().getDrivePosition() - initialEncoderPosition);
    }

    @Override
    public void start() {
        initialEncoderPosition = getRobot().getDrivePosition();
        getRobot().setDrivePower(power);
    }

    @Override
    public void loop() {
        getRobot().telemetry.addData(name, String.format("Driven %f encoder counts", getProgress()));
    }

    @Override
    public void stop() {
        getRobot().stopDriveMotors();
    }
}
