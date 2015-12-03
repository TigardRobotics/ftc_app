/**
 * Code developed Summer 2015 by Mark Hancock for Android FTC Platform Evaluation
**/
package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * State that drives for a fixed distance then moves to the specified state
 */
class DriveState extends OpState {

	private RAutoWheelz opMode;
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
	DriveState(String name, RAutoWheelz opmode, double power, double distance, String next_state) {
		super(name);
		opMode = opmode;
		Power = power;
		TargetDistance = opmode.CtsFromDist(distance);
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
        opMode.telemetry.addData(Name, String.format("%f of %f", distance, TargetDistance));
		if (distance >= TargetDistance) SetCurrentState(NextStateName);
	}

	@Override
	public void OnExit() {
		super.OnExit();
		opMode.StopMotors();
	}
}

/**
 * State that drives for a fixed distance then moves to the specified state
 */
class TurnState extends OpState {

	double TurnCircumference = 37.0;

	private RAutoWheelz opMode;
	private String NextStateName;
	public double Power;
	private double TargetDifference;
	private double StartDifference;

	/**
	 * Constructor
	 *
	 * @param name       State Name
	 * @param opmode     OpMode
	 * @param power      Motor Power to use
	 * @param angle   Distance to go
	 * @param next_state Next State Name
	 */
	TurnState(String name, RAutoWheelz opmode, double power, double angle, String next_state) {
		super(name);
		opMode = opmode;
		Power = power;
		double turnDistance = angle/360.0*TurnCircumference;
		TargetDifference = opmode.CtsFromDist(turnDistance);;
		NextStateName = next_state;
	}

	@Override
	public void OnEntry() {
		super.OnEntry();
		StartDifference = opMode.GetMotorDifference();
		opMode.MotorsTurn(Power);
	}

	@Override
	public void Do() {
		double currentDifference = Math.abs(opMode.GetMotorDifference() - StartDifference);
		opMode.telemetry.addData(Name, String.format("%f of %f", currentDifference, TargetDifference));
		if (currentDifference >= TargetDifference) SetCurrentState(NextStateName);
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
public class RAutoWheelz extends Wheelz {

	private static double CtsPerRev = 1440.0;
	private static double DistPerRev = 8.3;   //Distance Travelled per motor rev
	protected boolean TurnL = true;

	/**
	 * Constructor
	 */
	public RAutoWheelz() {

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
		double turnSpeed = 0.5;
		double driveSpeed = 0.5;
		if(!TurnL) {
			turnSpeed *= -1;
		}
		//Construct States
		OpState[] states = new OpState[]{
			new DriveState("Forward", this, driveSpeed, 48.0, "Turn"),
			new TurnState("Turn", this, turnSpeed, 90.0, "Forward2"),
			new DriveState("Forward2", this, driveSpeed, 60.0, "Delay"),
			new DelayState("Delay", this, 200, "Delay"),
		};
		OpState.SetCurrentState("Forward");

	}
	
	/*
	 * This method will be called repeatedly in a loop
	 * Run the State Machine
	 */
	@Override
	public void loop() {

		OpState.DoCurrentState();
	}

	/**
	 * Destructor
	 */
	@Override
	public void stop() {
	}

	public void MotorsForward( double power ){
		DbgLog.msg(toString().format("Motor forward w/ power = %f",power));
		motorR.setPower(Range.clip(power, -1, 1));
		motorL.setPower(Range.clip(power, -1, 1));
	}

	public void MotorsTurn( double power ){
		DbgLog.msg(toString().format("Motor turning w/ power = %f",power));
		motorR.setPower(Range.clip(power, -1, 1));
		motorL.setPower(Range.clip(-power, -1, 1));
	}

	public double GetMotorDistance(){
		float distanceR = motorR.getCurrentPosition();
		float distanceL = motorL.getCurrentPosition();
		//Report the motor that traveled the shortest distance (slipped the least)
		float distance = Math.min(distanceR,distanceL);
		DbgLog.msg(toString().format("Motor distance = %f",distance));
		return distance;
	}

	public double GetMotorDifference(){
		float distanceR = motorR.getCurrentPosition();
		float distanceL = motorL.getCurrentPosition();
		float difference = distanceR-distanceL;
		DbgLog.msg(toString().format("Motor difference = %f",difference));
		return difference;
	}
	public void StopMotors(){
		DbgLog.msg("Stopping Motors");
		motorR.setPower(0);
		motorL.setPower(0);
	}

	public double CtsFromDist(double distance) {
			return distance / DistPerRev * CtsPerRev;
	}
}
