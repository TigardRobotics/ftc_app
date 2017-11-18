package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.statemachines.DriveState;
import org.firstinspires.ftc.teamcode.statemachines.ProgressTrans;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;
import org.firstinspires.ftc.teamcode.statemachines.TurnState;

/**
 * Created by Derek on 10/3/17.
 * Test driving in a square.
 */

@Autonomous(name="Square", group="test")
@Disabled
public class SquareAuto extends TankBot {

    private double sqrSideLen = 30.5;   //12 inches
    private double turnAmount = 90.0;   //90 degrees

    @Override
    public void init() {
        super.init();
        //sensors.init();
    }

    @Override
    public void start(){
        super.start();
        stateMachine = new StateMachine(
                new DriveState("drive", -0.5, new ProgressTrans("turn", sqrSideLen)),
                new TurnState("turn", 0.5, new ProgressTrans("drive", turnAmount))
        );
    }

}
