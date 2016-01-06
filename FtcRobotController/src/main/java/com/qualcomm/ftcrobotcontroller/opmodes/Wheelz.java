/**
 * Code developed Summer 2015 by Mark Hancock for Android FTC Platform Evaluation
**/
package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
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

	// position of the plow and wing servos
	double dumpPosition = 0;
	double plowPosition = 0;
	double wingPositionR = 0;
	double wingPositionL = 0;
	final static double dumpIncrement = 5.0/180.0;
	final static double DUMP_MIN_RANGE = 0.00/180;
	final static double DUMP_MAX_RANGE = 180.0/180.0;
	final static double DUMP_HOME = 180.0/180.0;
	final static double plowIncrement = 5.0/180.0;
	final static double PLOW_MIN_RANGE  = 0.00/180.0;
	final static double PLOW_MAX_RANGE  = 180.0/180.0;
	final static double PLOW_HOME  = 180.0/180.0;
	final static double wingIncrement = 2.5 /180.0;
	final static double WING_MIN_RANGE = 0.00/180.0;
	final static double WING_MAX_RANGE = 180.00/180.0;
	final static double WING_R_HOME = 90.00/180.0;
	final static double WING_L_HOME = 90.00/180.0;

	//
	DcMotor motorR;
	DcMotor motorL;
	DcMotor armLift;
	DcMotor armWinch;
	DcMotor armAngle;
	Servo dump;
	Servo plow;
	Servo wing_r;
	Servo wing_l;
	TouchSensor crash_r;
	TouchSensor crash_l;
	UltrasonicSensor eyes;
	OpticalDistanceSensor lineDetect;

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

		armAngle = hardwareMap.dcMotor.get("arm_angle");
		armLift = hardwareMap.dcMotor.get("arm_lift");
		armWinch = hardwareMap.dcMotor.get("arm_winch");

		dump = hardwareMap.servo.get("dump");
		plow = hardwareMap.servo.get("plow");
		wing_r = hardwareMap.servo.get("wing_r");
		wing_l = hardwareMap.servo.get("wing_l");
		crash_r = hardwareMap.touchSensor.get("crash_r");
		crash_l = hardwareMap.touchSensor.get("crash_l");
		lineDetect = hardwareMap.opticalDistanceSensor.get("lineDetect");

		eyes = hardwareMap.ultrasonicSensor.get("eyes");
		dump.setPosition(DUMP_HOME);
		plow.setPosition(PLOW_HOME);
		wing_r.setPosition(WING_R_HOME);
		wing_l.setPosition(WING_L_HOME);

		//armLift.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
		//armLift.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

		armAngle.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
		armAngle.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

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
		dumpPosition = dump.getPosition();
		plowPosition = plow.getPosition();
		wingPositionL = wing_l.getPosition();
		wingPositionR = wing_r.getPosition();

        // tank drive
        // note that if y equals -1 then joystick is pushed all of the way forward.
        double powerR = -gamepad1.right_stick_y;
		double powerL = -gamepad1.left_stick_y;

		double powerLift = -gamepad2.right_stick_y;
		double powerWinch = -gamepad2.left_stick_y;

		// clip the value so it never exceed +/- 1
		powerR = Range.clip(powerR, -1, 1);
		powerL = Range.clip(powerL, -1, 1);

		// write the values to the motors
		motorR.setPower(powerR);
		motorL.setPower(powerL);
		telemetry.addData("MotorR", String.format("Power=%.2f", powerR));
		telemetry.addData("MotorL", String.format("Power=%.2f", powerL));

		//Control Plow B=Up, X=Down
		if (gamepad1.b) {
			// if the B button is pushed on gamepad1, put the plow up
			plowPosition += plowIncrement;
		}
		else if (gamepad1.x) {
			// if the X button is pushed on gamepad1, put the plow down
			plowPosition -= plowIncrement;
		}
		plowPosition = Range.clip(plowPosition, PLOW_MIN_RANGE, PLOW_MAX_RANGE);
		plow.setPosition(plowPosition);

		//Control Wings R-Bumper=Up, L-Bumper=Down
		if (gamepad1.right_bumper) {
			// if the right bumper is pushed on gamepad1, put the wings out
			wingPositionR -= wingIncrement;
			wingPositionL += wingIncrement;
		}
		else if (gamepad1.left_bumper) {
			// if the right bumper is pushed on gamepad1, put the wings out
			wingPositionR += wingIncrement;
			wingPositionL -= wingIncrement;
		}

		// Arm angle teleop control

		//Arm Angle Control using triggers
		double armAnglePower = 0;
		if (gamepad1.right_trigger>0.1) {
			armAnglePower = -0.1;
		}
		else if (gamepad1.left_trigger>0.1) {
			armAnglePower = 0.1;
		}

		armAnglePower = Range.clip(armAnglePower, -1, 1);
		armAngle.setPower(armAnglePower);

		double armAnglePosition = armAngle.getCurrentPosition();

		wingPositionR = Range.clip(wingPositionR, WING_MIN_RANGE, WING_MAX_RANGE);
		wingPositionL = Range.clip(wingPositionL, WING_MIN_RANGE, WING_MAX_RANGE);
		wing_l.setPosition(wingPositionL);
		wing_r.setPosition(wingPositionR);

		double lineLight = lineDetect.getLightDetectedRaw();

		double distance = GetUltraSonicDistance();
		/*
		telemetry.addData("Plow", String.format("Position=%.2f", plowPosition));
		telemetry.addData("Wing_R", String.format("Position=%.2f", wingPositionR));
		telemetry.addData("Wing_L", String.format("Position=%.2f", wingPositionL));
		*/
		telemetry.addData("LineDetect",String.format("line=%f, dist=%f", lineLight, distance ));
		telemetry.addData("Arm",String.format("power=%f, angle=%f",armAnglePower, armAnglePosition));
	}

	//my mom's a realestate agent

	/**
	 * Destructor
	 */
	@Override
	public void stop() {
	}

	public double GetUltraSonicDistance() {
		return eyes.getUltrasonicLevel();
	}

}
