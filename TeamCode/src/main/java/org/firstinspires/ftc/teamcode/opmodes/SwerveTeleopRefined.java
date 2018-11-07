package org.firstinspires.ftc.teamcode.opmodes;

import org.firstinspires.ftc.teamcode.Tools;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;
import org.firstinspires.ftc.teamcode.statemachines.WaitState;

/**
 * Refined Teleop for Swerve Drive
 */

public class SwerveTeleopRefined extends SwerveBase {

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void start() {
        super.start();
        stateMachine = new StateMachine(new WaitState("wait"));
    }

    @Override
    public void loop() {
        super.loop();

        boolean leftActive = getGamepad1LeftJoystickAmplitude() > 0.1;
        boolean rightActive = getGamepad1RightJoystickAmplitude() > 0.1;

        //boolean directionReached = Math.abs(drive.getMeanDirectionError()) < 4.0;
        double errorFactor = 1.0 / Math.max(1.0, Math.abs(drive.getMeanDirectionError()) - 3.0);

        // Spin
        if(leftActive && rightActive) {
            drive.spinMode();

            double lpow = -gamepad1.left_stick_x * Math.abs(gamepad1.left_stick_x);
            double rpow = -gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x);
            double power = (lpow+rpow)/2.0;

            drive.setDrivePower(errorFactor*power);

            telemetry.addLine("Spin mode (power: "+power+")");
        }

        // Steer
        if(leftActive && !rightActive) {
            double powerMag = Math.pow(getGamepad1LeftJoystickAmplitude(), 2.0);

            double steer = gamepad1.left_stick_x * Math.abs(gamepad1.left_stick_x);
            double power = (gamepad1.left_stick_y < 0.0)? powerMag : -powerMag;

            drive.setDirection(0.0, steer);
            drive.setDrivePower(errorFactor*power);

            telemetry.addLine("Steer mode (steer: "+steer+", power: "+power+")");
        }

        // Crab
        if(!leftActive && rightActive) {
            double power = Math.pow(getGamepad1RightJoystickAmplitude(), 2.0);

            double theta = getRightJAngle();
            double s = 90.0;
            double pi = Math.PI;
            double x = (pi * theta) / s;

            double direction = (s/pi)*(x - (Math.sin(x)*Math.cos(x)));

            drive.forceDirection(Tools.frontize(direction));

            if(Tools.notFrontized(direction)) drive.setDrivePower(-power);
            else drive.setDrivePower(errorFactor*power);

            telemetry.addData("right joystick angle", getRightJAngle());
            telemetry.addLine("Crab mode (direction: "+direction+", power: "+power+")");
        }

        // Hold
        if(!leftActive && !rightActive) {
            drive.setDrivePower(0.0);
            drive.setDirection(0.0, 0.0);
        }

        //Logging statements
        drive.getDrivePosition();
    }
}
