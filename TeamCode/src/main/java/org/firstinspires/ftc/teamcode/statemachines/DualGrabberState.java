package org.firstinspires.ftc.teamcode.statemachines;

import org.firstinspires.ftc.teamcode.controllers.DualServoGrabber;
import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

public class DualGrabberState extends State{
    private DualServoGrabber grabber;

    private boolean drop;

    public DualGrabberState(String name, boolean drop, Transition... transitions) {
        super(name, transitions);
        this.drop = drop;
        grabber = (DualServoGrabber) RobotBase.findController(DualServoGrabber.class);
    }

    @Override
    public void onEntry() {
        super.onEntry();
        grabber.setDrop(drop);
    }

    @Override
    public void onExit(){
        super.onExit();
    }
}

