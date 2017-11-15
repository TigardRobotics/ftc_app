package org.firstinspires.ftc.teamcode.statemachines;

import org.firstinspires.ftc.teamcode.controllers.KnockerController;
import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Created by Derek on 11/8/2017.
 */

public class KnockState extends State {
    private KnockerController knocker;

    //HACK
    boolean retract_hack = false;

    public KnockState(String name, Transition... transitions) {
        super(name, transitions);
        knocker = (KnockerController) RobotBase.findController(KnockerController.class);
    }

    public KnockState(String name, boolean retract_hack, Transition... transitions) {
        super(name, transitions);
        knocker = (KnockerController) RobotBase.findController(KnockerController.class);
        this.retract_hack = retract_hack;
    }

    @Override
    public void onEntry() {
        super.onEntry();
        knocker.extend();
    }

    @Override
    public void onExit() {
        super.onExit();
        //This is a hack
        if(retract_hack) knocker.retract();
    }
}
