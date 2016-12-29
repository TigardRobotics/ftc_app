package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams of team 3965 on 11/13/2016.
 */

public class PushButtonState extends VelocityVortexState {
    protected String buttonToPress;
    protected String sensorColor;

    PushButtonState(String name, String color) {
        this.name = name;
        this.buttonToPress = color;
        if (buttonToPress != RobotBase.BLUE && buttonToPress != RobotBase.RED) {
            throw new RuntimeException("Color given for button state neither red nor blue");
        }
    }

    @Override
    public void start() {
        super.start();
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
    }

    @Override
    public void loop() {
        //getStateMachine().robot.telemetry.addData(name+" runtime:", runtime);
    }

    @Override
    public void stop() {
        getVelocityVortexRobotBase().retractLeftPusher();
        getVelocityVortexRobotBase().retractRightPusher();
    }
}
