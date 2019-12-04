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

@Autonomous(name = "SS_FoundationRed ", group = "3965")

public class SS_FoundationRed extends TankBot{

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
                new TurnState("turn",-0.5, new ProgressTrans("move1", 15.0)),
                new DriveState("move1", 0.5, new ProgressTrans("turn1",900)),
                new TurnState("turn1",0.5, new ProgressTrans("move2", 17.0) ),
                new DriveState("move2",0.5, new TimeTrans("turn2",1.5)),
                new TurnState("turn2",0.5, new ProgressTrans("move3", 15.0)),
                new DriveState("move3", 0.5, new ProgressTrans("turn3",900)),
                new TurnState("turn3",0.5, new ProgressTrans("move4", 15.0)),
                new DriveState("move4",0.5,new ProgressTrans("wait", 2000)),
                new WaitState("wait", new TimeTrans("wait", 1.0))
        );
    }

}
//Turn State + speed = Right turn
//Turn state - speed = Left turn


