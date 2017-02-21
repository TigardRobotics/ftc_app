package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Derek Williams of team 3965 on 2/20/2017.
 */

@Autonomous(name="AutoRed with Gyro", group="3965")
public class RedGyroAutonomous extends VelocityVortexAutonomous {
    @Override
    public void init() {
        super.init();
        color = RED;
    }

    @Override
    public void start(){
        super.start();

        // Adding states to state machine
        stateMachine.add(new State[]{
                // Driving to first beacon
                new DriveWithHeadingState("forward1", driveSpeed, 0),
                new TurnToHeadingState("turn1", 40),
                new DriveWithHeadingState("forward2", driveSpeed, 40),

                // Pressing first button
                new EdgeFollowState("follow1", followSpeed),
                new PushButtonState("push1", color),

                // Throwing particles into vortex
                new FlickParticleState("throw"),
                new DriveWithHeadingState("reverse1", -driveSpeed, 90),

                // Driving to second beacon
                new TurnToHeadingState("turn2", 0),
                new DriveWithHeadingState("forward3", driveSpeed, 0),
                new TurnState("turn3", -turnSpeed),

                // Pressing second beacon
                new EdgeFollowState("follow2", followSpeed),
                new PushButtonState("push2", color),

                // Pushing the capball and parking
                new DriveWithHeadingState("reverse3", -driveSpeed, 90),
                new TurnToHeadingState("turn4", 33),
                new DriveWithHeadingAndSweepState("forward4", -driveSpeed, 33),
        });

        // Adding transitions to state machine
        stateMachine.add(new Transition[]{
                // Driving to first beacon
                new ProgressReachedTrans("forward1", "turn1", cmToEnc(45.0)),
                new StateCompletedTrans("turn1", "forward2"),
                new ProgressReachedTrans("forward2", "follow1", cmToEnc(92.0)),

                // Pressing first button
                new BelowRangeTrans("follow1", "push1", rangeToBeacon),
                new TimeElapsedTrans("push1", "throw", 1),

                // Throwing particles into vortex
                new TimeElapsedTrans("throw", "reverse1", throwDuration),

                // Driving to second beacon
                new ProgressReachedTrans("reverse1", "turn2", cmToEnc(30.0)),
                new StateCompletedTrans("turn2", "forward3"),
                new ProgressReachedTrans("forward3", "turn3", cmToEnc(116.0)),
                new StateCompletedTrans("turn3", "follow2"),

                // Pressing second button
                new BelowRangeTrans("follow2", "push2", rangeToBeacon),
                new TimeElapsedTrans("push2", "reverse3", 1),

                // Pushing the capball and parking
                new AboveRangeTrans("reverse3", "turn4", rangeFromBeacon),
                new StateCompletedTrans("turn4", "forward4"),
                new ProgressReachedTrans("forward4", null, cmToEnc(125.0)),
        });

        // Setting Initial active state
        stateMachine.setActiveState("forward1");
    }
}
