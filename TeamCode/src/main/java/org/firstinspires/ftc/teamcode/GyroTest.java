package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;

/**
 * Created by Derek Williams on 1/12/2017.
 * This OpMode uses the 'sensor test' configuration
 */

@TeleOp(name = "Gyro test", group = "3965")
public class GyroTest extends RobotBase {
    private ModernRoboticsSensorModule sensorModule = new ModernRoboticsSensorModule(this);
    protected double maxPower = 0.6;
    protected double minPower = 0.01;
    protected double maxPowerThreshold = 0.5;
    //protected DcMotor r_motor;
    //protected DcMotor l_motor;
    protected int threshold = 1;
    protected int targetHeading = 0;

    protected int iterationsWithinThresholdCount;
    protected int requiredIterationsWithinThresholdForHeadingReached = 2;

    @Override
    public void init() {
        super.init();
        sensorModule.init();
        //r_motor = hardwareMap.dcMotor.get("motor_r");
        //l_motor = hardwareMap.dcMotor.get("motor_l");
    }

    @Override
    public void start() {
        iterationsWithinThresholdCount = 0;
    }

    @Override
    public void loop() {
        telemetry.addData("Heading", sensorModule.getHeading());

        if(Math.abs(sensorModule.getHeadingError(targetHeading)) < threshold) { // Exiting if heading within threshold
            iterationsWithinThresholdCount++;
            telemetry.addLine("Within Threshold");
            if(iterationsWithinThresholdCount >= requiredIterationsWithinThresholdForHeadingReached) {
                telemetry.addLine("Heading Reached");
                stopDriveMotors();
                return;
            }
        }
        else {
            iterationsWithinThresholdCount = 0;
        }

        double error = (sensorModule.getHeadingError(targetHeading) / 180.0);
        double power;
        if (error > 0)
        {
            power = minPower + (maxPower-minPower)*(Math.min(error,maxPowerThreshold)/maxPowerThreshold);
        }
        else
        {
            power = - (minPower + (maxPower-minPower)*(Math.min(-error,maxPowerThreshold)/maxPowerThreshold));
        }

        telemetry.addData("Error", error);
        telemetry.addData("Power", power);

        setRightDrivePower(-power);
        setLeftDrivePower(power);
    }
}
