package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams on 10/11/2016.
 */

public class VelocityVortexAutonomous extends VelocityVortexRobotBase {
    protected StateMachine stateMachine = new StateMachine(this);

    @Override
    public void start(){
        // Adding states to state machine
        stateMachine.add(new State[]{
                new DriveState("forward1", -100),
                new TurnState("turn1", 100),
                new DriveState("forward2", -100),
        });

        // Adding transitions to state machine
        stateMachine.add(new Transition[]{
                new BelowRangeTrans("forward1", "turn1", 20),
                new ProgressReachedTrans("turn1", "forward2", (315.0/365.0)*FULL_TURN_ROTATION),
                new BelowRangeTrans("forward2", null, 10),
        });

        // Setting Initial active state
        stateMachine.setActiveState("forward1");
    }

    @Override
    public void loop(){
        stateMachine.step();
    }

    @Override
    public void stop(){
        stateMachine.stop();
    }
}