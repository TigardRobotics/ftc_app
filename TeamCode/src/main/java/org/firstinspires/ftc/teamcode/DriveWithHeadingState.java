package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams of team 3965 on 2/18/2017.
 */

public class DriveWithHeadingState extends VelocityVortexState {
    protected double turnPower;
    protected double drivePower;
    protected int threshold = 1;
    protected int targetHeading = 0;

    protected double initialEncoderPosition;

    DriveWithHeadingState(String name, double drivePower, /*double turnPower,*/ int targetHeading) {
        this.name = name;
        this.drivePower = drivePower;
        this.turnPower = 2*drivePower;//turnPower;
        this.targetHeading = targetHeading;
    }

    @Override
    public double getProgress() {
        return Math.abs(getRobot().getDrivePosition() - initialEncoderPosition);
    }

    @Override
    public void start() {
        super.start();
        initialEncoderPosition = getRobot().getDrivePosition();
    }

    @Override
    public void loop() {
        getRobot().telemetry.addData("Heading", getSensorModule().getHeading());


        double error = (getSensorModule().getHeadingError(targetHeading) / 180.0);

        getRobot().telemetry.addData("Error", error);

        getRobot().setRightDrivePower(drivePower-turnPower*error);
        getRobot().setLeftDrivePower(drivePower+turnPower*error);
    }

    @Override
    public void stop() {
        super.stop();
        getRobot().stopDriveMotors();
    }
}
