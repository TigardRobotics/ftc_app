/**
 * Code developed Summer 2015 by Mark Hancock for Android FTC Platform Evaluation
**/
package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * TeleOp Mode for basic 2-motor tank drive robot
 * Hardware Setup
 * 	Motor Controller "wheels"
 * 		Port 1 - "motor_r"
 * 		Port 2 - "motor_l"
 *
 * Enables control of the robot via the gamepad
 */
public class Wheelz extends OpMode {

	//
	DcMotor motorR;
	DcMotor motorL;
	ElapsedTime runtime = new ElapsedTime();

	/**
	 * Constructor
	 */
	public Wheelz() {
	}

	/*
	 * Get and setup the motors
	 */
	@Override
	public void init() {

		telemetry.addData("OpMode", "*** Wheelz v1.0 ***");
		runtime.reset();
		motorR = hardwareMap.dcMotor.get("motor_r");
		motorL = hardwareMap.dcMotor.get("motor_l");
		motorL.setDirection(DcMotor.Direction.REVERSE);
	}

	@Override
	public void start() {
	}
	/*
	 * This method will be called repeatedly in a loop
	 * Relay the joystick commands to the motors
	 */
	@Override
	public void loop() {

        // tank drive
        // note that if y equal -1 then joystick is pushed all of the way forward.
        float powerR = -gamepad1.right_stick_y;
		float powerL = -gamepad1.left_stick_y;

		// clip the value so it never exceed +/- 1
		powerR = Range.clip(powerR, -1, 1);
		powerL = Range.clip(powerL, -1, 1);

		// write the values to the motors
		motorR.setPower(powerR);
		motorL.setPower(powerL);
		telemetry.addData("MotorR", "Power: " + String.format("%.2f", powerR));
		telemetry.addData("MotorL", "Power: " + String.format("%.2f", powerL));
	}

	/**
	 * Destructor
	 */
	@Override
	public void stop() {
	}

}
