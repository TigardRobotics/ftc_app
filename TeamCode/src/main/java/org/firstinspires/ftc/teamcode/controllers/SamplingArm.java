package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Derek on 10/2/2018
 */

public class SamplingArm extends HardwareController {
    protected Servo dropServo;

    // Tune these values with core discovery

    private static final double UP_POS = 0.75;
    private static final double DOWN_POS = 0.0;

    // Clockwise 0
    // Counterclockwise 255

    private boolean drop = false;

    public SamplingArm(Servo servo) {
        this.dropServo = servo;
    }

    @Override
    public void init() {
        super.init();
        dropServo.setPosition(UP_POS);
    }

    @Override
    public void loop() {
        if(drop) {
            Robot.telemetry.addLine("Dropping one servo");
            dropServo.setPosition(DOWN_POS);
        }
        else {
            Robot.telemetry.addLine("Raising one servo");
            dropServo.setPosition(UP_POS);
        }
    }

    @Override
    public void stop() {
        super.stop();
        dropServo.setPosition(UP_POS);
    }

    public void setDrop(boolean drop) {
        Robot.telemetry.addLine(String.format("Setting drop: %b", drop));
        this.drop = drop;
    }

}
