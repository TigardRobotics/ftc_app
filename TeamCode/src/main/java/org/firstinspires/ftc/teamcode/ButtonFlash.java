/*
* Button Test App
*/

package org.firstinspires.ftc.teamcode;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TeleOp Mode
 * <p>
 *Enables control of the robot via the gamepad
 */
@TeleOp(name="Button Flash", group="Concept")
public class ButtonFlash extends OpMode {

  private String startDate;
  private ElapsedTime runtime = new ElapsedTime();

  private Camera camera;
  private Parameters parm;
  private TouchSensor button;


  /**
   * Constructor
   */
  public ButtonFlash() {
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
    button = hardwareMap.touchSensor.get(("button"));

  }

  /*
   * This method will be called repeatedly in a loop
   * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
   */
  @Override
  public void loop() {


    if (button.isPressed()) {
      parm.setFlashMode(Parameters.FLASH_MODE_TORCH);
      camera.setParameters(parm);
    }
    else {
      parm.setFlashMode(Parameters.FLASH_MODE_OFF);
      camera.setParameters(parm);
    }

    telemetry.addData("Button", button.toString());
    telemetry.addData("Button", button.getConnectionInfo());
    telemetry.addData("Button", button.isPressed()? "on" : "off");
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

}
