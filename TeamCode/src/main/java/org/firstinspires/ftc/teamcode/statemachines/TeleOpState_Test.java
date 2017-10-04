package org.firstinspires.ftc.teamcode.statemachines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.controllers.ModernRoboticsSensorModule;
import org.firstinspires.ftc.teamcode.controllers.SensorModule;
import org.firstinspires.ftc.teamcode.opmodes.TankBot;

/**
 * Created by Derek on 10/3/17.
 * Tests the TeleOpState
 */
@Autonomous(name="TeleOpState_Test", group="test")
public class TeleOpState_Test extends TankBot {
    private ModernRoboticsSensorModule sensors = new ModernRoboticsSensorModule(this);

    @Override
    public void init() {
        super.init();
        sensors.init();
    }

    @Override
    public void start(){
        super.start();
        stateMachine = new StateMachine(
                new TeleOpState("teleop", new Transition[]{})
        );
    }

    @Override
    public SensorModule Sensors() {
        return sensors;
    }
}
