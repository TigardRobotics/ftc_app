package org.firstinspires.ftc.teamcode.statemachines;

import org.firstinspires.ftc.teamcode.controllers.KnockerController;
import org.firstinspires.ftc.teamcode.controllers.RobotHanger;
import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Created by Derek on 10/26/18.
 */

public class HangState extends State {
    private RobotHanger hanger;
    private double speed;

    //Constants to raise or lower robot
    public static final double RAISE_SPEED = 0.95;
    public static final double LOWER_SPEED = -0.95;

    public HangState(String name, double speed, Transition... transitions) {
        super(name, transitions);
        this.speed = speed;
        hanger = (RobotHanger) RobotBase.findController(RobotHanger.class);
    }

    @Override
    public void onEntry() {
        super.onEntry();
        hanger.setSpeed(speed);
    }

    @Override
    public void onExit() {
        super.onExit();
        hanger.setSpeed(0.0);
    }
}
