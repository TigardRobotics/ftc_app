package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;

import org.firstinspires.ftc.teamcode.Names;
import org.firstinspires.ftc.teamcode.controllers.HardwareController;
import org.firstinspires.ftc.teamcode.controllers.LedController;
import org.firstinspires.ftc.teamcode.controllers.MRGyroController;
import org.firstinspires.ftc.teamcode.controllers.TflowController;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;
import org.firstinspires.ftc.teamcode.statemachines.WaitState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Drive Teleop using iDrive interface
 */

@TeleOp(name="TFlowOp", group="3965")
public class SmTflowObjDetection extends RobotBase {

    @Override
    public List<HardwareController> getControllers() {
        List<HardwareController> controllers = super.getControllers();
        DeviceInterfaceModule io = hardwareMap.deviceInterfaceModule.get("Device Interface Module 1");
        LedController leds = new LedController(io, LedController.DEFAULT_LED_IO_MAP);
        controllers.add(leds);
        controllers.add(new TflowController(leds, true));
        return controllers;
    }

    @Override
    public void start() {
        super.start();
        stateMachine = new StateMachine(
                new WaitState("wait")
        );
    }
}
