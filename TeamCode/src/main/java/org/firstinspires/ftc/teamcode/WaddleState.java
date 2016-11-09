package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams of team 3965 on 11/3/2016.
 */

public class WaddleState extends TurnState {
    protected boolean pivotOnLeftDriveMotor;

    WaddleState(String name, double power, boolean pivotOnLeftDriveMotor){
        super(name, power);
        this.pivotOnLeftDriveMotor = pivotOnLeftDriveMotor;
    }

    @Override
    public void start() {
        initialEncoderPosition = getStateMachine().robot.getDrivePosition();
        getStateMachine().robot.stopDriveMotors();
        if(pivotOnLeftDriveMotor) {
            getStateMachine().robot.setRightDrivePower(power);
        }
        else {
            getStateMachine().robot.setLeftDrivePower(-power);
        }
    }
}
