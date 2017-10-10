package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;

import org.firstinspires.ftc.teamcode.controllers.HardwareController;
import org.firstinspires.ftc.teamcode.controllers.IColorIndicator;
import org.firstinspires.ftc.teamcode.controllers.IDrive;
import org.firstinspires.ftc.teamcode.controllers.LedController;
import org.firstinspires.ftc.teamcode.controllers.SensorModule;
import org.firstinspires.ftc.teamcode.controllers.TankDrive;
import org.firstinspires.ftc.teamcode.statemachines.State;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Derek Williams of team 3965 on 10/9/2016.
 * Superclass for all opmodes
 */

public abstract class RobotBase extends OpMode {
    public static final String RED = "red";
    public static final String BLUE = "blue";
    public static final String NO_COLOR = "NONE";

    //* List of supported Hardware Controllers */
    public List<HardwareController> Controllers = new ArrayList<HardwareController>();

    protected StateMachine stateMachine = null;

    public static RobotBase instance;

    @Override
    public void init() {
        instance = this;
        HardwareController.Robot = this;
        State.Robot = this;
    }

    @Override
    public void init_loop() {
        for (HardwareController control : Controllers ) control.init_loop();
    }

    @Override
    public void start() {
        for (HardwareController control : Controllers ) control.start();
    }

    @Override
    public void loop() {
        if(stateMachine == null) throw new RuntimeException("State Machine is never constructed!");
        stateMachine.step();
        for (HardwareController control : Controllers ) control.loop();
    }

    @Override
    public void stop() {
        stateMachine.stop();
        for (HardwareController control : Controllers ) control.stop();
    }

    //!TODO: Move to Controllers
    public SensorModule Sensors() {
        throw new RuntimeException("Sensor Module does not Exist");
    }
}
