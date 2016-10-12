package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams on 10/11/2016.
 */

public class VelocityVortexAutonomous extends VelocityVortexRobotBase {
    StateMachine States = new StateMachine();

    @Override
    public void start(){
        States.add(new State[]{

        });
    }

    @Override
    public void loop(){}

    @Override
    public void stop(){}
}

class DriveState extends State{
    DriveState(String name, RobotBase robot, double power, double distance, String nextstate){

    }
}
