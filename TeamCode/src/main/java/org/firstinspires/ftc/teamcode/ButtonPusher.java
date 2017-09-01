package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Derek Williams of team 3965 on 12/13/2016.
 */

public class ButtonPusher {
    private Servo servo;
    private String side;
    private boolean isExtended;

    // Positions
    private final static double RIGHT_BUTTON_PUSHER_EXTENDED = 0.99;
    private final static double RIGHT_BUTTON_PUSHER_RETRACTED = 0.6;
    private final static double LEFT_BUTTON_PUSHER_EXTENDED = 0.6;
    private final static double LEFT_BUTTON_PUSHER_RETRACTED = 0.98;

    // Sides
    public static final String RIGHT = "RIGHT";
    public static final String LEFT = "LEFT";

    ButtonPusher(Servo servo, String side) {
        this.side = side;
        this.servo = servo;

        if (this.side != LEFT && this.side != RIGHT) {
            throw new RuntimeException("Button Pusher side not initiated correctly");
        }
    }

    public void extend() {
        isExtended = true;
        if (side == RIGHT) {
            servo.setPosition(RIGHT_BUTTON_PUSHER_EXTENDED);
        }
        else if (side == LEFT) {
            servo.setPosition(LEFT_BUTTON_PUSHER_EXTENDED);
        }
        else {
            throw new RuntimeException("Button Pusher side not initiated correctly");
        }
    }

    public void retract() {
        isExtended = false;
        if (side == RIGHT) {
            servo.setPosition(RIGHT_BUTTON_PUSHER_RETRACTED);
        }
        else if (side == LEFT) {
            servo.setPosition(LEFT_BUTTON_PUSHER_RETRACTED);
        }
        else {
            throw new RuntimeException("Button Pusher side not initiated correctly");
        }
    }

    public void toggle() {
        if(isExtended) {
            retract();
        }
        else {
            extend();
        }
    }
}
