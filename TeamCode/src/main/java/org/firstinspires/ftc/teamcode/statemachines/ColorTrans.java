package org.firstinspires.ftc.teamcode.statemachines;

import org.firstinspires.ftc.teamcode.Color;
import org.firstinspires.ftc.teamcode.controllers.ColorController;
import org.firstinspires.ftc.teamcode.opmodes.RobotBase;

/**
 * Created by Katrina and Derek on 11/9/2017.
 */

public class ColorTrans extends Transition {

    private ColorController controller;
    private Color target;

    public ColorTrans (String destination, Color color) {
        super(destination);
        target = color;
        controller = (ColorController)RobotBase.findController(ColorController.class);
    }

    @Override
    public boolean test() {
        Color colorDetected = controller.getColor();
        boolean correct = colorDetected == target;
        if(correct) RobotBase.log("Color transition detected "+colorDetected.toString());
        return correct;
    }
}
