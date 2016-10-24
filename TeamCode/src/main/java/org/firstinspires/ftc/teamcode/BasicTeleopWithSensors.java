package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Derek Williams of team 3965 on 10/23/2016.
 */

@TeleOp(name="Sensor Testing", group="3965")
public class BasicTeleopWithSensors extends BasicTeleop {
    private ModernRoboticsSensorModule sensorModule = new ModernRoboticsSensorModule(this);

    @Override
    public void loop() {
        super.loop();
        telemetry.addData("Ultra Sonic Value:", sensorModule.getUsRange());
        telemetry.addData("Optical Distance Sensor Value", sensorModule.getOdsRange());
    }

    @Override
    public SensorModule getSensorModule() {
        return sensorModule;
    }
}
