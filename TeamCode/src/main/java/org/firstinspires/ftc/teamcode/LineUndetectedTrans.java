package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams of team 3965 on 11/8/2016.
 */

public class LineUndetectedTrans extends SensorTrans {
    LineUndetectedTrans (String fromStateName, String toStateName) {
        this.fromStateName = fromStateName;
        this.toStateName = toStateName;
    }

    @Override
    public boolean test() {
        return getSensorModule().isLineDetected();
    }
}