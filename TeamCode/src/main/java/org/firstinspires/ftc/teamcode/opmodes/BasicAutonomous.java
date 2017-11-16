package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.TimestampedData;

import org.firstinspires.ftc.teamcode.statemachines.DriveState;
import org.firstinspires.ftc.teamcode.statemachines.SpinState;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;
import org.firstinspires.ftc.teamcode.statemachines.ProgressTrans;
import org.firstinspires.ftc.teamcode.statemachines.TimeTrans;
import org.firstinspires.ftc.teamcode.statemachines.TurnState;

/**
 * Created by Derek Williams on 10/12/2016.
 */

@Autonomous(name="Basic Autonomous", group="3965")
//@Disabled

public class BasicAutonomous extends SwerveBase {

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void start(){
        super.start();
        stateMachine = new StateMachine(
                //new DriveState("forward", -100.0, new BelowRangeTrans("turnaround", sensors, 30)),
                new DriveState("forward", 0.5, new ProgressTrans("to spin", 12*2.54)),  //Drive 12"
                new SpinState("to spin", 0.0, new TimeTrans("turnaround", 1.5)),
                new SpinState("turnaround", 0.5, new ProgressTrans("to drive", 180.0)),         //Rotate 180 and repeat
                new DriveState("to drive", 0.0, new TimeTrans("forward", 1.5))
        );
    }

}
