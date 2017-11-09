package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Names;
import org.firstinspires.ftc.teamcode.Tools;
import org.firstinspires.ftc.teamcode.controllers.KnockerController;
import org.firstinspires.ftc.teamcode.controllers.SwerveUnit;
import org.firstinspires.ftc.teamcode.controllers.SwerveDrive;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;
import org.firstinspires.ftc.teamcode.statemachines.WaitState;

/**
 * Created by Derek on 10/26/2017.
 */

@TeleOp(name="Swerve Teleop", group="3965")
public class SwerveTeleop extends SwerveBase {

    KnockerController knocker;

    @Override
    public void init() {
        super.init();
        addControllers(new KnockerController(hardwareMap.servo.get(Names.knockServo)));
        knocker = (KnockerController)findController(KnockerController.class);
    }

    @Override
    public void start() {
        super.start();
        stateMachine = new StateMachine(new WaitState("wait"));
    }

    @Override
    public void loop() {
        super.loop();

        if(gamepad1.right_stick_button) {
            drive.spinMode();
            double spinPower = gamepad1.right_stick_x*0.6;
            drive.setDrivePower(spinPower);
            telemetry.addData("spin power", spinPower);
            return;
        }
        else drive.crabMode();

        double crab_direction = getGamepad1RightJoystickAngle(); //crab direction is right joystick direction
        //!WORKAROUND Prevent facing the hall hole (see comments in Swerve unit)
        if (crab_direction > 160 && crab_direction < 200) {
            if(crab_direction < 180) crab_direction = 160;
            else crab_direction = 200;
        }
        double steer_direction = gamepad1.left_stick_x; //steer direction is left joystick horizontal
        double drive_power = -gamepad1.left_stick_y*Math.abs(gamepad1.left_stick_y); //speed is Left joystick vertical (with square acceleration)
        //double drive_power = Tools.timesabs(Tools.sign(-gamepad1.left_stick_y)*getGamepad1LeftJoystickAmplitude());  //expiremental
        drive.setDirection(crab_direction, steer_direction);
        drive.setDrivePower(drive_power);
        telemetry.addData("steer direction", steer_direction);
        telemetry.addData("crab direction", crab_direction);
        telemetry.addData("drive power", drive_power);

        blockLift.lift(gamepad1.left_trigger - gamepad1.right_trigger);

        if(gamepad1.right_bumper) {
            blockLift.clamp();
        }
        else if(gamepad1.left_bumper) {
            blockLift.release();
        }

        if(gamepad1.x) {
            knocker.extend();
        }
        else {
            knocker.retract();
        }
    }
}
