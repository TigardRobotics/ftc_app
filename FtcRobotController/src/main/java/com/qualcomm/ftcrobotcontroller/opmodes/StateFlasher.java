package com.qualcomm.ftcrobotcontroller.opmodes;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * State that flashes for a fixed count then moves to the specified state
 */
class FlashState extends OpState{

    private StateFlasher opMode;
    private int FlashCounter = 0;
    private int FlashCount;
    public int blinker;
    private String NextStateName;

    /**
     * Constructor
     * @param name State Name
     * @param opmode OpMode
     * @param count Number of times to flash
     * @param next_state Next State Name
     */
    FlashState( String name, StateFlasher opmode, int count, String next_state){
        super(name);
        opMode = opmode;
        FlashCount = count;
        NextStateName = next_state;
    }

    @Override
    public void OnEntry() {
        FlashCounter = 0; //reset the flash counter
        blinker = 0;
    }

    @Override
    public void Do() {
        // Blink the LED.
        if (blink()) FlashCounter++;
        opMode.telemetry.addData(Name, "Count = " + String.format("%d", FlashCounter));
        if(FlashCounter>=FlashCount) SetCurrentState(NextStateName);
    }

    /**
     * Code to blink the LED at a regular interval
     * Turn on the LED at 0 cycles, then off at 10 cylces, repeat at 100 cycles
     * @return true when flash is complete(light is turned off) so you can count flashes
     */
    private boolean blink() {
        // Increment blinker and wrap at 100
        blinker = ++blinker % 100;

        if (blinker == 1) {
            opMode.SetLight(true);
            return false;
        } else  if (blinker == 10) {
            opMode.SetLight(false);
            return true;
        }
        else {
            return false;
        }
    }
}

/**
 * Simple OpMode to demonstrate control of the Camera Light
 */
public class StateFlasher extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();

    private Camera camera;
    private Parameters parm;

    //Construct the states

    private OpState[] States;

    /**
    * Constructor
    **/
    public StateFlasher() {
    }

    /*
    * Code to run when the op mode is first enabled goes here
    * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
    */
    @Override
    public void init() {
        runtime.reset();

        // Required to control the Camera LED
        camera = Camera.open();
        parm = camera.getParameters();
    }

    /*
     * Set the initial state
     */
    @Override
    public void start() {
        //Create the States
        States = new OpState[6];
        OpState.ClearAllStates();
        States[0] = new FlashState("Flash3", this, 3, "Delay1");
        States[1] = new DelayState("Delay1", this, 200, "Flash2");
        States[2] = new FlashState("Flash2", this, 2, "Delay2");
        States[3] = new DelayState("Delay2", this, 200, "Flash1");
        States[4] = new FlashState("Flash1", this, 1, "Delay3");
        States[5] = new DelayState("Delay3", this, 500, "Flash3");

        OpState.SetCurrentState("Flash3");
    }

    /*
    * This method will be called repeatedly in a loop
    * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
    */
    @Override
    public void loop() {
        OpState.DoCurrentState();
    }

    /*
    * Code to run when the op mode is first disabled goes here
    * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
    */
    @Override
    public void stop() {
        //Throw away the states
        States = null;
        OpState.ClearAllStates();

        if (camera != null) {
            camera.release();
        }
    }

    /**
     * Set Light State
     * @param on true=on, false=off
     */
    public void SetLight( boolean on ) {
        if (on) {
            parm.setFlashMode(Parameters.FLASH_MODE_TORCH);
        }
        else {
            parm.setFlashMode(Parameters.FLASH_MODE_OFF);
        }
        camera.setParameters(parm);
    }

}
