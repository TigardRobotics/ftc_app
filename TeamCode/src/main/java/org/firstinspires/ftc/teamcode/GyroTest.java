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
    protected int threshold = 2;

    @Override
    public void init() {
        //super.init();
        sensorModule.init();
        motor = hardwareMap.dcMotor.get("motor_r");
    }

    @Override
    public void loop() {
        telemetry.addData("Heading", sensorModule.getHeading());

        if(Math.abs(sensorModule.getHeadingError(0)) < threshold) { // Exiting if heading within threshold
            telemetry.addLine("Heading reached");
            //stopDriveMotors();
            motor.setPower(0.0);
            return;
        }

        double power = drivePower * (sensorModule.getHeadingError(0) / 180.0);
        motor.setPower(power);
    }

    @Override
    public void stop() {
        disableMotor();
    }

    public void enableMotor() {
        motor.setPower(drivePower);
    }

    public void reverseEnableMotor() {
        motor.setPower(-drivePower);
    }

    public void disableMotor() {
        motor.setPower(0.0);
    }
}
