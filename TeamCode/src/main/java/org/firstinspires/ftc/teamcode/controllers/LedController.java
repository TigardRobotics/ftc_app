package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.util.RobotLog;

import java.util.HashMap;
import java.util.Map;

/**
 * LED controler
 */
public class LedController extends HardwareController implements IColorIndicator {
    public static final String RED = "red";
    public static final String WHITE = "white";
    public static final String BLUE = "blue";
    public static final String GREEN = "green";
    public static final String YELLOW = "yellow";
    public static final String NO_COLOR = "NONE";
    public static final String ALL_COLORS = "ALL";

    private static Map<String, Byte> ioMap; //Maps Color Name to byte to write to DeviceInterfaceModule

    private DeviceInterfaceModule io;   //Where LEDs are connected to
    private byte colorData = 0x00;

    /**
     * LED controler
     * @param ledIo DeviceInterfaceModule
     * @param ledIoMap Maps Color Name to byte to write to DeviceInterfaceModule
     */
    public LedController(DeviceInterfaceModule ledIo, Map<String, Byte> ledIoMap) {
        this.io = ledIo;
        this.ioMap = ledIoMap;
        colorData = 0x00;
    }

     /**
     * Turn discrete LED on/off
     * @param color
     * @param on
     */
    public void setLed(String color, Boolean on) {
        byte bits = ioMap.get(color);
        byte next = (byte)((colorData & ~bits) | (on ? bits : 0x00));
        io.setDigitalOutputByte(next);
        colorData = next;
    }

    /**
     * Turn DeviceInterfaceModule on/off
     * @param color
     * @param on
     */
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
