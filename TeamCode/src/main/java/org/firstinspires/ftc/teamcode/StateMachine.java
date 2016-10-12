package org.firstinspires.ftc.teamcode;

import java.util.ArrayList;

/**
 * Created by Derek Williams of team 3965 on 10/9/2016.
 */

class StateMachine {
	private State CurrentState = null;
	private boolean StateHasStarted = false;
	private boolean Active = true;
	private ArrayList<State> OpStates = new ArrayList<State>();

	public boolean isActive(){
		return Active;
	}

	public State getByName(String name) {
		for(State state : OpStates) {
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
		CurrentState = getByName(name);
	}
	
	public void add(State state) {
		OpStates.add(state);
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
				CurrentState.start();
				StateHasStarted = true;
			}
			else if(CurrentState.isIncomplete()) {
				CurrentState.loop();
				CurrentState.checkComplete();
			}
			else {
				String nextStateName = CurrentState.NextStateName;
				CurrentState.exit();
				StateHasStarted = false;
				CurrentState = getByName(nextStateName);
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
	
	public final boolean isIncomplete(){
		return !Complete;
	}
	
	public void start(){}
	
	public void loop(){}
	
	abstract public void checkComplete();
	
	public void exit(){}
}

/*
class PrintState extends State{
	PrintState(String name, String nextstatename){
		Name = name;
		NextStateName = nextstatename;
	}

	@Override
	public void start(){
		System.out.println("Startin' the print state called "+Name);
	}

	@Override
	public void loop(){
		System.out.println("Doin' the state called "+Name);
	}

	@Override
	public void checkComplete(){
		System.out.println("Checkin' if this state is done");
		if(2+2==4){
			Complete = true;
		}
	}

	@Override
	public void exit(){
		System.out.println("Exitn' the state known as "+Name);
	}
}
*/