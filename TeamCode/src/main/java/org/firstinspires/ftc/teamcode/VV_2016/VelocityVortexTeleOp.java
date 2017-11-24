package org.firstinspires.ftc.teamcode.VV_2016;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.statemachines.Transition;

/**
 * Created by Derek Williams on 10/11/2016.
 */

@TeleOp(name="VV Teleop", group="3965")
@Disabled
public class VelocityVortexTeleOp extends VelocityVortexRobotBase {

    @Override
    public void start(){
        /*
        stateMachine.add(new EdgeFollowState("follow", followSpeed));
        //stateMachine.add(new BelowRangeTrans("follow", null, rangeToBeacon));
        */
    }

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
    }

    @Override
    public void stop() {
        //stateMachine.stop();
    }
    public void setSquareLeftDrivePower(double power) {
        Drive.setLeftDrivePower(power*Math.abs(power));
    }

    public void setSquareRightDrivePower(double power) {
        Drive.setRightDrivePower(power*Math.abs(power));
    }

}
