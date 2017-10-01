package org.firstinspires.ftc.teamcode.VV_2016;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.RobotBase;

/**
 * Created by Derek Williams of team 3965 on 2/20/2017.
 */

@Autonomous(name="AutoRed with Gyro", group="3965")
@Disabled

public class RedGyroAutonomous extends VelocityVortexAuto {
    @Override
    public void init() {
        super.init();
        color = RobotBase.RED;
    }

    @Override
    public void start(){
        super.start();
        /*
        // Adding states to state machine
        stateMachine.add(new State[]{
                // Driving to first beacon
                new DriveWithHeadingState("forward1", gyroDriveSpeed, 0),
                new TurnToHeadingState("turn1", 320),
                new DriveWithHeadingState("forward2", gyroDriveSpeed, 320),

                // Pressing first button
                new EdgeFollowState("follow1", followSpeed),
                new PushButtonState("push1", color),

                // Throwing particles into vortex
                new FlickParticleState("throw"),
                new DriveWithHeadingState("reverse1", -gyroDriveSpeed, 270),

                // Driving to second beacon
                new TurnToHeadingState("turn2", 0),
                new DriveWithHeadingState("forward3", gyroDriveSpeed, 0),
                new TurnState("turn3", -turnSpeed),

                // Pressing second beacon
                new EdgeFollowState("follow2", followSpeed),
                new PushButtonState("push2", color),

                // Pushing the capball and parking
                new DriveWithHeadingState("reverse3", -gyroDriveSpeed, 270),
                new TurnToHeadingState("turn4", 320),
                new DriveAndSweepState("forward4", -gyroDriveSpeed),
        });

        // Adding transitions to state machine
        /*
        stateMachine.add(new Transition[]{
                // Driving to first beacon
                new ProgressTrans("forward1", "turn1", cmToEnc(45.0)),
                new StateCompletedTrans("turn1", "forward2"),
                new ProgressTrans("forward2", "follow1", cmToEnc(92.0)),

                // Pressing first button
                new BelowRangeTrans("follow1", "push1", rangeToBeacon),
                new TimeTrans("push1", "throw", 1),

                // Throwing particles into vortex
                new TimeTrans("throw", "reverse1", throwDuration),

                // Driving to second beacon
                new ProgressTrans("reverse1", "turn2", cmToEnc(30.0)),
                new StateCompletedTrans("turn2", "forward3"),
                new ProgressTrans("forward3", "turn3", cmToEnc(116.0)),
                new ProgressTrans("turn3", "follow2", rotsToEnc(0.250)),

                // Pressing second button
                new BelowRangeTrans("follow2", "push2", rangeToBeacon),
                new TimeTrans("push2", "reverse3", 1),

                // Pushing the capball and parking
                new AboveRangeTrans("reverse3", "turn4", rangeFromBeacon),
                new StateCompletedTrans("turn4", "forward4"),
                new ProgressTrans("forward4", null, cmToEnc(125.0)),
        });


        // Setting Initial active state
        stateMachine.setActiveState("forward1");
        */
    }
}
