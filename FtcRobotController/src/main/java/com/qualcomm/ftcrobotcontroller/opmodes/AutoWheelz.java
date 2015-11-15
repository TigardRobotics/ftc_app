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
 * State that drives for a fixed distance then moves to the specified state
 */
class DriveState extends OpState {

	private AutoWheelz opMode;
	private String NextStateName;
	private double Power;
	private double TargetDistance;
	private double StartDistance;

	/**
	 * Constructor
	 *
	 * @param name       State Name
	 * @param opmode     OpMode
	 * @param power      Motor Power to use
	 * @param distance   Distance to go
	 * @param next_state Next State Name
	 */
	DriveState(String name, AutoWheelz opmode, double power, double distance, String next_state) {
		super(name);
		opMode = opmode;
		Power = power;
		TargetDistance = distance;
		NextStateName = next_state;
	}

	@Override
	public void OnEntry() {
		super.OnEntry();
		StartDistance = opMode.GetMotorDistance();
		opMode.MotorsForward(Power);
	}

	@Override
	public void Do() {
		double distance = Math.abs(opMode.GetMotorDistance() - StartDistance);
        opMode.telemetry.addData("Drive", String.format("%f of %f", distance, TargetDistance));
		if (distance >= TargetDistance) SetCurrentState(NextStateName);
	}

	@Override
	public void OnExit() {
		super.OnExit();
		opMode.StopMotors();
	}
}

/**
 * Autonomous Mode for basic 2-motor tank drive robot
 * Hardware Setup
 * 	Motor Controller "wheels"
 * 		Port 1 - "motor_r"
 * 		Port 2 - "motor_l"
 *
 * Enables control of the robot via the gamepad
 */
public class AutoWheelz extends Wheelz {

	//Construct drive states
	private OpState forward = new DriveState("Forward", this, 0.50, 1000, "Delay");
	private OpState delay = new DelayState("Delay", this, 200, "Backward");
	private OpState backward = new DriveState("Backward", this, -0.50, 1000, "Delay2");
	private OpState delay2 = new DelayState("Delay2", this, 200, "Forward");

	/**
	 * Constructor
	 */
	public AutoWheelz() {

	}

	/*
	 * Initialize the encoders
	 */
	@Override
	public void init() {
		
		super.init();
		telemetry.addData("OpMode", "*** AutoWheelz v1.0 ***");

		motorR.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
		motorR.setChannelMode( DcMotorController.RunMode.RUN_USING_ENCODERS);

		motorL.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
		motorL.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
	}

	/*
	 * Set the initial state
	 */
	@Override
	public void start() {
		OpState.SetCurrentState("Forward");
	}
	
	/*
	 * This method will be called repeatedly in a loop
	 * Run the State Machine
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

	public void MotorsForward( double power ){
		motorR.setPower(Range.clip(power, -1, 1));
		motorL.setPower(Range.clip(power, -1, 1));
	}

	public void MotorsTurn( double power ){
		motorR.setPower(Range.clip(power, -1, 1));
		motorL.setPower(Range.clip(-power, -1, 1));
	}

	public double GetMotorDistance(){
		float distanceR = motorR.getCurrentPosition();
		float distanceL = motorL.getCurrentPosition();
		//Report the motor that traveled the shortest distance (slipped the least)
		return Math.min(distanceR,distanceL);
	}

	public double GetMotorDifference(){
		float distanceR = motorR.getCurrentPosition();
		float distanceL = motorL.getCurrentPosition();
		//Report the motor that traveled the shortest distance (slipped the least)
		return distanceR-distanceL;
	}
	public void StopMotors(){
		motorR.setPower(0);
		motorL.setPower(0);
	}
}
