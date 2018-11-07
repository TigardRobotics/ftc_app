package org.firstinspires.ftc.teamcode.statemachines;

/**
 * Created by Derek Williams on 10/16/2016.
 */

public class ProgressTrans extends Transition {
    protected double progress;

    public ProgressTrans(String destination, double progress) {
        super(destination);
        this.progress = progress;
    }

    public boolean test() {
        //return getStateMachine().getState(source).getProgress() >= progress;
        return getSource().getProgress() >= progress;
    }
}
