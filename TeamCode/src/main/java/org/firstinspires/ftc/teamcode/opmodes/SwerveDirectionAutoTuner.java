package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Names;
import org.firstinspires.ftc.teamcode.controllers.HardwareController;
import org.firstinspires.ftc.teamcode.controllers.MRGyroController;
import org.firstinspires.ftc.teamcode.controllers.SwerveUnit;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;
import org.firstinspires.ftc.teamcode.statemachines.WaitState;

import java.util.List;

/**
 * Created by Derek on 1/10/18.
 */
@TeleOp(name="Auto Tune Swerve", group="3965")
@Disabled
public class SwerveDirectionAutoTuner extends RobotBase {

    private double kp = 0.0;
    private double ki = 0.001;
    private double kd = 0.0;
    private double maxI = 100.0;

    private SwerveUnit swerve;

    private ElapsedTime stopwatch = new ElapsedTime();

    @Override
    public void init() {
        super.init();
        //sensors.init();
    }

    @Override
    public List<HardwareController> getControllers() {
        List<HardwareController> controllers = super.getControllers();
        controllers.add(new SwerveUnit(hardwareMap.dcMotor.get("motor"),
                hardwareMap.servo.get("servo"),
                hardwareMap.analogInput.get("hall"), false));
        return controllers;
    }

    @Override
    public void start() {
        super.start();
        stateMachine = new StateMachine(new WaitState("wait"));
        stopwatch.reset();
    }

    private static final double dbgPos1 = 0.0;
    private static final double dbgPos2 = 90.0;
    private static final double otime = 5.0;

    private double dbgPos = dbgPos1;

    private double runningIntegralError = 0;

    @Override
    public void loop() {
        super.loop();
        //! fix increments
        //! create new pid instance if values change and set swerve's pid to it
        double incr = 0.0;

        // determine if incrementing or decrementing
        if(gamepad1.right_bumper) incr = 0.00001;
        if(gamepad1.left_bumper) incr = -0.00001;

        // change 10x speed if dpad pressed
        if(gamepad1.dpad_up) incr *= 10.0;
        if(gamepad1.dpad_down) incr /= 10.0;

        // change selected tuning values
        if(gamepad1.x) kp += incr;
        if(gamepad1.y) ki += incr;
        if(gamepad1.b) kd += incr;
        if(gamepad1.a) maxI += incr;

        // zero everything (but maxI)
        if(gamepad1.right_stick_button) {
            kp = 0.0;
            ki = 0.0;
            kd = 0.0;
        }

        runningIntegralError += stopwatch.milliseconds()*swerve.getDirectionError();

        telemetry.addLine(String.format("kp = %f, ki = %f, kd = %f, MaxI = %f", kp, ki, kd, maxI));
        swerve.getPid().tune(kp, ki, kd, maxI);

        if(stopwatch.seconds() >= otime) {
            stopwatch.reset();
            if(dbgPos == dbgPos1) {
                dbgPos = dbgPos2;
            }
            else if(dbgPos == dbgPos2) {
                dbgPos = dbgPos1;
            }
            swerve.setDirection(dbgPos);
        }

    }

}