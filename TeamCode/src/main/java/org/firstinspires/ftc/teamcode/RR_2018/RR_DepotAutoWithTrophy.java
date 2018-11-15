package org.firstinspires.ftc.teamcode.RR_2018;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Names;
import org.firstinspires.ftc.teamcode.controllers.HardwareController;
import org.firstinspires.ftc.teamcode.controllers.RobotHanger;
import org.firstinspires.ftc.teamcode.controllers.TrophyDropper;
import org.firstinspires.ftc.teamcode.opmodes.SwerveBase;
import org.firstinspires.ftc.teamcode.statemachines.CrabState;
import org.firstinspires.ftc.teamcode.statemachines.DropTrophyState;
import org.firstinspires.ftc.teamcode.statemachines.HangState;
import org.firstinspires.ftc.teamcode.statemachines.ProgressTrans;
import org.firstinspires.ftc.teamcode.statemachines.SpinState;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;
import org.firstinspires.ftc.teamcode.statemachines.TimeTrans;
import org.firstinspires.ftc.teamcode.statemachines.WaitState;

import java.util.List;

/**
 * Created by Brandon on 11/13/18.
 */

@Autonomous(name="DepotAuto w/ Trophy", group="3965")
//@Disabled
public class RR_DepotAutoWithTrophy extends SwerveBase {
    @Override
    public void init() {
        super.init();

    }

    @Override
    public List<HardwareController> getControllers() {
        List<HardwareController> controllers = super.getControllers();
        DcMotor hangmotor = hardwareMap.dcMotor.get(Names.hanger);
        Servo dropServo = hardwareMap.servo.get(Names.trophyDrop);
        controllers.add(new RobotHanger(hangmotor));
        controllers.add(new TrophyDropper(dropServo));
        return controllers;
    }

    @Override
    public void start() {
        super.start();
        stateMachine = new StateMachine(
                new HangState("lower", HangState.LOWER_SPEED, new TimeTrans("pre ground", 1.5)),

                // Back up to ground all 4 wheels
                new CrabState("pre ground", 90.0, 0.0, new TimeTrans("ground", 1.0)),
                new CrabState("ground", 90.0, 0.2, new TimeTrans("pre unhook", 0.25)),

                // Crab to side to unhook from lander
                new CrabState("pre unhook", 0.0, 0.0 ,new TimeTrans("unhook", 1.0)),
                new CrabState("unhook", 0.0, -0.2, new TimeTrans("pre back away", 1.0)),

                // Back away from lander
                new CrabState("pre back away", 90.0, 0.0, new TimeTrans("back away", 1.0)),
                new CrabState("back away", 90.0, 0.3, new TimeTrans("sense", 1.5)),
                new WaitState("sense", new TimeTrans("continue",2.0)),
                new CrabState("continue", 90.0, 0.0, new TimeTrans("drop", 1.0)),
                //Add DropState here to drop trophy
                new DropTrophyState("drop",new TimeTrans("to spin",1.0)),
                new SpinState("to spin", 0.0, new TimeTrans("spin", 0.1)),
                new SpinState("spin", 0.2, new ProgressTrans("pre crater", 45.0)),
                new CrabState("pre crater", 90.0, 0.0, new TimeTrans("crater", 0.5)),
                new CrabState("crater",90.0,-0.4, new TimeTrans("park", 9.0)),
                new WaitState("park", new TimeTrans("park", 1.0))
        );
    }
}
