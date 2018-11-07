/* Copyright (c) 2017 FIRST. All rights reserved.
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

import android.content.Context;
import android.content.res.Resources;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.vuforia.CameraDevice;

import org.firstinspires.ftc.robotcontroller.external.samples.ConceptVuforiaNavigation;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.Map;

/**
 * This Class sets up the basics for using the Vuforia engine
 */

public class VuforiaController extends HardwareController {
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
   * and paste it in to your code onthe next line, between the double quotes.
   */

    public static final String KEY = "AeVzAhr/////AAAAGXRSmCKs50SBs1rd/S528/st/2kCpgadInEirrV1g0tOoiJ6LHrprlXO+lZrEpbruFcBqVWXzjbZo4y5O6Dm2r1mhyfQ/n1B7JSCdiXlBwmLDCrAEz7NRyRQq8598ZyMZBqJZUqEiCkivK1CyOlXobCRdNnRTeELFYWJIETViKhC+Id0ke8iOHtvT7uWq0vQiaiMyuQaJyLQNKISnu4+1Vp2LzXLnETvI0MMvT0VaBwhoYBcWNDfUBlo/XVfgRcE8kjc81MFd6ZY4yVPseDmkW4912S5CmDDaVRUQjFbF2YEk9acUE/q5cMcGYLEYOIv2ZuqOr1cKAz2Wtxgz8zWQqXpUrFgI0+qlFDXzrIWfJR6";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    protected VuforiaLocalizer vuforia = null;

    //appContext (so we can request the CameraView)
    protected Context appContext;

    protected CameraDevice camera;

    /**
     * Viewforia Base (sets up static info that all Vuforia Controllers use
     * @param appContext Android Application Context (hardwareMap.appContext)
     * To get monitor use cameraId = hardwareMap.appContext.getResources()

     */
    public VuforiaController(Context appContext) {
        this.appContext = appContext;
    }

    /**
     * Turn camera light on/off
     * @param on
     */
    public void setLight( boolean on){
        CameraDevice.getInstance().setFlashTorchMode(on);
    }

    @Override
    public void init() {
        // Get the Id for the cameraMonitorView
        int cameraViewId = appContext.getResources().getIdentifier("cameraMonitorViewId", "id", appContext.getPackageName());
        /*
         * To start up Vuforia, tell it the view that we wish to use for camera monitor (on the RC phone);
         * If no camera monitor is desired, use the parameterless constructor instead (commented out below).
         */
            VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraViewId);

            // OR...  Do Not Activate the Camera Monitor View, to save power
            // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

            parameters.vuforiaLicenseKey = KEY;

        /*
         * We also indicate which camera on the RC that we wish to use.
         * Here we chose the back (HiRes) camera (for greater range), but
         * for a competition robot, the front camera might be more convenient.
         */
            parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
            vuforia = ClassFactory.createVuforiaLocalizer(parameters);

    }

}
