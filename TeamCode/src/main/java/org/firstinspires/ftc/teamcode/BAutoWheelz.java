package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Robotics on 11/21/2015.
 */
@Autonomous(name="BAutoWheelz", group="3058")
public class BAutoWheelz extends RAutoWheelz {
    @Override
    public void init() {
        super.init();
        TurnL = false;
    }

}
