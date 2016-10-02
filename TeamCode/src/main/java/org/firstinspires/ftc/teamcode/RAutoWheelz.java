/**
 * Code developed Summer 2015 by Mark Hancock for Android FTC Platform Evaluation
 * its also bilt by derek williams the most hardcore haxor of all time 2015
**/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * State that drives for a fixed distance then moves to the specified state
 */
@Autonomous(name="RAutoWheelz", group="Auto-Red")
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
 * State that turns a certain amount
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
		opMode.MotorsTurn(Power, true);
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
 * State that FOLLLOWS a line
 */
class LineFollowState extends OpState {

	protected RAutoWheelz opMode;
	protected String NextStateName;
	private double Power;
	private double TargetDistance;
	private double StartDistance;
	private boolean FoundLine;

	/**
	 * Constructor
	 *
	 * @param name       State Name
	 * @param opmode     OpMode
	 * @param power      Motor Power to use
	 * @param distance   Distance to go
	 * @param next_state Next State Name
	 */
	LineFollowState(String name, RAutoWheelz opmode, double power, double distance, String next_state) {
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
		opMode.MotorsTurn(Power, true);
		//lineWasDetected = opMode.LineDetected();
	}

	//boolean fallRight = true;
	boolean lineWasDetected = false;
	@Override
	public void Do() {

		//if(opMode.LineDetected()){
		//	lineWasDetected = true;
		//}
		//else if(lineWasDetected){
		//	Power = -Power;
		//	opMode.MotorsTurn(Power, false);
		//	lineWasDetected = false;
		//}
		if (opMode.LineDetected()) {
			lineWasDetected = true;
			Power = Math.abs(Power);
			opMode.MotorsTurn(Power, false);
		} else if (lineWasDetected) {
			Power = -Math.abs(Power);
			opMode.MotorsTurn(Power, false);
		}

		double distance = Math.abs(opMode.GetMotorDistance() - StartDistance);
		double lineDetect = opMode.lineDetect.getRawLightDetected();

		opMode.telemetry.addData(Name, String.format("%f of %f, linedetect=%d, Power=%f", distance, TargetDistance, lineDetect, Power));
		if (distance >= TargetDistance) SetCurrentState(NextStateName);

	}

	@Override
	public void OnExit() {
		super.OnExit();
		opMode.StopMotors();
	}
}

/**
 * State that FOLLLOWS a line
 */
class FollowWithUltrasonicState extends LineFollowState {

	private double StopAtDistance;
	/**
	 * Constructor
	 *
	 * @param name       State Name
	 * @param opmode     OpMode
	 * @param power      Motor Power to use
	 * @param maxDistance   Distance to go
	 * @param stopAtDistance Distance level from ultrasonic that we stop at
	 * @param next_state Next State Name
	 */
	FollowWithUltrasonicState(String name, RAutoWheelz opmode, double power, double maxDistance, double stopAtDistance,  String next_state) {
		super(name, opmode, power, maxDistance, next_state);
		StopAtDistance = stopAtDistance;
	}



	@Override
	public void Do() {
		super.Do();
		double ultrasonicLevel = opMode.GetUltraSonicDistance();



		if ( (ultrasonicLevel <= StopAtDistance)  && (ultrasonicLevel != 0) ) SetCurrentState(NextStateName);

	}
}

/**
 * State that DUMPS for a fixed DUMPRANGE then moves to the specified state
 */
class DumpState extends OpState {

	private RAutoWheelz opMode;
	private String NextStateName;
	private double TargetPosition;
	private double Speed;

	/**
	 * Constructor
	 *
	 * @param name       State Name
	 * @param opmode     OpMode
	 * @param targetPosition	Position to go
	 * @param next_state	Next State Name
	 */
	DumpState(String name, RAutoWheelz opmode, double targetPosition, double speed, String next_state) {
		super(name);
		opMode = opmode;
		TargetPosition = targetPosition;
		Speed = speed;
		NextStateName = next_state;
	}

	@Override
	public void OnEntry() {
		super.OnEntry();
	}

	@Override
	public void Do() {
		double dumpPosition = opMode.dump.getPosition();
		opMode.telemetry.addData(Name, String.format("%f of %f", dumpPosition, TargetPosition));
		if (dumpPosition <= TargetPosition) {
			dumpPosition += Speed;
		}
		else if (dumpPosition >= TargetPosition) {
			dumpPosition -= Speed;
		}
		//DumpPosition = Range.clip(DumpPosition, DUMP_MIN_RANGE, DUMP_MAX_RANGE);
		opMode.dump.setPosition(dumpPosition);

}

	@Override
	public void OnExit() {
		super.OnExit();
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
@Autonomous(name="RAutoWheelz", group="3058")
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

		motorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		motorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

		motorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		motorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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
			new DriveState("Forward", this, driveSpeed, 35.0, "Turn"),
			new TurnState("Turn", this, turnSpeed, 120.0, "Forward2"),
			new DriveState("Forward2", this, driveSpeed, 60.0, "Follow"),
			new FollowWithUltrasonicState("Follow",this, driveSpeed, 240, 10, "dump"),
			new DumpState("dump", this, 75.0/180., 1.0/180.0, "halt"),
			new DelayState("halt", this, 200, "halt"),
		};
		OpState.SetCurrentState("Forward");
		// #hardcore haxor 2016

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

	public void MotorsTurn( double power, boolean spin ){
		double rmotorPower = power;
		double lmotorPower = -power;
		if(!spin){
			rmotorPower = Math.max(rmotorPower,0);
			lmotorPower = Math.max(lmotorPower,0);
		}
		DbgLog.msg(toString().format("Motor turning w/ rpower = %f, lpower = %f",rmotorPower,lmotorPower));
		motorR.setPower(Range.clip(rmotorPower, -1, 1));
		motorL.setPower(Range.clip(lmotorPower, -1, 1));
	}

	public double GetMotorDistance(){
		float distanceR = motorR.getCurrentPosition();
		float distanceL = motorL.getCurrentPosition();
		//Report the motor that traveled the shortest distance (slipped the least)
		float distance = Math.max(distanceR,distanceL);
		DbgLog.msg(toString().format("Motor distance = %f", distance));
		return distance;
	}

	public double GetMotorDifference(){
		float distanceR = motorR.getCurrentPosition();
		float distanceL = motorL.getCurrentPosition();
		float difference = distanceR-distanceL;
		DbgLog.msg(toString().format("Motor difference = %f", difference));
		return difference;
	}

	public void StopMotors() {
		DbgLog.msg("Stopping Motors");
		motorR.setPower(0);
		motorL.setPower(0);
	}
	
	public void SetDumpPostion(double position){
		dumpPosition = position;
		dump.setPosition(dumpPosition);
	}

	public double GetDumpPosition() {
		dumpPosition = dump.getPosition();
		return dumpPosition;
	}

	double threshold = 200;
	public boolean LineDetected(){
		return(lineDetect.getRawLightDetected() > threshold);
	}

	public double CtsFromDist(double distance) {
			return distance / DistPerRev * CtsPerRev;
	}
}