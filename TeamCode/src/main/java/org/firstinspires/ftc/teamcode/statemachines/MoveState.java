package org.firstinspires.ftc.teamcode.statemachines;

import org.firstinspires.ftc.teamcode.controllers.HardwareController;
import org.firstinspires.ftc.teamcode.controllers.IDrive;
import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Created by Derek on 9/19/17.
 */

public abstract class MoveState extends State {
    protected double speed;
    protected IDrive drive;

    public MoveState(String name, double speed, Transition... transitions){
        super(name, transitions);
        this.speed = speed;
        drive = (IDrive)(RobotBase.findController(IDrive.class));
    }
}
