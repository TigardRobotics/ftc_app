package org.firstinspires.ftc.teamcode.statemachines;

import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Created by Derek Williams on 10/17/2016.
 */

public class GlobalTimeTrans extends Transition {
    double triggerAtTime;

    public GlobalTimeTrans(String destination, double time) {
        super(destination);
        triggerAtTime = time;
    }

    public boolean test(){
        //return getFromState().runtime.time() >= triggerAtTime;
        return RobotBase.instance.stopwatch.time() >= triggerAtTime;
    }
}
