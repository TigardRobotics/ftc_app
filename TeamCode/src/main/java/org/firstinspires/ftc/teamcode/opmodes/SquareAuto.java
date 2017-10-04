package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.controllers.ModernRoboticsSensorModule;
import org.firstinspires.ftc.teamcode.controllers.SensorModule;
import org.firstinspires.ftc.teamcode.statemachines.BelowRangeTrans;
import org.firstinspires.ftc.teamcode.statemachines.DriveState;
import org.firstinspires.ftc.teamcode.statemachines.ProgressTrans;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;
import org.firstinspires.ftc.teamcode.statemachines.TurnState;

/**
 * Created by Derek on 10/3/17.
 * Test driving in a square.
 */

@Autonomous(name="Square", group="test")
public class SquareAuto extends TankBot {
    private ModernRoboticsSensorModule sensors = new ModernRoboticsSensorModule(this);

    private double sqrSideLen = 1000; //!! These values are guesses
    private double turnAmount = 3830; //!! These values are guesses

    @Override
    public void init() {
        super.init();
        sensors.init();
    }

    @Override
    public void start(){
        super.start();
        stateMachine = new StateMachine(
                new DriveState("drive", -100.0, sensors, new ProgressTrans("turn", sqrSideLen)),
                new TurnState("turn", 100, sensors, new ProgressTrans("drive", turnAmount))
        );
    }

    @Override
    public SensorModule Sensors() {
        return sensors;
    }
}
