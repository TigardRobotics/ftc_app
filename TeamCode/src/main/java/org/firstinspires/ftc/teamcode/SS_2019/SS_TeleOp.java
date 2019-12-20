package org.firstinspires.ftc.teamcode.SS_2019;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.controllers.DualServoGrabber;
import org.firstinspires.ftc.teamcode.controllers.SamplingArm;
import org.firstinspires.ftc.teamcode.opmodes.BasicTeleop;

@TeleOp(name="SS_TeleOp", group="3965")
public class SS_TeleOp extends BasicTeleop {

    DualServoGrabber grabber=null;
    SamplingArm arm;

    @Override
    public void init() {
        super.init();
        grabber = (DualServoGrabber)(findController(DualServoGrabber.class));
        if (grabber==null) {
            telemetry.addLine("No Controller");
        }
        else {
            telemetry.addLine("Got Controller");
        }
        arm = (SamplingArm)(findController(SamplingArm.class));
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        if (gamepad1.right_bumper)
        {
            telemetry.addLine("R Bumper");
            grabber.setDrop(true);
        }
        else if (gamepad1.left_bumper) {
            telemetry.addLine("L Bumper");
            grabber.setDrop(false);
        }
        if(gamepad1.right_bumper) arm.setDrop(true);
        else if(gamepad1.left_bumper) arm.setDrop(false);
        super.loop();
    }

    @Override
    public void stop() {
        super.stop();
    }
}
