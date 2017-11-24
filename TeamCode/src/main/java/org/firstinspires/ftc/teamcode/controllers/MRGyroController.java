package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Modern Robotics Gyro Orientation Sensor
 */

public class MRGyroController extends HardwareController  implements IOrientation{
    protected ModernRoboticsI2cGyro gyro;
    protected double offset = 0;

    public MRGyroController(ModernRoboticsI2cGyro gyro) {
        this.gyro = gyro;
    }

    @Override
    public void setBearing( double bearing ){
        gyro.resetZAxisIntegrator();    //This will set sensor to zero
        offset = bearing;
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void start() {
        gyro.resetZAxisIntegrator();
        super.start();
    }

    @Override
    public double getBearing(){
        return ((gyro.getHeading()+offset)+360.0)%360.0 ;
    }
}
