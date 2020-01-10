package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.ControllerConfiguration;

/**
 * Created by Mark Hancock on 11/21/2019
 */

public class DualServoGrabber extends HardwareController{
    protected Servo grabServo;
    protected Servo grabServo1;

    // Tune these values with REV Hub Interface

    private static final double DOWN_POS = 0.00;
    private static final double UP_POS = 1.00;
    private static final double DOWN_POS1 = 1.00;
    private static final double UP_POS1 = 0.10;


    private boolean drop = true;

    public DualServoGrabber(Servo servo, Servo servo1) {
        this.grabServo = servo;
        this.grabServo1 = servo1;
    }



    @Override
    public void start() {
        super.start();
        grabServo.setPosition(UP_POS);
        grabServo1.setPosition(UP_POS1);
    }

    @Override
    public void loop() {
        if(drop) {
            Robot.telemetry.addLine("Dropping both servos");
            grabServo.setPosition(DOWN_POS);
            grabServo1.setPosition(DOWN_POS1);
        }
        else {
            Robot.telemetry.addLine("Raising both servos");
            grabServo.setPosition(UP_POS);
            grabServo1.setPosition(UP_POS1);
        }
    }

    @Override
    public void stop() {
        super.stop();
        grabServo.setPosition(UP_POS);
        grabServo1.setPosition(UP_POS1);
    }
    public void setDrop(boolean drop) {
        Robot.telemetry.addLine(String.format("Setting drop: %b", drop));
        this.drop = drop;
    }
}
