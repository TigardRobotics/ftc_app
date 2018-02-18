package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Names;
import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Derek Robert Williams on 2/18/2018.
 */

@Autonomous(name="RangeController_Test", group="test")
//@Disabled
public class RangeController_Test extends RobotBase {
    private RangeController rc;

    @Override
    public void init() {
        super.init();
        rc = (RangeController) findController(RangeController.class);
    }

    @Override
    protected List<HardwareController> getControllers() {
        List<HardwareController> controllers = super.getControllers();
        ModernRoboticsI2cRangeSensor sensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, Names.range);
        controllers.add(new RangeController(sensor, true));
        return controllers;
    }

    @Override
    public void loop() {
        super.loop();
        rc.getRangeCm();
    }
}
