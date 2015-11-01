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
    }

    @Override
    public void Do() {
        // Blink the LED.
        if (blink()) FlashCounter++;
        opMode.telemetry.addData(Name, "Count = " + String.format("%d", FlashCounter));
        opMode.telemetry.addData(Name, "blinker = " + String.format("%d", blinker));
        if(FlashCounter>=FlashCount) SetCurrentState(NextStateName);
    }

    /**
     * Code to blink the LED at a regular interval
     * Turn on the LED for 10 cylces and turn off at 100 cycles
     * @return true when flash is complete(light is turned off) so you can count flashes
     */
    private boolean blink() {
        // Increment blinker and wrap at 100
        blinker = ++blinker % 100;

        if (blinker == 0) {
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
 * State that delays for a fixed time then moves to the specified state
 */
class DelayState extends OpState {

    private StateFlasher opMode;
    private int DelayCounter = 0;
    private int DelayCount;
    private String NextStateName;

    /**
     * Constructor
     *
     * @param name       State Name
     * @param opmode     OpMode
     * @param count      Number of times to flash
     * @param next_state Next State Name
     */
    DelayState(String name, StateFlasher opmode, int count, String next_state) {
        super(name);
        opMode = opmode;
        DelayCount = count;
        NextStateName = next_state;
    }

    @Override
    public void OnEntry() {
        DelayCounter = 0; //reset the delay count
    }

    @Override
    public void Do() {
        DelayCounter++;
        opMode.telemetry.addData(Name, "Delaying");
        opMode.telemetry.addData(Name, "Count = " + String.format("%d", DelayCounter));
        if(DelayCounter>=DelayCount) SetCurrentState(NextStateName);
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
    private OpState flash3 = new FlashState("FlashThree", this, 3, "Delay");
    private OpState delay = new DelayState("Delay", this, 200, "FlashThree");

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
        OpState.SetCurrentState("FlashThree");
    }

    /*
    * This method will be called repeatedly in a loop
    * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
    */
    @Override
    public void loop() {
        telemetry.addData(OpState.GetCurrentState(), "Running for " + runtime.toString());
        OpState.DoCurrentState();
    }

    /*
    * Code to run when the op mode is first disabled goes here
    * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
    */
    @Override
    public void stop() {
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
