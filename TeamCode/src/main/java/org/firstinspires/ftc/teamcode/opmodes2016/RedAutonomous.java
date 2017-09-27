package org.firstinspires.ftc.teamcode.opmodes2016;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RobotBase;

/**
 * Created by Derek Williams on 10/11/2016.
 */

@Autonomous(name="AutoRed", group="3965")
public class RedAutonomous extends VelocityVortexAuto {
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
                new DriveState("forward1", driveSpeed),
                new TurnState("turn1", -turnSpeed),
                new DriveState("forward2", driveSpeed),

                // Pressing first button
                new EdgeFollowState("follow1", followSpeed),
                new PushButtonState("push1", color),

                // Throwing particles into vortex
                new FlickParticleState("throw"),
                new DriveState("reverse1", -driveSpeed),

                // Driving to second beacon
                new DriveState("reverse2", -driveSpeed),
                new TurnState("turn2", turnSpeed),
                new DriveState("forward3", driveSpeed),
                new TurnState("turn3", -turnSpeed),

                // Pressing second beacon
                new EdgeFollowState("follow2", followSpeed),
                new PushButtonState("push2", color),

                // Pushing the capball and parking
                new DriveState("reverse3", -driveSpeed),
                new TurnState("turn4", turnSpeed),
                new DriveAndSweepState("forward4", -driveSpeed),
        });

        // Adding transitions to state machine
        /*
        stateMachine.add(new Transition[]{
                // Driving to first beacon
                //new ProgressTrans("forward1", "turn1", cmToEnc(45.0)),
                //new ProgressTrans("turn1", "forward2", rotsToEnc(0.14)),  //0.135
                //new ProgressTrans("forward2", "follow1", cmToEnc(92.0)),

                // Pressing first button
                //new BelowRangeTrans("follow1", "push1", rangeToBeacon),
                //new TimeTrans("push1", "throw", 1),

                // Throwing particles into vortex
                //new TimeTrans("throw", "reverse1", throwDuration),
                //new AboveRangeTrans("reverse1", "reverse2", rangeToShoot),

                // Driving to second beacon
                //new ProgressTrans("reverse2", "turn2", cmToEnc(15.0)),  //20.0
                new ProgressTrans("turn2", "forward3", rotsToEnc(0.24)),
                new ProgressTrans("forward3", "turn3", cmToEnc(116.0)),
                new ProgressTrans("turn3", "follow2", rotsToEnc(0.250)),

                // Pressing second button
                new BelowRangeTrans("follow2", "push2", rangeToBeacon),
                new TimeTrans("push2", "reverse3", 1),

                // Pushing the capball and parking
                new AboveRangeTrans("reverse3", "turn4", rangeFromBeacon),
                new ProgressTrans("turn4", "forward4", rotsToEnc(0.11)),
                new ProgressTrans("forward4", null, cmToEnc(125.0)),
        });
        */

        // Setting Initial active state
        //stateMachine.setActiveState("forward1");
    }
}
