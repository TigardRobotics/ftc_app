package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Robotics on 1/31/2017.
 */

@TeleOp(name="VV test Teleop", group="3965")
public class TestTeleopState extends VelocityVortexRobotBase {
    @Override
    public void start(){
        stateMachine.add(new State[]{
                new TeleOpState("teleop"),
                new EdgeFollowState("follow", followSpeed),
        });

        stateMachine.add(new Transition[] {
                new StateCompletedTrans("teleop", "follow"),
                new BelowRangeTrans("follow", "teleop", rangeToBeacon),
        });

        stateMachine.setActiveState("teleop");
    }

    @Override
    public void loop() {
        stateMachine.step();
    }

    @Override
    public void stop() {
        stateMachine.stop();
    }
}
