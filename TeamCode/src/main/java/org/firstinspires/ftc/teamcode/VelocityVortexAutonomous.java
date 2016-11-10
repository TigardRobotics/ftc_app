package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Derek Williams on 10/11/2016.
 */

//@Autonomous(name="VV Auto Base", group="3965")
public class VelocityVortexAutonomous extends VelocityVortexRobotBase {
    protected int directionMultiplier;  // Multiply turn power by this
    protected StateMachine stateMachine = new StateMachine(this);

    @Override
    public void start(){
        // Adding states to state machine
        stateMachine.add(new State[]{
                new DriveState("forward1", -100),
                new TurnState("turn1", 100*directionMultiplier),
                new DriveState("forward2", 100),
        });

        // Adding transitions to state machine
        stateMachine.add(new Transition[]{
                new BelowRangeTrans("forward1", "turn1", 5),
                new ProgressReachedTrans("turn1", "forward2", 28),
                new ProgressReachedTrans("forward2", null, 15000),
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