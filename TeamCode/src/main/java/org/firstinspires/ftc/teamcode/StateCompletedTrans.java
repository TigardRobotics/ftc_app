package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams of team 3965 on 1/12/2017.
 */

public class StateCompletedTrans extends Transition {

    StateCompletedTrans(String fromStateName, String toStateName) {
        this.fromStateName = fromStateName;
        this.toStateName = toStateName;
    }

    @Override
    public boolean test() {
        return getFromState().isComplete();
    }
}
