package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.controllers.AccelDrive;
import org.firstinspires.ftc.teamcode.controllers.HardwareController;
import org.firstinspires.ftc.teamcode.controllers.IDrive;
import org.firstinspires.ftc.teamcode.controllers.TankDrive;

/**
 * This is a basic Tank Drive Robot
 */

public abstract class TankBot extends RobotBase {

    private DcMotor leftDriveMotor;
    private DcMotor rightDriveMotor;
    public IDrive Drive;

    public void init() {
        super.init();
        leftDriveMotor = hardwareMap.dcMotor.get("motor_l");
        rightDriveMotor = hardwareMap.dcMotor.get("motor_r");
        //addControllers(new AccelDrive(rightDriveMotor, leftDriveMotor, 3.0));
        addControllers(new TankDrive(rightDriveMotor, leftDriveMotor));

        Drive = (IDrive)(findController(IDrive.class));
    }

    @Override
    public void stop() {
        super.stop();
        Drive.stopDriveMotors();
    }
}
