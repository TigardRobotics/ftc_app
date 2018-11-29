/* Copyright (c) 2018 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class TflowController extends HardwareController {
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private static final String LEFT = "Left";
    private static final String CENTER = "Center";
    private static final String RIGHT = "Right";
    private static final String NONE = "None";

    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY = "AeVzAhr/////AAAAGXRSmCKs50SBs1rd/S528/st/2kCpgadInEirrV1g0tOoiJ6LHrprlXO+lZrEpbruFcBqVWXzjbZo4y5O6Dm2r1mhyfQ/n1B7JSCdiXlBwmLDCrAEz7NRyRQq8598ZyMZBqJZUqEiCkivK1CyOlXobCRdNnRTeELFYWJIETViKhC+Id0ke8iOHtvT7uWq0vQiaiMyuQaJyLQNKISnu4+1Vp2LzXLnETvI0MMvT0VaBwhoYBcWNDfUBlo/XVfgRcE8kjc81MFd6ZY4yVPseDmkW4912S5CmDDaVRUQjFbF2YEk9acUE/q5cMcGYLEYOIv2ZuqOr1cKAz2Wtxgz8zWQqXpUrFgI0+qlFDXzrIWfJR6";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    private Recognition Gold;     // The best one
    private List<Recognition> Silvers; // 2 or less

    @Override
    public void init() {
        super.init();
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            Robot.telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

    }

    @Override
    public void start() {
        super.start();
        if (tfod != null) {
            tfod.activate();
        }
    }

    @Override
    public void loop() {
        super.loop();
        Robot.telemetry.clearAll();

        if (tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            // New information might be nothing important
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                Robot.telemetry.addData("Objects Detected", updatedRecognitions.size());
                Recognition gold = null;
                List<Recognition> silvers = new ArrayList<Recognition>();

                for (Recognition mineral : updatedRecognitions){
                    String name = mineral.getLabel();
                    Robot.telemetry.addData("Object Label",name );
                    if (name.equals(LABEL_GOLD_MINERAL)){
                        if (gold == null){
                            gold = mineral;
                            Robot.telemetry.addData("Chose gold",mineral.getConfidence() );
                        }
                        else{
                            //Robot.telemetry.addData("Skipped gold",mineral.getConfidence() );
                        }
                    }
                    if (name.equals(LABEL_SILVER_MINERAL)){
                        if (silvers.size() <2){
                            silvers.add(mineral);
                            Robot.telemetry.addData("Chose silver",mineral.getConfidence() );
                        }
                        else{
                            //Robot.telemetry.addData("Skipped silver",mineral.getConfidence() );
                        }
                    }
                }

                if(gold!=null && silvers.size()==2){
                    float left = Math.min(gold.getLeft(), Math.min(silvers.get(0).getLeft(), silvers.get(1).getLeft()));
                    float right = Math.max(gold.getRight(), Math.min(silvers.get(0).getRight(), silvers.get(1).getRight()));
                    float top = Math.min(gold.getTop(), Math.min(silvers.get(0).getTop(), silvers.get(1).getTop()));
                    float bottom = Math.max(gold.getBottom(), Math.min(silvers.get(0).getBottom(), silvers.get(1).getBottom()));
                    Robot.telemetry.addData("", "top:%f , bottom:%f", top, bottom);
                    Robot.telemetry.addData("", "left:%f , right:%f", left, right);
                    Robot.telemetry.addData("gold-H",getPos(gold, silvers, false) );
                    Robot.telemetry.addData("gold-V",getPos(gold, silvers, true) );

                    if(Gold==null){
                        Gold=gold;
                        Silvers=silvers;
                    }
                    else{
                        //Evaluate if this is a better recognition
                        //!! For now always replace (for diagnostics)
                        Gold=gold;
                        Silvers=silvers;
                    }
                }
            }
            else if (Gold!=null)
            {
                Robot.telemetry.addData("Final Gold-H",getPos(Gold, Silvers, false) );
                Robot.telemetry.addData("Final Gold-V",getPos(Gold, Silvers, true) );
            }
        }

    }

    @Override
    public void stop() {
        if (tfod != null) {
            tfod.shutdown();
        }
    }

    float getPos(Recognition gold, List<Recognition> silvers, boolean vert)
    {
        float left = Math.min(gold.getLeft(), Math.min(silvers.get(0).getLeft(), silvers.get(1).getLeft()));
        float right = Math.max(gold.getRight(), Math.min(silvers.get(0).getRight(), silvers.get(1).getRight()));
        float top = Math.min(gold.getTop(), Math.min(silvers.get(0).getTop(), silvers.get(1).getTop()));
        float bottom = Math.max(gold.getBottom(), Math.min(silvers.get(0).getBottom(), silvers.get(1).getBottom()));
        float hpos = ( ((gold.getLeft()+gold.getRight() )/2) - ((left+right)/2) ) / (right-left);
        float vpos = ( ((gold.getTop() +gold.getBottom())/2) - ((bottom+top)/2) ) / (bottom-top);

        return vert ? hpos : vpos;
    }

    public String getGoldPos() {
        return ""; //!!!!!
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = Robot.hardwareMap.appContext.getResources().getIdentifier(
            "tfodMonitorViewId", "id", Robot.hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
        Robot.telemetry.addLine("TensorFlow initialized");
        Robot.log("TensorFlow initialized");
    }
}
