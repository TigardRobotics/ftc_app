package org.firstinspires.ftc.teamcode.RR_2017;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.Color;
import org.firstinspires.ftc.teamcode.controllers.SwerveDrive;
import org.firstinspires.ftc.teamcode.controllers.VuMarkController;
import org.firstinspires.ftc.teamcode.opmodes.SwerveBase;
import org.firstinspires.ftc.teamcode.statemachines.ColorTrans;
import org.firstinspires.ftc.teamcode.statemachines.DriveState;
import org.firstinspires.ftc.teamcode.statemachines.GlobalTimeTrans;
import org.firstinspires.ftc.teamcode.statemachines.KnockState;
import org.firstinspires.ftc.teamcode.statemachines.PickUpBlockState;
import org.firstinspires.ftc.teamcode.statemachines.ProgressTrans;
import org.firstinspires.ftc.teamcode.statemachines.SpinState;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;
import org.firstinspires.ftc.teamcode.statemachines.TimeTrans;
import org.firstinspires.ftc.teamcode.statemachines.VuMarkTrans;
import org.firstinspires.ftc.teamcode.statemachines.WaitState;

/**
 * BLUE Autonomous for Stone closest to Recovery Zone
 */

@Autonomous(name="Primary Blue", group="3965")
public class RR_BluePrimaryAuto extends RR_AutoBase {

    @Override
    public void init() {
        super.init();
        addControllers(new VuMarkController(hardwareMap.appContext));
        ((SwerveDrive)findController(SwerveDrive.class)).spinMode();
    }

    @Override
    public void start() {
        super.start();
        stateMachine = new StateMachine(
                new SpinState("to spin", 0.0, new TimeTrans("lift block", 1.5)),
                new PickUpBlockState("lift block", -1.0, new TimeTrans("knock", 1.0)),
                new KnockState("knock",
                        new ColorTrans("spin right", Color.RED),
                        new ColorTrans("spin left", Color.BLUE),
                        new TimeTrans("search", 1.0)),

                new SpinState("search", 0.1,
                        new ColorTrans("spin right", Color.RED),
                        new ColorTrans("spin left", Color.BLUE),
                        new ProgressTrans("unknock", 8.0)),

                //Blue on the right
                new SpinState("spin right", 0.2, new ProgressTrans("unknock from right", 20.0)),
                new KnockState("unknock from right", true, new TimeTrans("spin left back", 0.5)),
                new SpinState("spin left back", -0.2, new ProgressTrans("to crab", 20.0)),

                //Blue on the left
                new SpinState("spin left", -0.2, new ProgressTrans("unknock from left", 20.0)),
                new KnockState("unknock from left", true, new TimeTrans("spin right back", 0.5)),
                new SpinState("spin right back", 0.2, new ProgressTrans("to crab", 20.0)),

                new KnockState("unknock", true, new TimeTrans("to crab", 1.5)),

                //Drive to cryptobox
                new DriveState("to crab", 0.0, new TimeTrans("key column", 1.5)),
                new WaitState("key column",
                        new VuMarkTrans("to right column", RelicRecoveryVuMark.RIGHT),
                        new VuMarkTrans("to left column", RelicRecoveryVuMark.LEFT),
                        new VuMarkTrans("to center column", RelicRecoveryVuMark.CENTER),
                        new GlobalTimeTrans("to center column", 10.0) // Default to center column
                ),
                new DriveState("to right column", -0.4,
                        new ProgressTrans("to spin box", 143.0),
                        new TimeTrans("end", 5.0) //in case stall
                ),
                new DriveState("to center column", -0.4,
                        new ProgressTrans("to spin box", 117.0),
                        new TimeTrans("end", 5.0) //in case stall
                ),
                new DriveState("to left column", -0.4,
                        new ProgressTrans("to spin box", 93.0),
                        new TimeTrans("end", 5.0) //in case stall
                ),
                new SpinState("to spin box", 0.0, new TimeTrans("spin box", 1.5)),
                new SpinState("spin box", 0.3, new ProgressTrans("to final crab", 132.0)),
                new DriveState("to final crab", 0.0, new TimeTrans("drop block", 1.5)),
                new PickUpBlockState("drop block", 1.0, true, new TimeTrans("ram block", 1.0)),
                new DriveState("ram block", 0.5, new TimeTrans("backup", 2.0)),
                new DriveState("backup", -0.5, new TimeTrans("end", 0.3)),
                new WaitState("end")
        );
    }
}
