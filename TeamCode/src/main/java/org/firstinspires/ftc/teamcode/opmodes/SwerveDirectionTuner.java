package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Names;
import org.firstinspires.ftc.teamcode.controllers.IDrive;
import org.firstinspires.ftc.teamcode.controllers.ModernRoboticsSensorModule;
import org.firstinspires.ftc.teamcode.controllers.SensorModule;
import org.firstinspires.ftc.teamcode.controllers.SwerveController;
import org.firstinspires.ftc.teamcode.statemachines.DriveState;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;
import org.firstinspires.ftc.teamcode.statemachines.TeleOpState;
import org.firstinspires.ftc.teamcode.statemachines.Transition;
import org.firstinspires.ftc.teamcode.statemachines.WaitState;

/**
 * Created by Derek on 10/18/17.
 */
@TeleOp(name="Tune Swerve", group="3965")
public class SwerveDirectionTuner extends RobotBase {
    private ModernRoboticsSensorModule sensors; //= new ModernRoboticsSensorModule(this);

    private double kd = 0.0;
    private double ti = Double.POSITIVE_INFINITY;
    private double td = 0.0;
    private double maxi = 0.0;
    private double mini = 0.0;

    private SwerveController swerve;

    @Override
    public void init() {
        super.init();
        //sensors.init();
        addControllers(new SwerveController(hardwareMap.servo.get(Names.swerveServo),
                                            hardwareMap.analogInput.get(Names.hall)));
        swerve = (SwerveController)(findController(SwerveController.class));
    }

    @Override
    public void start() {
        super.start();
        stateMachine = new StateMachine(new WaitState("wait"));
    }

    @Override
    public void loop() {
        super.loop();
        //! fix increments
        //! create new pid instance if values change and set swerve's pid to it
        if(gamepad1.dpad_up) {
            if(gamepad1.a) kd++;
            if(gamepad1.b) ti++;
            if(gamepad1.x) td++;
        }
        else if(gamepad1.dpad_down) {
            if(gamepad1.a) kd--;
            if(gamepad1.b) ti--;
            if(gamepad1.x) td--;
        }
    }

    @Override
    public SensorModule Sensors() {
        return sensors;
    }
}
