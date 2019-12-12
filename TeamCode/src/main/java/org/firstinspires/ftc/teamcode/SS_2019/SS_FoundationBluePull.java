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

@Autonomous(name = "SS_FoundationBluePull", group = "3965")

public class SS_FoundationBluePull extends TankBot{

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void start() {
        super.start();
        stateMachine = new StateMachine(
                new DriveState("forward", 0.75, new ProgressTrans("adjust", 1000)),
                new TurnState("adjust", -0.5, new ProgressTrans("pre-inch", 18.0)),
                new WaitState("pre-inch", new TimeTrans("inch",1.0)),
                new DriveState("inch", 0.5, new ProgressTrans("readjust", 130.0)),
                new TurnState("readjust", 0.5, new ProgressTrans("foundation", 23.0)),
                new DriveState("foundation", 0.5, new ProgressTrans("grab",1000)),
                new DualGrabberState("grab",true, new TimeTrans("pull", 1.0)),
                new DriveState("pull",-0.75, new ProgressTrans("release",8000)),
                new DualGrabberState("release", false, new TimeTrans("pre-bump", 1.0)),
                new TurnState("pre-bump", 0.5, new ProgressTrans("bump", 9.0)),
                new DriveState("bump", 0.5, new ProgressTrans("pullback", 1000)),
                new DriveState("pullback", -0.5, new ProgressTrans("wait", 1100)),
                new WaitState("wait", new TimeTrans("wait", 1.0))
        );
    }
}
//Turn State + speed = Right turn
//Turn state - speed = Left turn

