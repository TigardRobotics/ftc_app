package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Derek Williams of team 3965 on 1/31/2017.
 */

//@TeleOp(name="VV test Teleop", group="3965")
public abstract class StateBasedTeleop extends VelocityVortexRobotBase {
    protected String color;

    @Override
    public void start() {
        stateMachine.add(new State[]{
                new TeleOpState("teleop"),
                new EdgeFollowState("follow", followSpeed),
                new PushButtonState("push", color),
        });

        stateMachine.add(new Transition[] {
                new BelowRangeTrans("follow", "push", rangeToBeacon),
                new TimeElapsedTrans("push", "teleop", 1),
        });

        stateMachine.setActiveState("teleop");
    }

    @Override
    public void loop() {
        stateMachine.step();

        if(stateMachine.getActiveStateName() != "teleop") {
            if(gamepad1.dpad_down) stateMachine.setActiveState("teleop");
        }
        else {
            if(gamepad1.dpad_up) stateMachine.setActiveState("follow");
        }
    }

    @Override
    public void stop() {
        stateMachine.stop();
    }
}
