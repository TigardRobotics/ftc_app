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
        sensorModule = machine.robot.getSensorModule();
    }

    @Override
    public boolean test() {
        return sensorModule.getRangeCm() == range;
    }
}

/*
class BelowRangeTrans extends RangeSensorTrans {

    @Override
    public boolean test() {
        return sensorModule.getRangeCm() < range;
    }
}

class AboveRangeTrans extends RangeSensorTrans {

    @Override
    public boolean test() {
        return sensorModule.getRangeCm() > range;
    }
}
*/
