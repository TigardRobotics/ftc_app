package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams of team 3965 on 1/31/2017.
 */

public class TeleOpState extends VelocityVortexState {
    protected double stickPressedPowerFactor = 0.3;
    protected double triggerActuationThreshold = 0.5;

    TeleOpState(String name) {
        this.name = name;
    }

    public void start() {
        getRobot().stopDriveMotors();
    }

    public void loop() {
        VelocityVortexRobotBase robot = getVelocityVortexRobotBase();

        /**
         * Drive motors
         */
       // if(robot.gamepad1.left_stick_button) robot.setSquareLeftDrivePower(robot.gamepad1.left_stick_y*stickPressedPowerFactor);
         robot.setSquareLeftDrivePower(-robot.gamepad1.left_stick_y);
       // if(robot.gamepad1.right_stick_button) robot.setSquareRightDrivePower(robot.gamepad1.right_stick_y*stickPressedPowerFactor);
         robot.setSquareRightDrivePower(-robot.gamepad1.right_stick_y);

        /**
         * Button Pushers
         */
        if (robot.gamepad1.b) {
            //double rightButtonPos = RIGHT_BUTTON_PUSHER_EXTENDED;
            //rightButtonPusher.setPosition(rightButtonPos);
            //telemetry.addData("R OUT", rightButtonPos);
            robot.extendRightPusher();
        }
        else {
            //double rightButtonPos = RIGHT_BUTTON_PUSHER_RETRACTED;
            //rightButtonPusher.setPosition(rightButtonPos);
            //telemetry.addData("R IN", rightButtonPos);
            robot.retractRightPusher();
        }

        if(robot.gamepad1.x) {
            //double leftButtonPos = LEFT_BUTTON_PUSHER_EXTENDED;
            //leftButtonPusher.setPosition(leftButtonPos);
            //telemetry.addData("L OUT", leftButtonPos);
            robot.extendLeftPusher();
        }
        else {
            //double leftButtonPos = LEFT_BUTTON_PUSHER_RETRACTED;
            //leftButtonPusher.setPosition(leftButtonPos);
            //telemetry.addData("L IN", leftButtonPos);
            robot.retractLeftPusher();
        }
        //telemetry.addData("Right pusher pos", rightButtonPusher.getPosition());
        //telemetry.addData("Left pusher pos", leftButtonPusher.getPosition());


        /**
         * Particle Flicker
         */
        if (robot.gamepad1.right_trigger > triggerActuationThreshold) {
            robot.enableLifter();
        }
        else {
            robot.disableLifter();
        }

        /**
         * Particle Collector
         */
        if (robot.gamepad1.left_trigger > triggerActuationThreshold) {
            robot.enableFlicker();
        }
        else {
            robot.disableFlicker();
        }

        /**
         * Particle Collector
         */
        if (robot.gamepad1.dpad_up) {
            robot.raiseBallLift();
        }
        else if (robot.gamepad1.dpad_down) {
            robot.lowerBallLift();
        }
        else{
            robot.disableBallLift();
        }

    }

    public void stop() {
        getRobot().stopDriveMotors();
        getVelocityVortexRobotBase().disableLifter();
        getVelocityVortexRobotBase().disableBallLift();
        getVelocityVortexRobotBase().disableFlicker();
    }
}
