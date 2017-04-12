package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Derek Williams on 10/11/2016.
 */

@Autonomous(name="AutoBlue", group="3965")
public class BlueAutonomous extends VelocityVortexAutonomous {
    @Override
    public void init() {
        turnSpeed = -turnSpeed;
        color = BLUE;
        super.init();
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
                new TurnState("turn1", -turnSpeed),
                new DriveState("forward2", driveSpeed),
                new TurnState("turn1a", -turnSpeed),

                // Pressing first button
                new EdgeFollowState("follow1", followSpeed),
                new PushButtonState("push1", color),

                // Driving to second beacon
                new DriveState("reverse1", -driveSpeed*0.7),
                new TurnState("turn2", turnSpeed*0.7),
                new DriveState("forward3", driveSpeed),
                new TurnState("turn3", -turnSpeed),

                // Pressing second beacon
                new EdgeFollowState("follow2", followSpeed),
                new PushButtonState("push2", color),

                // Pushing the capball and parking
                new DriveState("reverse2", -driveSpeed),
                new TurnState("turn4", turnSpeed),
                new DriveState("forward4", -driveSpeed),
        });

        // Adding transitions to state machine
        stateMachine.add(new Transition[]{
                // Throwing particles into vortex
                new TimeElapsedTrans("throw", "forward1", throwDuration),

                // Driving to first beacon
                new ProgressReachedTrans("forward1", "turn1", inToEnc(25.0)),
                new ProgressReachedTrans("turn1", "forward2", degToEnc(38.0)),
                new ProgressReachedTrans("forward2", "turn1a", inToEnc(47.0)),
                new ProgressReachedTrans("turn1a", "follow1", degToEnc(52.0)),

                // Pressing first button
                new BelowRangeTrans("follow1", "push1", rangeToBeacon),
                new TimeElapsedTrans("push1", "reverse1", 1),


                // Driving to second beacon
                new ProgressReachedTrans("reverse1", "turn2", inToEnc(40.0)), //36 //34
                new ProgressReachedTrans("turn2", "forward3", degToEnc(62.0)), //60
                new ProgressReachedTrans("forward3", "turn3", inToEnc(60.0)),
                new ProgressReachedTrans("turn3", "follow2", degToEnc(78.0)), //58 //54

                // Pressing second button
                new BelowRangeTrans("follow2", "push2", rangeToBeacon),
                new TimeElapsedTrans("push2", "reverse2", 1),

                // Pushing the capball and parking
                new ProgressReachedTrans("reverse2", "turn4", inToEnc(5.0)), //14
                new ProgressReachedTrans("turn4", "forward4", degToEnc(55.0)),
                new ProgressReachedTrans("forward4", null, inToEnc(69.0)),  //60
        });

        // Setting Initial active state
        stateMachine.setActiveState("throw");
    }
}
