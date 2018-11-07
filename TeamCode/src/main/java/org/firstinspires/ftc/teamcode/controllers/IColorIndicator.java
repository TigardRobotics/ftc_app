package org.firstinspires.ftc.teamcode.controllers;

/**
 * LED Display Interface
 */

public interface IColorIndicator {

    /**
     * Turn discrete LED on/off
     * @param color
     * @param on
     */
    public void setLed(String color, Boolean on);

    /**
     * Turn DeviceInterfaceModule on/off
     * @param color
     * @param on
     */
    public void setModuleLed(String color, Boolean on);
}
