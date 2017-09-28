package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by Derek Williams of team 3965 on 10/9/2016.
 */

public abstract class RobotBase extends OpMode {
    public static final String RED = "red";
    public static final String BLUE = "blue";
    public static final String NO_COLOR = "NONE";


    private DcMotor leftDriveMotor;
    private DcMotor rightDriveMotor;
    private DeviceInterfaceModule io;

    public IColorIndicator Leds;
    private static Map<String, Byte> LedIoMap = new HashMap<String, Byte>(){{
        put(LedController.ALL_COLORS, (byte)0x1F);
        put(LedController.NO_COLOR, (byte)0x00);
        put(LedController.RED, (byte)0x01);   //D0
        put(LedController.WHITE, (byte)0x02); //D1
        put(LedController.BLUE, (byte)0x04);  //D2
        put(LedController.GREEN, (byte)0x08); //D3
        put(LedController.YELLOW, (byte)0x10);//D4
    }};

    @Override
    public void init() {
        io = hardwareMap.deviceInterfaceModule.get("Device Interface Module 1");
        Leds = new LedController(this, io, LedIoMap);
        telemetry.addLine("Basic Hardware Initialized");
        leftDriveMotor = hardwareMap.dcMotor.get("motor_l");
        rightDriveMotor = hardwareMap.dcMotor.get("motor_r");
        leftDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void stop() {
        stopDriveMotors();
    }

    public void setDrivePower(double power){
        leftDriveMotor.setPower(power);
        rightDriveMotor.setPower(-power);
    }

    public void stopDriveMotors(){
        setDrivePower(0.0);
    }

    public void setLeftDrivePower(double power){
        RobotLog.i(String.format("Enabling Left Motor w/ speed: %f", power));
        telemetry.addLine(String.format("Enabling Left Motor w/ speed: %f", power));
        leftDriveMotor.setPower(power);
    }

    public void setRightDrivePower(double power){
        RobotLog.i(String.format("Enabling Right Motor w/ speed: %f", power));
        telemetry.addLine(String.format("Enabling Right Motor w/ speed: %f", power));
        rightDriveMotor.setPower(-power);
    }

    public void setSquareLeftDrivePower(double power) {
        setLeftDrivePower(power*Math.abs(power));
    }

    public void setSquareRightDrivePower(double power) {
        setRightDrivePower(power*Math.abs(power));
    }

    public double getDrivePosition() {
        return Math.max(leftDriveMotor.getCurrentPosition(), rightDriveMotor.getCurrentPosition());
    }

    public double countsToCentimeters(double counts) {
        return 0.0;
    }

    protected double getDriveMotorDiameter() {
        return 0.0;
    }

    protected double getFullRotation() {
        return 0.0;
    }

    public double getTurnCircumference(){
        return Math.PI*getDriveMotorDiameter();
    }

    public SensorModule Sensors() {
        throw new RuntimeException("Sensor Module does not Exist");
    }
}
