/**
 * Code developed Summer 2015 by Mark Hancock for Android FTC Platform Evaluation
**/
package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * State that delays for a fixed time then moves to the specified state
 */
class DriveState extends OpState {

	private AutoWheelz opMode;
	private String NextStateName;
	private float Power;
	private float TargetDistance;
	private float TravelDistance;

	/**
	 * Constructor
	 *
	 * @param name       State Name
	 * @param opmode     OpMode
	 * @param power      Motor Power to use
	 * @param distance   Distance to go
	 * @param next_state Next State Name
	 */
	DriveState(String name, AutoWheelz opmode, float power, float distance, String next_state) {
		super(name);
		opMode = opmode;
		Power = power;
		TravelDistance = distance;
		NextStateName = next_state;
	}

	@Override
	public void OnEntry() {
		TargetDistance = opMode.GetMotorDistance() + TravelDistance;
		opMode.MotorsForward(Power);
	}

	@Override
	public void Do() {
		float distance = opMode.GetMotorDistance();
        opMode.telemetry.addData("Drive", String.format("%d of %d", distance, TargetDistance));
		if(distance>=TargetDistance) SetCurrentState(NextStateName);
	}

	@Override
	public void OnExit() {
		opMode.StopMotors();
	}
}
/**
 * TeleOp Mode for controlling 2014-2145 FTC Team 3058 Cascade Effect Robot
 * Hardware Setup
 * 	Motor Controller "wheels"
 * 		Port 1 - "motor_r"
 * 		Port 2 - "motor_l"
 *
 * Enables control of the robot via the gamepad
 */
public class AutoWheelz extends OpMode {

	//
	DcMotor motorR;
	DcMotor motorL;
	private ElapsedTime runtime = new ElapsedTime();

	private OpState delay = new DelayState("Delay", this, 200, "Forward");
	private OpState forward = new DriveState("Forward", this, 50, 100, "Delay");

	/**
	 * Constructor
	 */
	public AutoWheelz() {
	}

	/*
	 * Code to run when the op mode is first enabled goes here
	 * 
	 * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
	 */
	@Override
	public void init() {

		telemetry.addData("OpMode", "*** AutoWheelz v1.0 ***");
		runtime.reset();

		motorR = hardwareMap.dcMotor.get("motor_r");
		motorR.setDirection (DcMotor.Direction.REVERSE);
		motorR.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
		motorR.setChannelMode( DcMotorController.RunMode.RUN_USING_ENCODERS);

		motorL = hardwareMap.dcMotor.get("motor_l");
		motorL.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
		motorL.setChannelMode( DcMotorController.RunMode.RUN_USING_ENCODERS);
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

		telemetry.addData(OpState.GetCurrentState(), "Running for " + runtime.toString());
		OpState.DoCurrentState();
	}

	/**
	 * Destructor
	 */
	@Override
	public void stop() {
	}

	public void MotorsForward( float power ){
		motorR.setPower(Range.clip(power, -1, 1));
		motorL.setPower(Range.clip(power, -1, 1));
	}

	public void MotorsTurn( float power ){
		motorR.setPower(Range.clip(power, -1, 1));
		motorL.setPower(Range.clip(-power, -1, 1));
	}

	public float GetMotorDistance(){
		float distanceR = motorR.getCurrentPosition();
		float distanceL = motorL.getCurrentPosition();
		//Report the motor that traveled the shortest distance (slipped the least)
		return Math.min(distanceR,distanceL);
	}

	public void StopMotors(){
		motorR.setPower(0);
		motorL.setPower(0);
	}
}
