package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Derek Williams  on 10/11/2016.
 */

//@Autonomous(name="VV Auto Base", group="3965")
public abstract class VelocityVortexAutonomous extends VelocityVortexRobotBase {
    protected int directionMultiplier;  // Multiply turn power by this
    protected String color = NO_COLOR;  // Pass this into the button pusher state
    protected StateMachine stateMachine = new StateMachine(this);

    protected double followSpeed = 0.35;
    protected double rangeToBeacon = 6.0;
    protected double rangeFromBeacon = 50.0;

    @Override
    public void loop(){
        stateMachine.step();
        //telemetry.addData("line detector level", getSensorModule().getLineDetectorLightLevel());
    }

    @Override
    public void stop(){
        stateMachine.stop();
    }
}