package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Derek Williams on 10/11/2016.
 */

@Autonomous(name="AutoBlue", group="3965")
public class BlueAutonomous extends VelocityVortexAutonomous {
    @Override
    public void init() {
        super.init();
        directionMultiplier = -1;
        color = BLUE;
    }

    @Override
    public void start(){
        // Adding states to state machine
        stateMachine.add(new State[]{
                // Driving to first beacon
                new DriveState("forward1", driveSpeed),
                new TurnState("turn1", -turnSpeed*directionMultiplier),
                new DriveState("forward2", driveSpeed),

                // Pressing first button
                new EdgeFollowState("follow1", followSpeed),
                new PushButtonState("push1", color),

                // Driving to second beacon
                new DriveState("reverse1", -driveSpeed),
                new TurnState("turn2", turnSpeed*directionMultiplier),
                new DriveState("forward3", driveSpeed),
                new TurnState("turn3", -turnSpeed*directionMultiplier),

                // Pressing second beacon
                new EdgeFollowState("follow2", followSpeed),
                new PushButtonState("push2", color),

        });

        // Adding transitions to state machine
        stateMachine.add(new Transition[]{
                // Driving to first beacon
                new ProgressReachedTrans("forward1", "turn1", cmToEnc(55.0)),
                new ProgressReachedTrans("turn1", "forward2", rotsToEnc(0.14)),  //0.135
                new ProgressReachedTrans("forward2", "follow1", cmToEnc(100.0)),

                // Pressing first button
                new BelowRangeTrans("follow1", "push1", rangeToBeacon),
                new TimeElapsedTrans("push1", "reverse1", 2),

                // Driving to second beacon
                new AboveRangeTrans("reverse1", "turn2", rangeFromBeacon),
                new ProgressReachedTrans("turn2", "forward3", rotsToEnc(0.25)),
                new ProgressReachedTrans("forward3", "turn3", cmToEnc(105.0)),
                new ProgressReachedTrans("turn3", "follow2", cmToEnc(0.250)),

                // Pressing second button
                new BelowRangeTrans("follow2", "push2", rangeToBeacon),
                new TimeElapsedTrans("push2", null, 10),
        });

        // Setting Initial active state
        stateMachine.setActiveState("forward1");
    }
}
