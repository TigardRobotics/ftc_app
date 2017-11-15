package org.firstinspires.ftc.teamcode.controllers;

import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Swerve Drive (4 Swerve Units)
 */

public class SwerveDrive extends HardwareController implements IDrive {

    //Order of the SwerveUnit controllers
    static final int FRONT_RIGHT=0, FRONT_LEFT=1, BACK_RIGHT=2, BACK_LEFT=3;

    SwerveUnit[] drives;

    private boolean spinMode = false;

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
        for(SwerveUnit drive : drives) drive.loop();
    }

    @Override
    public void stop() {
        super.stop();
        for(SwerveUnit drive : drives) drive.stop();
    }

    @Override
    public void setDrivePower(double power) {
        String msg = String.format("SwerveDrive drive_power = %1$.1f%%", power*100);
        RobotBase.log(msg);
        Robot.telemetry.addLine(msg);
        for(SwerveUnit drive : drives) drive.setDrivePower(power);
    }

    @Override
    public void stopDriveMotors() {
        for(SwerveUnit drive : drives) drive.setDrivePower(0);
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
        double position = 0;
        for(SwerveUnit drive : drives) position = Math.max(position, drive.getDrivePosition());
        return PositionToCentimeters(position);
    }

    @Override
    public double getRotationPosition() {
        //For swerve, consider this the spin; and, since spin is done with the drive motors, it is also the DrivePosition
        double position = 0;
        for(SwerveUnit drive : drives) position = Math.max(position, drive.getDrivePosition());
        return RotationToDegrees(position);
    }

    private double countsPerCentimeter = 5.85;    //estimate based on 3" wheels, 280 ppr AndyMark encoder and 1:2 bevel gear ratio ( (280 ppr / 2) / (3.0*Math.PI*2.54) )
    public double PositionToCentimeters(double counts) { return counts/countsPerCentimeter;};

    private double countsPerDegree = countsPerCentimeter * 123.7 / 360.0; //estimate based on 15.5" turn-base ( countsPerCentimeter * (15.5.0*Math.PI*2.54) / 360 )
    public double RotationToDegrees(double counts) { return counts/countsPerDegree;}

    /**
     * Set drive direction
     * @param crab_direction (0-360)
     * @param steer_direction (-1 to +1)  -1 = max left steer, 0 = no steer, +1 = max right steer
     */
    public void setDirection(double crab_direction, double steer_direction) {
        Robot.log(String.format("SwerveDrive crab_direction = %1$.1f, steer_direction = %2$.1f", crab_direction,  steer_direction));
        spinMode = false;

        if ( drives.length > BACK_LEFT && ((crab_direction >350) || (crab_direction <10)) ) {

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

    /**
     * Enter Spin Mode
     */
    public void spinMode() {
        drives[FRONT_RIGHT].setDirection(135.0);
        drives[FRONT_LEFT].setDirection(45.0);
        drives[BACK_RIGHT].setDirection(225.0);
        drives[BACK_LEFT].setDirection(315.0);

        spinMode = true;
    }

}
