package org.firstinspires.ftc.teamcode.statemachines;

import org.firstinspires.ftc.teamcode.controllers.HardwareController;
import org.firstinspires.ftc.teamcode.controllers.IDrive;
import org.firstinspires.ftc.teamcode.controllers.SensorModule;

/**
 * Created by Derek on 9/19/17.
 */

public abstract class MoveState extends State {
    protected SensorModule sensors;
    protected double speed;
    protected double power;
    protected IDrive Drive;

    public MoveState(String name, double power, SensorModule sensors, Transition... transitions){
        super(name, transitions);
        this.sensors = sensors;
        this.power = power;
        Drive = (IDrive)(HardwareController.Find(Robot.Controllers,IDrive.class));
    }
}
