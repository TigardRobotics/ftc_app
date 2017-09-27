package org.firstinspires.ftc.teamcode.opmodes2016;

import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Derek Williams on 10/11/2016.
 */

//@Autonomous(name="VV Auto Base", group="3965")
public abstract class VelocityVortexAuto extends VelocityVortexRobotBase {
    //protected int directionMultiplier;  // Multiply turn speed by this
    protected String color = NO_COLOR;  // Pass this into the button pusher state


    //protected double reverseSpeed = 0.35;

    protected double rangeFromBeacon = 30.0;
    protected double rangeToShoot = rangeFromBeacon*0.2;

    protected double throwDuration = 3.0;

    private ElapsedTime timeElapsed;

    @Override
    public void start() {
        retractLeftPusher();
        retractRightPusher();
        timeElapsed = new ElapsedTime();
    }

    @Override
    public void loop(){
        timeElapsed.reset();
        //stateMachine.step();
        //telemetry.addData("line detector level", getSensorModule().getLineDetectorLightLevel());
        telemetry.addData("time elapsed", timeElapsed.milliseconds());
    }

    @Override
    public void stop(){
        //stateMachine.stop();
    }
}