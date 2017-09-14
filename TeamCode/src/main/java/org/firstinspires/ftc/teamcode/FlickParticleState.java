package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams of team 3965 on 12/7/2016.
 */

public class FlickParticleState extends VelocityVortexState {
     FlickParticleState(String name) {
         this.name = name;
     }


    @Override
    public void onEntry() {
        super.onEntry();
        getVelocityVortexRobotBase().enableFlicker();
        getVelocityVortexRobotBase().enableLifter();
    }

    @Override
    public void doState() {
        //getRobot().telemetry.addLine();
    }

    @Override
    public void onExit() {
        getVelocityVortexRobotBase().disableFlicker();
        getVelocityVortexRobotBase().disableLifter();
    }
}
