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
            getStateMachine().robot.setLeftDrivePower(power/2);
        }
        else {
            getStateMachine().robot.setRightDrivePower(power/2);
        }
    }
}
