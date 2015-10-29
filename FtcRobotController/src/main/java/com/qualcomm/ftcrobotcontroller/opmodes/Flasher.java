package com.qualcomm.ftcrobotcontroller.opmodes;

import android.hardware.Camera;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Simple OpMode to demonstrate control of the Camera Light
 */
public class Flasher extends OpMode {

  private String startDate;
  private ElapsedTime runtime = new ElapsedTime();

  private Camera camera;
  private Parameters parm;
  private int blinker;

  /**
   * Constructor
   */
  public Flasher() {
  }

  /*
   * Code to run when the op mode is first enabled goes here
   * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
   */
  @Override
  public void init() {
    startDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
    runtime.reset();

    // Required to control the Camera LED
    camera = Camera.open();
    parm = camera.getParameters();
  }

  /*
   * This method will be called repeatedly in a loop
   * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
   */
  @Override
  public void loop() {
    telemetry.addData("1 Start", "NullOp started at " + startDate);
    telemetry.addData("2 Status", "running for " + runtime.toString());

    // Blink the LED.
    blink();
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

  /*
   * Code to blink the LED at a regular interval
   * Turn on the LED for 10 cylces and turn off at 100 cycles
   */
  public void blink() {
    if (blinker == 0) {
      parm.setFlashMode(Parameters.FLASH_MODE_TORCH);
      camera.setParameters(parm);
    }
    else  if (blinker == 10) {
      parm.setFlashMode(Parameters.FLASH_MODE_OFF);
      camera.setParameters(parm);
    }

    // Increment blinker and wrap at 100
    blinker = ++blinker % 100;
  }
}
