package org.firstinspires.ftc.teamcode;

import com.qualcomm.ftccommon.DbgLog;

/**
 * Created by Derek Williams of team 3965 on 11/19/2016.
 */

public class EdgeFollowState extends State {
    protected double power;

    EdgeFollowState (String name, double power) {
        this.name = name;
        this.power = power;
        if (power < -1 || power > 1) {
            throw new RuntimeException("Setting drive motors power outside of acceptable range in "+name);
        }
    }
    @Override
    public void start() {
        super.start();
        getRobot().telemetry.addLine("Starting Edge Detect");
    }

    @Override
    public void loop() {
        if (getSensorModule().isLineDetected()) {
            getRobot().setLeftDrivePower(power);
            getRobot().setRightDrivePower(0.0);
            String msg = "Detected: "+getSensorModule().getLastDetect()+" Looking for grey tile";
            getRobot().telemetry.addLine(msg);
            DbgLog.msg(msg);
        }
        else {
            getRobot().setLeftDrivePower(0.0);
            getRobot().setRightDrivePower(power);
            String msg = "Detected: "+getSensorModule().getLastDetect()+" Looking for white tile";
            getRobot().telemetry.addLine(msg);
            DbgLog.msg(msg);
        }
        //getRobot().telemetry.addData("Power", power);
    }

    @Override
    public void stop() {
        getRobot().stopDriveMotors();
        getRobot().telemetry.addLine("Stopping Edge Detect");
    }
}
