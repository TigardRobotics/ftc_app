package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

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

    @Override
    public SensorModule getSensorModule() {
        return sensorModule;
    }
}
