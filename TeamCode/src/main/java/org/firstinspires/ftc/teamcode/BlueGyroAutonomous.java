package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Derek Williams on 2/16/2017.
 */

@Autonomous(name="AutoBlue with Gyro", group="3965")
public class BlueGyroAutonomous extends VelocityVortexAutonomous {
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
                // Driving to first beacon
                new DriveWithHeadingState("forward1", gyroDriveSpeed, 0),
                new TurnToHeadingState("turn1", 40),
                new DriveWithHeadingState("forward2", gyroDriveSpeed, 40),
                new TurnState("turn1a", -turnSpeed),

                // Pressing first button
                new EdgeFollowState("follow1", followSpeed),
                //new TurnToHeadingState("align1", 90),
                new PushButtonState("push1", color),
                //new TurnToHeadingState("align2", 90),

                // Throwing particles into vortex
                new FlickParticleState("throw"),
                new DriveWithHeadingState("reverse1", -gyroDriveSpeed, 90),

                // Driving to second beacon
                new TurnToHeadingState("turn2", 0),
                new DriveWithHeadingState("forward3", gyroDriveSpeed, 0),
                new TurnState("turn3", -turnSpeed),

                // Pressing second beacon
                new EdgeFollowState("follow2", followSpeed),
                //new TurnToHeadingState("align3", 90),
                new PushButtonState("push2", color),

                // Pushing the capball and parking
                new DriveWithHeadingState("reverse3", -gyroDriveSpeed, 90),
                new TurnToHeadingState("turn4", 30),
                new DriveAndSweepState("forward4", -driveSpeed),
        });

        // Adding transitions to state machine
        stateMachine.add(new Transition[]{
                // Driving to first beacon
                //new ProgressTrans("forward1", "turn1", cmToEnc(55.0)),
                //new StateCompletedTrans("turn1", "forward2"),
                //new ProgressTrans("forward2", "turn1a", cmToEnc(100.0)),
                //new ProgressTrans("turn1a", "follow1", rotsToEnc(0.18)),

                // Pressing first button
                //new BelowRangeTrans("follow1", "push1", rangeToBeacon),
                //new StateCompletedTrans("align1", "push1"),
                //new TimeTrans("push1", "throw", 1),
                //new StateCompletedTrans("align2", "throw"),

                // Throwing particles into vortex
                //new TimeTrans("throw", "reverse1", throwDuration),
                //new ProgressTrans("reverse1", "turn2", cmToEnc(30.0)),

                // Driving to second beacon
                //new ProgressTrans("reverse2", "turn2", cmToEnc(25.0)),  // was 20.0
                //new ProgressTrans("turn2", "forward3", rotsToEnc(0.24)),
                //new StateCompletedTrans("turn2", "forward3"),
                //new ProgressTrans("forward3", "turn3", cmToEnc(110.0)),
                //new ProgressTrans("turn3", "follow2", rotsToEnc(0.27)),

                // Pressing second button
                //new BelowRangeTrans("follow2", "push2", rangeToBeacon),
                //new StateCompletedTrans("align3", "push2"),
                //new TimeTrans("push2", "reverse3", 1),

                // Pushing the capball and parking
                //new AboveRangeTrans("reverse3", "turn4", rangeFromBeacon),
                //new StateCompletedTrans("turn4", "forward4"),
                //new ProgressTrans("forward4", null, cmToEnc(150.0)),
        });

        // Setting Initial active state
        stateMachine.setActiveState("forward1");
    }
}
