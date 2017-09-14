package org.firstinspires.ftc.teamcode;

import java.util.ArrayList;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

/**
 * Created by Derek Williams of team 3965 on 10/9/2016.
 */

class StateMachine {
	private State activeState = null;
	private boolean stateHasStarted = false;
	public RobotBase robot;
	private ArrayList<State> states = new ArrayList<State>();
	private ArrayList<Transition> transitions = new ArrayList<Transition>();

	StateMachine(RobotBase robot) {
		this.robot = robot;
	}

	public boolean isActive(){
		return activeState != null;
	}

	public String getActiveStateName() {
		return activeState.name;
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
		if (activeState != null) {
			activeState.onExit();
		}
	}
	
	public void setActiveState(State state) {
		activeState = state;
		stateHasStarted = false;
	}
	
	public void setActiveState(String name) {
		setActiveState(getState(name));
	}

	public void deactivate() {
		activeState.onExit();
		activeState = null;
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

	public void add(Transition transition) {
		transition.onAddition(this);
		transitions.add(transition);
	}

	public void add(Transition[] transitions) {
		robot.telemetry.addLine("Adding transitions to state machine");
		for(Transition transition : transitions) {
			add(transition);
		}
	}

	/**
	 * Methods that handle transitions
     */
	public ArrayList<Transition> getPossibleTransitions() {
		ArrayList<Transition> possibleTransitions = new ArrayList<Transition>();
		for(Transition transition : transitions) {
			if(activeState == transition.getFromState()) {
				possibleTransitions.add(transition);
			}
		}
		RobotLog.i(String.format("%d transitions detected for "+activeState.name, possibleTransitions.toArray().length));
		return possibleTransitions;
	}

	public Transition getTransitionToTrigger() {
		for(Transition transition : getPossibleTransitions()) {
			if(transition.test()){
				return transition;
			}
		}
		return null;
	}

	public void triggerTransition(Transition transition) {
		setActiveState(transition.getToState());
		if (isActive()) {
			stateHasStarted = false;
		}
	}

	/**
	 * Methods that run every step when state machine is active
	 */
	private void handleStartingStates() {
		if(isActive() && !stateHasStarted) {
			RobotLog.i("Entering "+ activeState.name +" State");
			robot.telemetry.addLine("Entering "+ activeState.name +" State");
			activeState.onEntry();
			stateHasStarted = true;
		}
	}

	private void handleLooping() {
		if(isActive()) {
			activeState.doState();
		}
	}

	private void handleTransitions() {
		Transition transitionToTrigger = getTransitionToTrigger();
		if(transitionToTrigger != null) {
			RobotLog.i("Leaving "+ activeState.name +" State");
			activeState.onExit();
			triggerTransition(transitionToTrigger);
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
		if (isActive()) {
			handleStartingStates();
			handleLooping();
			handleTransitions();
			handleBecomingInactive();
		}
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
	private boolean complete = false;
	protected String name = null;
	protected ElapsedTime runtime = new ElapsedTime();

	@Override
	public void onAddition(StateMachine stateMachine) {
		super.onAddition(stateMachine);
		runtime.reset();
	}

	public void setName(String name) {
		this.name = name;
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

	public final boolean isComplete() {
		return complete;
	}

	protected void makeComplete() {
		complete = true;
	}
}

abstract class Transition extends StateMachineComponent{
	protected String fromStateName;
	protected String toStateName;

	public final State getFromState() {
		return getStateMachine().getState(fromStateName);
	}

	public final State getToState() {
		return getStateMachine().getState(toStateName);
	}

	abstract public boolean test();
}