/**
 * Code developed Summer 2015 by Mark Hancock for Android FTC Platform Evaluation
**/
package org.firstinspires.ftc.teamcode;

import android.hardware.Camera;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.Range;

/**
 * TeleOp Mode for controlling 2014-2145 FTC Team 3058 Cascade Effect Robot
 * Headware Setup
 * Legacy Module
 * 	Port 0 - Motor Controller "wheels"
 * 		Port 1 - "wheels_R"
 * 		Port 2 - "wheels_L"
 * 	Port 1 - Motor Controller "lift"
 * 		Port 1 - "lift_1"
 * 		Port 2 - "lift_2"
 * 	Port 2 - Motor Controller "collector"
 * 		Port 1 - "beater"
 * 		Port 2 - N/C
 * 	Port 3 - Servo Controller "servos"
 * 		Port 1 - "tailhook"
 * 		Port 2 - "gate"
 * 		Port 3-6 - N/C
 * 	Port 4 - IR_SEEKER "ir_seeker"
 * 	Port 5 - TOUCH_SENSOR "lift_limit"
 *
 * Enables control of the robot via the gamepad
 */
@TeleOp(name="CascadeEffect_3058", group="3058")
@Disabled
public class CascadeEffect_3058 extends OpMode {

	// position of the tailhook servo.
	double tailhookPosition = 0;
	final static double tailhookIncrement = 5.0/360.0;
	final static double TAILHOOK_MIN_RANGE  = 0.00;
	final static double TAILHOOK_MAX_RANGE  = 0.65;


	// position of the gate servo
	double gatePosition;
	final static double gateIncrement = 5.0/360.0;
	final static double GATE_MIN_RANGE  = 0.30;
	final static double GATE_MAX_RANGE  = 0.80;

	//
	double LiftSpeed = 0.2;
	double BeaterSpeed = 1.0;
	boolean lift_stop = false;

	DcMotor motorRight;
	DcMotor motorLeft;
	DcMotor motorLift1;
	DcMotor motorLift2;
	DcMotor motorBeater;
	Servo tailhook;
	Servo gate;
	TouchSensor lift_limit;
	IrSeekerSensor irSeeker;

	private Camera camera;
	private Camera.Parameters parm;
	private int blinker;
	String CameraState = "N/C";

	/**
	 * Constructor
	 */
	public CascadeEffect_3058() {
	}

	/*
	 * Code to run when the op mode is first enabled goes here
	 * 
	 * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
	 */
	@Override
	public void init() {

		telemetry.addData("OpMode", "*** Cascade Effect 3058 v1.2 ***");
		motorRight = hardwareMap.dcMotor.get("wheels_R");
		motorRight.setDirection(DcMotor.Direction.REVERSE);
		motorLeft = hardwareMap.dcMotor.get("wheels_L");
		motorLift1 = hardwareMap.dcMotor.get("lift_1");
		motorLift1.setDirection(DcMotor.Direction.REVERSE);
		motorLift2 = hardwareMap.dcMotor.get("lift_2");
		motorLift2.setDirection(DcMotor.Direction.REVERSE);
		motorBeater = hardwareMap.dcMotor.get("beater");
		tailhook = hardwareMap.servo.get("tailhook");
		gate = hardwareMap.servo.get("gate");
		lift_limit = hardwareMap.touchSensor.get(("lift_limit"));
		irSeeker = hardwareMap.irSeekerSensor.get("ir_seeker");
		irSeeker.setMode(IrSeekerSensor.Mode.MODE_1200HZ);
	}

	@Override
	public void start() {
		// Required to control the Camera LED
		try
		{
			if(camera == null) {
				camera = Camera.open();
				parm = camera.getParameters();
				CameraState = "Connected";
			}
		}
		catch(Exception ex)
		{
			telemetry.addData("Error", "*** Exception opening camera ***");
		}
	}
	/*
	 * This method will be called repeatedly in a loop
	 * 
	 * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
	 */
	@Override
	public void loop() {

		boolean ir_detected = irSeeker.signalDetected();
		double ir_angle = irSeeker.getAngle();
		double ir_strength = irSeeker.getStrength();

		/*
		 * Gamepad 1
		 *
		 */

        // tank drive
        // note that if y equal -1 then joystick is pushed all of the way forward.
        float left = -gamepad1.left_stick_y;
        float right = -gamepad1.right_stick_y;

		// clip the right/left values so that the values never exceed +/- 1
		right = Range.clip(right, -1, 1);
		left = Range.clip(left, -1, 1);

		// scale the joystick value to make it easier to control
		// the robot more precisely at slower speeds.
		right = (float)scaleInput(right);
		left =  (float)scaleInput(left);
		
		// write the values to the motors
		motorRight.setPower(right);
		motorLeft.setPower(left);

		// update the position of the tailhook.
		if (gamepad1.a) {
			// if the A button is pushed on gamepad1, put the tailhook down
			tailhookPosition -= tailhookIncrement;
			tailhookPosition = Range.clip(tailhookPosition, TAILHOOK_MIN_RANGE, TAILHOOK_MAX_RANGE);
			tailhook.setPosition(tailhookPosition);
		}
		else if (gamepad1.x) {
			// if the X button is pushed on gamepad1, put the tailhook up
			tailhookPosition += tailhookIncrement;
			tailhookPosition = Range.clip(tailhookPosition, TAILHOOK_MIN_RANGE, TAILHOOK_MAX_RANGE);
			tailhook.setPosition(tailhookPosition);
		}
		// update the position of the gate.
		//gatePosition = gate.getPosition();
		if (gamepad1.b) {
			// if the B button is pushed on gamepad1, move the gate down
			gatePosition -= gateIncrement;
			gatePosition = Range.clip(gatePosition, GATE_MIN_RANGE, GATE_MAX_RANGE);
			gate.setPosition(gatePosition);
		}
		else if (gamepad1.y) {
			// if the Y button is pushed on gamepad1, move the gate up
			gatePosition += gateIncrement;
			gatePosition = Range.clip(gatePosition, GATE_MIN_RANGE, GATE_MAX_RANGE);
			gate.setPosition(gatePosition);
		}

        if (gamepad1.right_bumper) {
			motorBeater.setPower(BeaterSpeed);
        }
		else if (gamepad1.left_bumper) {
			motorBeater.setPower(-BeaterSpeed);
		}
		else {
			motorBeater.setPower(0.0);
		}

		lift_stop = lift_limit.isPressed();
        if ((gamepad1.right_trigger > 0.25)&& !lift_stop) {
			motorLift1.setPower(LiftSpeed);
			motorLift2.setPower(LiftSpeed);
        }
		else if (gamepad1.left_trigger > 0.25) {
			motorLift1.setPower(-LiftSpeed);
			motorLift2.setPower(-LiftSpeed);
		}
		else {
			motorLift1.setPower(0.0);
			motorLift2.setPower(0.0);
		}

		//Turn camera light on/off using gamepad ^ and v
		if(camera != null) {
			if (gamepad1.dpad_up) {
				// if the ^ is pushed on gamepad1, turn on the camera light
				parm.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
				camera.setParameters(parm);
				CameraState = "ON";
			} else if (gamepad1.dpad_down) {
				// if the v is pushed on gamepad1, turn off the camera light
				parm.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
				camera.setParameters(parm);
				CameraState = "OFF";
			}
		}
		/*
		 * Send telemetry data back to driver station. Note that if we are using
		 * a legacy NXT-compatible motor controller, then the getPower() method
		 * will return a null value. The legacy NXT-compatible motor controllers
		 * are currently write only.
		 */

		telemetry.addData("status", "light=" + CameraState
								+ " lift_stop=" + (lift_stop ? "on" : "off"));
        telemetry.addData("servos", "tailhook=" + String.format("%.2f", tailhookPosition)
								+ ", gate=" + String.format("%.2f", gatePosition));
		telemetry.addData("IR", "detected=" + (ir_detected ? "yes" : "no")
				+ ", angle=" + String.format("%.2f", ir_angle)
				+ ", strength=" + String.format("%.2f", ir_strength));
		//telemetry.addData("left tgt pwr",  "left  pwr: " + String.format("%.2f", left));
		//telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", right));
	}

	/**
	 * Destructor
	 */
	@Override
	public void stop() {
		if (camera != null) {
			camera.release();
		}
	}

	/**
	 * Destructor
	 */
	@Override
	public void finalize() {
		if (camera != null) {
			camera.release();
		}
	}
	/*
	 * This method scales the joystick input so for low joystick values, the 
	 * scaled value is less than linear.  This is to make it easier to drive
	 * the robot more precisely at slower speeds.
	 */
	double scaleInput(double dVal)  {
		double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
				0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };
		
		// get the corresponding index for the scaleInput array.
		int index = (int) (dVal * 16.0);
		if (index < 0) {
			index = -index;
		} else if (index > 16) {
			index = 16;
		}
		
		double dScale = 0.0;
		if (dVal < 0) {
			dScale = -scaleArray[index];
		} else {
			dScale = scaleArray[index];
		}
		
		return dScale;
	}

}
