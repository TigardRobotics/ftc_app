package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Derek Williams on 10/12/2016.
 */

@Autonomous(name="Basic Autonomous", group="3965")
public class BasicAutonomous extends RobotBase {
    private StateMachine machine = new StateMachine(this);

    @Override
    public void start(){
        // Adding states to state machine
        machine.add(new State[]{
            new TurnState("Turn", 100),
            //new DriveState("Forward", this, 100, 2000, "Backward"),
            //new DriveState("Backward", this, -100, 2000, null),
        });

        // Adding transitions to state machine
        machine.add(new Transition[]{
            new TimeElapsedTrans("Turn", null, 7900),
        });

        // Setting Initial active state
        machine.setActiveState("Turn");
    }

    @Override
    public void loop(){
        machine.step();
    }

    @Override
    public void stop(){
        machine.stop();
    }
}
