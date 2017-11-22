package org.firstinspires.ftc.teamcode.statemachines;

import org.firstinspires.ftc.teamcode.controllers.BlockLift;

/**
 * Created by Derek on 11/21/2017.
 */

public class PickUpBlockState extends State {
    private BlockLift lift;
    private double power;
    private boolean releaseOnExit;

    public PickUpBlockState(String name, BlockLift lift, double power, Transition... transitions) {
        super(name, transitions);
        this.lift = lift;
        this.power = power;
        releaseOnExit = false;
    }

    public PickUpBlockState(String name, BlockLift lift, double power, boolean releaseOnExit, Transition... transitions) {
        super(name, transitions);
        this.lift = lift;
        this.power = power;
        this.releaseOnExit = releaseOnExit;
    }

    @Override
    public void onEntry() {
        super.onEntry();
        lift.clamp();
        lift.lift(power);
    }

    @Override
    public void onExit() {
        super.onExit();
        lift.lift(0.0);
        if(releaseOnExit) lift.release();
    }
}
