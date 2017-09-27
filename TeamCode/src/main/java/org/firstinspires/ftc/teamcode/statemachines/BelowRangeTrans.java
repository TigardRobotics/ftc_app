package org.firstinspires.ftc.teamcode.statemachines;

import org.firstinspires.ftc.teamcode.SensorModule;

/**
 * Created by Derek on 9/26/17.
 */
public class BelowRangeTrans extends RangeSensorTrans {

    public BelowRangeTrans (String destination, SensorModule sensors, double range) {
        super(destination, sensors, range);
    }

    @Override
    public boolean test() {
        return sensors.getRangeCm() < range;
    }
}
