package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;

import org.firstinspires.ftc.teamcode.controllers.HardwareController;
import org.firstinspires.ftc.teamcode.controllers.LedController;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Derek Williams of team 3965 on 10/9/2016.
 */

@TeleOp(name="Basic Teleop", group="3965")
public class BasicTeleop extends TankBot {

    private DeviceInterfaceModule io;
    LedController Leds;

    private static Map<String, Byte> LedIoMap = new HashMap<String, Byte>(){{
        put(LedController.ALL_COLORS, (byte)0x1F);
        put(LedController.NO_COLOR, (byte)0x00);
        put(LedController.RED, (byte)0x01);   //D0
        put(LedController.WHITE, (byte)0x02); //D1
        put(LedController.BLUE, (byte)0x04);  //D2
        put(LedController.GREEN, (byte)0x08); //D3
        put(LedController.YELLOW, (byte)0x10);//D4
    }};

    @Override
    public void init() {
        super.init();
        //io = hardwareMap.deviceInterfaceModule.get("Device Interface Module 1");
        //addControllers(new LedController(io, LedIoMap));

        //Leds = (LedController)(findController(LedController.class));

        telemetry.addLine("Basic Hardware Initialized");
        //Leds.setLed(LedController.BLUE, true);
    }

    @Override
    public void start() {
        super.start();
        //Leds.setLed(LedController.GREEN, true);
        stateMachine = new StateMachine();
    }

    @Override
    public void loop(){
        Drive.setLeftDrivePower(gamepad1.left_stick_y);
        Drive.setRightDrivePower(gamepad1.right_stick_y);
        super.loop();
    }

    @Override
    public void stop() {
        super.stop();
        //Leds.setLed(LedController.ALL_COLORS, false);
    }

}
