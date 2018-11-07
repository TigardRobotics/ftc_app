package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Names;
import org.firstinspires.ftc.teamcode.controllers.HardwareController;
import org.firstinspires.ftc.teamcode.controllers.MRGyroController;
import org.firstinspires.ftc.teamcode.statemachines.DriveWithHeadingState;
import org.firstinspires.ftc.teamcode.statemachines.ProgressTrans;
import org.firstinspires.ftc.teamcode.statemachines.SpinToHeadingState;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;

import java.util.List;

/**
 * Created by Derek on 10/3/17.
 * Test driving in a square.
 */

@Autonomous(name="SquareGyro", group="test")
public class SquareAuto extends SwerveBase {

    private double sqrSideLen = 30.5;   //12 inches

    @Override
    public List<HardwareController> getControllers() {
        List<HardwareController> controllers = super.getControllers();
        controllers.add(new MRGyroController((ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get(Names.gyro)));
        return controllers;
    }

    @Override
    public void start(){
        super.start();
        stateMachine = new StateMachine(
                new SpinToHeadingState("turn0", 0.5, 0.0, new ProgressTrans("drive0", 0.0)),
                new DriveWithHeadingState("drive0", 0.5, 0.0, new ProgressTrans("turn90", sqrSideLen)),
                new SpinToHeadingState("turn90", 0.5, 90.0, new ProgressTrans("drive90", 0.0)),
                new DriveWithHeadingState("drive90", 0.5, 90.0, new ProgressTrans("turn180", sqrSideLen)),
                new SpinToHeadingState("turn180", 0.5, 180.0, new ProgressTrans("drive180", 0.0)),
                new DriveWithHeadingState("drive180", 0.5, 180.0, new ProgressTrans("turn270", sqrSideLen)),
                new SpinToHeadingState("turn270", 0.5, 270.0, new ProgressTrans("drive270", 0.0)),
                new DriveWithHeadingState("drive270", 0.5, 270.0, new ProgressTrans("turn0", sqrSideLen))
        );
    }

}
