package org.firstinspires.ftc.teamcode.opmodes2016;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

/**
 * Created by Derek Williams of team 3965 on 2/25/2017.
 */

@Autonomous(name="Just Shoot It", group="3965")
@Disabled
public class JustShootAutonomous extends VelocityVortexAuto {
    @Override
    public void start(){
        super.start();
        /*
        stateMachine.add(new State[]{
                new FlickParticleState("throw"),
        });

        // Adding transitions to state machine
        stateMachine.add(new Transition[]{
                //new TimeTrans("throw", null, throwDuration),
        });

        // Setting Initial active state
        stateMachine.setActiveState("throw");
        */
    }
}
