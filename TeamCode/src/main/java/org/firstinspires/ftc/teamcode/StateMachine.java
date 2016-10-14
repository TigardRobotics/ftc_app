package org.firstinspires.ftc.teamcode;

import java.util.ArrayList;
import com.qualcomm.ftccommon.DbgLog;

/**
 * Created by Derek Williams of team 3965 on 10/9/2016.
 */

class StateMachine {
	private State CurrentState = null;
	private boolean StateHasStarted = false;
	private boolean Active = true;
	private ArrayList<State> States = new ArrayList<State>();
	//private ArrayList<Transition> Transitions = new Arraylist<Transition>();

	public boolean isActive(){
		return Active;
	}

	public State getState(String name) {
		for(State state : States) {
			if(state.Name == name) {
				return state;
			}
		}
		return null;
	}
	
	public void setCurrentState(State state) {
		CurrentState = state;
	}
	
	public void setCurrentState(String name) {
		CurrentState = getState(name);
	}
	
	public void add(State state) {
		States.add(state);
	}
	
	public void add(State[] states) {
		for(State state : states) {
			add(state);
		}
	}
	
	public void step(){
		if(CurrentState != null) {
			Active = true;
			if(!StateHasStarted){
				DbgLog.msg("Entering "+CurrentState.Name+" State");
				CurrentState.start();
				StateHasStarted = true;
			}
			else if(CurrentState.isIncomplete()) {
				CurrentState.loop();
				CurrentState.checkComplete();
			}
			else {
				DbgLog.msg("Leaving "+CurrentState.Name+" State");
				String nextStateName = CurrentState.NextStateName;
				CurrentState.stop();
				StateHasStarted = false;
				CurrentState = getState(nextStateName);
			}
		}
		else {
			Active = false;
		}
	}
}

abstract class State {
	protected String Name = null;
	protected boolean Complete = false;
	protected String NextStateName = null;
	protected RobotBase Robot = null;
	// protected StateMachine Machine;
	
	public final boolean isIncomplete(){
		return !Complete;
	}
	
	public void start(){}
	
	public void loop(){}
	
	public abstract void checkComplete();
	
	public void stop(){}
}

// New idea: Experimental
abstract class Transition {
	protected StateMachine Machine;
	protected String FromStateName;
	protected String ToStateName;

	final State getFromState() {
		return Machine.getState(FromStateName);
	}

	final State getToState() {
		return Machine.getState(ToStateName);
	}

	abstract public boolean Condition();
}