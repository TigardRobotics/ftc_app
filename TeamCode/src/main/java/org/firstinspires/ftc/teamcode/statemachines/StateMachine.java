package org.firstinspires.ftc.teamcode.statemachines;

import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

import java.util.List;
import java.util.Arrays;

/**
 * Created by Derek Williams of team 3965 on 10/9/2016.
 */

public class StateMachine {
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
			RobotBase.instance.telemetry.addData("Exiting state", currentState.getName());
		}
	}

	/*
	* Step Method is periodically called
	 */
	public void step() {
		if(firstUse) {
			currentState.onEntry();
			RobotBase.instance.telemetry.addData("Entering state", currentState.getName());
			RobotBase.log("Entering state "+currentState.getName());
			firstUse = false;
		}
		boolean transitioned;
		do {
			currentState.doState();
			String next = currentState.checkTransitions();
			if(next != "") {
				boolean nextExists = false;
				transitioned = true;
				currentState.onExit();
				RobotBase.instance.telemetry.addData("Exiting state", currentState.getName());
				RobotBase.log("Exiting state "+currentState.getName());
				for(State state : this.states) {
					if(state.getName() == next) {
						currentState = state;
						nextExists = true;
						currentState.onEntry();
						RobotBase.instance.telemetry.addData("Entering state", currentState.getName());
						RobotBase.log("Entering state "+currentState.getName());
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
