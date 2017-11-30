package org.firstinspires.ftc.teamcode.RR_2017;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Color;
import org.firstinspires.ftc.teamcode.controllers.VuMarkController;
import org.firstinspires.ftc.teamcode.opmodes.SwerveBase;
import org.firstinspires.ftc.teamcode.statemachines.ColorTrans;
import org.firstinspires.ftc.teamcode.statemachines.CrabState;
import org.firstinspires.ftc.teamcode.statemachines.DriveState;
import org.firstinspires.ftc.teamcode.statemachines.KnockState;
import org.firstinspires.ftc.teamcode.statemachines.PickUpBlockState;
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
    public void init() {
        super.init();
        addControllers(new VuMarkController(hardwareMap.appContext));
    }

    @Override
    public void start() {
        super.start();
        stateMachine = new StateMachine(
                new PickUpBlockState("lift block", -1.0, new TimeTrans("to spin", 1.0)),
                new SpinState("to spin", 0.0, new TimeTrans("knock", 1.5)),
                new KnockState("knock",
                        new ColorTrans("spin right", Color.BLUE),
                        new ColorTrans("spin left", Color.RED),
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
                new DriveState("to crab", 0.0, new TimeTrans("forward", 1.5)),
                new DriveState("forward", 0.4,
                        new ProgressTrans("to left crab", 120.0),
                        new TimeTrans("end", 5.0)), //in case stall
                new CrabState("to left crab", 270.0, 0.0, new TimeTrans("left crab", 1.5)),
                new CrabState("left crab", 270.0, 0.3,
                        new ProgressTrans("to push", 90.0),
                        new TimeTrans("end", 2.0)), //in case stall
                new DriveState("to push", 0.0, new TimeTrans("drop block", 1.5)),
                new PickUpBlockState("drop block", 1.0, true, new TimeTrans("push", 1.0)),
                new DriveState("push", 0.4,
                        new ProgressTrans("end", 120.0),
                        new TimeTrans("end", 5.0)), //in case stall
                new WaitState("end")

        );
    }
}