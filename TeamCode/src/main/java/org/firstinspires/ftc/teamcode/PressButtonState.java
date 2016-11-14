package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams of team 3965 on 11/13/2016.
 */

public class PressButtonState extends State {
    protected String buttonToPress;
    String leftColor;

    PressButtonState (String name, String color) {
        this.name = name;
        this.buttonToPress = color;
        if (buttonToPress != "blue" && buttonToPress != "red") {
            throw new RuntimeException("Color given for button state neither red nor blue");
        }
    }

    @Override
    public void start() {
        super.start();
        leftColor = getStateMachine().robot.getSensorModule().getFrontColor();
        if(leftColor == buttonToPress) {
            // Push left button
        }
        else {
            // Push right button
        }
    }

    @Override
    public void loop() {
        getStateMachine().robot.telemetry.addData(name+" runtime:", runtime);
    }

    @Override
    public void stop() {

    }
}
