package org.firstinspires.ftc.teamcode.statemachines;

import org.firstinspires.ftc.teamcode.controllers.HardwareController;
import org.firstinspires.ftc.teamcode.controllers.IDrive;
import org.firstinspires.ftc.teamcode.controllers.SensorModule;
import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Created by Derek on 9/19/17.
 */

public abstract class MoveState extends State {
    protected SensorModule sensors;
    protected double speed;
    protected IDrive drive;

    public MoveState(String name, double speed, SensorModule sensors, Transition... transitions){
        super(name, transitions);
        this.sensors = sensors;
        this.speed = speed;
        drive = (IDrive)(RobotBase.findController(IDrive.class));
    }
}
