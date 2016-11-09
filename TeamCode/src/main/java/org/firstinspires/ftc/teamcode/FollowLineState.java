package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams of team 3965 on 11/8/2016.
 */

public class FollowLineState extends State{
    protected StateMachine lineFollowStateMachine = new StateMachine(getStateMachine().robot);

    @Override
    public void start() {

        lineFollowStateMachine.add(new State[]{
            new WaddleState("waddle_right", 100, false),
            new WaddleState("waddle_left", 100, true),
        });

        lineFollowStateMachine.add(new Transition[]{

        });
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
