package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Pid;


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

    private static final double HOME = 0.0; //! Figure out correct home
    private static final double HOMING_SPEED = 1.0; //! Figure out the correct speed

    private double direction = HOME;

    /**
     * Constructor
     */
    public SwerveController(Servo directionServo, AnalogInput hall) {
        this.directionServo = directionServo;
        this.hall = hall;
        pid = new Pid(0.0, Double.POSITIVE_INFINITY, 0.0, 0.0, 0.0); //!! Tune to actual values
    }

    @Override
    public void init() {

    }

    @Override
    public void init_loop() {
        // Slowly move to point towards home
        if(hall.getVoltage() > HOME) {
            directionServo.setPosition(HOMING_SPEED);
        }
        else if(hall.getVoltage() < HOME) {
            directionServo.setPosition(-HOMING_SPEED);
        }
        else {
            directionServo.setPosition(0.0);
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {
        double power = pid.update(direction, hall.getVoltage(), stopwatch.milliseconds());
        stopwatch.reset();
        directionServo.setPosition(power);
    }

    @Override
    public void stop() {

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

    public double getRotationPosition() {
        return 0.0; //! fix this
    }

    public void setCountsPerDegree(double cpd) {
        //!implement this
    }

    public double RotationToDegrees( double position ) {
        return 0.0; //! fix this
    }
}
