package org.firstinspires.ftc.teamcode.VV_2016;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.opmodes.RobotBase;
//import org.firstinspires.ftc.teamcode.StateMachine;

/**
 * Created by Derek Williams of team 3965 on 10/11/2016.
 */

public abstract class VelocityVortexRobotBase extends RobotBase {
    //private ModernRoboticsSensorModule sensorModule = new ModernRoboticsSensorModule(this);
    //protected StateMachine stateMachine = new StateMachine(this);

    protected static final double FULL_TURN_ROTATION = 4843; // Encoder counts
    protected static final double COUNTS_PER_DEGREE = FULL_TURN_ROTATION/360.0;
    private static final double COUNTS_PER_ROTATION = 1440.0;
    private static final double CENTIMETERS_PER_ROTATION = 31.928;
    protected static final double COUNTS_PER_CENTIMETER = COUNTS_PER_ROTATION/CENTIMETERS_PER_ROTATION;

    protected double driveSpeed = 0.50;
    protected double gyroDriveSpeed = 0.54;
    protected double turnSpeed = 0.25;
    protected double followSpeed = 0.25;

    protected double rangeToBeacon = 5.0; // was 4.0 on 2/17

    // All hardware custom to velocity vortex defined here
    protected Servo rightButtonPusher;
    protected Servo leftButtonPusher;

    protected DcMotor particleFlicker;
    protected DcMotor particleLifter;
    protected DcMotor particleCollector;

    // Hardware constants
    final static double RIGHT_BUTTON_PUSHER_EXTENDED = 0.563;
    final static double RIGHT_BUTTON_PUSHER_RETRACTED = 0.226;
    final static double LEFT_BUTTON_PUSHER_EXTENDED = 0.471;
    final static double LEFT_BUTTON_PUSHER_RETRACTED = 0.797;
    final static double PARTICLE_FLICKER_SPEED = 0.7;
    final static double PARTICLE_LIFTER_SPEED = -1.0;
    final static double PARTICLE_COLLECTOR_SPEED = 0.8;

    @Override
    public void init() {
        super.init();
        //sensorModule.init();
        rightButtonPusher = hardwareMap.servo.get("right_button_pusher");
        leftButtonPusher = hardwareMap.servo.get("left_button_pusher");
        particleFlicker = hardwareMap.dcMotor.get("particle_flicker");
        particleLifter = hardwareMap.dcMotor.get("particle_lifter");
        particleCollector = hardwareMap.dcMotor.get("particle_collector");
        telemetry.addLine("Velocity Vortex Base Initialized");

        retractLeftPusher();
        retractRightPusher();

        Drive.setCountsPerCentimeter(COUNTS_PER_CENTIMETER);
        Drive.setCountsPerDegree(COUNTS_PER_DEGREE);
    }

    @Override
    public void init_loop() {
        //sensorModule.init_loop();
    }

    public void extendRightPusher() {
        rightButtonPusher.setPosition(RIGHT_BUTTON_PUSHER_EXTENDED);
        telemetry.addLine("Extending Right Button Pusher");
    }

    public void retractRightPusher() {
        rightButtonPusher.setPosition(RIGHT_BUTTON_PUSHER_RETRACTED);
        telemetry.addLine("Retracting Right Button Pusher");
    }

    public void extendLeftPusher() {
        leftButtonPusher.setPosition(LEFT_BUTTON_PUSHER_EXTENDED);
        telemetry.addLine("Extending Left Button Pusher");
    }

    public void retractLeftPusher() {
        leftButtonPusher.setPosition(LEFT_BUTTON_PUSHER_RETRACTED);
        telemetry.addLine("Retracting Left Button Pusher");
    }

    public void enableFlicker() {
        particleFlicker.setPower(PARTICLE_FLICKER_SPEED);
    }

    public void disableFlicker() {
        particleFlicker.setPower(0.0);
    }

    public void enableLifter() {
        particleLifter.setPower(PARTICLE_LIFTER_SPEED);
    }

    public void disableLifter() {
        particleLifter.setPower(0.0);
    }

    public void enableCollector() {
        particleCollector.setPower(PARTICLE_COLLECTOR_SPEED);
    }

    /*public void reverseEnableCollector() {
        particleCollector.setPower(-PARTICLE_COLLECTOR_SPEED);
    }*/

    public void disableCollector() {
        particleCollector.setPower(0.0);
    }

    protected double rotsToEnc (double rots) {
        return rots*FULL_TURN_ROTATION;
    }

    protected double cmToEnc(double dist) {
        return dist*COUNTS_PER_CENTIMETER;
    }

    public void setSquareLeftDrivePower(double power) {
        Drive.setLeftDrivePower(power*Math.abs(power));
    }

    public void setSquareRightDrivePower(double power) {
        Drive.setRightDrivePower(power*Math.abs(power));
    }


    /**
     * Created by Derek Williams of team 3965 on 12/13/2016.
     */

    public static class ButtonPusher {
        private Servo servo;
        private String side;
        private boolean isExtended;

        // Positions
        private final static double RIGHT_BUTTON_PUSHER_EXTENDED = 0.99;
        private final static double RIGHT_BUTTON_PUSHER_RETRACTED = 0.6;
        private final static double LEFT_BUTTON_PUSHER_EXTENDED = 0.6;
        private final static double LEFT_BUTTON_PUSHER_RETRACTED = 0.98;

        // Sides
        public static final String RIGHT = "RIGHT";
        public static final String LEFT = "LEFT";

        public ButtonPusher(Servo servo, String side) {
            this.side = side;
            this.servo = servo;

            if (this.side != LEFT && this.side != RIGHT) {
                throw new RuntimeException("Button Pusher side not initiated correctly");
            }
        }

        public void extend() {
            isExtended = true;
            if (side == RIGHT) {
                servo.setPosition(RIGHT_BUTTON_PUSHER_EXTENDED);
            }
            else if (side == LEFT) {
                servo.setPosition(LEFT_BUTTON_PUSHER_EXTENDED);
            }
            else {
                throw new RuntimeException("Button Pusher side not initiated correctly");
            }
        }

        public void retract() {
            isExtended = false;
            if (side == RIGHT) {
                servo.setPosition(RIGHT_BUTTON_PUSHER_RETRACTED);
            }
            else if (side == LEFT) {
                servo.setPosition(LEFT_BUTTON_PUSHER_RETRACTED);
            }
            else {
                throw new RuntimeException("Button Pusher side not initiated correctly");
            }
        }

        public void toggle() {
            if(isExtended) {
                retract();
            }
            else {
                extend();
            }
        }
    }
}
