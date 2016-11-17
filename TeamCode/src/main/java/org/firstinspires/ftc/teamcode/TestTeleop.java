package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Derek Williams of team 3965 on 11/15/2016.
 */

@TeleOp(name="Test Teleop", group="3965")
public class TestTeleop extends VelocityVortexRobotBase {

    @Override
    public void start(){}

    @Override
    public void loop(){
        setLeftDrivePower(gamepad1.left_stick_y*Math.abs(gamepad1.left_stick_y));
        setRightDrivePower(gamepad1.right_stick_y*Math.abs(gamepad1.right_stick_y));

        if (gamepad1.a) {
            double rightButtonPos = RIGHT_BUTTON_PUSHER_EXTENDED;
            rightButtonPusher.setPosition(rightButtonPos);
            telemetry.addData("R OUT", rightButtonPos);
        }
        else if (gamepad1.b) {
            double rightButtonPos = RIGHT_BUTTON_PUSHER_RETRACTED;
            rightButtonPusher.setPosition(rightButtonPos);
            telemetry.addData("R IN", rightButtonPos);
        }

        if(gamepad1.x) {
            double leftButtonPos = LEFT_BUTTON_PUSHER_EXTENDED;
            leftButtonPusher.setPosition(leftButtonPos);
            telemetry.addData("L OUT", leftButtonPos);
        }
        else if (gamepad1.y) {
            double leftButtonPos = LEFT_BUTTON_PUSHER_RETRACTED;
            leftButtonPusher.setPosition(leftButtonPos);
            telemetry.addData("L IN", leftButtonPos);
        }


        //telemetry.addData("Right pusher pos", rightButtonPusher.getPosition());
        //telemetry.addData("Left pusher pos", leftButtonPusher.getPosition());
    }

    @Override
    public void stop(){}
}
