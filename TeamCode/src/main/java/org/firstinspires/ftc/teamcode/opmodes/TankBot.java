package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Names;
import org.firstinspires.ftc.teamcode.controllers.AccelDrive;
import org.firstinspires.ftc.teamcode.controllers.DualServoGrabber;
import org.firstinspires.ftc.teamcode.controllers.HardwareController;
import org.firstinspires.ftc.teamcode.controllers.TankDrive;

import java.util.List;

/**
 * Created by Mark on 10/26/17
 * This is a basic Tank Drive Robot
 */

public abstract class TankBot extends RobotBase {

    private DcMotor leftDriveMotor;
    private DcMotor rightDriveMotor;
    public TankDrive Drive;

    public void init() {
        super.init();
        Drive = (TankDrive)(findController(TankDrive.class));
    }

    @Override
    public List<HardwareController> getControllers() {
        List<HardwareController> controllers = super.getControllers();
        leftDriveMotor = hardwareMap.dcMotor.get("motor_l");
        rightDriveMotor = hardwareMap.dcMotor.get("motor_r");
        controllers.add(new AccelDrive(rightDriveMotor, leftDriveMotor, 3.0, true));
        //controllers.add(new TankDrive(rightDriveMotor, leftDriveMotor));
        Servo grabServoR = hardwareMap.servo.get(Names.grabServoR);
        Servo grabServoL = hardwareMap.servo.get(Names.grabServoL);
        controllers.add(new DualServoGrabber(grabServoR,grabServoL));
        return controllers;
    }

    @Override
    public void stop() {
        super.stop();
        Drive.stopDriveMotors();
    }
}
