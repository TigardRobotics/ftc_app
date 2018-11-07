package org.firstinspires.ftc.teamcode.VV_2016;

import org.firstinspires.ftc.teamcode.VV_2016.SensorModule;
import org.firstinspires.ftc.teamcode.statemachines.Transition;

/**
 * Created by Derek Williams of team 3965 on 10/26/2016.
 */
//! Get rid of this
public abstract class SensorTrans extends Transition {
    protected SensorModule sensors;

    public SensorTrans(String destination, SensorModule sensors) {
        super(destination);
        this.sensors = sensors;
    }
}
