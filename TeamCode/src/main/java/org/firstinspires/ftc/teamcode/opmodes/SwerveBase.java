package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Names;
import org.firstinspires.ftc.teamcode.controllers.BlockLift;
import org.firstinspires.ftc.teamcode.controllers.IBlockLift;
import org.firstinspires.ftc.teamcode.controllers.SwerveDrive;
import org.firstinspires.ftc.teamcode.controllers.SwerveUnit;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;
import org.firstinspires.ftc.teamcode.statemachines.WaitState;

/**
 * Created by Derek on 11/1/2017.
 */

public abstract class SwerveBase extends RobotBase {

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

    protected SwerveDrive drive;
    protected IBlockLift blockLift;

    @Override
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

        // Get hardware from hardware map
        DcMotor liftMotor = hardwareMap.dcMotor.get(Names.liftMotor);
        Servo leftClamp = hardwareMap.servo.get(Names.leftClamp);
        Servo rightClamp = hardwareMap.servo.get(Names.rightClamp);

        // Add blocklift controller to controller list
        addControllers(new BlockLift(liftMotor, rightClamp, leftClamp));

        // Get blocklift controller from controller list
        blockLift = (IBlockLift)findController(IBlockLift.class);
        drive = (SwerveDrive)(findController(SwerveDrive.class));
    }

    @Override
    public void stop() {
        super.stop();
        drive.stopDriveMotors();
    }
}