package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Robotics on 11/21/2015.
 */
public class BAutoWheelz extends RAutoWheelz {
    @Override
    public void init() {
        super.init();
        turn.Power=-0.5;
    }

}
