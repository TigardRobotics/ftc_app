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

@Autonomous(name="A-DepotAutoSample", group="3965")
//@Disabled
public class RR_DepotAutoSample extends SwerveBase {

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
                new CrabState("unhook", 0.0, -0.2, new ProgressTrans("pre back away", 9*2.54)),

                // Back away from lander
                new CrabState("pre back away", 90.0, 0.0, new TimeTrans("back away", 0.5)),
                new CrabState("back away", 90.0, 0.4, new ProgressTrans("pre sample", 15*2.54)),
                new CrabState("pre sample", 0.0, 0.0, new TimeTrans("wait", 1.0)),
                //Sample
                new WaitState( "wait",
                        new MineralTrans("R drop", TflowController.GOLD_ON_RIGHT),
                        new MineralTrans("C drop", TflowController.GOLD_CENTER),
                        new MineralTrans("L crab left", TflowController.GOLD_ON_LEFT),
                        new GlobalTimeTrans("no sample", 10.0)),
                //Gold on right
                new MoveSamplingArmState("R drop", true, new TimeTrans("R knock", 0.2)),
                new CrabState("R knock", 0.0, -0.2, new ProgressTrans("R raise", 16*2.54)),
                new MoveSamplingArmState("R raise", false, new TimeTrans("R to wall", 0.2)),
                new CrabState("R to wall", 0.0, 0.5, new ProgressTrans("to spin", 72*2.54)),

                //Gold at center
                new MoveSamplingArmState("C drop", true, new TimeTrans("C knock", 0.2)),
                new CrabState("C knock", 0.0, 0.2, new ProgressTrans("C raise", 10*2.54)),
                new MoveSamplingArmState("C raise", false, new TimeTrans("C to wall", 0.2)),
                new CrabState("C to wall", 0.0, 0.4, new ProgressTrans("to spin", 49*2.54)),

                //Gold on left
                new CrabState("L crab left", 0.0, 0.2, new ProgressTrans("L drop", 14*2.54)),
                new MoveSamplingArmState("L drop", true, new TimeTrans("L knock", 0.2)),
                new CrabState("L knock", 0.0, 0.2, new ProgressTrans("L raise", 10*2.54)),
                new MoveSamplingArmState("L raise", false, new TimeTrans("L to wall", 0.2)),
                new CrabState("L to wall", 0.0, 0.5, new ProgressTrans("to spin", 29*2.54)),

                //Gold not detected
                new CrabState("no sample", 0.0, 0.4, new ProgressTrans("to spin", 62*2.54)),

                new SpinState("to spin", 0.0, new TimeTrans("spin", 1.0)),
                new SpinState("spin", -0.5, new ProgressTrans("to crab", 115.0)),
                new CrabState("to crab", 90.0, 0.0, new TimeTrans("to depot", 1.5)),
                new CrabState("to depot", 90.0, -0.6, new ProgressTrans("to spin2", 50*2.54)),
                new SpinState("to spin2", 0.0, new TimeTrans("spin2", 1.0)),
                new SpinState("spin2", -0.4, new ProgressTrans("drop", 21.0)),
                new DropTrophyState("drop",new TimeTrans("spin3",1.0)),
                new SpinState("spin3", 0.4, new ProgressTrans("to to park", 20.0)),
                new CrabState("to to park", 90.0, 0.0, new TimeTrans("to park", 1.0)),
                new CrabState("to park", 90.0, 0.8
                        , new ProgressTrans("park", 90*2.54)),
                new WaitState("park", new TimeTrans("park", 1.0))
        );
    }
}
