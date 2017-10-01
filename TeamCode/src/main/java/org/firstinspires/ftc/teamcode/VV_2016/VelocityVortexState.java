package org.firstinspires.ftc.teamcode.VV_2016;

import org.firstinspires.ftc.teamcode.statemachines.State;
import org.firstinspires.ftc.teamcode.statemachines.Transition;

/**
 * Created by Derek Williams of team 3965 on 11/19/2016.
 */

public abstract class VelocityVortexState extends State {
    private VelocityVortexRobotBase velocityVortexRobotBase;


    VelocityVortexState(String name, Transition... transitions) {
        super(name, transitions);
    }


    /*
    @Override
    public void onAddition(StateMachine stateMachine) {
        super.onAddition(stateMachine);
        if (getRobot() instanceof VelocityVortexRobotBase) {
            velocityVortexRobotBase = (VelocityVortexRobotBase) getRobot();
        }
        else throw new RuntimeException("Attempting to use VVstate on non-VVRobot base");
    }
    */

    protected VelocityVortexRobotBase getVelocityVortexRobotBase() {
        return velocityVortexRobotBase;
    }

    /**
     * Created by Derek Williams of team 3965 on 12/7/2016.
     */

    public static class FlickParticleState extends VelocityVortexState {
         FlickParticleState(String name, Transition... transitions) {
             super(name, transitions);
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
}
