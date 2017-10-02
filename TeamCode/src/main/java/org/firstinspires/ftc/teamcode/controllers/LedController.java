package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.util.RobotLog;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mark Hancock and Derek Williams on 3/26/2017 and 3/28/2017.
 * LED control
 */

public class LedController extends HardwareController implements IColorIndicator{
    public static final String RED = "red";
    public static final String WHITE = "white";
    public static final String BLUE = "blue";
    public static final String GREEN = "green";
    public static final String YELLOW = "yellow";
    public static final String NO_COLOR = "NONE";
    public static final String ALL_COLORS = "ALL";

    private static Map<String, Byte> ioMap = new HashMap<String, Byte>(){{
        put(ALL_COLORS, (byte)0x1F);
        put(NO_COLOR, (byte)0x00);
        put(RED, (byte)0x01);   //D0
        put(WHITE, (byte)0x02); //D1
        put(BLUE, (byte)0x04);  //D2
        put(GREEN, (byte)0x08); //D3
        put(YELLOW, (byte)0x10);//D4
    }};

    private DeviceInterfaceModule io;
    private byte colorData = 0x00;

    public LedController(DeviceInterfaceModule LedIo, Map<String, Byte> ioMap) {
        this.io = LedIo;
        colorData = 0x00;
        //LedIo.setDigitalIOControlByte((byte)ioMap.get(ALL_COLORS)); //Set color bits as outputs
    }

    /**
     * Turn LED on/off
     */
    public void setLed(String color, Boolean on) {
        byte bits = ioMap.get(color);
        byte next = (byte)((colorData & ~bits) | (on ? bits : 0x00));
        io.setDigitalOutputByte(next);
        colorData = next;
    }

    public void setModuleLed(String color, Boolean on) {
        switch(color) {
            case BLUE:
                io.setLED(0,on);
                break;
            case RED:
                io.setLED(1,on);
                break;
            default:
                throw new RuntimeException(color + " is not supported as an module led");
        }
    }

}
