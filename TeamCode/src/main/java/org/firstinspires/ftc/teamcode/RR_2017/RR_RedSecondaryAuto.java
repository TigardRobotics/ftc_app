package org.firstinspires.ftc.teamcode.RR_2017;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Color;
import org.firstinspires.ftc.teamcode.opmodes.SwerveBase;
import org.firstinspires.ftc.teamcode.statemachines.ColorTrans;
import org.firstinspires.ftc.teamcode.statemachines.DriveState;
import org.firstinspires.ftc.teamcode.statemachines.KnockState;
import org.firstinspires.ftc.teamcode.statemachines.ProgressTrans;
import org.firstinspires.ftc.teamcode.statemachines.SpinState;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;
import org.firstinspires.ftc.teamcode.statemachines.TimeTrans;
import org.firstinspires.ftc.teamcode.statemachines.WaitState;

/**
 * BLUE Autonomous for Stone furthest from Recovery Zone
 */

@Autonomous(name="Secondary Red", group="3965")
public class RR_RedSecondaryAuto extends SwerveBase {
    @Override
    public void start() {
        super.start();
        stateMachine = new StateMachine(
                new SpinState("to spin", 0.0, new TimeTrans("knock", 1.5)),
                new KnockState("knock",
                        new ColorTrans("spin right", Color.RED),
                        new ColorTrans("spin left", Color.BLUE),
                        new TimeTrans("unknock", 10.0)),

                //Blue on the right
                new SpinState("spin right", 0.2, new ProgressTrans("unknock from right", 45.0)),
                new KnockState("unknock from right", true, new TimeTrans("spin left back", 0.5)),
                new SpinState("spin left back", -0.3, new ProgressTrans("to crab", 45.0)),

                //Blue on the left
                new SpinState("spin left", -0.2, new ProgressTrans("unknock from left", 45.0)),
                new KnockState("unknock from left", true, new TimeTrans("spin right back", 0.5)),
                new SpinState("spin right back", 0.3, new ProgressTrans("to crab", 45.0)),

                new KnockState("unknock", true, new TimeTrans("to crab", 1.5)),

                //Drive to cryptobox
                new DriveState("to crab", 0.0, new TimeTrans("to box", 1.5)),
                new DriveState("to box", 0.4,
                        new ProgressTrans("to spin box", 120.0),
                        new TimeTrans("end", 5.0)), //in case stall
                new SpinState("to spin box", 0.0, new TimeTrans("spin box", 1.5)),
                new SpinState("spin box", 0.3,
                        new ProgressTrans("to reverse to box", 90.0),
                        new TimeTrans("end", 2.0)), //in case stall
                new DriveState("to reverse to box", 0.0, new TimeTrans("reverse to box", 1.0)),
                new DriveState("reverse to box", -0.3,
                        new ProgressTrans("end", 24*2.54),
                        new TimeTrans("end", 4.0)),
                new WaitState("end")

        );
    }
}