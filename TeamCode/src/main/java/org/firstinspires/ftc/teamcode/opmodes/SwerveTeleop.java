package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Color;
import org.firstinspires.ftc.teamcode.Names;
import org.firstinspires.ftc.teamcode.Tools;
import org.firstinspires.ftc.teamcode.controllers.ColorController;
import org.firstinspires.ftc.teamcode.controllers.KnockerController;
import org.firstinspires.ftc.teamcode.controllers.SwerveUnit;
import org.firstinspires.ftc.teamcode.controllers.SwerveDrive;
import org.firstinspires.ftc.teamcode.opmodes.SwerveBase;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;
import org.firstinspires.ftc.teamcode.statemachines.WaitState;

/**
 * Teleop for Swerve Drive
 */

@TeleOp(name="Swerve Teleop", group="3965")
public class SwerveTeleop extends SwerveBase {

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void start() {
        super.start();
        stateMachine = new StateMachine(new WaitState("wait"));
    }

    boolean drive_sideways = false;

    @Override
    public void loop() {
        super.loop();

        if(gamepad1.right_stick_button) {
            drive.spinMode();
            double spinPower = -gamepad1.left_stick_x * Math.abs(gamepad1.left_stick_x); //speed is Left joystick vertical (with square acceleration)
            drive.setDrivePower(spinPower);
            telemetry.addData("spin power", spinPower);
        }
        else {  //SetDirection will exit Spin Mode and set driving direction
            double crab_direction = getGamepad1RightJoystickAngle(); //crab direction is right joystick direction
            //!WORKAROUND Prevent facing the hall hole (see comments in Swerve unit)
            if (crab_direction > 160 && crab_direction < 200) {
                if (crab_direction < 180) crab_direction = 160;
                else crab_direction = 200;
            }
            double steer_direction = Math.pow(gamepad1.left_stick_x, 3.0); //steer direction is left joystick horizontal
            //if(steer_direction < 0.15 && steer_direction > -0.15) steer_direction = 0.0;

            //Shortcut to crab sideways
            //TODO: reconsider
            if(gamepad1.left_stick_button) {
                drive_sideways = true;
            }
            if(getGamepad1RightJoystickAmplitude() > 0.01 || gamepad1.right_stick_button) {
                drive_sideways = false;
            }
            if(drive_sideways) {
                crab_direction = 270.0;
            }

            drive.setDirection(crab_direction, steer_direction);
            telemetry.addData("steer direction", steer_direction);
            telemetry.addData("crab direction", crab_direction);

            //double drive_power = -gamepad1.left_stick_y * Math.abs(gamepad1.left_stick_y); //speed is Left joystick vertical (with square acceleration)
            double drive_power = gamepad1.left_stick_y*gamepad1.left_stick_y + gamepad1.left_stick_x*gamepad1.left_stick_x;
            if(gamepad1.left_stick_y > 0.0) drive_power *= -1.0; // Make power negative if stick is down
            drive.setDrivePower(drive_power);
            telemetry.addData("drive power", drive_power);

            //Logging statements
            drive.getDrivePosition();
        }
    }
}