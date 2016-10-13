package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Derek Williams on 10/12/2016.
 */

@Autonomous(name="Basic Autonomous", group="3965")
public class BasicAutonomous extends RobotBase {
    private StateMachine States = new StateMachine();

    @Override
    public void start(){
        States.add(new State[]{
            new DriveState("Forward", this, 100, 100, "Backward"),
            new DriveState("Backward", this, -100, 100, null),
        });
    }

    @Override
    public void loop(){
        States.step();
    }

    @Override
    public void stop(){

    }
}
