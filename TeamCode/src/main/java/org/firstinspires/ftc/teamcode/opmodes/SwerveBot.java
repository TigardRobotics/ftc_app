package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.controllers.AccelDrive;
import org.firstinspires.ftc.teamcode.controllers.IDrive;
import org.firstinspires.ftc.teamcode.controllers.SwerveDrive;

/**
 * Created by Derek on 10/26/2017.
 */

public class SwerveBot extends RobotBase {

    // Drive Motors
    private DcMotor frontRightDriveMotor;
    private DcMotor frontLeftDriveMotor;
    //private DcMotor backRightDriveMotor; //already defined, SNAFU
    //private DcMotor backLeftDriveMotor;

    // Swerve Servos
    private Servo frontRightServo;
    private Servo frontLeftServo;
    private Servo backRightDriveMotor;
    private Servo backLeftDriveMotor;


    public SwerveDrive drive;

    public void init() {
        super.init();
        //addControllers(new TankDrive(rightDriveMotor, leftDriveMotor));

        drive = (SwerveDrive)(findController(SwerveDrive.class));
    }

    @Override
    public void stop() {
        super.stop();
        drive.stopDriveMotors();
    }
}
