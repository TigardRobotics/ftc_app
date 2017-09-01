package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams of team 3965 on 11/19/2016.
 */

public abstract class VelocityVortexState extends State {
    private VelocityVortexRobotBase velocityVortexRobotBase;

    @Override
    public void onAddition(StateMachine stateMachine) {
        super.onAddition(stateMachine);
        if (getRobot() instanceof VelocityVortexRobotBase) {
            velocityVortexRobotBase = (VelocityVortexRobotBase) getRobot();
        }
        else throw new RuntimeException("Attempting to use VVstate on non-VVRobot base");
    }

    protected VelocityVortexRobotBase getVelocityVortexRobotBase() {
        return velocityVortexRobotBase;
    }
}
