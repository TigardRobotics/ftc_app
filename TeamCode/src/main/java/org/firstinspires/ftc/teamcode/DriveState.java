package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Derek Williams on 10/12/2016.
 */

public class DriveState extends MoveState {
    protected double speed;
    protected double initialPos;

    protected Telemetry telemetry; //!! needs to be initialized

    DriveState(String name, double speed, SensorModule sensors, Transition... transitions) {
        super(name, speed, sensors, transitions);
    }

    @Override
    public double getProgress() {
        return Math.abs(driveSys.getPos() - initialPos);
    }

    @Override
    public void onEntry() {
        initialPos = driveSys.getPos();
        driveSys.setSpeed(speed);
    }

    @Override
    public void doState() {
        telemetry.addData(name, String.format("Driven %f encoder counts", getProgress()));
        RobotLog.i(name, String.format("Driven %f encoder counts", getProgress()));
    }

    @Override
    public void onExit() {
        super.onExit();
        driveSys.stop();
    }
}
