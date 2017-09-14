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
        /**
         * Drive motors
         */
        setSquareLeftDrivePower(gamepad1.left_stick_y);
        setSquareRightDrivePower(gamepad1.right_stick_y);


        /**
         * Button Pushers
         */
        if (gamepad1.a) {
            //double rightButtonPos = RIGHT_BUTTON_PUSHER_EXTENDED;
            //rightButtonPusher.setPosition(rightButtonPos);
            //telemetry.addData("R OUT", rightButtonPos);
            extendRightPusher();
        }
        else if (gamepad1.b) {
            //double rightButtonPos = RIGHT_BUTTON_PUSHER_RETRACTED;
            //rightButtonPusher.setPosition(rightButtonPos);
            //telemetry.addData("R IN", rightButtonPos);
            retractRightPusher();
        }

        if(gamepad1.x) {
            //double leftButtonPos = LEFT_BUTTON_PUSHER_EXTENDED;
            //leftButtonPusher.setPosition(leftButtonPos);
            //telemetry.addData("L OUT", leftButtonPos);
            extendLeftPusher();
        }
        else if (gamepad1.y) {
            //double leftButtonPos = LEFT_BUTTON_PUSHER_RETRACTED;
            //leftButtonPusher.setPosition(leftButtonPos);
            //telemetry.addData("L IN", leftButtonPos);
            retractLeftPusher();
        }
        //telemetry.addData("Right pusher pos", rightButtonPusher.getPosition());
        //telemetry.addData("Left pusher pos", leftButtonPusher.getPosition());


        /**
         * Particle Flicker
         */
        if(gamepad1.right_bumper) {
            enableFlicker();
        }
        else {
            disableFlicker();
        }


        /**
         * Particle Collector
         */
        if (gamepad1.left_bumper) {
            enableLifter();
            enableCollector();
        }
        else {
            disableLifter();
            disableCollector();
        }

        /**
         * Line follow
         *
        if(gamepad1.dpad_up && (!stateMachine.isActive())) {
            stateMachine.setCurrentState("follow");
        }
        if (gamepad1.dpad_down && stateMachine.isActive()) {
            stateMachine.deactivate();
        }
        stateMachine.step();
         */
    }

    @Override
    public void stop(){}
}
