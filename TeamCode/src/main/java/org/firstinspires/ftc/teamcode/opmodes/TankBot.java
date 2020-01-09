package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Names;
import org.firstinspires.ftc.teamcode.controllers.AccelDrive;
import org.firstinspires.ftc.teamcode.controllers.DualServoGrabber;
import org.firstinspires.ftc.teamcode.controllers.HardwareController;
import org.firstinspires.ftc.teamcode.controllers.RevImuController;
import org.firstinspires.ftc.teamcode.controllers.SamplingArm;
import org.firstinspires.ftc.teamcode.controllers.TankDrive;

import java.util.List;

/**
 * Created by Mark on 10/26/17
 * This is a basic Tank Drive Robot
 */

public abstract class TankBot extends RobotBase {

    private DcMotor leftDriveMotor;
    private DcMotor rightDriveMotor;
    private BNO055IMU imu;
    public TankDrive Drive;
    public double Accel = 100000.0;  // %/sec

    public void init() {
        super.init();
        Drive = (TankDrive)(findController(TankDrive.class));
    }

    @Override
    public List<HardwareController> getControllers() {
        List<HardwareController> controllers = super.getControllers();
        leftDriveMotor = hardwareMap.dcMotor.get("motor_l");
        rightDriveMotor = hardwareMap.dcMotor.get("motor_r");
        controllers.add(new AccelDrive(rightDriveMotor, leftDriveMotor, Accel, true));
        //controllers.add(new TankDrive(rightDriveMotor, leftDriveMotor));
        Servo grabServoR = hardwareMap.servo.get(Names.grabServoR);
        Servo grabServoL = hardwareMap.servo.get(Names.grabServoL);
        controllers.add(new DualServoGrabber(grabServoR,grabServoL));
        Servo armServo = hardwareMap.servo.get(Names.sampler);
        controllers.add(new SamplingArm(armServo));
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        controllers.add(new RevImuController(imu));
        return controllers;
    }

    @Override
    public void stop() {
        super.stop();
        Drive.stopDriveMotors();
    }
}
