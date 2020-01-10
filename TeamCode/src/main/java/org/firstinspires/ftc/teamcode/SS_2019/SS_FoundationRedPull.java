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

@Autonomous(name = "SS_FoundationRedPull", group = "3965")

public class SS_FoundationRedPull extends TankBot{

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void start() {
        super.start();
        double start_angle = 45.0;
        stateMachine = new StateMachine(
                new DualGrabberState("reset", true, new TimeTrans("forward", 1.0)),
                new DriveWithHeadingState("forward", 0.75, 0.0, new ProgressTrans("adjust", 1500)),
                new TurnState("adjust", -0.25, new ProgressTrans("inch", 3.0)),
                new DriveWithHeadingState("inch", 0.5,360.0 - start_angle, new ProgressTrans("grab", 1250.0)),
                new DualGrabberState("grab",false, new TimeTrans("pullback", 1.0)),
                new DriveState("pullback", -0.5, new ProgressTrans("release",3000)),
                new DualGrabberState("release", true, new TimeTrans("bump", 2.0)),
                new DriveState("bump",-0.50, new ProgressTrans("turn1",100)),
                new TurnState("turn1", -0.5, new ProgressTrans("move1", 14.0)),
                new DriveWithHeadingState("move1", 0.5,270.0 - start_angle, new ProgressTrans("turn2", 2000)),
                new TurnState("turn2", 0.5, new ProgressTrans("move2", 14.0)),
                new DriveWithHeadingState("move2",0.5,360.0 - start_angle, new ProgressTrans("turn3",4500)),
                new TurnState("turn3",0.5, new ProgressTrans("move3", 14.0)),
                new DriveWithHeadingState("move3", 0.5,90.0 - start_angle, new ProgressTrans("turn4", 1250)),
                new TurnState("turn4",0.5, new ProgressTrans("move4", 14.0)),
                new DriveWithHeadingState("move4", 0.5,180.0 - start_angle,new ProgressTrans("parkturn",3000)),
                new TurnState("parkturn", 0.5,new ProgressTrans("parkmove", 14.0)),
                new DriveWithHeadingState("parkmove", 0.5,270.0 - start_angle, new ProgressTrans("wait", 3000)),
                new WaitState("wait", new TimeTrans("wait", 1.0))
        );
    }

}
//Turn State + speed = Right turn
//Turn state - speed = Left turn


