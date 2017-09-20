package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Derek Williams on 10/12/2016.
 */

@Autonomous(name="Basic Autonomous", group="3965")
public class BasicAutonomous extends RobotBase {
    private StateMachine machine; //= new StateMachine(this);
    private ModernRoboticsSensorModule sensors = new ModernRoboticsSensorModule(this);

    @Override
    public void init() {
        super.init();
        sensors.init();
    }

    @Override
    public void start(){
        machine = new StateMachine(
                new DriveState("forward", -100.0, sensors, new BelowRangeTrans("turnaround", sensors, 30)),
                new TurnState("turnaround", 100, sensors, new ProgressTrans("forward", 7660/2))
        );

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
        return sensors;
    }
}
