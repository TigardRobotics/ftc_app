package org.firstinspires.ftc.teamcode;

/**
 * Created by Robotics on 1/31/2017.
 */

public class TeleOpState extends VelocityVortexState {
    TeleOpState(String name) {
        this.name = name;
        complete = false;
    }

    public void start() {
        getRobot().stopDriveMotors();
        complete = false;
    }

    public void loop() {
        VelocityVortexRobotBase robot = getVelocityVortexRobotBase();

        if(robot.gamepad1.dpad_up) {
            complete = true;
            return;
        }

        /**
         * Drive motors
         */
        robot.setSquareLeftDrivePower(robot.gamepad1.left_stick_y);
        robot.setSquareRightDrivePower(robot.gamepad1.right_stick_y);


        /**
         * Button Pushers
         */
        if (robot.gamepad1.a) {
            //double rightButtonPos = RIGHT_BUTTON_PUSHER_EXTENDED;
            //rightButtonPusher.setPosition(rightButtonPos);
            //telemetry.addData("R OUT", rightButtonPos);
            robot.extendRightPusher();
        }
        else if (robot.gamepad1.b) {
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
        else if (robot.gamepad1.y) {
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
        if(robot.gamepad1.right_bumper) {
            robot.enableFlicker();
        }
        else {
            robot.disableFlicker();
        }

        /**
         * Particle Collector
         */
        if (robot.gamepad1.left_bumper) {
            robot.enableLifter();
            robot.enableCollector();
        }
        else {
            robot.disableLifter();
            robot.disableCollector();
        }
    }

    public void stop() {
        getRobot().stopDriveMotors();
        complete = false;
    }
}
