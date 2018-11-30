package org.firstinspires.ftc.teamcode.RR_2018;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Names;
import org.firstinspires.ftc.teamcode.controllers.HardwareController;
import org.firstinspires.ftc.teamcode.controllers.RobotHanger;
import org.firstinspires.ftc.teamcode.controllers.SamplingArm;
import org.firstinspires.ftc.teamcode.controllers.TrophyDropper;
import org.firstinspires.ftc.teamcode.opmodes.SwerveTeleop;
import org.firstinspires.ftc.teamcode.opmodes.TankBot;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;
import org.firstinspires.ftc.teamcode.statemachines.WaitState;

import java.util.List;

/**
 * Drive Teleop using iDrive interface
 */

@TeleOp(name="Rover Ruckus Teleop", group="3965")
//@Disabled
public class RR_Teleop extends SwerveTeleop {

    RobotHanger hanger;
    SamplingArm arm;

    //! TODO: Move this into a Rover Ruckus Opbase
    @Override
    public List<HardwareController> getControllers() {
        List<HardwareController> controllers = super.getControllers();
        DcMotor hangmotor = hardwareMap.dcMotor.get(Names.hanger);
        controllers.add(new RobotHanger(hangmotor));
        Servo dropServo = hardwareMap.servo.get(Names.trophyDrop);
        controllers.add(new TrophyDropper(dropServo));
        Servo armServo = hardwareMap.servo.get(Names.sampler);
        controllers.add(new SamplingArm(armServo));
        return controllers;
    }

    @Override
    public void init() {
        super.init();
        hanger = (RobotHanger)(findController(RobotHanger.class));
        arm = (SamplingArm)(findController(SamplingArm.class));
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        super.loop();
        hanger.setSpeed(gamepad1.left_trigger-gamepad1.right_trigger);
        if(gamepad1.right_bumper) arm.setDrop(true);
        else if(gamepad1.left_bumper) arm.setDrop(false);
        telemetry.addData("lift pos", hanger.getPos());

    }

    @Override
    public void stop() {
        super.stop();
    }
}
