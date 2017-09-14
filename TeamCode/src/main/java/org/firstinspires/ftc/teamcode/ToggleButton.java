package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams of team 3965 on 11/8/2016.
 */


public class ToggleButton {
    StateMachine stateMachine;
    ValueTrans[] transitions;

    ToggleButton (RobotBase robot, State firstState, State secondState) {
        this.stateMachine = new StateMachine(robot);

        // Renaming the given states
        firstState.setName("first");
        secondState.setName("second");

        // Adding states to state machine
        stateMachine.add(new State[]{
            firstState,
            secondState,
            new WaitState("wait_before_first"),
            new WaitState("wait_before_second")
        });

        // Initializing button pressed transitions
        transitions = new ValueTrans[]{
            new ValueTrans("second", "wait_before_first"),
            new ValueTrans("wait_before_first", "first"),
            new ValueTrans("first", "wait_before_second"),
            new ValueTrans("wait_before_second", "second"),
        };

        // Adding Transitions to state machine
        stateMachine.add(transitions);

        // Setting initial state
        stateMachine.setActiveState("first");
    }

    public void step(boolean button) {
        transitions[0].setValue(button);
        transitions[1].setValue(!button);
        transitions[2].setValue(button);
        transitions[3].setValue(!button);
        stateMachine.step();
    }
}


class WaitState extends State {

    WaitState (String name) {
        this.name = name;
    }

    @Override
    public void doState() {
        getRobot().telemetry.addData(name, "waiting . . .");
    }

    @Override
    public void onExit() {}
}



