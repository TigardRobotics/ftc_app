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
                new DriveState("forward1", driveSpeed),
                new TurnState("turn1", -turnSpeed*directionMultiplier),
                new DriveState("forward2", driveSpeed),
                new EdgeFollowState("follow1", 0.35),
                new PushButtonState("push1", color),
                new DriveState("reverse1", -driveSpeed),
                new TurnState("turn2", -turnSpeed*directionMultiplier),
                new DriveState("forward3", driveSpeed),
                new TurnState("turn3", -turnSpeed*directionMultiplier),
                new EdgeFollowState("follow2", 0.35),
                new PushButtonState("push2", color),

        });

        // Adding transitions to state machine
        stateMachine.add(new Transition[]{
                new ProgressReachedTrans("forward1", "turn1", cmToEnc(55.0)),
                new ProgressReachedTrans("turn1", "forward2", rotsToEnc(0.135)),
                new ProgressReachedTrans("forward2", "follow1", cmToEnc(100.0)),
                new BelowRangeTrans("follow1", "push1", 5),
                new TimeElapsedTrans("push1", "reverse1", 10),
                new ProgressReachedTrans("reverse1", "turn2", cmToEnc(25.0)),
                new ProgressReachedTrans("turn2", "forward3", rotsToEnc(0.270)),
                new ProgressReachedTrans("forward3", "turn3", cmToEnc(50.0)),
                new ProgressReachedTrans("turn3", "follow2", cmToEnc(0.250)),
                new BelowRangeTrans("follow2", "push2", 5),
                new TimeElapsedTrans("push2", null, 10),
    });

        // Setting Initial active state
        stateMachine.setActiveState("forward1");
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