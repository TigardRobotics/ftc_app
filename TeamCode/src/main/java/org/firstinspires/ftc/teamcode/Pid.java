package org.firstinspires.ftc.teamcode;

/**
 * Imported from github.com/pmtischler/ftc_app
 */

/**
 * PID Controller: kp * (e + (integral(e) / ti) + (td * derivative(e))).
 * https://en.wikipedia.org/wiki/PID_controller#Ideal_versus_standard_PID_form
 * http://pmtischler-ftc-app.readthedocs.io/en/latest/tutorials/pid_control.html
 * http://pmtischler-ftc-app.readthedocs.io/en/latest/javasphinx/com/github/pmtischler/control/Pid.html
 */
public class Pid {
    /**
     * Creates a PID Controller.
     * @param kp Proportional factor to scale error to output.
     * @param ti The number of seconds to eliminate all past errors.
     * @param td The number of seconds to predict the error in the future.
     * @param integralMin The min of the running integral.
     * @param integralMax The max of the running integral.
     */
    public Pid(double kp, double ti, double td, double integralMin,
               double integralMax) {
        this.kp = kp;
        this.ti = ti;
        this.td = td;
        this.integralMin = integralMin;
        this.integralMax = integralMax;

        this.previousError = 0;
        this.runningIntegral = 0;
    }

    /**
     * Performs a PID update and returns the output control.
     * @param e desiredValue-actual
     * @param dt The amount of time (sec) elapsed since last update.
     * @return The output which impacts state value (e.g. motor throttle).
     */
    public double update(double e, double dt) {
        runningIntegral = clampValue(runningIntegral + e * dt,
                integralMin, integralMax);
        double d = (e - previousError) / dt;
        double output = kp * (e + (runningIntegral / ti) + (td * d));

        previousError = e;
        return output;
    }

    /**
     * Clamps a value to a given range.
     * @param value The value to acquire.
     * @param min The min acquire.
     * @param max The max acquire.
     * @return The clamped value.
     */
    public static double clampValue(double value, double min, double max) {
        return Math.min(max, Math.max(min, value));
    }

    // Proportional factor to scale error to output.
    private double kp;
    // The number of seconds to eliminate all past errors.
    private double ti;
    // The number of seconds to predict the error in the future.
    private double td;
    // The min of the running integral.
    private double integralMin;
    // The max of the running integral.
    private double integralMax;

    // The last error value.
    private double previousError;
    // The discrete running integral (bounded by integralMax).
    private double runningIntegral;

    /**
     * Used to tune pid
     * @param kp
     * @param ti
     * @param td
     */
    public void tune(double kp, double ti, double td) {
        runningIntegral = 0.0;
        this.kp = kp;
        this.ti = ti;
        this.td = td;
    }
}