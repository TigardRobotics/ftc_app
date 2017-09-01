package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Robotics on 2/21/2017.
 */

@Autonomous(name="Test Gyro Drive", group="3965")
public class TestDriveWithGyro extends VelocityVortexAutonomous {

    @Override
    public void start() {
        super.start();
        stateMachine.add(new State[]{
                new DriveWithHeadingState("drive", driveSpeed, 0),
        });

        stateMachine.add(new Transition[]{
        });

        stateMachine.setActiveState("drive");
    }
}
