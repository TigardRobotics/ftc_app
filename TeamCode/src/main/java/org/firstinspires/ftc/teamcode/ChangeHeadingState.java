package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams of team 3965 on 1/12/2017.
 */

public class ChangeHeadingState extends VelocityVortexState {
    protected int targetHeading;
    protected double speed = 0.15;

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
        //int headingError = (getHeading()-targetHeading+180)%360-180;

        // Turning Towards the target heading if not there already
        if(!complete) {
            if(Math.abs(getSensorModule().getHeadingError(targetHeading)) < 1) { // Exiting if heading within threshold
                getRobot().telemetry.addLine("Heading reached");
                getRobot().stopDriveMotors();
                complete = true;
                return;
            }

            double power = speed * (getSensorModule().getHeadingError(targetHeading) / 180.0);
            getRobot().setRightDrivePower(power);
            getRobot().setLeftDrivePower(-power);
            /*else if( headingError > 0) {  // checking if faster to turn left
                getRobot().telemetry.addLine(String.format("Turning Left, h=%d, t=%d", getHeading(), targetHeading));
                getRobot().setRightDrivePower(speed);
                getRobot().setLeftDrivePower(-speed);
            }
            else {
                getRobot().telemetry.addLine(String.format("Turning Right, h=%d, t=%d", getHeading(), targetHeading));
                getRobot().setRightDrivePower(-speed);
                getRobot().setLeftDrivePower(speed);
            }*/
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
