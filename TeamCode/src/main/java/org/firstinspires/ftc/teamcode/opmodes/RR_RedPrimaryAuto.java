package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.statemachines.BelowRangeTrans;
import org.firstinspires.ftc.teamcode.statemachines.DriveState;
import org.firstinspires.ftc.teamcode.statemachines.KnockState;
import org.firstinspires.ftc.teamcode.statemachines.StateMachine;
import org.firstinspires.ftc.teamcode.statemachines.TimeTrans;
import org.firstinspires.ftc.teamcode.statemachines.WaitState;

/**
 * Created by Derek on 11/7/2017.
 */

@Autonomous(name="Primary Red", group="3965")
public class RR_RedPrimaryAuto extends SwerveBase {
    @Override
    public void start() {
        super.start();
        stateMachine = new StateMachine(
                new KnockState("knock")
                //! add color detect and twist
        );
    }
}
