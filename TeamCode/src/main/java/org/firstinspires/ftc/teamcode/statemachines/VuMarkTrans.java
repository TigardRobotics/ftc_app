package org.firstinspires.ftc.teamcode.statemachines;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.Color;
import org.firstinspires.ftc.teamcode.controllers.ColorController;
import org.firstinspires.ftc.teamcode.controllers.VuMarkController;
import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Transition based on the detected vuMark
 */

public class VuMarkTrans extends Transition {

    private VuMarkController controller;
    private RelicRecoveryVuMark target;

    public VuMarkTrans(String destination, RelicRecoveryVuMark mark) {
        super(destination);
        target = mark;
        controller = (VuMarkController)RobotBase.findController(VuMarkController.class);
    }

    @Override
    public boolean test() {
        return controller.getMark() == target;
    }
}
