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
        turnSpeed = -turnSpeed;
        color = BLUE;
    }

    @Override
    public void start(){
        super.start();

        // Adding states to state machine
        stateMachine.add(new State[]{
                // Throwing particles into vortex
                new FlickParticleState("throw"),

                // Driving to first beacon
                new DriveState("forward1", driveSpeed),
                new TurnState("turn1", turnSpeed),
                new DriveState("forward2", driveSpeed),

                // Pressing first button
                new TurnState("turn2", -turnSpeed),
                new DriveState("reverse1", -driveSpeed),
                new PushButtonState("push1", color),

                // Driving to second beacon
                new DriveState("forward3", driveSpeed),
                new PushButtonState("push2", color),

                // Pushing the capball and parking
                new TurnState("turn3", -turnSpeed),
                new DriveState("forward4", driveSpeed),
        });

        // Adding transitions to state machine
        stateMachine.add(new Transition[]{
                // Throwing particles into vortex
                new TimeElapsedTrans("throw", "forward1", 3),

                // Driving to first beacon
                new ProgressReachedTrans("forward1", "turn1", cmToEnc(20.0)),
                new ProgressReachedTrans("turn1", "forward2", rotsToEnc(0.07)),
                new ProgressReachedTrans("forward2", "turn2", cmToEnc(350.0)),
                new ProgressReachedTrans("turn2", "reverse1", rotsToEnc(0.08)),

                // Pressing first button
//!todo                new BelowRangeTrans("reverse1", "push1", rangeToBeacon),
                new ProgressReachedTrans("reverse1", "push1", cmToEnc(53.0)),
                new TimeElapsedTrans("push1", "forward3", 1),

                // Driving to second beacon
                new ProgressReachedTrans("forward3", "push2", cmToEnc(116.0)),
                new TimeElapsedTrans("push2", "turn3", 1),

                // Pushing the capball and parking
                new ProgressReachedTrans("turn3", "forward4", rotsToEnc(0.32)),
                new ProgressReachedTrans("forward4", null, cmToEnc(125.0)),
        });

        // Setting Initial active state
        stateMachine.setActiveState("throw");
    }
}
