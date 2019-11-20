package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Derek on 10/2/2018
 */

public class DualServoGrabber extends SamplingArm {
    private Servo secondServo;

    // Tune these values with core discovery

    private static final double UP_POS1 = 0.75;
    private static final double DOWN_POS1 = 0.0;
    private static final double UP_POS2 = 1-UP_POS1;
    private static final double DOWN_POS2 = 1-DOWN_POS1;
    // Clockwise 0
    // Counterclockwise 255

    private boolean drop = false;

    public DualServoGrabber(Servo servo1, Servo servo2) {
        super(servo1);
        this.secondServo = servo2;
    }

    @Override
    public void init() {
        super.init();
        dropServo.setPosition(UP_POS1);
        secondServo.setPosition(UP_POS2);
    }

    @Override
    public void loop() {
        if(drop) {
            dropServo.setPosition(DOWN_POS1);
            secondServo.setPosition(DOWN_POS2);
        }
        else {
            dropServo.setPosition(UP_POS1);
            secondServo.setPosition(UP_POS2);
        }
    }

    @Override
    public void stop() {
        super.stop();
        dropServo.setPosition(UP_POS1);
        secondServo.setPosition(UP_POS2);
    }

}
