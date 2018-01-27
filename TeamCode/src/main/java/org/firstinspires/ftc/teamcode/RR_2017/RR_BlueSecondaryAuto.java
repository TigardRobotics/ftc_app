package org.firstinspires.ftc.teamcode.RR_2017;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.Color;
import org.firstinspires.ftc.teamcode.controllers.HardwareController;
import org.firstinspires.ftc.teamcode.controllers.SwerveDrive;
import org.firstinspires.ftc.teamcode.controllers.VuMarkController;
import org.firstinspires.ftc.teamcode.statemachines.ColorTrans;
import org.firstinspires.ftc.teamcode.statemachines.CrabState;
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

import java.util.List;

/**
 * BLUE Autonomous for Stone furthest from Recovery Zone
 */

@Autonomous(name="Secondary Blue", group="3965")
public class RR_BlueSecondaryAuto extends RR_AutoBase {

    @Override
    public void init() {
        super.init();
        ((SwerveDrive)findController(SwerveDrive.class)).spinMode();
    }

    @Override
    public List<HardwareController> getControllers() {
        List<HardwareController> controllers = super.getControllers();
        controllers.add(new VuMarkController(hardwareMap.appContext));
        return controllers;
    }

    @Override
    public void start() {
        super.start();
        stateMachine = new StateMachine(
                new SpinState("to spin", 0.0, new TimeTrans("lift block", 1.5)),
                new PickUpBlockState("lift block", 1.0, new TimeTrans("knock", 1.0)),
                new KnockState("knock",
                        new ColorTrans("spin right", Color.RED),
                        new ColorTrans("spin left", Color.BLUE),
                        new TimeTrans("search", 1.0)),

                new SpinState("search", 0.1,
                        new ColorTrans("spin right", Color.RED),
                        new ColorTrans("spin left", Color.BLUE),
                        new ProgressTrans("unknock", 5.0)),

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
                new DriveState("to crab", 0.0, new TimeTrans("reverse", 1.5)),
                new DriveState("reverse", -0.6,
                        new ProgressTrans("to spin", 77.0),
                        new TimeTrans("end", 5.0)), //in case stall

                new SpinState("to spin", 0.0, new TimeTrans("spin around", 2.0)),
                new SpinState("spin around", -0.5, new ProgressTrans("to crab left", 230.0)),

                new CrabState("to crab left", 270.0, 0.0, new TimeTrans("crab to wall", 1.5)),
                new CrabState("crab to wall", 270.0, 0.2, new TimeTrans("to key column", 5.0)),
                new CrabState("to key column", 270, 0.0, new TimeTrans("key column", 2.0)),
                new WaitState("key column",
                        new VuMarkTrans("to right column", RelicRecoveryVuMark.RIGHT),
                        new VuMarkTrans("to left column", RelicRecoveryVuMark.LEFT),
                        new VuMarkTrans("to center column", RelicRecoveryVuMark.CENTER),
                        new GlobalTimeTrans("to center column", 10.0) // Default to center column
                ),

                new CrabState("to right column", 270.0, -0.4,
                        new ProgressTrans("to forward", 105.0),
                        new TimeTrans("end", 5.0) //in case stall
                ),
                new CrabState("to center column", 270.0, -0.4,
                        new ProgressTrans("to forward", 77.5),
                        new TimeTrans("end", 5.0) //in case stall
                ),
                new CrabState("to left column", 270.0, -0.4,
                        new ProgressTrans("to forward", 50.0),
                        new TimeTrans("end", 5.0) //in case stall
                ),

                new CrabState("to forward", 0.0, 0.0, new TimeTrans("drop block", 0.5)),
                new PickUpBlockState("drop block", -1.0, true, new TimeTrans("spit", 1.0)),
                new PickUpBlockState("spit", 0.0, true, new TimeTrans("ram block", 0.1)),
                new DriveState("ram block", 0.5, new TimeTrans("backup", 2.0)),
                new DriveState("backup", -0.5, new TimeTrans("end", 0.7)),
                new WaitState("end")
        );
    }
}