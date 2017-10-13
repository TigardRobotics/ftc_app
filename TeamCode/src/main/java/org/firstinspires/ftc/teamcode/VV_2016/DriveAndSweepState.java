package org.firstinspires.ftc.teamcode.VV_2016;

import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.teamcode.opmodes.RobotBase;
import org.firstinspires.ftc.teamcode.statemachines.Transition;

/**
 * Created by Derek Williams of team 3965 on 2/1/2017.
 */

public class DriveAndSweepState extends VelocityVortexState {
    protected double power;
    protected double initialEncoderPosition;

    DriveAndSweepState(String name, double power, Transition... transitions){
        super(name, transitions);
        this.power = power;
    }

    /*
    @Override
    public double getProgress() {
        return Math.abs(getRobot().getDrivePosition() - initialPos);
    }
    */

    @Override
    public void onEntry() {
        //initialPos = getRobot().getDrivePosition();
        //getRobot().setDrivePower(speed);
        //getVelocityVortexRobotBase().enableCollector();
    }

    @Override
    public void doState() {
        //getRobot().telemetry.addData(name, String.format("Driven %f encoder counts", getProgress()));
       RobotBase.log(name+String.format(" Driven %f encoder counts", getProgress()));
    }

    @Override
    public void onExit() {
        //getRobot().stopDriveMotors();
        getVelocityVortexRobotBase().disableCollector();
    }
}
