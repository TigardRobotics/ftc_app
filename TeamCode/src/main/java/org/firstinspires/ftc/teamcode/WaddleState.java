package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams of team 3965 on 11/3/2016.
 */

public class WaddleState extends DriveState {
    protected boolean pivotOnLeftDriveMotor;

    WaddleState(String name, double power, boolean pivotOnLeftDriveMotor){
        super(name, power);
        this.pivotOnLeftDriveMotor = pivotOnLeftDriveMotor;
    }

    @Override
    public void start() {
        super.start();
        if(pivotOnLeftDriveMotor) {
            getStateMachine().robot.setLeftDrivePower(0.0);
        }
        else {
            getStateMachine().robot.setRightDrivePower(0.0);
        }
    }

    @Override
    public void stop() {
        getStateMachine().robot.stopDriveMotors();
    }
}
