package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams of team 3965 on 11/8/2016.
 */

public class LineDetectedTrans extends SensorTrans {
    public LineDetectedTrans (String destination) {
        super(destination);
    }

    @Override
    public boolean test() {
        return getSensorModule().isLineDetected();
    }
}
