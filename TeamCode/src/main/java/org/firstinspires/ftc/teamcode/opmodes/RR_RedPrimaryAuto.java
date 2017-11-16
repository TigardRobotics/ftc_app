package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Color;
import org.firstinspires.ftc.teamcode.statemachines.ColorTrans;
import org.firstinspires.ftc.teamcode.statemachines.DriveState;
import org.firstinspires.ftc.teamcode.statemachines.KnockState;
import org.firstinspires.ftc.teamcode.statemachines.ProgressTrans;
import org.firstinspires.ftc.teamcode.statemachines.SpinState;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;
import org.firstinspires.ftc.teamcode.statemachines.TimeTrans;
import org.firstinspires.ftc.teamcode.statemachines.WaitState;

/**
 * Created by Derek on 11/7/2017.
 */

@Autonomous(name="Primary Red", group="3965")
public class RR_RedPrimaryAuto extends SwerveBase {
    @Override
    public void start() {
        super.start();
        stateMachine = new StateMachine(
                new SpinState("to spin", 0.0, new TimeTrans("knock", 5.0)),
                new KnockState("knock", new ColorTrans("spin right", Color.BLUE), new ColorTrans("spin left", Color.RED)),
                new SpinState("spin right", 0.2, new ProgressTrans("unknock from right", 45.0)),
                new SpinState("spin left", -0.2, new ProgressTrans("unknock from left", 45.0)),

                //hack to retract knocker
                new KnockState("unknock from right", true, new TimeTrans("spin left back", 3.0)),
                new KnockState("unknock from left", true, new TimeTrans("spin right back", 3.0)),

                //Spin back
                new SpinState("spin right back", 0.3, new ProgressTrans("to crab", 45.0)),
                new SpinState("spin left back", -0.3, new ProgressTrans("to crab", 45.0)),

                //Drive to cryptobox
                new DriveState("to crab", 0.0, new TimeTrans("to box", 5.0)),
                new DriveState("to box", 0.4, new ProgressTrans("to spin box", 100.0)),
                new SpinState("to spin box", 0.0, new TimeTrans("spin box", 5.0)),
                new SpinState("spin box", 0.2, new ProgressTrans("end", 90.0)),
                new WaitState("end")

        );
    }
}
