package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Derek Williams on 10/11/2016.
 */

public class VelocityVortexAutonomous extends VelocityVortexRobotBase {
    protected StateMachine States = new StateMachine();

    @Override
    public void start(){
        States.add(new State[]{
            // States go here
        });
    }

    @Override
    public void loop(){}

    @Override
    public void stop(){}
}
