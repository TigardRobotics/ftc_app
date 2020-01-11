package org.firstinspires.ftc.teamcode.SS_2019;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmodes.TankBot;
import org.firstinspires.ftc.teamcode.statemachines.DriveState;
import org.firstinspires.ftc.teamcode.statemachines.DriveWithHeadingState;
import org.firstinspires.ftc.teamcode.statemachines.DualGrabberState;
import org.firstinspires.ftc.teamcode.statemachines.ProgressTrans;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;
import org.firstinspires.ftc.teamcode.statemachines.TimeTrans;
import org.firstinspires.ftc.teamcode.statemachines.TurnState;
import org.firstinspires.ftc.teamcode.statemachines.WaitState;

@Autonomous(name = "SS_StoneGrabRed", group = "3965")

public class SS_StoneGrabRed extends TankBot{

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void start() {
        super.start();
        stateMachine = new StateMachine(
           new DualGrabberState("reset", true, new TimeTrans("forward", 0.5)),
                new DriveWithHeadingState("forward", 0.5, 0.0, new ProgressTrans("grab", 1200.0)),
                new DualGrabberState("grab", false, new TimeTrans("turn", 1.0)),
                new TurnState("turn", -0.5, new ProgressTrans("deliver", 14.0)),
                new DriveWithHeadingState("deliver", 0.5, -90.0, new ProgressTrans("release", 3000)),
                new DualGrabberState("release", true, new TimeTrans("park", 1.0)),
                new DriveState("park",-0.5,new ProgressTrans("wait",2000)),
                new WaitState("wait", new TimeTrans("wait", 30.0))
        );
    }

}
//Turn State + speed = Right turn
//Turn state - speed = Left turn


