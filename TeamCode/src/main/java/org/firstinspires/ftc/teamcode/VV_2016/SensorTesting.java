package org.firstinspires.ftc.teamcode.VV_2016;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.VV_2016.ModernRoboticsSensorModule;
import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Created by Derek Williams of team 3965 on 10/23/2016.
 */

@Autonomous(name="Sensor Testing", group="3965")
public class SensorTesting extends RobotBase {
    private ModernRoboticsSensorModule sensorModule = new ModernRoboticsSensorModule(this);

    @Override
    public void init() {
        sensorModule.init();
    }

    @Override
    public void loop() {
        telemetry.addData("Distance Value", sensorModule.getRangeCm());
        telemetry.addData("Front Red", sensorModule.getFrontRed());
        telemetry.addData("Front Green", sensorModule.getFrontGreen());
        telemetry.addData("Front Blue", sensorModule.getFrontBlue());
    }

}
