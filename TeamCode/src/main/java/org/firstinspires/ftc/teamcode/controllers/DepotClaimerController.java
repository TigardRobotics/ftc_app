package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Derek on 10/26/2018.
 */

public class DepotClaimerController extends HardwareController {
    private static final double DOWN = 0.13;
    private static final double UP = 0.8;

    private Servo servo;

    public DepotClaimerController(Servo servo) {
        this.servo = servo;
    }

    @Override
    public void init() {
        super.init();
        retract();
    }

    public void deposit() {
        servo.setPosition(DOWN);
    }

    public void retract() {
        servo.setPosition(UP);
    }
}
