package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Names;
import org.firstinspires.ftc.teamcode.controllers.SwerveUnit;
import org.firstinspires.ftc.teamcode.controllers.SwerveDrive;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;
import org.firstinspires.ftc.teamcode.statemachines.WaitState;

/**
 * Created by Derek on 10/26/2017.
 */

@TeleOp(name="Swerve Teleop", group="3965")
public class SwerveBot extends RobotBase {

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

        // set swerve motor zero power behaviors
        frontRightDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        frontLeftDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backRightDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
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
        stateMachine = new StateMachine(new WaitState("wait"));
    }

    @Override
    public void loop() {
        super.loop();
        double crab_direction = getGamepad1RightJoystickAngle(); //crab direction is right joystick direction
        double steer_direction = gamepad1.left_stick_y; //steer direction is left joystick horizontal
        double drive_power = -gamepad1.left_stick_y*Math.abs(gamepad1.left_stick_y); //speed is Left joystick vertical (with square acceleration)
        drive.setDirection(crab_direction, steer_direction);
        drive.setDrivePower(drive_power);
        telemetry.addData("steer direction", steer_direction);
        telemetry.addData("crab direction", crab_direction);
        telemetry.addData("drive power", drive_power);
    }

    @Override
    public void stop() {
        super.stop();
        drive.stopDriveMotors();
    }
}
