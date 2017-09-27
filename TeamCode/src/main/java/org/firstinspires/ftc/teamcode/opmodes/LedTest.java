package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mark on 3/26/2017.
 */
@Autonomous(name="LedTest", group="test")
public class LedTest extends OpMode {

    public static final String RED = "red";
    public static final String WHITE = "white";
    public static final String BLUE = "blue";
    public static final String GREEN = "green";
    public static final String YELLOW = "yellow";
    public static final String NO_COLOR = "NONE";
    public static final String ALL_COLORS = "ALL";

    private static Map<String, Byte> IoMap = new HashMap<String, Byte>(){{
        put(ALL_COLORS, (byte)0x1F);
        put(NO_COLOR, (byte)0x00);
        put(RED, (byte)0x01);   //D0
        put(WHITE, (byte)0x02); //D1
        put(BLUE, (byte)0x04);  //D2
        put(GREEN, (byte)0x08); //D3
        put(YELLOW, (byte)0x10);//D4
    }};

    private DeviceInterfaceModule IO;

    private ElapsedTime lightTimer;
    private double lightTime = 1;
    private String light = NO_COLOR;
    byte data =0x00;


    public LedTest() {
        super();
    }

    @Override
    public void init() {
        IO = hardwareMap.deviceInterfaceModule.get("Device Interface Module 1");
        IO.setDigitalIOControlByte((byte)IoMap.get(ALL_COLORS)); //Set color bits as outputs
        lightTimer = new ElapsedTime();
        lightTimer.reset();
    }

    @Override
    public void start() {
        super.start();
        lightTimer.reset();
        light = NO_COLOR;
        SetLED(ALL_COLORS, false);
    }

    @Override
    public void loop() {
        if (lightTimer.seconds()>lightTime) {
            lightTimer.reset();
            SetLED(light, false);
            switch (light) {
                case RED:
                    light = WHITE;
                    break;
                case WHITE:
                    light = BLUE;
                    break;
                case BLUE:
                    light = GREEN;
                    break;
                case GREEN:
                    light = YELLOW;
                    break;
                case YELLOW:
                    light = NO_COLOR;
                    break;
                case NO_COLOR:
                    light = RED;
                    break;
            }
            telemetry.addLine("COLOR="+light);
        }
        else
        {
            SetLED(light, true);
        }
    }

    @Override
    public void stop() {
        SetLED(ALL_COLORS, false);
        super.stop();
    }


    private void SetLED( String color, Boolean on) {
        byte bits = IoMap.get(color);
        byte next = (byte)((data & ~bits) | (on ? bits : 0x00));
        IO.setDigitalOutputByte(next);
        data=next;
    }
}
