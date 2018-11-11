package org.firstinspires.ftc.teamcode.statemachines;

/**
 * Created by Derek on 9/26/17.
 */
public class BelowRangeTrans extends RangeTrans {

    public BelowRangeTrans (String destination, double range) {
        super(destination, range);
    }

    @Override
    public boolean test() {
        return sensor.getRangeCm() < range;
    }
}