package org.firstinspires.ftc.teamcode.statemachines;

import org.firstinspires.ftc.teamcode.controllers.TrophyDropper;
import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Created by Brandon and Derek on 11/14/2018.
 */

public class DropTrophyState extends State {
    private TrophyDropper dropper;

    public DropTrophyState(String name, Transition... transitions) {
        super(name, transitions);
        dropper = (TrophyDropper) RobotBase.findController(TrophyDropper.class);
    }

    @Override
    public void onEntry() {
        super.onEntry();
        dropper.setDrop(true);
    }

    @Override
    public void onExit(){
        super.onExit();
        //dropper.setDrop(false);
    }
}
