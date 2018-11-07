package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Names;
import org.firstinspires.ftc.teamcode.opmodes.BasicTeleop;

import java.util.List;

/**
 * Created by Katrina on 10/26/2017.
 */

@TeleOp(name="Test Lift", group="test")
public class BlockLift_Test extends BasicTeleop {

    private IBlockLift blockLift;

    @Override
    public void init() {
        super.init();

        // Get blocklift controller from controller list
        blockLift = (IBlockLift)findController(IBlockLift.class);
    }

    @Override
    public List<HardwareController> getControllers() {
        List<HardwareController> controllers = super.getControllers();

        // Get hardware from hardware map
        DcMotor liftMotor = hardwareMap.dcMotor.get(Names.liftMotor);
        Servo leftClamp = hardwareMap.servo.get(Names.leftClamp);
        Servo rightClamp = hardwareMap.servo.get(Names.rightClamp);

        controllers.add(new BlockLift(liftMotor, rightClamp, leftClamp));
        return controllers;
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
            blockLift.setBlockControlMode(IBlockLift.BlockControlMode.acquire);
        }
        else if(gamepad1.left_bumper) {
            blockLift.setBlockControlMode(IBlockLift.BlockControlMode.release);
        }
    }

    @Override
    public void stop() {
        super.stop();
        blockLift.reset();
    }

}
