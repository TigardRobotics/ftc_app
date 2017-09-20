package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams of team 3965 on 11/19/2016.
 */

public class EdgeFollowState extends State {
    protected double power;

    EdgeFollowState (String name, double power, Transition... transitions) {
        super(name, transitions);
        this.power = power;
        if (power < -1 || power > 1) {
            throw new RuntimeException("Setting drive motors speed outside of acceptable range in "+name);
        }
    }
    @Override
    public void onEntry() {
        super.onEntry();
        //getRobot().telemetry.addLine("Starting Edge Detect");
    }

    @Override
    public void doState() {
        /*
        if (getSensorModule().isLineDetected()) {
            getRobot().setLeftDrivePower(0.0);
            getRobot().setRightDrivePower(power);
            getRobot().telemetry.addLine("Detected: "+getSensorModule().getLastDetect()+" Looking for grey tile");
        }
        else {
            getRobot().setLeftDrivePower(power);
            getRobot().setRightDrivePower(0.0);
            getRobot().telemetry.addLine("Detected: "+getSensorModule().getLastDetect()+" Looking for white line");
        }
        //getRobot().telemetry.addData("Power", speed);
        */
    }

    @Override
    public void onExit() {
        //getRobot().stopDriveMotors();
        //getRobot().telemetry.addLine("Stopping Edge Detect");
    }
}
