package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Derek Williams of team 3965 on 11/9/2016.
 */

@Autonomous(name="Test Auto", group="3965")
public class TestAutonomous extends VelocityVortexRobotBase {
    protected StateMachine stateMachine = new StateMachine(this);

    @Override
    public void start(){
        // Adding states to state machine
        stateMachine.add(new State[]{
            new EdgeFollowState("follow", 0.35),
            new TurnState("align", -0.35),
            new PushButtonState("push", RED),
        });

        // Adding transitions to state machine
        stateMachine.add(new Transition[]{
            new BelowRangeTrans("follow", "align", 4),
            new ProgressReachedTrans("align", "push", 50),
            new TimeElapsedTrans("push", null, 10)
        });

        // Setting initial active state
        stateMachine.setActiveState("follow");
    }

    @Override
    public void loop(){
        stateMachine.step();
        //telemetry.addData("ods value", getSensorModule().getLineDetectorLightLevel());
        telemetry.addLine(getSensorModule().getFrontColor() + " detected");
    }

    @Override
    public void stop(){
        stateMachine.stop();
    }
}
