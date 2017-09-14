package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams on 2/18/2017.
 */

public class DriveWithHeadingAndSweepState extends DriveWithHeadingState {
    protected double power;
    protected double initialEncoderPosition;

    DriveWithHeadingAndSweepState(String name, double drivePower, /*double turnPower,*/ int targetHeading) {
        super(name, drivePower, targetHeading);
    }

    @Override
    public void onEntry() {
        super.onEntry();
        getVelocityVortexRobotBase().enableCollector();
    }

    @Override
    public void onExit() {
        super.onExit();
        getVelocityVortexRobotBase().disableCollector();
    }

}
