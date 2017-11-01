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

    /* For the servo:
       set position 0.0 : full speed counter-clockwise
       set position 0.5 : stop
       set position 1.0 : full speed clockwise
    */

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
        /* Mr. Hancock's Error Formula:
            double error = (direction-getRotationPosition()+540)%360-180
         */

        // Derek's Error Formula:
        /*
        double positiveError = (360.0 - getRotationPosition() + direction) % 360.0;
        double negativeError = positiveError - 360.0;
        double error = Math.abs(negativeError) > positiveError ? positiveError : negativeError;
        */

        double error = (getRotationPosition()-direction+540.0)%360.0-180.0;

        double power = pid.update(error, stopwatch.seconds());
        stopwatch.reset();
        Robot.telemetry.addData("actual direction", getRotationPosition());
        Robot.telemetry.addData("hall volt", hall.getVoltage());
        Robot.telemetry.addData("cmd direction", direction);
        Robot.telemetry.addLine(String.format("error = %f, power = %f", error, power));
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
     * @param direction Direction Servo (handles -360 to + 360)
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
     * @return 0-359.99...
     */
    public double getRotationPosition() {
        //! TODO: account for min and max hall sensor valuse
        return ((360.0 * (hall.getVoltage()-2.5) / 5.0)+360.0)%360.0 ;
    }

    public void setCountsPerDegree(double cpd) {
        //!implement this
    }

    public double RotationToDegrees( double position ) {
        return 0.0; //! fix this
    }
}
