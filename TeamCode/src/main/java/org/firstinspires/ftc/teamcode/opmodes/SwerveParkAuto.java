package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Names;
import org.firstinspires.ftc.teamcode.controllers.SwerveDrive;
import org.firstinspires.ftc.teamcode.controllers.SwerveUnit;
import org.firstinspires.ftc.teamcode.statemachines.DriveState;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;
import org.firstinspires.ftc.teamcode.statemachines.TimeTrans;
import org.firstinspires.ftc.teamcode.statemachines.WaitState;

/**
 * Created by Katrina and Derek on 11/1/2017.
 */

@Autonomous(name="Simple Swerve Auto", group="test")
public class SwerveParkAuto extends SwerveTeleop {
    private double driveTime = 30.0;

    // Drive Motors
    private DcMotor frontRightDriveMotor;
    private DcMotor frontLeftDriveMotor;
    private DcMotor backRightDriveMotor;
    private DcMotor backLeftDriveMotor;

    // Swerve Servos
    private Servo frontRightServo;
    private Servo frontLeftServo;
    private Servo backRightServo;
    private Servo backLeftServo;

    // Swerve Hall sensors
    private AnalogInput frontRightHall;
    private AnalogInput frontLeftHall;
    private AnalogInput backRightHall;
    private AnalogInput backLeftHall;

    public SwerveDrive drive;

    public void init() {
        super.init();

        // Get swerve motors from hardwaremap
        frontRightDriveMotor = hardwareMap.dcMotor.get(Names.frm);
        frontLeftDriveMotor = hardwareMap.dcMotor.get(Names.flm);
        backRightDriveMotor = hardwareMap.dcMotor.get(Names.brm);
        backLeftDriveMotor = hardwareMap.dcMotor.get(Names.blm);

        // set swerve motor zero power behaviors and reversals
        frontRightDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        frontRightDriveMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeftDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backRightDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backRightDriveMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        //Get swerve servos from hardwaremap
        frontRightServo = hardwareMap.servo.get(Names.frs);
        frontLeftServo = hardwareMap.servo.get(Names.fls);
        backRightServo = hardwareMap.servo.get(Names.brs);
        backLeftServo = hardwareMap.servo.get(Names.bls);

        //Get swerve hall sensors from hardwaremap
        frontRightHall = hardwareMap.analogInput.get(Names.frh);
        frontLeftHall = hardwareMap.analogInput.get(Names.flh);
        backRightHall = hardwareMap.analogInput.get(Names.brh);
        backLeftHall = hardwareMap.analogInput.get(Names.blh);

        // create SwerveDrive object
        addControllers(new SwerveDrive(
                new SwerveUnit(frontRightDriveMotor, frontRightServo, frontRightHall), //front right
                new SwerveUnit(frontLeftDriveMotor, frontLeftServo, frontLeftHall),    //front left //not currently on robot
                new SwerveUnit(backRightDriveMotor, backRightServo, backRightHall),    //back right
                new SwerveUnit(backLeftDriveMotor, backLeftServo, backLeftHall)        //back left
        ));

        drive = (SwerveDrive)(findController(SwerveDrive.class));
    }

    @Override
    public void start() {
        super.start();
        stateMachine = new StateMachine(
                new DriveState("drive", 0.5),
                new WaitState("stop")
        );
    }

    @Override
    public void stop() {
        super.stop();
        drive.stopDriveMotors();
    }
}
