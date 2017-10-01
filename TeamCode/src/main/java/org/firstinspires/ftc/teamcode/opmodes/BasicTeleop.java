package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Derek Williams of team 3965 on 10/9/2016.
 */

@TeleOp(name="Basic Teleop", group="3965")
public class BasicTeleop extends RobotBase {

    @Override
    public void loop(){
        Drive.setLeftDrivePower(gamepad1.left_stick_y);
        Drive.setRightDrivePower(gamepad1.right_stick_y);
    }
}
