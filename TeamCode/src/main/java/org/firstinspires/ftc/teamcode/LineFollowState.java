package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams of team 3965 on 11/8/2016.
 */

public class LineFollowState extends State {
    protected StateMachine lineFollowStateMachine;
    protected double power;

    LineFollowState(String name, double power) {
        this.name = name;
        this.power = power;
    }

    @Override
    public void onAddition (StateMachine stateMachine) {
        super.onAddition(stateMachine);
        lineFollowStateMachine = new StateMachine(getStateMachine().robot);
    }

    @Override
    public void start() {
        super.start();

        // Adding states to state machine
        lineFollowStateMachine.add(new State[]{
            new WaddleRightState("waddle_right", power),
            new WaddleLeftState("waddle_left", power),
        });

        // Adding transitions to state machine
        lineFollowStateMachine.add(new Transition[]{
            new LineDetectedTrans("waddle_right", "waddle_left"),
            new LineUndetectedTrans("waddle_left", "waddle_right"),
        });

        // Setting initial state for line follow state machine
        lineFollowStateMachine.setActiveState("waddle_right");
    }

    @Override
    public void loop() {
        lineFollowStateMachine.step();
    }

    @Override
    public void stop() {
        lineFollowStateMachine.stop();
    }
}
