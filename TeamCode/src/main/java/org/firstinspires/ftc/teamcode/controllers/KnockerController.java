package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Derek on 11/8/2017.
 */

public class KnockerController extends HardwareController {
    private static final double DOWN = 0.0;
    private static final double UP = 0.5;

    private Servo servo;

    public KnockerController(Servo servo) {
        this.servo = servo;
    }

    @Override
    public void init() {
        super.init();
        retract();
    }

    public void extend() {
        servo.setPosition(DOWN);
    }

    public void retract() {
        servo.setPosition(UP);
    }
}
