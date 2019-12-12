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

import java.nio.file.Watchable;

@Autonomous(name = "SS_StoneGrabBlue", group = "3965")

public class SS_StoneGrabBlue extends TankBot{

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void start() {
        super.start();
        stateMachine = new StateMachine(
           new DriveState("forward",0.5, new ProgressTrans("Grab", 2500.0)),//Forward
           new DualGrabberState("Grab", true, new TimeTrans("reposition", 1.0)),     //Grab block
                new DriveState("reposition", -0.5, new ProgressTrans("Turn", 700)),
           new TurnState("Turn", -0.5, new ProgressTrans("deliver", 18.0)),     //Turn Left 90 degrees
           new DriveState("deliver",0.5, new ProgressTrans("release",3000.0)),    //Deliver Drive straight
                new DualGrabberState("release", false, new TimeTrans("turn", 1.0)), //Let go of block
                new TurnState("turn",0.5,new ProgressTrans("drive",18.0)), //Turn Right 90 degrees
                new DriveState("drive", 0.5, new ProgressTrans("grabfoundation",200)), //Drive forward to grab foundation
                new DualGrabberState("grabfoundation", true, new TimeTrans("reverse", 1.0)), //Grab foundation
                new DriveState("reverse",-0.5, new ProgressTrans("releasefoundation", 2600.0)), // Back up to wall
                new DualGrabberState("releasefoundation", false, new TimeTrans("bump", 1.0)),
                new DriveState("bump", 0.5, new ProgressTrans("pullback", 300)),
                new DriveState("pullback", -0.5, new ProgressTrans("park", 500)),
                new WaitState("park", new TimeTrans("wait",1.0))
        );
    }

}
//Turn State + speed = Right turn
//Turn state - speed = Left turn


