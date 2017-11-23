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
        //Use Steer Control rather than Tank Control so that it works with any iDrive controller
        //Left JoyStick sets Drive Power and Steering Angle
        double steer_direction = Robot.gamepad1.left_stick_x; //steer direction is left joystick horizontal
        drive.setDriveDirection(steer_direction);

        double drive_power = -Robot.gamepad1.left_stick_y * Math.abs(Robot.gamepad1.left_stick_y); //speed is Left joystick vertical (with square acceleration)
        drive.setDrivePower(drive_power);

        //Right JoyStick sets Rotation Power
        double rotation_power = Robot.gamepad1.right_stick_x; //rotation power is right joystick horizontal
        drive.setRotationPower(rotation_power);

    }

    public void onExit() {
        drive.stopDriveMotors();
    }
}
