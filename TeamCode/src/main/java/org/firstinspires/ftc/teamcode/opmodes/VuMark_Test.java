package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.controllers.VuMarkController;
import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Created by Derek on 11/21/2017.
 */

@Autonomous(name="VuMark Test", group ="test")
//@Disabled
public class VuMark_Test extends RobotBase {
    @Override
    public void init() {
        super.init();
        addControllers(new VuMarkController(hardwareMap.appContext));
    }
}
