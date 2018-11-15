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
import org.firstinspires.ftc.teamcode.statemachines.DriveState;
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

@Autonomous(name="TrophyDropTest", group="3965")
//@Disabled
public class RR_TrophyDropTest extends SwerveBase {
    @Override
    public void init() {
        super.init();

    }

    @Override
    public List<HardwareController> getControllers() {
        List<HardwareController> controllers = super.getControllers();
        Servo dropServo = hardwareMap.servo.get(Names.trophyDrop);
        controllers.add(new TrophyDropper(dropServo));
        return controllers;
    }

    @Override
    public void start() {
        super.start();
        stateMachine = new StateMachine(
                new DriveState("forward", 0.5, new ProgressTrans("drop", 12*2.54)),  //Drive 12"
                new DropTrophyState("drop",new TimeTrans("back",3.0)),
                new DriveState("back", -0.5, new ProgressTrans("park", 12*2.54)),
                new WaitState("park", new TimeTrans("park", 1.0))
        );
    }
}
