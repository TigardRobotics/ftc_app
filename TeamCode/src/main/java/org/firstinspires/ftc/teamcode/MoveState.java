package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek on 9/19/17.
 */

public abstract class MoveState extends State {
    protected SensorModule sensors;
    protected iEncDrive driveSys;
    protected double speed;
    protected double power;

    public MoveState(String name, double power, SensorModule sensors, Transition... transitions){
        super(name, transitions);
        this.sensors = sensors;
        this.power = power;
    }
}
