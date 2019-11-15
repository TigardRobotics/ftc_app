package org.firstinspires.ftc.teamcode.SS_2019;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.controllers.SamplingArm;
import org.firstinspires.ftc.teamcode.opmodes.BasicTeleop;

@TeleOp(name="SS_TeleOp", group="3965")
public class SS_TeleOp extends BasicTeleop {

    SamplingArm arm;

    @Override
    public void init() {
        super.init();
        arm = (SamplingArm)(findController(SamplingArm.class));
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        super.loop();
        if (gamepad1.right_bumper) arm.setDrop(true);
        else if (gamepad1.left_bumper) arm.setDrop(false);

    }

    @Override
    public void stop() {
        super.stop();
    }
}
