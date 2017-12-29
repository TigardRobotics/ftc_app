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

@Autonomous(name="*", group="test")
public class StarAuto extends SwerveBase {

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
                new CrabState("to forward", 0.0, 0.0, new TimeTrans("forward", 2.0)),
                new CrabState("forward", 0.0, speed, new ProgressTrans("return from forward", sideLen)),
                new CrabState("return from forward", 0.0, -speed, new ProgressTrans("to forward left", sideLen)),

                new CrabState("to forward left", 315.0, 0.0, new TimeTrans("forward left", 2.0)),
                new CrabState("forward left", 315.0, speed, new ProgressTrans("return from forward left", sideLen)),
                new CrabState("return from forward left", 315.0, -speed, new ProgressTrans("to left", sideLen)),

                new CrabState("to left", 270.0, 0.0, new TimeTrans("left", 2.0)),
                new CrabState("left", 270.0, speed, new ProgressTrans("return from left", sideLen)),
                new CrabState("return from left", 270.0, -speed, new ProgressTrans("to backward left", sideLen)),

                new CrabState("to backward left", 225.0, 0.0, new TimeTrans("backward left", 2.0)),
                new CrabState("backward left", 225.0, speed, new ProgressTrans("return from backward left", sideLen)),
                new CrabState("return from backward left", 225.0, -speed, new ProgressTrans("to backward right", sideLen)),

                /* This one is in the hall hole
                new CrabState("to backward", 180.0, 0.0, new TimeTrans("backward", 2.0)),
                new CrabState("backward", 180.0, speed, new ProgressTrans("return from backward", sideLen)),
                new CrabState("return from backward", 180.0, -speed, new ProgressTrans("to backward right", sideLen)),
                */

                new CrabState("to backward right", 135.0, 0.0, new TimeTrans("backward right", 2.0)),
                new CrabState("backward right", 135.0, speed, new ProgressTrans("return from backward right", sideLen)),
                new CrabState("return from backward right", 135.0, -speed, new ProgressTrans("to right", sideLen)),

                new CrabState("to right", 90.0, 0.0, new TimeTrans("right", 2.0)),
                new CrabState("right", 90.0, speed, new ProgressTrans("return from right", sideLen)),
                new CrabState("return from right", 90.0, -speed, new ProgressTrans("to forward right", sideLen)),

                new CrabState("to forward right", 45.0, 0.0, new TimeTrans("forward right", 2.0)),
                new CrabState("forward right", 45.0, speed, new ProgressTrans("return from forward right", sideLen)),
                new CrabState("return from forward right", 45.0, -speed, new ProgressTrans("to forward", sideLen))
        );
    }

}
