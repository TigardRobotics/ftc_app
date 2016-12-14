package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams of team 3965 on 12/10/2016.
 */

public class BlueAutonomous2 extends VelocityVortexAutonomous {
    @Override
    public void init() {
        super.init();
        turnSpeed = -turnSpeed;
        color = BLUE;
    }

    @Override
    public void start() {
        // Adding states to state machine
        stateMachine.add(new State[]{
                new DriveState("forward", driveSpeed),
                new TurnState("turnaround", turnSpeed),
                new FlickParticleState("throw"),
                new DriveState("forward", driveSpeed),
        });


        // Adding transitions to state machine
        stateMachine.add(new Transition[]{
                new ProgressReachedTrans("forward", "turnaround",cmToEnc(60.96)),
                new ProgressReachedTrans("turnaround", "throw", rotsToEnc(0.625)),
                new TimeElapsedTrans("throw", "forward", 4),
                new ProgressReachedTrans("forward", null, cmToEnc(137.16)),
        });
    }
}
