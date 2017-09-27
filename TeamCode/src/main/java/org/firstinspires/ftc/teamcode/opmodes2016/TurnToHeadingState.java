package org.firstinspires.ftc.teamcode.opmodes2016;

import org.firstinspires.ftc.teamcode.statemachines.Transition;

/**
 * Created by Derek Williams of team 3965 on 2/16/2017.
 */

public class TurnToHeadingState extends VelocityVortexState {
    protected double maxPower = 0.5;
    protected double minPower = 0.01;
    protected double maxPowerThreshold = 0.5;
    protected int threshold = 3;
    protected int targetHeading = 0;

    protected int iterationsWithinThresholdCount;
    protected int requiredIterationsWithinThresholdForHeadingReached = 2;


    TurnToHeadingState(String name, int targetHeading, Transition... transitions) {
        super(name, transitions);
        this.targetHeading = targetHeading;
    }


    @Override
    public void onEntry() {
        super.onEntry();
        iterationsWithinThresholdCount = 0;
    }

    @Override
    public void doState() {
        /*
        getRobot().telemetry.addData("Heading", getSensorModule().getHeading());

        if(Math.abs(getSensorModule().getHeadingError(targetHeading)) < threshold) { // Exiting if heading within threshold
            iterationsWithinThresholdCount++;
            getRobot().telemetry.addLine("Within Threshold");
            if(iterationsWithinThresholdCount >= requiredIterationsWithinThresholdForHeadingReached) {
                getRobot().telemetry.addLine("Heading Reached");
                makeComplete();
                return;
            }
        }
        else {
            iterationsWithinThresholdCount = 0;
        }

        double error = (getSensorModule().getHeadingError(targetHeading) / 180.0);
        double power;
        if (error > 0)
        {
            power = minPower + (maxPower-minPower)*(Math.min(error,maxPowerThreshold)/maxPowerThreshold);
        }
        else
        {
            power = - (minPower + (maxPower-minPower)*(Math.min(-error,maxPowerThreshold)/maxPowerThreshold));
        }

        getRobot().telemetry.addData("Error", error);
        getRobot().telemetry.addData("Power", power);

        getRobot().setRightDrivePower(-power);
        getRobot().setLeftDrivePower(power);
        */
    }

    @Override
    public void onExit() {
        super.onExit();
        //getRobot().stopDriveMotors();
    }
}
