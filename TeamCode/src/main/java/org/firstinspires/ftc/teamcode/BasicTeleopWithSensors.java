package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Derek Williams of team 3965 on 10/23/2016.
 */

@TeleOp(name="Sensor Testing", group="3965")
public class BasicTeleopWithSensors extends RobotBase {
    private ModernRoboticsSensorModule sensorModule = new ModernRoboticsSensorModule(this);

    @Override
    public void init() {
        sensorModule.init();
    }

    @Override
    public void loop() {
        telemetry.addData("Distance Value", sensorModule.getRangeCm());
        telemetry.addData("Front RGB Color", sensorModule.getFrontRGB());
    }

    @Override
    public SensorModule getSensorModule() {
        return sensorModule;
    }
}
