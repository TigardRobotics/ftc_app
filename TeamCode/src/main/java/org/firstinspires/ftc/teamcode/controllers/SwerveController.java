package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Pid;
import org.firstinspires.ftc.teamcode.opmodes.RobotBase;


/**
 * Created by Derek on 10/17/17.
 */
//! Add IHolonomic and implement instead of IDrive
//! Split into SwerveUnits
public class SwerveController extends HardwareController implements IDrive {
    private Servo directionServo;
    private AnalogInput hall;

    private Pid pid;
    private ElapsedTime stopwatch = new ElapsedTime();

    /* For the servo:
       set position 0.0 : full speed counter-clockwise
       set position 0.5 : stop
       set position 1.0 : full speed clockwise
    */

    private static final double HOME = 50.0; 
    private static final double HOMING_SPEED = 130.0; //! Figure out the correct speed
    private static final double DIRECTION_SERVO_STOP = 0.5;

    private double direction = HOME;

    /**
     * Constructor
     */
    public SwerveController(Servo directionServo, AnalogInput hall) {
        this.directionServo = directionServo;
        this.hall = hall;
        pid = new Pid(0.0, Double.POSITIVE_INFINITY, 0.0, 0.0, 0.0); //! Tune to actual values
    }

    @Override
    public void init() {
        //! TODO: Scan for min and max hall values
        directionServo.setPosition(HOMING_SPEED);
    }

    @Override
    public void init_loop() {
        // Slowly move to point towards home
        double rp = getRotationPosition();
        Robot.telemetry.addData("servo pos", rp);
        Robot.telemetry.addData("hall volt", hall.getVoltage());
        if(rp < HOME) {
            directionServo.setPosition(DIRECTION_SERVO_STOP);
        }
    }

    @Override
    public void start() {
        directionServo.setPosition(DIRECTION_SERVO_STOP);
    }

    @Override
    public void loop() {
        double power = pid.update(direction, hall.getVoltage(), stopwatch.seconds());
        stopwatch.reset();
        directionServo.setPosition(power);
    }

    @Override
    public void stop() {
        directionServo.setPosition(DIRECTION_SERVO_STOP);
    }

    /**
     * Change the swerve pid for tuning purposes
     * @param pid
     */
    public void setPid(Pid pid) {
        this.pid = pid;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public void setDrivePower(double power) {
        //! implement this
    }

    public void stopDriveMotors() {
        //! implement this
    }

    public void setRotationPower(double power) {
        //! implement this
    }

    public void setLeftDrivePower(double power) {
        //! implement this
    }

    public void setRightDrivePower(double power) {
        //! implement this
    }

    public double getDrivePosition() {
        //! implement this
        return 0.0; //! fix this
    }

    public void setCountsPerCentimeter(double cpc) {
        //!implement this
    }

    public double PositionToCentimeters(double position) {
        return 0.0; //! fix this
    }

    /**
     *
     * @return 0-359.99...
     */
    public double getRotationPosition() {
        return (360.0 * hall.getVoltage() / 5.0) ;
    }

    public void setCountsPerDegree(double cpd) {
        //!implement this
    }

    public double RotationToDegrees( double position ) {
        return 0.0; //! fix this
    }
}
