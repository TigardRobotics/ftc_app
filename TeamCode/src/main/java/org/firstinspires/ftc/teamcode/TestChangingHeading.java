package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams of team 3965 on 1/12/2017.
 */

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
    }
}
