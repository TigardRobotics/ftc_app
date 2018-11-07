package org.firstinspires.ftc.teamcode.statemachines;

/**
 * Created by Derek on 9/26/17.
 */
public class AboveRangeTrans extends RangeTrans {

    public AboveRangeTrans (String destination, double range) {
        super(destination, range);
    }

    @Override
    public boolean test() {
        return sensor.getRangeCm() > range;
    }
}
