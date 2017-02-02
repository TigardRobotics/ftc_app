package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Derek Williams of team 3965 on 2/1/2017.
 */

@TeleOp(name="Red vvsb Teleop", group="3965")
public class RedStateBasedTeleop extends StateBasedTeleop {
    @Override
    public void start() {
        color = RED;
        super.start();
    }
}
