package org.firstinspires.ftc.teamcode.controllers;

/**
 * Swerve Drive (4 Swerve Assemblies)
 */

public class SwerveDrive extends HardwareController implements IDrive {

    SwerveController[] controllers;

    public SwerveDrive(SwerveController... controllers) {
        this.controllers = controllers;
    }

    @Override
    public void init() {
        super.init();
        for(SwerveController controller : controllers) controller.init();
    }

    @Override
    public void init_loop() {
        super.init_loop();
        for(HardwareController controller : controllers) controller.init_loop();
    }

    @Override
    public void start() {
        super.start();
        for(SwerveController controller : controllers) controller.init();
    }

    @Override
    public void loop() {
        super.loop();
        for(SwerveController controller : controllers) controller.loop();
    }

    @Override
    public void stop() {
        super.stop();
        for(SwerveController controller : controllers) controller.stop();
    }

    @Override
    public void setDrivePower(double power) {
        for(SwerveController controller : controllers) controller.setDrivePower(power);
    }

    @Override
    public void stopDriveMotors() {
        for(SwerveController controller : controllers) controller.stopDriveMotors();
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
        for(SwerveController controller : controllers) controller.stopDriveMotors();
    }

}
