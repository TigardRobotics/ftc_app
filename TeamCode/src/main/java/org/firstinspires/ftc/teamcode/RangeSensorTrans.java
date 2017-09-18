package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams on 10/26/2016.
 */

public class RangeSensorTrans extends SensorTrans {
    protected double range;

    public RangeSensorTrans (String destination, double range) {
        super(destination);
        this.range = range;
    }

    @Override
    public boolean test() {
        return getSensorModule().getRangeCm() == range;
    }
}

class BelowRangeTrans extends RangeSensorTrans {

    BelowRangeTrans (String destination, double range) {
        super(destination, range);
    }

    @Override
    public boolean test() {
        return getSensorModule().getRangeCm() < range;
    }
}

class AboveRangeTrans extends RangeSensorTrans {

    AboveRangeTrans (String destination, double range) {
        super(destination, range);
    }

    @Override
    public boolean test() {
        return getSensorModule().getRangeCm() > range;
    }
}

