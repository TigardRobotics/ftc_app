package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams of team 3965 on 10/26/2016.
 */

public abstract class SensorTrans extends Transition {
    protected SensorModule sensorModule;

    @Override
    public void init(){
        sensorModule = machine.robot.getSensorModule();
    }
}
