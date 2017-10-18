package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.controllers.ModernRoboticsSensorModule;
import org.firstinspires.ftc.teamcode.controllers.SensorModule;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;
import org.firstinspires.ftc.teamcode.statemachines.TeleOpState;
import org.firstinspires.ftc.teamcode.statemachines.Transition;

/**
 * Created by Derek on 10/18/17.
 */
@TeleOp(name="Tune Swerve", group="3965")
public class SwerveDirectionTuner extends RobotBase {
    private ModernRoboticsSensorModule sensors = new ModernRoboticsSensorModule(this);

    private double kd = 0.0;
    private double ti = Double.POSITIVE_INFINITY;
    private double td = 0.0;
    private double maxi = 0.0;
    private double mini = 0.0;

    @Override
    public void init() {
        super.init();
        sensors.init();
        //! add swerve controller
    }

    @Override
    public void start() {
        super.start();
        stateMachine = new StateMachine(); //! add states to turn back and forth
    }

    @Override
    public void loop() {
        super.loop();
        //! use gamepad to change pid constants
    }

    @Override
    public SensorModule Sensors() {
        return sensors;
    }
}
