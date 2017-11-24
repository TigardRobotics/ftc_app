package org.firstinspires.ftc.teamcode.statemachines;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.controllers.IOrientation;
import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Spin until the desired heading is reached
 * Note: Progress is absolute Heading
 */

public class SpinToHeadingState extends SpinState {
    protected double targetHeading = 0;
    protected IOrientation gyro;
    protected double maxSpeed;

    static final double SPIN_COOEF = 1/45.0;   //Set max spin at 45deg error

    SpinToHeadingState(String name, double speed, double targetHeading, Transition... transitions) {
        super(name, speed, transitions);
        this.targetHeading = targetHeading;
        gyro = (IOrientation)(RobotBase.findController(IOrientation.class));
        maxSpeed = Math.abs(speed);
    }

    @Override
    public double getProgress() {
        return gyro.getBearing();
    }

    @Override
    public void onEntry() {
        speed = getSpeed();
        super.onEntry();
    }

    @Override
    public void doState() {
        speed = getSpeed();
        super.doState();
    }

    protected double getSpeed() {
        //calculate direction error (-180 to +180)
        double error = (targetHeading - gyro.getBearing() + 360.0 + 180.0) % 360.0 - 180.0;
        return maxSpeed*Range.clip(error * SPIN_COOEF, -1, 1);
    }

}
