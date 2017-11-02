package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Pid;
import org.firstinspires.ftc.teamcode.opmodes.RobotBase;


/**
 * Created by Derek on 10/17/17.
 */
//! Add IHolonomic and implement instead of IDrive
//! Split into SwerveUnits
public class SwerveUnit extends HardwareController {
    private DcMotor motor;
    private Servo directionServo;
    private AnalogInput hall;

    private Pid pid;
    private ElapsedTime stopwatch = new ElapsedTime();

    private static final double HOME_RANGE = 5.0;
    private static final double HOMING_SPEED = 130.0; //! Figure out the correct speed
    private static final double DIRECTION_SERVO_STOP = 0.5;

    // the commanded direction
    private double direction = 0;

    /**
     * Constructor
     */
    public SwerveUnit(DcMotor motor, Servo directionServo, AnalogInput hall) {
        this.motor = motor;
        this.directionServo = directionServo;
        this.hall = hall;
        pid = new Pid(0.025, Double.POSITIVE_INFINITY, 0.0, 0.0, 0.0); //! Tune to actual values
    }

    @Override
    public void init() {
        directionServo.setPosition(HOMING_SPEED);
    }

    @Override
    public void init_loop() {
        //! TODO: Scan for min and max hall values
        // Slowly move to point towards home
        double rp = getRotationPosition();
        Robot.telemetry.addData("actual pos", rp);
        Robot.telemetry.addData("cmd pos", direction);
        if(rp < HOME_RANGE || rp > 360.0-HOME_RANGE) {
            direction = getRotationPosition();
            directionServo.setPosition(DIRECTION_SERVO_STOP);
        }
    }

    @Override
    public void start() {
        directionServo.setPosition(DIRECTION_SERVO_STOP);
    }

    @Override
    public void loop() {
        //Calculate an error that is -180 to +180
        //Negative indicates Commanded Direction is lower (counter-clockwise) of Actual Direction
        //Positive indicates Commanded Direction is higher (clockwise) of Actual Direction
        double error = (direction-getRotationPosition()+360.0+180.0)%360.0-180.0;

        double power = pid.update(error, stopwatch.seconds());
        stopwatch.reset();
        //Enable logging if needed, but probably would flood the log if we did it all the time
        //Robot.log(String.format("SwerveUnit cmd = %1$.1f, actual = %2$.1f , power = %2$.4f", direction,  getRotationPosition(), power));
        Robot.telemetry.addLine(String.format("cmd = %1$.1f, actual = %2$.1f (%3$.2fV)", direction,  getRotationPosition(), hall.getVoltage()));
        Robot.telemetry.addLine(String.format("error = %1$.2f, power = %2$.4f", error, power));

        //negative power needs to try to fix negative error (turn clockwise)
        //positive power needs to try to fix negative error (turn counter-clockwise)
        /* For the servo:
           set position 0.0 : full speed counter-clockwise
           set position 0.5 : stop
           set position 1.0 : full speed clockwise
        */
        directionServo.setPosition(power+0.5);
    }

    @Override
    public void stop() {
        stopDriveMotors();
    }

    /**
     * Get the swerve pid for tuning purposes
     */
    public Pid getPid() {
        return pid;
    }

    /**
     * Set Commanded Direction
     * @param direction Direction Servo (handles -360 to + 360) Positive is clockwise
     */
    public void setDirection(double direction) {
        this.direction = (direction+360.0)%360.0;
    }

    /**
     * Set power to Drive Motor
     * @param power Drive Motor power
     */
    public void setDrivePower(double power) {
        motor.setPower(power);
    }

    public void stopDriveMotors() {
        motor.setPower(0.0);
        directionServo.setPosition(DIRECTION_SERVO_STOP);
    }

    public double getDrivePosition() {
        //! implement this
        return 0.0; //! fix this
    }

    /**
     *
     * @return 0-359.99... Positive is clockwise
     */
    public double getRotationPosition() {
        //! TODO: account for min and max hall sensor values
        //Calculate position assuming forward in center position on the hall (2.5V)
        return ((360.0 * (2.5-hall.getVoltage()) / 5.0)+360.0)%360.0 ;
    }

    public void setCountsPerDegree(double cpd) {
        //!implement this
    }

    public double RotationToDegrees( double position ) {
        return 0.0; //! fix this
    }
}
