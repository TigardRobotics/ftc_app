package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams of team 3965 on 11/15/2016.
 */

public class WaddleLeftState extends DriveState {

    WaddleLeftState (String name, double power, Transition... transitions) {
        super(name, power, transitions);
    }

    @Override
    public void onEntry() {
        getRobot().setLeftDrivePower(power);
        getRobot().setRightDrivePower(0.0);
    }
}
