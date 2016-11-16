package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Derek Williams and Mark Hancock of team 3965 on 11/15/2016.
 */

@TeleOp(name="servo testing", group="3965")
public class ServoTesting extends VelocityVortexRobotBase {

    @Override
    public void init(){
        rightButtonPusher = hardwareMap.servo.get("right_button_pusher");
        leftButtonPusher = hardwareMap.servo.get("left_button_pusher");
    }

    @Override
    public void start(){}

    @Override
    public void loop(){
        double lb = (1+(-gamepad1.left_stick_y))/2;
        double rb = (1+(-gamepad1.right_stick_y))/2;
        leftButtonPusher.setPosition(lb);
        rightButtonPusher.setPosition(rb);
        double rba = rightButtonPusher.getPosition();
        double lba = leftButtonPusher.getPosition();
        telemetry.addData("Right pusher pos", "%f, %f", rb, rba);
        telemetry.addData("Left pusher pos", "%f, %f", lb, lba);
    }

    @Override
    public void stop(){}
}
