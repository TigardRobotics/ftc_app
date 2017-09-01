package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams of team 3965 on 12/7/2016.
 */

public class FlickParticleState extends VelocityVortexState {
     FlickParticleState(String name) {
         this.name = name;
     }


    @Override
    public void start() {
        super.start();
        getVelocityVortexRobotBase().enableFlicker();
        getVelocityVortexRobotBase().enableLifter();
    }

    @Override
    public void loop() {
        //getRobot().telemetry.addLine();
    }

    @Override
    public void stop() {
        getVelocityVortexRobotBase().disableFlicker();
        getVelocityVortexRobotBase().disableLifter();
    }
}
