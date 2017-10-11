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
    protected double power;
    protected IDrive drive;

    public MoveState(String name, double power, SensorModule sensors, Transition... transitions){
        super(name, transitions);
        this.sensors = sensors;
        this.power = power;
        drive = (IDrive)(RobotBase.findController(IDrive.class));
    }
}
