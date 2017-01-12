package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams of team 3965 on 1/12/2017.
 */

public class ChangeHeadingState extends VelocityVortexState {
    protected int targetHeading;
    protected double speed;

    ChangeHeadingState(String name, int targetHeading, double speed) {
        this.name = name;
        this.targetHeading = targetHeading;
        this.speed = speed;
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {
        // Exiting if the target heading has been reached
        if(getHeading() == targetHeading) {
            complete = true;
            return;
        }

        // Turning Towards the target heading if not there already
        if(!complete) {
            if(((360-getHeading())+targetHeading)%360 > 180) {  // checking if faster to turn left
                getRobot().telemetry.addLine(String.format("Turning Left, h=%d, t=%d", getHeading(), targetHeading));
                getRobot().setRightDrivePower(speed);
                getRobot().setLeftDrivePower(-speed);
            }
            else {
                getRobot().telemetry.addLine(String.format("Turning Right, h=%d, t=%d", getHeading(), targetHeading));
                getRobot().setRightDrivePower(-speed);
                getRobot().setLeftDrivePower(speed);
            }
        }
    }

    @Override
    public void stop() {
        getRobot().stopDriveMotors();
    }

    protected int getHeading() {
        return getVelocityVortexRobotBase().getSensorModule().getHeading();
    }
}
