package org.firstinspires.ftc.teamcode.VV_2016;

import org.firstinspires.ftc.teamcode.VV_2016.VelocityVortexRobotBase;
import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Created by Derek Williams of team 3965 on 12/13/2016.
 */

public class ToggleTest extends RobotBase {
    protected VelocityVortexRobotBase.ButtonPusher rightButtonPusher;
    protected VelocityVortexRobotBase.ButtonPusher leftButtonPusher;

    protected boolean previousX;
    protected boolean previousB;

    @Override
    public void start(){
        rightButtonPusher = new VelocityVortexRobotBase.ButtonPusher(hardwareMap.servo.get("right_button_pusher"), VelocityVortexRobotBase.ButtonPusher.RIGHT);
        leftButtonPusher = new VelocityVortexRobotBase.ButtonPusher(hardwareMap.servo.get("left_button_pusher"), VelocityVortexRobotBase.ButtonPusher.LEFT);
    }

    @Override
    public void loop(){ //Finish this
        // Handling button press events
        if (gamepad1.x && previousX) {

        }

        saveGamepadValues();
    }

    private void saveGamepadValues() {
        previousX = gamepad1.x;
        previousB = gamepad1.b;
    }

    @Override
    public void stop() {

    }
}
