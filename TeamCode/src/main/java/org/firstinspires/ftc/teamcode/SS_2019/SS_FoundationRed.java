package org.firstinspires.ftc.teamcode.SS_2019;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmodes.TankBot;
import org.firstinspires.ftc.teamcode.statemachines.DriveState;
import org.firstinspires.ftc.teamcode.statemachines.DualGrabberState;
import org.firstinspires.ftc.teamcode.statemachines.ProgressTrans;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;
import org.firstinspires.ftc.teamcode.statemachines.TimeTrans;
import org.firstinspires.ftc.teamcode.statemachines.TurnState;
import org.firstinspires.ftc.teamcode.statemachines.WaitState;

import java.util.List;

@Autonomous(name = "SS_FoundationBlue", group = "3965")

public class SS_Foundation extends TankBot{

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void start() {
        super.start();
        stateMachine = new StateMachine(
                new DriveState("forward", 0.5, new ProgressTrans("grab", 2200)),
                new DualGrabberState("grab",true, new TimeTrans("pull", 1.0)),
                new DriveState("pull",-0.5, new ProgressTrans("release",2000)),
                new DualGrabberState("release",false, new TimeTrans("turn",1.0)),
                new TurnState("turn",0.5, new ProgressTrans("move1", 18.0)),
                new DriveState("move1", 0.5, new ProgressTrans("turn1",700)),
                new TurnState("turn1",-0.5, new ProgressTrans("move2", 18.0) ),
                new DriveState("move2",0.5, new ProgressTrans("turn2",1600)),
                new TurnState("turn2",-0.5, new ProgressTrans("move3", 18.0)),
                new DriveState("move3",0.5,new ProgressTrans("wait", 2000)),
                new WaitState("wait", new TimeTrans("wait", 1.0))
        );
    }

}
//Turn State + speed = Right turn
//Turn state - speed = Left turn

