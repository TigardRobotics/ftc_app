package org.firstinspires.ftc.teamcode.SS_2019;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.controllers.HardwareController;
import org.firstinspires.ftc.teamcode.opmodes.TankBot;
import org.firstinspires.ftc.teamcode.statemachines.DriveWithHeadingState;
import org.firstinspires.ftc.teamcode.statemachines.ProgressTrans;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;
import org.firstinspires.ftc.teamcode.statemachines.TimeTrans;
import org.firstinspires.ftc.teamcode.statemachines.WaitState;

import java.util.List;

@Autonomous(name="SS_GyroDrive", group="3965")
//@Disabled
public class SS_GyroDrive extends TankBot {
    @Override
    public void init() {
        super.init();
        this.Accel = 100000.0;   //Set Accel really high to Disable Accel Drive (100% Accel/ 1 ms)
    }

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
                new DriveWithHeadingState("forward", 0.25, 0.0, new ProgressTrans("wait", 8000)),
                new WaitState("wait", new TimeTrans("wait", 1.0))
        );
    }

}
