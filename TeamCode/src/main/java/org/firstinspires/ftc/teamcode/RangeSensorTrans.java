package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams on 10/26/2016.
 */

public class RangeSensorTrans extends SensorTrans {
    protected double range;

    RangeSensorTrans (String fromStateName, String toStateName, double range) {
        this.fromStateName = fromStateName;
        this.toStateName = toStateName;
        this.range = range;
    }

    @Override
    public boolean test() {
        return sensorModule.getRangeCm() == range;
    }
}

class BelowRangeTrans extends RangeSensorTrans {

    BelowRangeTrans (String fromStateName, String toStateName, double range) {
        super(fromStateName, toStateName, range);
    }

    @Override
    public boolean test() {
        return sensorModule.getRangeCm() < range;
    }
}

class AboveRangeTrans extends RangeSensorTrans {

    AboveRangeTrans (String fromStateName, String toStateName, double range) {
        super(fromStateName, toStateName, range);
    }

    @Override
    public boolean test() {
        return sensorModule.getRangeCm() > range;
    }
}

