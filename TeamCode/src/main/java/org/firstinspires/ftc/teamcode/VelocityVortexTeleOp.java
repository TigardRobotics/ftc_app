package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Derek Williams on 10/11/2016.
 */

@TeleOp(name="VV Teleop", group="3965")
public class VelocityVortexTeleOp extends VelocityVortexRobotBase {

    @Override
    public void start(){}

    @Override
    public void loop(){
        setLeftDrivePower(-gamepad1.left_stick_y);
        setRightDrivePower(-gamepad1.right_stick_y);

        if (gamepad1.a) {
            rightButtonPusher.setPosition(100/128);
            telemetry.addLine("R OUT");
        }
        else if (gamepad1.b) {
            rightButtonPusher.setPosition(50/128);
            telemetry.addLine("R IN");
        }

        if(gamepad1.x) {
            leftButtonPusher.setPosition(100/128);
            telemetry.addLine("L OUT");
        }
        else if (gamepad1.y) {
            leftButtonPusher.setPosition(50/128);
            telemetry.addLine("L IN");
        }

        //telemetry.addData("Right pusher pos", rightButtonPusher.getPosition());
        //telemetry.addData("Left pusher pos", leftButtonPusher.getPosition());
    }

    @Override
    public void stop(){}
}
