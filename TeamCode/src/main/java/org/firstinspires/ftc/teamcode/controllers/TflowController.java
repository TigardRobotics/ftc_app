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
import com.vuforia.CameraDevice;

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

    public static final String GOLD_ON_LEFT = "Left";
    public static final String GOLD_CENTER = "Center";
    public static final String GOLD_ON_RIGHT = "Right";
    public static final String GOLD_NONE = "None";

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

    private Boolean ShowMessages = false;

    private LedController Leds = null; //for debugging

    private Recognition Gold;     // The best one
    private List<Recognition> Silvers; // 2 or less

    private boolean lockRecognitions = false;

    public TflowController( LedController leds, Boolean showMessages ){
        Leds = leds;
        ShowMessages = showMessages;
    }

    public TflowController( Boolean showMessages){
        Leds = null;
        ShowMessages = showMessages;
    }

    public TflowController( ){
        Leds = null;
        ShowMessages = false;
    }
    @Override
    public void init() {
        super.init();
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();
        //Try this: CameraDevice.getInstance().setFlashTorchMode(true);

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            Robot.telemetry.addData("Sorry!", "This device is not compatible with TFOD");
            Robot.log("This device is not compatible with TFOD");
        }
        reset();

        if (tfod != null) {
            tfod.activate();

        }
    }

    @Override
    public void init_loop() {
        super.init_loop();
        if (ShowMessages) Robot.telemetry.clearAll();

        if (tfod != null) {
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            processRecognitions(updatedRecognitions);
        }
    }

    @Override
    public void start() {
        super.start();
        lock();
    }

    @Override
    public void loop() {
        super.loop();
        if (ShowMessages) Robot.telemetry.clearAll();

        if (tfod != null && !lockRecognitions) {
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            processRecognitions(updatedRecognitions);
        }
    }

    @Override
    public void stop() {
        //Try this: CameraDevice.getInstance().setFlashTorchMode(false);
        if (tfod != null) {
            tfod.shutdown();
        }
    }

    public void lock() {
        lockRecognitions = true;
    }

    public void reset() {
        lockRecognitions = false;
        Gold = null;
        if (Silvers != null) Silvers.clear();
        if(Leds != null) {
            Leds.setModuleLed(LedController.RED, false);
            Leds.setModuleLed(LedController.BLUE, false);
        }
    }

    void processRecognitions(List<Recognition> updatedRecognitions) {
        // getUpdatedRecognitions() will return null if no new information is available since
        // the last time that call was made.
        // New information might be nothing important
        if (updatedRecognitions != null) {
            if (ShowMessages) Robot.telemetry.addData("Objects Detected", updatedRecognitions.size());
            if (updatedRecognitions.size() <=2 ) return; //!!fix
            Recognition gold = null;
            List<Recognition> silvers = new ArrayList<Recognition>();
            for (Recognition mineral : updatedRecognitions){
                String name = mineral.getLabel();
                //if (ShowMessages) Robot.telemetry.addData("Object Label",name );
                Robot.log("Object Label " + name);
                if (name.equals(LABEL_GOLD_MINERAL)){
                    if (gold == null){
                        gold = mineral;
                        if (ShowMessages) Robot.telemetry.addData("Chose gold",mineral.getConfidence() );
                        if (ShowMessages) Robot.telemetry.addData("Gold-AbsPos",(mineral.getTop()+mineral.getBottom())/2.0 );
                        Robot.log("Chose gold with confidence" + Double.toString(mineral.getConfidence()) );
                    }
                    else if (IsBetter( mineral, gold)) {
                        gold = mineral;
                        if (ShowMessages)
                            Robot.telemetry.addData("Replaced gold", mineral.getConfidence());
                        if (ShowMessages) Robot.telemetry.addData("Gold-AbsPos",(mineral.getTop()+mineral.getBottom())/2.0 );
                        Robot.log("Replaced gold with confidence" + Double.toString(mineral.getConfidence()));
                    }
                }
                if (name.equals(LABEL_SILVER_MINERAL)){
                    if (silvers.size() <2){
                        silvers.add(mineral);
                        if (ShowMessages) Robot.telemetry.addData("Chose silver",mineral.getConfidence() );
                        if (ShowMessages) Robot.telemetry.addData("Silver-AbsPos",(mineral.getTop()+mineral.getBottom())/2.0 );
                        Robot.log("Chose silver with confidence" + Double.toString(mineral.getConfidence()) );
                    }
                    else if ( IsBetter(silvers.get(1), silvers.get(0)) && IsBetter( mineral, silvers.get(0)) ) {
                        silvers.remove(0);
                        silvers.add(mineral);
                        if (ShowMessages)
                            Robot.telemetry.addData("Replaced silver", mineral.getConfidence());
                        if (ShowMessages) Robot.telemetry.addData("Silver-AbsPos",(mineral.getTop()+mineral.getBottom())/2.0 );
                        Robot.log("Replaced silver with confidence" + Double.toString(mineral.getConfidence()));
                    }
                    else if ( IsBetter(silvers.get(0), silvers.get(1)) && IsBetter( mineral, silvers.get(1)) ) {
                        silvers.remove(1);
                        silvers.add(mineral);
                        if (ShowMessages)
                            Robot.telemetry.addData("Replaced silver", mineral.getConfidence());
                        if (ShowMessages) Robot.telemetry.addData("Silver-AbsPos",(mineral.getTop()+mineral.getBottom())/2.0 );
                        Robot.log("Replaced silver with confidence" + Double.toString(mineral.getConfidence()));
                    }
                }
            }

            if(gold!=null && silvers.size()==2){
                //!if (ShowMessages) Robot.telemetry.addData("gold-H",getPos(gold, silvers, false) );
                if (ShowMessages) Robot.telemetry.addData("gold-V",getPos(gold, silvers, true) );

                if(Gold==null){
                    Gold=gold;
                    Silvers=silvers;
                }
                else /*if ( (gold.getConfidence()+silvers.get(0).getConfidence()+silvers.get(1).getConfidence())
                        > (Gold.getConfidence()+Silvers.get(0).getConfidence()+Silvers.get(1).getConfidence()) )*/{
                    Gold=gold;
                    Silvers=silvers;
                }
            }
        }

        if (Gold!=null && !lockRecognitions)
        {
            /* Ignore for now
            if (ShowMessages) Robot.telemetry.addData("Final Gold-H",getPos(Gold, Silvers, false) );
            Robot.log("Final Gold-H position" + Double.toString(getPos(Gold, Silvers, false)) );
            */
            if (ShowMessages) Robot.telemetry.addData("Gold-AbsPos",(Gold.getTop()+Gold.getBottom())/2.0 );
            if (ShowMessages) Robot.telemetry.addData("Silver0-AbsPos",(Silvers.get(0).getTop()+Silvers.get(0).getBottom())/2.0 );
            if (ShowMessages) Robot.telemetry.addData("Silver1-AbsPos",(Silvers.get(1).getTop()+Silvers.get(1).getBottom())/2.0 );

            if(ShowMessages) Robot.telemetry.addData("Final-V = "+getGoldPos() , getPos(Gold, Silvers, true) );
            Robot.log("Final Gold-V position" + Double.toString(getPos(Gold, Silvers, true)) );
            if (Leds != null){
                switch (getGoldPos()) {
                    case GOLD_ON_RIGHT:
                        Leds.setModuleLed(LedController.BLUE, false);
                        Leds.setModuleLed(LedController.RED, true);
                        break;
                    case GOLD_ON_LEFT:
                        Leds.setModuleLed(LedController.RED, false);
                        Leds.setModuleLed(LedController.BLUE, true);
                        break;
                    case GOLD_CENTER:
                        Leds.setModuleLed(LedController.RED, true);
                        Leds.setModuleLed(LedController.BLUE, true);
                        break;
                    default:
                        Leds.setModuleLed(LedController.RED, false);
                        Leds.setModuleLed(LedController.BLUE, false);
                        break;
                }
            }
        }
    }

    /**
     * Determine if the test recognition is better than the reference
     * @param test
     * @param reference
     * @return
     */
    boolean IsBetter( Recognition test, Recognition reference){
        if (test == null) return false;
        if (reference == null) return true;
        return (test.getConfidence() > reference.getConfidence());
    }

    /**
     * Get the relative position for the gold mineral
     * @param gold the gold element
     * @param silvers the silver elements
     * @param vert = true if minerals are vertically oriented (usually because phone is horizontal/landscape)
     * @return -0.5 to +0.5
     */
    float getPos(Recognition gold, List<Recognition> silvers, boolean vert)
    {
        //!! implement vert=false
        float pos = Float.NaN;
        if (gold != null && silvers!= null && silvers.size()>1) {

            float gold_pos = (gold.getTop()+gold.getBottom())/2;
            float silver0_pos = (silvers.get(0).getTop()+silvers.get(0).getBottom())/2;
            float silver1_pos = (silvers.get(1).getTop()+silvers.get(1).getBottom())/2;

            float min = Math.min(gold_pos, Math.min(silver0_pos, silver1_pos));
            float max = Math.max(gold_pos, Math.max(silver0_pos, silver1_pos));
            pos = ( gold_pos - ((min+max)/2) ) / (min-max);
        }

        return pos;
    }

    /**
     * Get string name of gold position relative within the current chosen recognition (phone is horizontal/landscape)
     * @return
     */
    public String getGoldPos( ) {
        return getGoldPos(true);
    }

    /**
     * Get string name of gold position relative within the current chosen recognition
     * @param vert = true if minerals are vertically oriented (usually because phone is horizontal/landscape)
     * @return
     */
    public String getGoldPos( Boolean vert) {
        float gold_pos = getPos(Gold, Silvers, vert);
        String gold_pos_name = GOLD_NONE;
        if (! Float.isNaN(gold_pos) ){
            if (gold_pos < -0.25){
                gold_pos_name = GOLD_ON_LEFT;
            }
            else if (gold_pos > 0.25){
                gold_pos_name = GOLD_ON_RIGHT;
            }
            else {
                gold_pos_name = GOLD_CENTER;
            }
        }
        return  gold_pos_name;
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
        if (ShowMessages) Robot.telemetry.addLine("TensorFlow initialized");
        Robot.log("TensorFlow initialized");
    }
}
