package org.firstinspires.ftc.teamcode.VV_2016;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Created by Derek Williams of team 3965 on 12/10/2016.
 */

@Autonomous(name="AutoRed2", group="3965")
@Disabled

public class RedAutonomous2 extends VelocityVortexAuto {
    @Override
    public void init() {
        super.init();
        color = RobotBase.BLUE;
    }

    @Override
    public void start() {
        /*
        // Adding states to state machine
        stateMachine.add(new State[]{
                new DriveState("forward", driveSpeed),
                new TurnState("turnaround", turnSpeed),
                new FlickParticleState("throw"),
                new DriveState("forward", driveSpeed),
        });


        // Adding transitions to state machine
        /*
        stateMachine.add(new Transition[]{
                new ProgressTrans("forward", "turnaround",cmToEnc(60.96)),
                new ProgressTrans("turnaround", "throw", rotsToEnc(0.625)),
                new TimeTrans("throw", "forward", 4),
                new ProgressTrans("forward", null, cmToEnc(137.16)),
        });
        */
    }
}
