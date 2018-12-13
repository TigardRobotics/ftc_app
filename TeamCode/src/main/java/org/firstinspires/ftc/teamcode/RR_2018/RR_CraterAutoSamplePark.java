package org.firstinspires.ftc.teamcode.RR_2018;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Names;
import org.firstinspires.ftc.teamcode.controllers.HardwareController;
import org.firstinspires.ftc.teamcode.controllers.LedController;
import org.firstinspires.ftc.teamcode.controllers.RobotHanger;
import org.firstinspires.ftc.teamcode.controllers.SamplingArm;
import org.firstinspires.ftc.teamcode.controllers.TflowController;
import org.firstinspires.ftc.teamcode.controllers.TrophyDropper;
import org.firstinspires.ftc.teamcode.opmodes.SwerveBase;
import org.firstinspires.ftc.teamcode.statemachines.CrabState;
import org.firstinspires.ftc.teamcode.statemachines.DropTrophyState;
import org.firstinspires.ftc.teamcode.statemachines.GlobalTimeTrans;
import org.firstinspires.ftc.teamcode.statemachines.HangState;
import org.firstinspires.ftc.teamcode.statemachines.MineralTrans;
import org.firstinspires.ftc.teamcode.statemachines.MoveSamplingArmState;
import org.firstinspires.ftc.teamcode.statemachines.ProgressTrans;
import org.firstinspires.ftc.teamcode.statemachines.SpinState;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;
import org.firstinspires.ftc.teamcode.statemachines.TimeTrans;
import org.firstinspires.ftc.teamcode.statemachines.WaitState;

import java.util.List;

/**
 * Created by Derek on 10/26/18.
 */

@Autonomous(name="B-CraterAutoSamplePark", group="3965")
//@Disabled
public class RR_CraterAutoSamplePark extends SwerveBase {

    private DeviceInterfaceModule io;

    @Override
    public void init() {
        super.init();
    }

    @Override
    public List<HardwareController> getControllers() {
        List<HardwareController> controllers = super.getControllers();
        DcMotor hangmotor = hardwareMap.dcMotor.get(Names.hanger);
        controllers.add(new RobotHanger(hangmotor));
        Servo dropServo = hardwareMap.servo.get(Names.trophyDrop);
        controllers.add(new TrophyDropper(dropServo));
        Servo sampleServo = hardwareMap.servo.get(Names.sampler);
        DeviceInterfaceModule io = hardwareMap.deviceInterfaceModule.get("Device Interface Module 1");
        LedController leds = new LedController(io, LedController.DEFAULT_LED_IO_MAP);
        controllers.add(new SamplingArm(sampleServo));
        controllers.add(new TflowController(leds, true));
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
                new CrabState("unhook", 0.0, -0.2, new ProgressTrans("pre back away", 9.5*2.54)),

                // Back away from lander
                new CrabState("pre back away", 90.0, 0.0, new TimeTrans("back away", 1.0)),
                new CrabState("back away", 90.0, 0.4, new ProgressTrans("pre sample", 13*2.54)),
                new CrabState("pre sample", 0.0, 0.0, new TimeTrans("wait", 1.0)),
                //Sample
                new WaitState( "wait",
                        new MineralTrans("R drop", TflowController.GOLD_ON_RIGHT),
                        new MineralTrans("C drop", TflowController.GOLD_CENTER),
                        new MineralTrans("L crab left", TflowController.GOLD_ON_LEFT),
                        new GlobalTimeTrans("to crater", 10.0)),
                //Gold on right
                new MoveSamplingArmState("R drop", true, new TimeTrans("R knock", 0.2)),
                new CrabState("R knock", 0.0, -0.2, new ProgressTrans("R raise", 12*2.54)),
                new MoveSamplingArmState("R raise", false, new TimeTrans("to crater", 1.0)),

                //Gold at center
                new MoveSamplingArmState("C drop", true, new TimeTrans("C knock", 0.2)),
                new CrabState("C knock", 0.0, 0.2, new ProgressTrans("C raise", 10*2.54)),
                new MoveSamplingArmState("C raise", false, new TimeTrans("C bacl", 1.0)),
                new CrabState("C back", 0.0, -0.2, new ProgressTrans("to crater", 10*2.54)),

                //Gold on left
                new CrabState("L crab left", 0.0, 0.2, new ProgressTrans("L drop", 14*2.54)),
                new MoveSamplingArmState("L drop", true, new TimeTrans("L knock", 0.2)),
                new CrabState("L knock", 0.0, 0.2, new ProgressTrans("L raise", 12*2.54)),
                new MoveSamplingArmState("L raise", false, new TimeTrans("to crater", 1.0)),

                new CrabState("to crater", 90.0, 0.0, new TimeTrans("drive", 1.0)),
                new CrabState("drive", 90.0, 0.6, new ProgressTrans("park", 10*2.54)),
                new WaitState("park", new TimeTrans("park", 1.0))
        );
    }
}
