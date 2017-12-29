package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Names;
import org.firstinspires.ftc.teamcode.opmodes.BasicTeleop;

/**
 * Created by Katrina on 10/26/2017.
 */

@TeleOp(name="Test Lift", group="test")
public class BlockLift_Test extends BasicTeleop {

    private IBlockLift blockLift;

    @Override
    public void init() {
        super.init();

        // Get hardware from hardware map
        DcMotor liftMotor = hardwareMap.dcMotor.get(Names.liftMotor);
        Servo leftClamp = hardwareMap.servo.get(Names.leftClamp);
        Servo rightClamp = hardwareMap.servo.get(Names.rightClamp);

        // Add blocklift controller to controller list
        addControllers(new BlockLift(liftMotor, rightClamp, leftClamp));

        // Get blocklift controller from controller list
        blockLift = (IBlockLift)findController(IBlockLift.class);
    }

    @Override
    public void start() {
        super.start();

    }

    @Override
    public void loop() {
        super.loop();

        blockLift.lift(gamepad1.left_trigger - gamepad1.right_trigger);

        if(gamepad1.right_bumper) {
            blockLift.acquire();
        }
        else if(gamepad1.left_bumper) {
            blockLift.release();
        }
    }

    @Override
    public void stop() {
        super.stop();
        blockLift.reset();
    }

}
