package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.statemachines.DriveState;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;
import org.firstinspires.ftc.teamcode.controllers.ModernRoboticsSensorModule;
import org.firstinspires.ftc.teamcode.statemachines.ProgressTrans;
import org.firstinspires.ftc.teamcode.controllers.SensorModule;
import org.firstinspires.ftc.teamcode.statemachines.TurnState;
import org.firstinspires.ftc.teamcode.statemachines.BelowRangeTrans;

/**
 * Created by Derek Williams on 10/12/2016.
 */

@Autonomous(name="Basic Autonomous", group="3965")
//@Disabled

public class BasicAutonomous extends TankBot {
    //private ModernRoboticsSensorModule sensors = new ModernRoboticsSensorModule(this);

    @Override
    public void init() {
        super.init();
        //sensors.init();
    }

    @Override
    public void start(){
        super.start();
        stateMachine = new StateMachine(
                //new DriveState("forward", -100.0, new BelowRangeTrans("turnaround", sensors, 30)),
                new DriveState("forward", -0.5, new ProgressTrans("turnaround", 1000)),
                new TurnState("turnaround", 100, new ProgressTrans("forward", 7660/2))
        );
    }

}
