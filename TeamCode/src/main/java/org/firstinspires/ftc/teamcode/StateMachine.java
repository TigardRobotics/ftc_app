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
	private List<State> states;
	private boolean firstUse;

	public StateMachine(State... states) {
		firstUse = true;
		currentState = states[0];
		this.states = Arrays.asList(states);
	}

	public void stop() {
		if (currentState != null) {
			currentState.onExit();
		}
	}

	/*
	* Step Method is periodically called
	 */
	public void step(){
		if(firstUse) currentState.onEntry();
		boolean transitioned;
		do {
			currentState.doState();
			String next = currentState.checkTransitions();
			if(next != "") {
				boolean nextExists = false;
				transitioned = true;
				currentState.onExit();
				for(State state : this.states) {
					if(state.getName() == next) {
						currentState = state;
						nextExists = true;
						currentState.onEntry();
					}
				}
				if(!nextExists) {
					throw new RuntimeException("State "+next+" does not exist.");
				}
			}
			else transitioned = false;
		} while(transitioned);
	}
}
