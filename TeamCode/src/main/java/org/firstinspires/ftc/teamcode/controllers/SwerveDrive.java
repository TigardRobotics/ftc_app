package org.firstinspires.ftc.teamcode.controllers;

import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Swerve Drive (4 Swerve Units)
 */

public class SwerveDrive extends HardwareController implements IDrive {

    //Order of the SwerveUnit controllers
    static final int FrontRight=0, FrontLeft=1, BackRight=2, BackLeft=3;

    SwerveUnit[] controllers;

    /**
     * Drive Controller for 4-wheel swerve drive chassis
     * @param controllers (frontRight, frontLeft, backRight, backLeft)
     */
    public SwerveDrive(SwerveUnit... controllers) {
        RobotBase.log("Swerve Drive constructed with "+controllers.length+" swerve units");
        this.controllers = controllers;
    }

    @Override
    public void init() {
        super.init();
        for(SwerveUnit controller : controllers) controller.init();
    }

    @Override
    public void init_loop() {
        super.init_loop();
        for(HardwareController controller : controllers) controller.init_loop();
    }

    @Override
    public void start() {
        super.start();
        for(SwerveUnit controller : controllers) controller.init();
    }

    @Override
    public void loop() {
        super.loop();
        for(SwerveUnit controller : controllers) controller.loop();
    }

    @Override
    public void stop() {
        super.stop();
        for(SwerveUnit controller : controllers) controller.stop();
    }

    @Override
    public void setDrivePower(double power) {
        for(SwerveUnit controller : controllers) controller.setDrivePower(power);
    }

    @Override
    public void stopDriveMotors() {
        for(SwerveUnit controller : controllers) controller.stopDriveMotors();
    }

    @Override
    public void setRotationPower(double power) {
        //!TODO Support this
        throw new UnsupportedOperationException("setRotationPower not supported in Swerve");
    }

    @Override
    public void setLeftDrivePower(double power) {
        //!TODO Support this
        throw new UnsupportedOperationException("setLeftDrivePower not supported in Swerve");
    }

    @Override
    public void setRightDrivePower(double power) {
        //!TODO Support this
        throw new UnsupportedOperationException("setRightDrivePower not supported in Swerve");
    }

    @Override
    public double getDrivePosition() {
        return 0;
    }

    @Override
    public void setCountsPerCentimeter(double cpc) {}

    @Override
    public double PositionToCentimeters(double position) {
        return 0;
    }

    @Override
    public double getRotationPosition() {
        return 0;
    }

    @Override
    public void setCountsPerDegree(double cpd) {}

    @Override
    public double RotationToDegrees(double position) {
        return 0;
    }

    /**
     * Set drive direction
     * @param crab_direction (0-360)
     * @param steer_direction (-1 to +1)  -1 = max left steer, 0 = no steer, +1 = max right steer
     */
    public void setDirection(double crab_direction, double steer_direction) {
        if (controllers.length > BackLeft)
        {
            double MaxSteer = 45;   //Max steer is 45 degrees
            double right_steer, left_steer;
            if (steer_direction > 0)
            {
                right_steer = Math.atan(steer_direction)*MaxSteer;
                left_steer = Math.atan(steer_direction/(1+ 2*steer_direction))*MaxSteer;
            }
            else
            {
                right_steer = Math.atan(-steer_direction/(1- 2*steer_direction))*MaxSteer;
                left_steer = Math.atan(-steer_direction)*MaxSteer;
            }
            controllers[FrontRight].setDirection(crab_direction+right_steer);
            controllers[FrontLeft].setDirection(crab_direction+left_steer);
            controllers[BackRight].setDirection(crab_direction-right_steer);
            controllers[BackLeft].setDirection(crab_direction-left_steer);
        }
        else {
            for (SwerveUnit controller : controllers) controller.setDirection(crab_direction);
        }
    }

}
