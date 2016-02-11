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
	double armAnglePosition = 0;
	final static double dumpIncrement = 1.0/180.0;
	final static double DUMP_MIN_RANGE = 0.00/180;
	final static double DUMP_MAX_RANGE = 180.0/180.0;
	final static double DUMP_HOME = 0.0/180.0;
	final static double plowIncrement = 5.0/180.0;
	final static double PLOW_MIN_RANGE  = 90.00/180.0;
	final static double PLOW_MAX_RANGE  = 180.0/180.0;
	final static double PLOW_HOME  = 180.0/180.0;
	final static double wingIncrement = 1.0 /180.0;
	final static double WING_MIN_RANGE = 0.00/180.0;
	final static double WING_MAX_RANGE = 180.00/180.0;
	final static double WING_R_HOME = 90.00/180.0;
	final static double WING_L_HOME = 90.00/180.0;
	final static double armAngleIncrement = 1.0/180.0;
	final static double armAngleHome = 0.0/180.0;
	final static double ARM_ANGLE_MAX_RANGE = 180.0/180.0;
	final static double ARM_ANGLE_MIN_RANGE = 0.0/180.0;

	boolean RButtDown = false;
	boolean RWingDirec = true;
	int Rcount = 0;

	boolean LButtDown = false;
	boolean LWingDirec = true;
	int Lcount = 0;

	//
	DcMotor motorR;
	DcMotor motorL;
	DcMotor armExtend;
	DcMotor armWinch;
	Servo dump;
	Servo plow;
	Servo wing_r;
	Servo wing_l;
	Servo armAngle;
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

		armAngle = hardwareMap.servo.get("arm_angle");
		armExtend = hardwareMap.dcMotor.get("arm_extend");
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

		double powerExtend = -gamepad2.right_stick_y;
		double powerWinch = -gamepad2.left_stick_y;

		// clip the value so it never exceed +/- 1
		powerR = Range.clip(powerR, -1, 1);
		powerL = Range.clip(powerL, -1, 1);

		// write the values to the motors
		motorR.setPower(powerR);
		motorL.setPower(powerL);
		armExtend.setPower(powerExtend);
		armWinch.setPower(powerWinch);
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



		//Control Wings R-Bumper=Right wing, L-Bumper=left wing

		//State Transitions Right Wing
		if (RButtDown && !gamepad1.right_bumper) {
			Rcount = 10;
			RButtDown = false;
		}
		else if (!RButtDown && (Rcount > 0) && gamepad1.right_bumper) {
			RButtDown = true;
		}
		else if (!RButtDown && (Rcount > 0) ) {
			--Rcount;
		}
		else if (!RButtDown && (Rcount < 1) && gamepad1.right_bumper ) {
			RWingDirec = !RWingDirec;
			RButtDown = true;
		}

		//State Transitions Left Wing
		if (LButtDown && !gamepad1.left_bumper) {
			Lcount = 10;
			LButtDown = false;
		}
		else if (!LButtDown && (Lcount > 0) && gamepad1.left_bumper) {
			LButtDown = true;
		}
		else if (!LButtDown && (Lcount > 0) ) {
			--Lcount;
		}
		else if (!LButtDown && (Lcount < 1) && gamepad1.left_bumper ) {
			LWingDirec = !LWingDirec;
			LButtDown = true;
		}

		//State Actions Right Wing
		if (RButtDown) {
			if (RWingDirec) {
				wingPositionR += wingIncrement;
			}
			else {
				wingPositionR -= wingIncrement;
			}
		}

		//State Actions Left Wing
		if (LButtDown) {
			if (LWingDirec) {
				wingPositionL += wingIncrement;
			}
			else {
				wingPositionL -= wingIncrement;
			}
		}


		wingPositionR = Range.clip(wingPositionR, WING_R_HOME, WING_MAX_RANGE);
		wingPositionL = Range.clip(wingPositionL, WING_MIN_RANGE, WING_L_HOME);
		wing_l.setPosition(wingPositionL);
		wing_r.setPosition(wingPositionR);



		//Control Dump arm (aux)
		if (gamepad2.dpad_up) {
			dumpPosition += dumpIncrement;
		}
		else if (gamepad2.dpad_down) {
			dumpPosition -= dumpIncrement;
		}
		dumpPosition = Range.clip(dumpPosition, DUMP_MIN_RANGE, DUMP_MAX_RANGE);
		dump.setPosition(dumpPosition);



		//Arm Angle Control using triggers
		if (gamepad2.left_trigger > 0) {
			armAnglePosition += armAngleIncrement;
		}
		else if (gamepad2.right_trigger > 0) {
			armAnglePosition -= armAngleIncrement;
		}
		armAnglePosition = Range.clip(armAnglePosition, ARM_ANGLE_MIN_RANGE, ARM_ANGLE_MAX_RANGE);
		armAngle.setPosition(armAnglePosition);


		double lineLight = lineDetect.getLightDetectedRaw();

		double distance = GetUltraSonicDistance();
		/*
		telemetry.addData("Plow", String.format("Position=%.2f", plowPosition));
		telemetry.addData("Wing_L", String.format("Position=%.2f", wingPositionL));
		telemetry.addData("Arm",String.format("power=%f, angle=%f",armAnglePower, armAnglePosition));
		telemetry.addData("Wing_R", String.format("Position=%.2f", wingPositionR));
		telemetry.addData("Wing_R", String.format("direction=%s, count=%d, ButDown=%s", RWingDirec?"T":"F" , Rcount, RButtDown?"T":"F"));
		*/
		telemetry.addData("LineDetect",String.format("line=%f, dist=%f", lineLight, distance ));

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
