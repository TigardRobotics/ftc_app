package org.firstinspires.ftc.teamcode.RR_2018;

import org.firstinspires.ftc.teamcode.Color;
import org.firstinspires.ftc.teamcode.controllers.HardwareController;
import org.firstinspires.ftc.teamcode.controllers.SwerveDrive;
import org.firstinspires.ftc.teamcode.controllers.VuMarkController;
import org.firstinspires.ftc.teamcode.opmodes.SwerveBase;
import org.firstinspires.ftc.teamcode.statemachines.ColorTrans;
import org.firstinspires.ftc.teamcode.statemachines.CrabState;
import org.firstinspires.ftc.teamcode.statemachines.DriveState;
import org.firstinspires.ftc.teamcode.statemachines.GlobalTimeTrans;
import org.firstinspires.ftc.teamcode.statemachines.HangState;
import org.firstinspires.ftc.teamcode.statemachines.KnockState;
import org.firstinspires.ftc.teamcode.statemachines.PickUpBlockState;
import org.firstinspires.ftc.teamcode.statemachines.ProgressTrans;
import org.firstinspires.ftc.teamcode.statemachines.RangeTrans;
import org.firstinspires.ftc.teamcode.statemachines.SpinState;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;
import org.firstinspires.ftc.teamcode.statemachines.TimeTrans;
import org.firstinspires.ftc.teamcode.statemachines.TurnState;
import org.firstinspires.ftc.teamcode.statemachines.VuMarkTrans;
import org.firstinspires.ftc.teamcode.statemachines.WaitState;

import java.util.List;

import static org.firstinspires.ftc.teamcode.opmodes.RobotBase.findController;

/**
 * Created by Derek on 10/26/18.
 */

@Autonomous(name="Primary RR", group="3965")
@Disabled
public class RR_Auto extends SwerveBase {
    @Override
    public void init() {
        super.init();
        //((SwerveDrive)findController(SwerveDrive.class)).spinMode();
    }

    @Override
    public List<HardwareController> getControllers() {
        List<HardwareController> controllers = super.getControllers();
        return controllers;
    }

    @Override
    public void start() {
        super.start();
        stateMachine = new StateMachine(
                new HangState("lower", HangState.LOWER_SPEED, new TimeTrans("pre unhook", 3.0)),
                new CrabState("pre unhook", 90.0, 0.0 ,new TimeTrans("unhook", 1.0)),
                new CrabState("unhook", 90.0, 0.5, new TimeTrans("pre align", 2.0)),
                new SpinState("pre align", 0.0, new TimeTrans("align to wall", 1.0)),
                new TurnState("align to wall", 0.5, new ProgressTrans("pre crab to wall", 0.5)), //Tune Progress here
                new CrabState("pre crab to wall", 0.9, 0.0, new TimeTrans("crab to wall", 0.1)), //Tune Direction here
                new CrabState("crab to wall", 0.9, 9.0, new RangeTrans("pre drive to park", 5.0)),
                new DriveState("pre drive to park", 0.0, new TimeTrans("drive to park", 0.0)),
                new DriveState("drive to park", 0.4, new TimeTrans("park", 8.0)),
                new WaitState("park", new TimeTrans("park", 1.0))
        );
    }
}
