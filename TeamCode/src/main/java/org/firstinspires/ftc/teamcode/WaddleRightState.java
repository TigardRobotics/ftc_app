package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams of team 3965 on 11/3/2016.
 */

public class WaddleRightState extends DriveState {

    WaddleRightState (String name, double power) {
        super(name, power);
    }

    @Override
    public void start() {
        getRobot().setLeftDrivePower(0.0);
        getRobot().setRightDrivePower(power);
    }
}
