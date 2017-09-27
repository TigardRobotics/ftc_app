package org.firstinspires.ftc.teamcode.opmodes2016;

import org.firstinspires.ftc.teamcode.RobotBase;
import org.firstinspires.ftc.teamcode.statemachines.Transition;

/**
 * Created by Derek Williams of team 3965 on 11/13/2016.
 */

public class PushButtonState extends VelocityVortexState {
    protected String buttonToPress;
    protected String sensorColor;

    public PushButtonState(String name, String color, Transition... transitions) {
        super(name, transitions);
        this.buttonToPress = color;
        if (buttonToPress != RobotBase.BLUE && buttonToPress != RobotBase.RED) {
            throw new RuntimeException("Color given for button state neither red nor blue");
        }
    }

    @Override
    public void onEntry() {
        /*
        super.onEntry();
        if(getSensorModule().getFrontColor() == buttonToPress) {
            // Push left button
            getVelocityVortexRobotBase().retractRightPusher();
            getVelocityVortexRobotBase().extendLeftPusher();
            getRobot().telemetry.addLine("pushing right button");
        }

        else if (getSensorModule().getFrontColor() != RobotBase.NO_COLOR){
            // Push right button
            getVelocityVortexRobotBase().retractLeftPusher();
            getVelocityVortexRobotBase().extendRightPusher();
            getRobot().telemetry.addLine("pushing left button");
        }
        */
    }

    @Override
    public void doState() {
        //getStateMachine().robot.telemetry.addData(name+" runtime:", runtime);
    }

    @Override
    public void onExit() {
        getVelocityVortexRobotBase().retractLeftPusher();
        getVelocityVortexRobotBase().retractRightPusher();
    }
}
