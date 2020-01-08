package org.firstinspires.ftc.teamcode.SS_2019;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.controllers.HardwareController;
import org.firstinspires.ftc.teamcode.controllers.RevImuController;
import org.firstinspires.ftc.teamcode.opmodes.TankBot;
import org.firstinspires.ftc.teamcode.statemachines.DriveState;
import org.firstinspires.ftc.teamcode.statemachines.DriveWithHeadingState;
import org.firstinspires.ftc.teamcode.statemachines.ProgressTrans;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;
import org.firstinspires.ftc.teamcode.statemachines.TimeTrans;
import org.firstinspires.ftc.teamcode.statemachines.WaitState;

import java.util.List;

@Autonomous(name="SS_GyroDriveTiny", group="3965")
//@Disabled
public class SS_GyroDriveTiny extends TankBot {
    private BNO055IMU imu;
    @Override
    public void init() {
        super.init();
    }

    @Override
    public List<HardwareController> getControllers() {
        List<HardwareController> controllers = super.getControllers();
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        controllers.add(new RevImuController(imu));
        // controller.add any additional controllers
        return controllers;
    }

    @Override
    public void start() {
        super.start();
        stateMachine = new StateMachine(
                new DriveWithHeadingState("forward", 0.5, 0.0, new ProgressTrans("wait", 400)),
                new WaitState("wait", new TimeTrans("wait", 1.0))
        );
    }

}
