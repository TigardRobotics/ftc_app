package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Robotics on 4/15/2017.
 */

@Autonomous(name="Back n' Forth", group="3965")
public class TestBackForth extends VelocityVortexAutonomous {
    @Override
    public void start(){
        super.start();

        // Adding states to state machine
        stateMachine.add(new State[]{
                new DriveState("forward", driveSpeed),
                new TurnState("cturn", turnSpeed),
                new DriveState("reverse", -driveSpeed),
                new TurnState("ccturn", -turnSpeed)
        });

        // Adding transitions to state machine
        stateMachine.add(new Transition[]{
                new ProgressReachedTrans("forward", "cturn", inToEnc(3.0*12.0)),
                new ProgressReachedTrans("cturn", "reverse", degToEnc(360.0)),
                new ProgressReachedTrans("reverse", "ccturn", inToEnc(3.0*12.0)),
                new ProgressReachedTrans("ccturn", "forward", degToEnc(360.0)),
        });

        // Setting Initial active state
        stateMachine.setActiveState("forward");
    }
}
