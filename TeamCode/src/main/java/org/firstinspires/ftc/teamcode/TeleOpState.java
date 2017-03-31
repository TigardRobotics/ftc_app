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
        if (robot.gamepad1.dpad_right) {
            robot.extendRightPusher();
            robot.retractLeftPusher();
        }
        else if (robot.gamepad1.dpad_left){
            robot.extendLeftPusher();
            robot.retractRightPusher();
        }
        else {
            robot.retractLeftPusher();
            robot.retractRightPusher();
        }


        /**
         * Particle Collecter
         */
        if (robot.gamepad1.right_trigger > triggerActuationThreshold) {
            robot.forwardCollecter();
        }
        else if(robot.gamepad1.right_bumper) {
            robot.reverseCollecter();
        }
        else {
            robot.disableCollecter();
        }

        /**
         * Particle Launcher
         */
        if (robot.gamepad1.left_trigger > triggerActuationThreshold) {
            robot.forwardFlicker();
        }
        else if(robot.gamepad1.left_bumper) {
            robot.reverseFlicker();
        }
        else {
            robot.disableFlicker();
        }

        /**
         * Capball Lift
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
        getVelocityVortexRobotBase().disableCollecter();
        getVelocityVortexRobotBase().disableBallLift();
        getVelocityVortexRobotBase().disableFlicker();
    }
}
