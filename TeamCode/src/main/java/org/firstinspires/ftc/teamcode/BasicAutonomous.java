package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Derek Williams on 10/12/2016.
 */

@Autonomous(name="Basic Autonomous", group="3965")
public class BasicAutonomous extends RobotBase {
    private StateMachine machine = new StateMachine(this);
    private ModernRoboticsSensorModule sensorModule = new ModernRoboticsSensorModule(this);

    @Override
    public void init() {
        super.init();
        sensorModule.init();
    }

    @Override
    public void start(){
        // Adding states to state machine
        machine.add(new State[]{
            new DriveState("forward", -100),
            new TurnState("turnaround", 100),
        });

        // Adding transitions to state machine
        machine.add(new Transition[]{
            new BelowRangeTrans("forward", "turnaround", 30),
            new ProgressReachedTrans("turnaround", "forward", 7660/2),
        });

        // Setting Initial active state
        machine.setActiveState("forward");
    }

    @Override
    public void loop(){
        machine.step();
    }

    @Override
    public void stop(){
        machine.stop();
    }

    @Override
    public SensorModule Sensors() {
        return sensorModule;
    }
}
