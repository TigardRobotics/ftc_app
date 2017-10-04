package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.controllers.HardwareController;
import org.firstinspires.ftc.teamcode.controllers.IColorIndicator;
import org.firstinspires.ftc.teamcode.controllers.LedController;
import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

import java.util.HashMap;
import java.util.Map;

/**
 * LED Controller Test
 */
@Autonomous(name="LedController_Test", group="test")
public class LedController_Test extends RobotBase {

    private static Map<String, Byte> LED_IO_MAP = new HashMap<String, Byte>(){{
        put(LedController.ALL_COLORS, (byte)0x1F);
        put(LedController.NO_COLOR, (byte)0x00);
        put(LedController.RED, (byte)0x01);   //D0
        put(LedController.WHITE, (byte)0x02); //D1
        put(LedController.BLUE, (byte)0x04);  //D2
        put(LedController.GREEN, (byte)0x08); //D3
        put(LedController.YELLOW, (byte)0x10);//D4
    }};

    private DeviceInterfaceModule io;
    IColorIndicator leds;

    private ElapsedTime lightTimer;
    private double lightTime = 1;
    private String light = LedController.NO_COLOR;

    @Override
    public void init() {
        super.init();
        io = hardwareMap.deviceInterfaceModule.get("Device Interface Module 1");
        Controllers.add(new LedController(io, LED_IO_MAP));

        leds = (IColorIndicator)(HardwareController.find(Controllers,IColorIndicator.class));

        telemetry.addLine("Basic Hardware Initialized");

        leds.setLed(LedController.ALL_COLORS, true);
        lightTimer = new ElapsedTime();
        lightTimer.reset();
    }

    @Override
    public void start() {
        super.start();
        lightTimer.reset();
        light = LedController.NO_COLOR;
        leds.setLed(light, true);
    }

    @Override
    public void loop() {
        if (lightTimer.seconds()>lightTime) {
            lightTimer.reset();
            leds.setLed(light, false);
            switch (light) {
                case LedController.RED:
                    light = LedController.WHITE;
                    break;
                case LedController.WHITE:
                    light = LedController.BLUE;
                    break;
                case LedController.BLUE:
                    light = LedController.GREEN;
                    break;
                case LedController.GREEN:
                    light = LedController.YELLOW;
                    break;
                case LedController.YELLOW:
                    light = LedController.NO_COLOR;
                    break;
                case LedController.NO_COLOR:
                    light = LedController.RED;
                    break;
            }
            telemetry.addLine("COLOR="+light);
        }
        else {
            leds.setLed(light, true);
        }
    }

    @Override
    public void stop() {
        leds.setLed(LedController.ALL_COLORS, false);
        super.stop();
    }


}
