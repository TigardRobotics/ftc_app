package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.VV_2016.VelocityVortexRobotBase;

/**
 * Created by Derek Williams and Mark Hancock of team 3965 on 11/15/2016.
 */

@TeleOp(name="servo testing", group="3965")
public class ServoTesting extends VelocityVortexRobotBase {
    double rightButtonPusherPos = 0.5;
    double leftButtonPusherPos = 0.5;

    @Override
    public void init() {
        rightButtonPusher = hardwareMap.servo.get("right_button_pusher");
        leftButtonPusher = hardwareMap.servo.get("left_button_pusher");
    }

    @Override
    public void start() {}

    @Override
    public void loop() {
        rightButtonPusherPos += (-gamepad1.left_stick_y)/100;
        leftButtonPusherPos += (-gamepad1.right_stick_y)/100;
        leftButtonPusher.setPosition(leftButtonPusherPos);
        rightButtonPusher.setPosition(rightButtonPusherPos);
        telemetry.addData("Right pusher pos", "%f, %f", gamepad1.right_stick_y, rightButtonPusherPos);
        telemetry.addData("Left pusher pos", "%f, %f", gamepad1.left_stick_y, leftButtonPusherPos);
    }

    @Override
    public void stop() {}
}
