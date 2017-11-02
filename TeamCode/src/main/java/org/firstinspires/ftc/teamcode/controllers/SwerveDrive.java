package org.firstinspires.ftc.teamcode.controllers;

import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Swerve Drive (4 Swerve Units)
 */

public class SwerveDrive extends HardwareController implements IDrive {

    //Order of the SwerveUnit controllers
    static final int FRONT_RIGHT=0, FRONT_LEFT=1, BACK_RIGHT=2, BACK_LEFT=3;

    SwerveUnit[] drives;

    boolean spinMode = false;

    /**
     * Drive Controller for 4-wheel swerve drive chassis
     * @param drives (frontRight, frontLeft, backRight, backLeft)
     */
    public SwerveDrive(SwerveUnit... drives) {
        RobotBase.log("Swerve Drive constructed with "+drives.length+" swerve units");
        this.drives = drives;

    }

    @Override
    public void init() {
        super.init();
        for(SwerveUnit drive : drives) drive.init();
    }

    @Override
    public void init_loop() {
        super.init_loop();
        for(HardwareController drive : drives) drive.init_loop();
    }

    @Override
    public void start() {
        super.start();
        for(SwerveUnit drive : drives) drive.init();
    }

    @Override
    public void loop() {
        super.loop();

        if(spinMode) {
            drives[FRONT_RIGHT].setDirection(315.0);
            drives[FRONT_LEFT].setDirection(45.0);
            drives[BACK_RIGHT].setDirection(45.0);
            drives[BACK_LEFT].setDirection(315.0);
        }

        for(SwerveUnit drive : drives) drive.loop();
    }

    @Override
    public void stop() {
        super.stop();
        for(SwerveUnit drive : drives) drive.stop();
    }

    @Override
    public void setDrivePower(double power) {
        Robot.log(String.format("SwerveDrive drive_power = %1$.1f%%", power*100));
        if(spinMode) {
            drives[FRONT_RIGHT].setDrivePower(-power);
            drives[FRONT_LEFT].setDrivePower(power);
            drives[BACK_RIGHT].setDrivePower(-power);
            drives[BACK_LEFT].setDrivePower(power);
        }
        else for(SwerveUnit drive : drives) drive.setDrivePower(power);
    }

    @Override
    public void stopDriveMotors() {
        for(SwerveUnit drive : drives) drive.stopDriveMotors();
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
        Robot.log(String.format("SwerveDrive crab_direction = %1$.1f, steer_direction = %2$.1f", crab_direction,  steer_direction));
        if (drives.length > BACK_LEFT) {

            double MaxSteer = 45;   //Max steer is 45 degrees
            double right_steer=0, left_steer=0;

            if (steer_direction > 0) {
                right_steer = Math.atan(steer_direction)*MaxSteer;
                left_steer = Math.atan(steer_direction/(1+ 2*steer_direction))*MaxSteer;
            }
            else {
                right_steer = -Math.atan(-steer_direction/(1- 2*steer_direction))*MaxSteer;
                left_steer = -Math.atan(-steer_direction)*MaxSteer;
            }
            drives[FRONT_RIGHT].setDirection(crab_direction+right_steer);
            drives[FRONT_LEFT].setDirection(crab_direction+left_steer);
            drives[BACK_RIGHT].setDirection(crab_direction-right_steer);
            drives[BACK_LEFT].setDirection(crab_direction-left_steer);
        }
        else {
            for (SwerveUnit drive : drives) drive.setDirection(crab_direction);
        }
    }

    public void spinMode() {
        spinMode = true;
    }

    public void crabMode() {
        spinMode = false;
    }

}
