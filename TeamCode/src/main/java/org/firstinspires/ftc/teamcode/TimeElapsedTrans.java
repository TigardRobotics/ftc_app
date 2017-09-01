package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams on 10/17/2016.
 */

public class TimeElapsedTrans extends Transition {
    double triggerAtTime;

    TimeElapsedTrans(String from_state_name, String to_state_name, double time) {
        fromStateName = from_state_name;
        toStateName = to_state_name;
        triggerAtTime = time;
    }

    public boolean test(){
        return getFromState().runtime.time() >= triggerAtTime;
    }
}
