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

	// position of the plow servo.
	double plowPosition = 0;
	final static double plowIncrement = 5.0/180.0;
	final static double PLOW_MIN_RANGE  = 0.00/180.0;
	final static double PLOW_MAX_RANGE  = 180.0/180.0;


	//
	DcMotor motorR;
	DcMotor motorL;
	Servo plow;
	TouchSensor crash_r;
	TouchSensor crash_l;

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

		plow = hardwareMap.servo.get("plow");
		crash_r = hardwareMap.touchSensor.get(("crash_r"));
		crash_l = hardwareMap.touchSensor.get(("crash_l"));
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
        // note that if y equals -1 then joystick is pushed all of the way forward.
        float powerR = -gamepad1.right_stick_y;
		float powerL = -gamepad1.left_stick_y;

		// clip the value so it never exceed +/- 1
		powerR = Range.clip(powerR, -1, 1);
		powerL = Range.clip(powerL, -1, 1);

		// write the values to the motors
		motorR.setPower(powerR);
		motorL.setPower(powerL);
		telemetry.addData("MotorR", String.format("Power=%.2f", powerR));
		telemetry.addData("MotorL", String.format("Power=%.2f", powerL));

		if (gamepad1.b) {
			// if the B button is pushed on gamepad1, put the plow up
			plowPosition += plowIncrement;
			plowPosition = Range.clip(plowPosition, PLOW_MIN_RANGE, PLOW_MAX_RANGE);
			plow.setPosition(plowPosition);
		}
		else if (gamepad1.x) {
			// if the X button is pushed on gamepad1, put the plow down
			plowPosition -= plowIncrement;
			plowPosition = Range.clip(plowPosition, PLOW_MIN_RANGE, PLOW_MAX_RANGE);
			plow.setPosition(plowPosition);
		}

		telemetry.addData("Plow", String.format("Position=%.2f", plowPosition));

	}

	/**
	 * Destructor
	 */
	@Override
	public void stop() {
	}

}
