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
        if(spinMode) {
            drives[FRONT_RIGHT].setDrivePower(-power);
            drives[FRONT_LEFT].setDrivePower(power);
            drives[BACK_RIGHT].setDrivePower(-power);
            drives[BACK_LEFT].setDrivePower(power);
        }
        else {
            RobotBase.log("Set drive power (fr, fl, br, bl)");
            for (SwerveUnit drive : drives) drive.setDrivePower(power);
        }
    }

    @Override
    public void stopDriveMotors() {
        for(SwerveUnit drive : drives) drive.setDrivePower(0);
    }

    @Override
    public void setDriveDirection(double direction) {
        setDirection(0, direction);
        RobotBase.log("Drive direction set to "+direction);
    }

    @Override
    public void setRotationPower(double power) {
        //!TODO Support this
        throw new UnsupportedOperationException("setRotationPower not supported in Swerve");
    }

    @Override
    public double getDrivePosition() {
        double enc_fl = drives[FRONT_LEFT].getDrivePosition();
        double enc_fr = drives[FRONT_RIGHT].getDrivePosition();
        double enc_bl = drives[BACK_LEFT].getDrivePosition();
        double enc_br = drives[BACK_RIGHT].getDrivePosition();
        //average the position of the left motors - they always rotate forward
        double position = PositionToCentimeters(enc_fl);// +enc_bl)/2.0;
        RobotBase.log(String.format("DPos=%f (fl=%f,fr=%f,bl=%f,br=%f)", position, enc_fl, enc_fr, enc_bl, enc_br));
        double angle_fl = drives[FRONT_LEFT].getActualDirection();
        double angle_fr = drives[FRONT_RIGHT].getActualDirection();
        double angle_bl = drives[BACK_LEFT].getActualDirection();
        double angle_br = drives[BACK_RIGHT].getActualDirection();
        RobotBase.log(String.format("DAngle (fl=%f,fr=%f,bl=%f,br=%f)", angle_fl, angle_fr, angle_bl, angle_br));

        double hallv_fl = drives[FRONT_LEFT].getHallVoltage();
        double hallv_fr = drives[FRONT_RIGHT].getHallVoltage();
        double hallv_bl = drives[BACK_LEFT].getHallVoltage();
        double hallv_br = drives[BACK_RIGHT].getHallVoltage();
        RobotBase.log(String.format("Hall Volts (fl=%f,fr=%f,bl=%f,br=%f)", hallv_fl, hallv_fr, hallv_bl, hallv_br));

        return position;
    }

    @Override
    public double getRotationPosition() {
        //For swerve, consider this the spin; and, since spin is done with the drive motors, it is also the DrivePosition
        double enc_fl = drives[FRONT_LEFT].getDrivePosition();
        double enc_fr = drives[FRONT_RIGHT].getDrivePosition();
        double enc_bl = drives[BACK_LEFT].getDrivePosition();
        double enc_br = drives[BACK_RIGHT].getDrivePosition();
        //average the position of the left motors - they always rotate forward
        double position = RotationToDegrees(enc_fl);// +enc_bl)/2.0;
        RobotBase.log(String.format("RPos=%f (fl=%f,fr=%f,bl=%f,br=%f)", position,enc_fl,enc_fr,enc_bl,enc_br));
        return position;
    }

    private double countsPerCentimeter = 16.25;    // 5.84=(280/2)/(3.0*Math.PI*2.54 (based on 3" wheels, 280 ppr AndyMark encoder and 1:2 bevel gear ratio)
    public double PositionToCentimeters(double counts) { return counts/countsPerCentimeter;};

    private double countsPerDegree = 2625.0 / 360.0; // 2.0=5.85*(15.5*Math.PI*2.54)/360 (based on 5.85 cts/cm and 15.5" turn-base)
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

            double MaxSteer = 45.0;   //Max steer is 45 degrees
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
            RobotBase.log("Front right drive unit direction set to "+(crab_direction+right_steer));

            drives[FRONT_LEFT].setDirection(crab_direction+left_steer);
            RobotBase.log("Front left drive unit direction set to "+(crab_direction+left_steer));

            drives[BACK_RIGHT].setDirection(crab_direction-right_steer);
            RobotBase.log("Back right drive unit direction set to "+(crab_direction-right_steer));

            drives[BACK_LEFT].setDirection(crab_direction-left_steer);
            RobotBase.log("Back left drive unit direction set to "+(crab_direction-left_steer));
        }
        else {
            for (SwerveUnit drive : drives) drive.setDirection(crab_direction);
        }
    }

    /**
     * Enter Spin Mode
     */
    public void spinMode() {
        drives[FRONT_RIGHT].setDirection(315.0);
        drives[FRONT_LEFT].setDirection(45.0);
        drives[BACK_RIGHT].setDirection(45.0);
        drives[BACK_LEFT].setDirection(315.0);

        spinMode = true;
    }

}
