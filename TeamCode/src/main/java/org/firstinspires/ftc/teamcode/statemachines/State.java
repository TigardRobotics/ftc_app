package org.firstinspires.ftc.teamcode.statemachines;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Derek on 9/18/17.
 */

public abstract class State {
    public static RobotBase Robot;
    private List<Transition> transitions;
    //private boolean complete = false;  //! Should be removed
    protected String name;
    protected ElapsedTime runtime = new ElapsedTime();

    public State(String name, Transition... transitions){
        this.name = name;
        this.transitions = Arrays.asList(transitions);
        for(Transition transition : this.transitions) {
            transition.setSource(this);
        }
    }

    public final String getName() {
        return name;
    }

    public double getProgress() {
        return 0.0;
    }

    public void onEntry() {
        RobotBase.log("Entering "+name+" state");
        runtime.reset();
        //complete = false;
    }

    public abstract void doState();

    public void onExit() {
        RobotBase.log("Exiting "+name+" state");
    }

    public String checkTransitions() {
        for(Transition transition : transitions) {
            if(transition.check()) return transition.getDestination();
        }
        return "";
    }

    //! Should be removed with CompleteState
    public final boolean isComplete() {
        return getProgress() != 0;
    }

}
