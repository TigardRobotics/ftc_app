package org.firstinspires.ftc.teamcode.opmodes2016;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotBase;

/**
 * Created by Derek Williams of team 3965 on 2/1/2017.
 */

@TeleOp(name="Red vvsb Teleop", group="3965")
public class RedStateBasedTeleop extends StateBasedTeleop {
    @Override
    public void start() {
        color = RobotBase.RED;
        super.start();
    }
}
