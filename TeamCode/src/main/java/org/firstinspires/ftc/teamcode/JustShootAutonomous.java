package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Derek Williams of team 3965 on 2/25/2017.
 */

@Autonomous(name="Just Shoot It", group="3965")
public class JustShootAutonomous extends VelocityVortexAutonomous {
    @Override
    public void start(){
        super.start();

        stateMachine.add(new State[]{
                new FlickParticleState("throw"),
        });

        // Adding transitions to state machine
        stateMachine.add(new Transition[]{
                new TimeElapsedTrans("throw", null, throwDuration),
        });

        // Setting Initial active state
        stateMachine.setActiveState("throw");
    }
}
