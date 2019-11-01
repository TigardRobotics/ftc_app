package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;

import org.firstinspires.ftc.teamcode.controllers.HardwareController;
import org.firstinspires.ftc.teamcode.controllers.LedController;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;
import org.firstinspires.ftc.teamcode.statemachines.WaitState;

import java.util.HashMap;
import java.util.Map;

/**
 * Drive Teleop using iDrive interface
 */

@TeleOp(name="Tank Teleop", group="3965")
//@Disabled
public class BasicTeleop extends TankBot {

    @Override
    public void init() {
        super.init();

        telemetry.addLine("Basic Hardware Initialized");
    }

    @Override
    public void start() {
        super.start();
        stateMachine = new StateMachine(
                new WaitState("wait")
        );
    }

    @Override
    public void loop(){
        //Use Steer Control rather than Tank Control so that it works with any iDrive controller
        //Left JoyStick sets Drive Power and Steering Angle
        double steer_direction = gamepad1.left_stick_x; //steer direction is left joystick horizontal
        Drive.setDriveDirection(steer_direction);

        double drive_power = -gamepad1.left_stick_y * Math.abs(gamepad1.left_stick_y); //speed is Left joystick vertical (with square acceleration)
        //Right JoyStick sets Rotation Power
        double rotation_power = gamepad1.right_stick_x; //rotation power is right joystick horizontal

        if (Math.abs(drive_power)>Math.abs(rotation_power)) {
            Drive.setDrivePower(drive_power);
        }
        else {
            Drive.setRotationPower(rotation_power);
        }

        telemetry.addData("drive power", drive_power);
        telemetry.addData("steer direction", steer_direction);
        telemetry.addData("rotation power", rotation_power);

        super.loop();
    }

    @Override
    public void stop() {
        super.stop();
    }

}
