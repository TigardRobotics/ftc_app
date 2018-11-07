package org.firstinspires.ftc.teamcode.statemachines;

/**
 * Created by Derek Williams of team 3965 on 1/12/2017.
 */

public class StateCompletedTrans extends Transition {

    StateCompletedTrans(String destination) {
        super(destination);
    }

    @Override
    public boolean test() {
        return getSource().isComplete();
    }
}
