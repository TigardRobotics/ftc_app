package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

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

    private double kp = 0.0;
    private double ti = Double.POSITIVE_INFINITY;
    private double td = 0.0;

    private SwerveController swerve;

    private ElapsedTime stopwatch = new ElapsedTime();

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
        stopwatch.reset();
    }

    private static final double dbgPos1 = 90.0;
    private static final double dbgPos2 = 180.0;
    private static final double otime = 5.0;

    private double dbgPos = dbgPos1;

    @Override
    public void loop() {
        super.loop();
        //! fix increments
        //! create new pid instance if values change and set swerve's pid to it
        double incr = 0.0;

        // determine if incrementing or decrementing
        if(gamepad1.right_bumper) incr = 0.001;
        if(gamepad1.left_bumper) incr = -0.001;

        // change 10x speed if dpad pressed
        if(gamepad1.dpad_up) incr *= 10.0;
        if(gamepad1.dpad_down) incr /= 10.0;

        // change selected tuning values
        if(gamepad1.x) kp += incr;
        if(gamepad1.y) {
            if(ti == Double.POSITIVE_INFINITY) ti = 10.0/kp;
            ti += incr;
        }
        if(gamepad1.b) td += incr;

        telemetry.addLine(String.format("kp = %f, ti = %f, td = %f", kp, ti, td));
        swerve.getPid().tune(kp, ti, td);

        if(stopwatch.seconds() >= otime) {
            stopwatch.reset();
            if(dbgPos == dbgPos1) dbgPos = dbgPos2;
            else if(dbgPos == dbgPos2) dbgPos = dbgPos1;
            swerve.setDirection(dbgPos);
        }

    }

    @Override
    public SensorModule Sensors() {
        return sensors;
    }
}
