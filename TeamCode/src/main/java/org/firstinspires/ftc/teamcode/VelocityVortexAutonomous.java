package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Derek Williams  on 10/11/2016.
 */

//@Autonomous(name="VV Auto Base", group="3965")
public class VelocityVortexAutonomous extends VelocityVortexRobotBase {
    protected int directionMultiplier;  // Multiply turn power by this
    protected String color = NO_COLOR;  // Pass this into the button pusher state
    protected StateMachine stateMachine = new StateMachine(this);

    @Override
    public void start(){
        // Adding states to state machine
        stateMachine.add(new State[]{
                //new DriveState("forward1", 100),
                //new TurnState("turn1", 100*directionMultiplier),
                //new DriveState("forward2", 100),
                new DriveState("forward", 100),
                new EdgeFollowState("follow", 0.35),
                new TurnState("align", -0.35),
                new PushButtonState("push", color),
        });

        // Adding transitions to state machine
        stateMachine.add(new Transition[]{
                //new ProgressReachedTrans("forward1", "turn1", cmToEnc(35.0)),
                //new ProgressReachedTrans("turn1", "forward2", angToEnc(120.0)),
                //new ProgressReachedTrans("forward2", "follow", cmToEnc(60.0)),
                new ProgressReachedTrans("forward", "follow", 6000),
                new BelowRangeTrans("follow", "align", 50),
                new ProgressReachedTrans("align", "push", 50.0),
                new TimeElapsedTrans("push", null, 10),
        });

        // Setting Initial active state
        stateMachine.setActiveState("forward");
    }

    @Override
    public void loop(){
        stateMachine.step();
        telemetry.addData("line detector level", getSensorModule().getLineDetectorLightLevel());
    }

    @Override
    public void stop(){
        stateMachine.stop();
    }
}