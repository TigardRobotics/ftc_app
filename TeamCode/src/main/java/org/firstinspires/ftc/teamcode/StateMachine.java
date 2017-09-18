package org.firstinspires.ftc.teamcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

/**
 * Created by Derek Williams of team 3965 on 10/9/2016.
 */

class StateMachine {
	private State currentState;
	private boolean stateHasStarted = false;
	public RobotBase robot;
	private ArrayList<State> states = new ArrayList<State>();

	StateMachine(RobotBase robot) {
		this.robot = robot;
	}

	public boolean isActive(){
		return currentState != null;
	}

	public String getActiveStateName() {
		return currentState.name;
	}

	public State getState(String name) {
		if (name == null) return null;
		for(State state : states) {
			if(state.name == name) {
				return state;
			}
		}
		throw new RuntimeException(name+" NOT FOUND");
	}

	public void stop() {
		if (currentState != null) {
			currentState.onExit();
		}
	}
	
	public void setCurrentState(State state) {
		currentState = state;
		stateHasStarted = false;
	}
	
	public void setActiveState(String name) {
		setCurrentState(getState(name));
	}

	public void deactivate() {
		currentState.onExit();
		currentState = null;
	}

	/**
	 * Methods used to add states and transitions to state machine
     */
	public void add(State state) {
		state.onAddition(this);
		robot.telemetry.addLine("Adding "+state.name+" to state machine");
		states.add(state);
	}
	
	public void add(State[] states) {
		for(State state : states) {
			add(state);
		}
	}

	//!! REMOVE THESE TRANSITION METHODS
	public void add(Transition transition) {

	}

	public void add(Transition[] transitions) {

	}

	/**
	 * Methods that run every step when state machine is active
	 */
	private void handleStartingStates() {
		if(isActive() && !stateHasStarted) {
			RobotLog.i("Entering "+ currentState.name +" State");
			robot.telemetry.addLine("Entering "+ currentState.name +" State");
			currentState.onEntry();
			stateHasStarted = true;
		}
	}

	private void handleLooping() {
		if(isActive()) {
			currentState.doState();
		}
	}


	public void handleBecomingInactive() {
		if(!isActive()) {
			robot.telemetry.addLine("STATE MACHINE NOW INACTIVE");
		}
	}


	/*
	* Step Method is periodically called
	 */
	public void step(){

	}
}


abstract class StateMachineComponent {
	private StateMachine stateMachine;
	private boolean stateMachineInitialized = false;

	public void onAddition(StateMachine stateMachine) {
		setStateMachine(stateMachine);
	}

	public StateMachine getStateMachine(){
		if (stateMachineInitialized) {
			return stateMachine;
		}
		throw new RuntimeException("State attempting to access state machine before addition to it");
	}

	protected void setStateMachine(StateMachine stateMachine) {
		stateMachineInitialized = true;
		this.stateMachine = stateMachine;
	}

	public RobotBase getRobot() {
		if (stateMachineInitialized) {
			return getStateMachine().robot;
		}
		throw new RuntimeException("State attempting to access robot before addition to state machine");
	}

	public SensorModule getSensorModule() {
		if (stateMachineInitialized) {
			return getStateMachine().robot.Sensors();
		}
		throw new RuntimeException("State attempting to access robot before addition to state machine");
	}
}

abstract class State extends StateMachineComponent {
	private List<Transition> transitions;
	private boolean complete = false;
	protected String name;
	protected ElapsedTime runtime = new ElapsedTime();

	State(String name, Transition... transitions){
		this.name = name;
		this.transitions = Arrays.asList(transitions);
	}

	@Override
	public void onAddition(StateMachine stateMachine) {
		super.onAddition(stateMachine);
		runtime.reset();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public double getProgress() {
		return 0.0;
	}
	
	public void onEntry() {
		runtime.reset();
		complete = false;
	}
	
	public abstract void doState();
	
	public void onExit() {
		complete = false;
	}

	public String checkTransitions() {
		for(Transition transition : transitions) {
			if(transition.test()) return transition.getDestination();
		}
		return "";
	}

	public final boolean isComplete() {
		return complete;
	}

	protected void makeComplete() {
		complete = true;
	}
}

abstract class Transition extends StateMachineComponent{
	protected State source;
	protected String destination;
	private boolean initialized = false;

	public Transition(String destination) {
		this.destination = destination;
	}

	public void setSource(State source) {
		this.source = source;
		this.initialized = true;
	}

	public final String getSourceName() {
		return source.getName();
	}

	public final State getSource() {
		return source;
	}

	public final String getDestination() {
		return destination;
	}

	public boolean check() {
		if(initialized) {
			return test();
		}
		else throw new RuntimeException("Transition from "+source.getName()+" to "+destination+" is being checked before it's initialized");
	}

	abstract protected boolean test();
}