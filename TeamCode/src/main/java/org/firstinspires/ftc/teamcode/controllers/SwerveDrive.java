package org.firstinspires.ftc.teamcode.controllers;

import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Swerve Drive (4 Swerve Assemblies)
 */

public class SwerveDrive extends HardwareController implements IDrive {

    SwerveUnit[] controllers;

    public SwerveDrive(SwerveUnit... controllers) {
        Robot.telemetry.addLine("Swerve Drive consturcted with "+controllers.length+" swerve units");
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
    }

    @Override
    public void setLeftDrivePower(double power) {

    }

    @Override
    public void setRightDrivePower(double power) {

    }

    @Override
    public double getDrivePosition() {
        return 0;
    }

    @Override
    public void setCountsPerCentimeter(double cpc) {

    }

    @Override
    public double PositionToCentimeters(double position) {
        return 0;
    }

    @Override
    public double getRotationPosition() {
        return 0;
    }

    @Override
    public void setCountsPerDegree(double cpd) {

    }

    @Override
    public double RotationToDegrees(double position) {
        return 0;
    }

    public void setDirection(double direction) {
        for(SwerveUnit controller : controllers) controller.setDirection(direction);
    }

}
