package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Derek Williams on 10/11/2016.
 */

//@Autonomous(name="VV Auto Base", group="3965")
public abstract class VelocityVortexAutonomous extends VelocityVortexRobotBase {
    //protected int directionMultiplier;  // Multiply turn power by this
    protected String color = NO_COLOR;  // Pass this into the button pusher state

    protected double rangeFromBeacon = 20.0;

    protected double throwDuration = 3.0;

    private ElapsedTime timeElapsed;

    @Override
    public void init() {
        super.init();   //Must call first to setup hardware
        leds.setLed(color, true);
    }

    @Override
    public void start() {
        retractLeftPusher();
        retractRightPusher();
        timeElapsed = new ElapsedTime();
    }

    @Override
    public void loop(){
        timeElapsed.reset();
        stateMachine.step();
        //telemetry.addData("line detector level", getSensorModule().getLineDetectorLightLevel());
        telemetry.addData("time elapsed", timeElapsed.milliseconds());
    }

    @Override
    public void stop(){
        stateMachine.stop();
    }
}