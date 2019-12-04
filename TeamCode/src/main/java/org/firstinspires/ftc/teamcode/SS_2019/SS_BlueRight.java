package org.firstinspires.ftc.teamcode.SS_2019;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.controllers.HardwareController;
import org.firstinspires.ftc.teamcode.opmodes.TankBot;
import org.firstinspires.ftc.teamcode.statemachines.DriveState;
import org.firstinspires.ftc.teamcode.statemachines.ProgressTrans;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;
import org.firstinspires.ftc.teamcode.statemachines.TimeTrans;
import org.firstinspires.ftc.teamcode.statemachines.TurnState;
import org.firstinspires.ftc.teamcode.statemachines.WaitState;

import java.util.List;

@Autonomous(name="SS_BlueRight", group="3965")
//@Disabled

public class SS_BlueRight extends TankBot {
    @Override
    public void init() {
        super.init(); }

    @Override
    public List<HardwareController> getControllers() {
        List<HardwareController> controllers = super.getControllers();
        // controller.add any additional controllers
        return controllers;
    }

    @Override
    public void start() {
        super.start();
        stateMachine = new StateMachine(
                new DriveState("forward", 0.5, new ProgressTrans("turn", 1900)),
                new TurnState("turn", -0.5, new ProgressTrans("line", 17.0)),
                new DriveState("line", 0.5, new TimeTrans("wait", 0.5)),
                new WaitState("wait", new TimeTrans("wait", 1.0))
        );
    }
    }