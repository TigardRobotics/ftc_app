package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Derek Williams of team 3965 on 1/12/2017.
 */

@Autonomous(name="turn right", group="3965")
public class TestChangingHeading extends VelocityVortexAutonomous {
    @Override
    public void start() {
        super.start();
        stateMachine.add(new State[]{
                new WaitState("wait"),
                new ChangeHeadingState("turn", 90, turnSpeed),
        });

        stateMachine.add(new Transition[]{
                new TimeElapsedTrans("wait", "turn", 3),
                new StateCompletedTrans("turn", null),
        });

        stateMachine.setActiveState("wait");
    }

    @Override
    public void loop() {
        super.loop();
        telemetry.addData("Heading: %d", getSensorModule().getHeading());
    }
}
