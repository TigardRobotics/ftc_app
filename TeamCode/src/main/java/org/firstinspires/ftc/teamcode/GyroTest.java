package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Derek Williams on 1/12/2017.
 * This OpMode uses the 'sensor test' configuration
 */

@TeleOp(name = "Gyro test", group = "3965")
public class GyroTest extends RobotBase {
    private ModernRoboticsSensorModule sensorModule = new ModernRoboticsSensorModule(this);
    protected double drivePower = 0.5;
    protected DcMotor motor;

    @Override
    public void init() {
        sensorModule.init();
        motor = hardwareMap.dcMotor.get("motor");
    }

    @Override
    public void loop() {
        if (sensorModule.getHeading() == 0) disableMotor();
        else enableMotor();
    }

    @Override
    public void stop() {
        disableMotor();
    }

    public void enableMotor() {
        motor.setPower(drivePower);
    }

    public void disableMotor() {
        motor.setPower(0.0);
    }
}
