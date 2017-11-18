package org.firstinspires.ftc.teamcode.RR_2017;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.controllers.VuforiaController;

/**
 * Enable Camera
 */

@Autonomous(name="Video Drive", group="3965")
@Disabled
public class VideoDrive extends SwerveTeleop {
    @Override
    public void init() {
        addControllers(new VuforiaController(hardwareMap.appContext));
        super.init();
    }
}
