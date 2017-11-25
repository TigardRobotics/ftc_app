package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Pid;


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
    private static final double DIRECTION_SERVO_STOP = 0.5;
    private static final double MAX_DRIVE_SPEED = 0.5;

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
        setDirection(0.0);    //Home location (forward)
        //Turn motor closest direction toward home
        if( getDirectionError() > 0 ) {
            directionServo.setPosition(1.0);    //clockwise
        }
        else {
            directionServo.setPosition(0.0);    //counter-clockwise
        }
    }

    @Override
    public void init_loop() {
        double power = pid.update(getDirectionError(), stopwatch.seconds());
        /* For the servo:
           set position 0.0 : full speed counter-clockwise
           set position 0.5 : stop
           set position 1.0 : full speed clockwise
        */
        directionServo.setPosition(power+0.5);
        stopwatch.reset();
    }

    @Override
    public void start() {
        directionServo.setPosition(DIRECTION_SERVO_STOP);
    }

    @Override
    public void loop() {
        double power = pid.update(getDirectionError(), stopwatch.seconds());
        /* For the servo:
           set position 0.0 : full speed counter-clockwise
           set position 0.5 : stop
           set position 1.0 : full speed clockwise
        */
        directionServo.setPosition(power+0.5);
        stopwatch.reset();
    }

    @Override
    public void stop() { stopMotors(); }

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
        motor.setPower(power*MAX_DRIVE_SPEED);
    }

    /**
     * Stop the Drive Motor
     */
    public void stopMotors() {
        motor.setPower(0.0);
        directionServo.setPosition(DIRECTION_SERVO_STOP);
    }

    /**
     * Get the encoder position of the Drive Motor
     * @return encoder position of the drive motor
     */
    public double getDrivePosition() {
        return motor.getCurrentPosition();
    }

    /**
     *  Get the actual direction of the swerve unit based on the hall voltage
     * @return 0-359.99... Positive is clockwise
     */
    public double getActualDirection() {
        //! TODO: account for min and max hall sensor values
        //Calculate position assuming forward in center position on the hall (2.5V)
        return ((360.0 * (2.5-hall.getVoltage()) / 5.0)+360.0)%360.0 ;
    }

    /**
     * Calculate direction error (how far the servo needs to more to move it to the commanded position)
     * @return Positional error -180 to +180 Positive is clockwise
     */
    public double getDirectionError() {
        //
        //Negative indicates Commanded Direction is lower (counter-clockwise) of Actual Direction
        //Positive indicates Commanded Direction is higher (clockwise) of Actual Direction
        return (direction - getActualDirection() + 360.0 + 180.0) % 360.0 - 180.0;
    }

    public void setCountsPerDegree(double cpd) {
        //!implement this
    }

    public double RotationToDegrees( double position ) {
        return 0.0; //! fix this
    }
}
