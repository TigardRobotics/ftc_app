package org.firstinspires.ftc.teamcode.statemachines;

import org.firstinspires.ftc.teamcode.controllers.RangeController;
import org.firstinspires.ftc.teamcode.controllers.SensorModule;
import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Created by Derek Williams on 10/26/2016.
 */

public class RangeTrans extends Transition {
    protected double range;
    protected RangeController sensor;

    public RangeTrans(String destination, double range) {
        super(destination);
        this.range = range;
        sensor = (RangeController)RobotBase.findController(RangeController.class);
    }

    @Override
    public boolean test() {
        return sensor.getRangeCm() == range;
    }
}

