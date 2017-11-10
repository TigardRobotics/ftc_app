package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Color;
import org.firstinspires.ftc.teamcode.statemachines.BelowRangeTrans;
import org.firstinspires.ftc.teamcode.statemachines.ColorTrans;
import org.firstinspires.ftc.teamcode.statemachines.DriveState;
import org.firstinspires.ftc.teamcode.statemachines.KnockState;
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
                new KnockState("knock", new ColorTrans("spin right", Color.BLUE), new ColorTrans("spin left", Color.RED)),
                new SpinState("spin right", 0.5, new TimeTrans("end", 2.0)),
                new SpinState("spin left", -0.5, new TimeTrans("end", 2.0)),
                new WaitState("end")

        );
    }
}
