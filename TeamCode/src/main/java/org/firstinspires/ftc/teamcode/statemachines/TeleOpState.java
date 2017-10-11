package org.firstinspires.ftc.teamcode.statemachines;


import org.firstinspires.ftc.teamcode.Tools;
import org.firstinspires.ftc.teamcode.controllers.HardwareController;
import org.firstinspires.ftc.teamcode.controllers.IDrive;
import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Created by Derek on 10/3/17.
 */

public class TeleOpState extends State {
    protected double stickPressedPowerFactor = 0.3;
    protected double triggerActuationThreshold = 0.5;
    protected IDrive drive;


    public TeleOpState(String name, Transition... transitions) {
        super(name, transitions);
        drive = (IDrive)(RobotBase.findController(IDrive.class));
    }

    public void onEntry() {
        drive.stopDriveMotors();
    }

    public void doState() {
        drive.setLeftDrivePower(Tools.timesabs(Robot.gamepad1.left_stick_y));
        drive.setRightDrivePower(Tools.timesabs(Robot.gamepad1.right_stick_y));
    }

    public void onExit() {
        drive.stopDriveMotors();
    }
}
