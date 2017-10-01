package org.firstinspires.ftc.teamcode.statemachines;

import org.firstinspires.ftc.teamcode.controllers.SensorModule;

/**
 * Created by Derek Williams on 10/26/2016.
 */

public class RangeSensorTrans extends SensorTrans {
    protected double range;

    public RangeSensorTrans (String destination, SensorModule sensors, double range) {
        super(destination, sensors);
        this.range = range;
    }

    @Override
    public boolean test() {
        return sensors.getRangeCm() == range;
    }
}

