package org.firstinspires.ftc.teamcode.statemachines;

/**
 * Created by Derek Williams on 10/17/2016.
 */

public class TimeTrans extends Transition {
    double triggerAtTime;

    public TimeTrans(String destination, double time) {
        super(destination);
        triggerAtTime = time;
    }

    public boolean test(){
        //return getFromState().runtime.time() >= triggerAtTime;
        return getSource().runtime.time() >= triggerAtTime;
    }
}
