package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.teamcode.controllers.HardwareController;
import org.firstinspires.ftc.teamcode.statemachines.State;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Derek Williams of team 3965 on 10/9/2016.
 * Superclass for all opmodes
 * The "Center of our Universe"
 */

public abstract class RobotBase extends OpMode {
    public static final String RED = "red";
    public static final String BLUE = "blue";
    public static final String NO_COLOR = "NONE";

    //* List of supported Hardware controllers */
    private List<HardwareController> controllers = new ArrayList<HardwareController>();

    /**
     * This method searches a list for a HardwareController with the desired interface
     */
    public static HardwareController findController(Class interf){
        for(HardwareController entry : instance.controllers){
            if(entry.implementsInterface(interf)) return entry;
        }
        throw new RuntimeException("Hardware controller with the "+interf.getName()+" interface does not exist.");
    }

    /**
     * This method allows opmodes to add controllers to the list
     */
    protected static void addControllers(HardwareController... controllers) {
        for(HardwareController controller : controllers) controller.init();
        instance.controllers.addAll(Arrays.asList(controllers));
    }

    public static void log(String msg) {
       RobotLog.i("<TM>: "+msg);
    }

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
        for (HardwareController control : controllers) control.init_loop();
    }

    @Override
    public void start() {
        for (HardwareController control : controllers) control.start();
    }

    @Override
    public void loop() {
        if(stateMachine == null) throw new RuntimeException("State Machine is never constructed!");
        stateMachine.step();
        for (HardwareController control : controllers) control.loop();
    }

    @Override
    public void stop() {
        if(stateMachine != null) stateMachine.stop();
        for (HardwareController control : controllers) control.stop();
    }

    /**
     * Methods for getting controller info
     */
    private static final double AMP_THRESHOLD = 0.8;
    public double getGamepad1RightJoystickAngle() {
        if(getGamepad1RightJoystickAmplitude() > AMP_THRESHOLD) {
            return (Math.toDegrees(Math.atan2(gamepad1.right_stick_x, -gamepad1.right_stick_y))+360)%360;
        }
        return 0;
    }

    public double getGamepad1RightJoystickAmplitude() {
        return Math.sqrt(gamepad1.right_stick_y*gamepad1.right_stick_y + gamepad1.right_stick_x*gamepad1.right_stick_x);
    }

}
