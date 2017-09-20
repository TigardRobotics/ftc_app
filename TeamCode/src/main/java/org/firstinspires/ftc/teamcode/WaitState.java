package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek on 9/19/17.
 */

class WaitState extends State {

    public WaitState (String name, Transition... transitions) {
        super(name, transitions);
    }

    @Override
    public void doState() {
        //getRobot().telemetry.addData(name, "waiting . . .");
    }

    @Override
    public void onExit() {}
}
