package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.controllers.HardwareController;
import org.firstinspires.ftc.teamcode.controllers.SwerveUnit;
import org.firstinspires.ftc.teamcode.controllers.VuMarkController;

import java.util.List;

/**
 * Created by Derek on 11/21/2017.
 */

@Autonomous(name="VuMark Test", group ="test")
//@Disabled
public class VuMark_Test extends RobotBase {
    @Override
    public List<HardwareController> getControllers() {
        List<HardwareController> controllers = super.getControllers();
        controllers.add(new VuMarkController(hardwareMap.appContext));
        return controllers;
    }
}
