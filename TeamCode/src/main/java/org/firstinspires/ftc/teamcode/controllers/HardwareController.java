package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

import java.util.List;

/**
 * Created by Mark on 9/27/2017.
 */

public abstract class HardwareController {

    static public RobotBase Robot;

    /**
     * User defined init method
     * <p>
     * This method will be called once when the INIT button is pressed.
     */
    public void init() {}

    /**
     * User defined init_loop method
     * <p>
     * This method will be called repeatedly when the INIT button is pressed.
     * This method is optional. By default this method takes no action.
     */
    public void init_loop() {}

    /**
     * User defined start method.
     * <p>
     * This method will be called once when the PLAY button is first pressed.
     * This method is optional. By default this method takes not action.
     * Example usage: Starting another thread.
     *
     */
    public void start() {}

    /**
     * User defined loop method
     * <p>
     * This method will be called repeatedly in a loop while this op mode is running
     */
    public void loop() {}

    /**
     * User defined stop method
     * <p>
     * This method will be called when this op mode is first disabled
     *
     * The stop method is optional. By default this method takes no action.
     */
    public void stop() {}

    /**
     * This method checks to see if the desired interface is implemented
     */
    public boolean implementsInterface(Class interf){
        return interf.isAssignableFrom(this.getClass());
    }

    /**
     * This method searches a list for a HardwareController with the desired interface
     */
    public static HardwareController find(List<HardwareController> list, Class interf){
        for(HardwareController entry : list){
            if(entry.implementsInterface(interf)) return entry;
        }
        return null;
    }
}
