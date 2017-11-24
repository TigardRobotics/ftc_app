package org.firstinspires.ftc.teamcode.statemachines;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.VV_2016.VelocityVortexState;
import org.firstinspires.ftc.teamcode.controllers.IDrive;
import org.firstinspires.ftc.teamcode.controllers.IOrientation;
import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Drive to match the hearing using a gyro
 */

public class DriveWithHeadingState extends DriveState {
    protected double targetHeading = 0;
    protected IOrientation gyro;
    static final double STEER_COOEF = 1/45.0;   //Set max steer at 45deg error

    DriveWithHeadingState(String name, double speed, double targetHeading, Transition... transitions) {
        super(name, speed, transitions);
        this.targetHeading = targetHeading;
        gyro = (IOrientation)(RobotBase.findController(IOrientation.class));
    }

    @Override
    public void onEntry() {
        super.onEntry();
        drive.setDriveDirection(getSteer());
    }

    @Override
    public void doState() {
        super.doState();
        drive.setDriveDirection(getSteer());
    }

    protected double getSteer() {
        //calculate direction error (-180 to +180)
        double error = (targetHeading - gyro.getBearing() + 360.0 + 180.0) % 360.0 - 180.0;
        return Range.clip(error * STEER_COOEF, -1, 1);
    }

}
