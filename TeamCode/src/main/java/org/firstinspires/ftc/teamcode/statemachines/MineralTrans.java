package org.firstinspires.ftc.teamcode.statemachines;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.controllers.TflowController;
import org.firstinspires.ftc.teamcode.controllers.VuMarkController;
import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Transition based on the detected position of the gold mineral
 * Used for sampling
 */

public class MineralTrans extends Transition {

    private TflowController controller;
    private String target;

    public MineralTrans(String destination, String pos) {
        super(destination);
        target = pos;
        controller = (TflowController)RobotBase.findController(TflowController.class);
    }

    @Override
    public boolean test() {
        String goldPos = controller.getGoldPos();
        boolean correct = goldPos.equals(target);
        if(correct) RobotBase.log(String.format("Mineral transition triggered: %s", goldPos));
        return correct;
    }
}
