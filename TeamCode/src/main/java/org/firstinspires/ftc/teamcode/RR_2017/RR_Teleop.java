package org.firstinspires.ftc.teamcode.RR_2017;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Color;
import org.firstinspires.ftc.teamcode.controllers.BlockRolling;
import org.firstinspires.ftc.teamcode.controllers.ColorController;
import org.firstinspires.ftc.teamcode.controllers.IBlockLift;
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

        //Disable Min Limit if back is pressed
        //Note: Limit is reset when button is released
        //blockLift.overrideMinLimit(gamepad1.y);

        if(gamepad1.left_bumper) {
            blockLift.setBlockControlMode(IBlockLift.BlockControlMode.acquire);
        }
        else if(gamepad1.right_bumper) {
            blockLift.setBlockControlMode(IBlockLift.BlockControlMode.release);
        }
        else if (gamepad1.dpad_right) {
            blockLift.setBlockControlMode(IBlockLift.BlockControlMode.clockwise);
        }
        else if (gamepad1.dpad_left) {
            blockLift.setBlockControlMode(IBlockLift.BlockControlMode.counterclockwise);
        }
        else {
            blockLift.setBlockControlMode(IBlockLift.BlockControlMode.hold);
        }

        if(blockLift instanceof BlockRolling) {
            BlockRolling blockRolling = (BlockRolling) blockLift;

            if(gamepad1.a) {
                blockRolling.setRow(0);
            }
            else if(gamepad1.dpad_up) {
                blockRolling.nextRow();
            }
            else if(gamepad1.dpad_down) {
                blockRolling.lastRow();
            }
            else {
                blockLift.lift(gamepad1.right_trigger - gamepad1.left_trigger);
            }
        }
        else {
            blockLift.lift(gamepad1.right_trigger - gamepad1.left_trigger);
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
