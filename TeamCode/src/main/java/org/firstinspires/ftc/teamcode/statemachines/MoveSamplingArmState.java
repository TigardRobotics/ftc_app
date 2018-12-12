package org.firstinspires.ftc.teamcode.statemachines;

import org.firstinspires.ftc.teamcode.controllers.SamplingArm;
import org.firstinspires.ftc.teamcode.controllers.TflowController;
import org.firstinspires.ftc.teamcode.controllers.TrophyDropper;
import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Created by Derek on 11/27/2018.
 */

public class MoveSamplingArmState extends State {
    private SamplingArm arm;

    private boolean drop;

    public MoveSamplingArmState(String name, boolean drop, Transition... transitions) {
        super(name, transitions);
        this.drop = drop;
        arm = (SamplingArm) RobotBase.findController(SamplingArm.class);
    }

    @Override
    public void onEntry() {
        super.onEntry();
        arm.setDrop(drop);
    }

    @Override
    public void onExit(){
        super.onExit();
    }
}
