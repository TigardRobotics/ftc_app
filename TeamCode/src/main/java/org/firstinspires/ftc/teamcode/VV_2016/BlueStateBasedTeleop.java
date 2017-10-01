package org.firstinspires.ftc.teamcode.VV_2016;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotBase;

/**
 * Created by Derek Williams of team 3965 on 2/1/2017.
 */

@TeleOp(name="Blue vvsb Teleop", group="3965")
@Disabled

public class BlueStateBasedTeleop extends StateBasedTeleop{
    @Override
    public void start() {
        color = RobotBase.BLUE;
        super.start();
    }
}
