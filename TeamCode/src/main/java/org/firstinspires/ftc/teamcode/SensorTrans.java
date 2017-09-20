package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams of team 3965 on 10/26/2016.
 */

public abstract class SensorTrans extends Transition {
    protected SensorModule sensors;

    public SensorTrans(String destination, SensorModule sensors) {
        super(destination);
        this.sensors = sensors;
    }
}
