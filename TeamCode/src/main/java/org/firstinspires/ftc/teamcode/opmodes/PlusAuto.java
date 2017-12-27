package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Names;
import org.firstinspires.ftc.teamcode.controllers.MRGyroController;
import org.firstinspires.ftc.teamcode.statemachines.CrabState;
import org.firstinspires.ftc.teamcode.statemachines.DriveWithHeadingState;
import org.firstinspires.ftc.teamcode.statemachines.ProgressTrans;
import org.firstinspires.ftc.teamcode.statemachines.SpinToHeadingState;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;
import org.firstinspires.ftc.teamcode.statemachines.TimeTrans;

/**
 * Created by Derek on 10/3/17.
 * Test driving in a square.
 */

@Autonomous(name="+", group="test")
public class PlusAuto extends SwerveBase {

    private double speed = 0.5;
    private double sideLen = 30.5; //12 inches

    @Override
    public void init() {
        super.init();
        //sensors.init();
        //addControllers(new MRGyroController((ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get(Names.gyro)));
    }

    @Override
    public void start(){
        super.start();
        stateMachine = new StateMachine(
                new CrabState("to forward", 0.0, 0.0, new TimeTrans("forward", 1.0)),
                new CrabState("forward", 0.0, speed, new ProgressTrans("return from forward", sideLen)),
                new CrabState("return from forward", 0.0, -speed, new ProgressTrans("to left", sideLen)),

                new CrabState("to left", 270.0, 0.0, new TimeTrans("left", 1.0)),
                new CrabState("left", 270.0, speed, new ProgressTrans("return from left", sideLen)),
                new CrabState("return from left", 270.0, -speed, new ProgressTrans("to backward", sideLen)),

                new CrabState("to backward", 180.0, 0.0, new TimeTrans("backward", 1.0)),
                new CrabState("backward", 180.0, speed, new ProgressTrans("return from backward", sideLen)),
                new CrabState("return from backward", 180.0, -speed, new ProgressTrans("to right", sideLen)),

                new CrabState("to right", 90.0, 0.0, new TimeTrans("right", 1.0)),
                new CrabState("right", 90.0, speed, new ProgressTrans("return from right", sideLen)),
                new CrabState("return from right", 90.0, -speed, new ProgressTrans("to forward", sideLen))
        );
    }

}
