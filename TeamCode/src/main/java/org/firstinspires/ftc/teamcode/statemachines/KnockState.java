package org.firstinspires.ftc.teamcode.statemachines;

import org.firstinspires.ftc.teamcode.controllers.KnockerController;
import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Created by Derek on 11/8/2017.
 */

public class KnockState extends State {
    private KnockerController knocker;

    public KnockState(String name, Transition... transitions) {
        super(name, transitions);
        knocker = (KnockerController) RobotBase.findController(KnockerController.class);
    }

    @Override
    public void onEntry() {
        super.onEntry();
        knocker.extend();
    }

    @Override
    public void onExit() {
        super.onExit();
        //This is kindof a hack
        //knocker.retract();
    }
}
