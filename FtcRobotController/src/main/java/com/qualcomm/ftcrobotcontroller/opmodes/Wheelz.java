/**
 * Code developed Summer 2015 by Mark Hancock for Android FTC Platform Evaluation
**/
package com.qualcomm.ftcrobotcontroller.opmodes;

import android.hardware.Camera;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.Range;

/**
 * TeleOp Mode for controlling 2014-2145 FTC Team 3058 Cascade Effect Robot
 * Hardware Setup
 * 	Motor Controller N/C
 * 		Port 1 - "Motor"
 * 		Port 2 - N/C
 *
 * Enables control of the robot via the gamepad
 */
public class Wheelz extends OpMode {

	//
	DcMotor motor;

	/**
	 * Constructor
	 */
	public Wheelz() {
	}

	/*
	 * Code to run when the op mode is first enabled goes here
	 * 
	 * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
	 */
	@Override
	public void init() {

		telemetry.addData("OpMode", "*** One Motor v1.0 ***");
		motor = hardwareMap.dcMotor.get("Motor");
	}

	@Override
	public void start() {
	}
	/*
	 * This method will be called repeatedly in a loop
	 * 
	 * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
	 */
	@Override
	public void loop() {

        // tank drive
        // note that if y equal -1 then joystick is pushed all of the way forward.
        float power = -gamepad1.right_stick_y;

		// clip the value so it never exceed +/- 1
		power = Range.clip(power, -1, 1);

		// write the values to the motors
		motor.setPower(power);
		telemetry.addData("Motor", "Power: " + String.format("%.2f", power));
	}

	/**
	 * Destructor
	 */
	@Override
	public void stop() {
	}

}
