package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Derek on 10/2/2018
 */

public class SamplingArm extends HardwareController {
    private Servo dropServo;

    // Tune these values with core discovery
    private static final double UP_POS = 0.0;
    private static final double DOWN_POS = 0.75;

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
            dropServo.setPosition(DOWN_POS);
        }
        else {
            dropServo.setPosition(UP_POS);
        }
    }

    @Override
    public void stop() {
        super.stop();
        dropServo.setPosition(UP_POS);
    }

    public void setDrop(boolean drop) {
        this.drop = drop;
    }

}
