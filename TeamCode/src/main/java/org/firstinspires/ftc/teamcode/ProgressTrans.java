package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams on 10/16/2016.
 */

public class ProgressTrans extends Transition {
    protected double progress;

    ProgressTrans(String fromStateName, String toStateName, double progress) {
        this.source = fromStateName;
        this.destination = toStateName;
        this.progress = progress;
    }

    public boolean test() {
        return getStateMachine().getState(source).getProgress() >= progress;
    }
}
