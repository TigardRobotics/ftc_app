package org.firstinspires.ftc.teamcode.RR_2017;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Color;
import org.firstinspires.ftc.teamcode.controllers.BlockRolling;
import org.firstinspires.ftc.teamcode.controllers.ColorController;
import org.firstinspires.ftc.teamcode.controllers.KnockerController;
import org.firstinspires.ftc.teamcode.opmodes.SwerveTeleop;

/**
 * Relic Recovery Teleop
 */

@TeleOp(name="RR Teleop", group="3965")
public class RR_Teleop extends SwerveTeleop {

    KnockerController knocker;
    ColorController colorController;
    boolean holdDown = false;

    @Override
    public void init() {
        super.init();
        knocker = (KnockerController)findController(KnockerController.class);
        colorController = (ColorController)findController(ColorController.class);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        super.loop();

        blockLift.lift(gamepad1.left_trigger - gamepad1.right_trigger);

        if(gamepad1.dpad_down) {
            blockLift.acquire();
        }
        else if(gamepad1.dpad_up) {
            blockLift.release();
        }
        else if(blockLift instanceof BlockRolling) {
            BlockRolling blockRolling = (BlockRolling) blockLift;
            if(gamepad1.dpad_right) {
                blockRolling.turnClockwise();
            }
            else if(gamepad1.dpad_left) {
                blockRolling.turnCounterClockwise();
            }

            if(gamepad1.right_bumper) {
                blockRolling.setPos();
            }
        }
        else {
            blockLift.hold();
        }

        // hold the knocker down
        if (gamepad1.b){
            holdDown = true;
        }

        if (gamepad1.x){
            holdDown = false;
        }

        if(gamepad1.x || holdDown) {
            knocker.extend();
        }
        else {
            knocker.retract();
        }

        if(colorController.getColor() == Color.BLUE) {
            telemetry.addLine("Blue Color Detected");
        }
        else if(colorController.getColor() == Color.RED) {
            telemetry.addLine("Red Color Detected");
        }
        else {
            telemetry.addLine("No Color Detected");
        }

        telemetry.addData("red", colorController.getRed());
        telemetry.addData("blue", colorController.getBlue());
    }
}
