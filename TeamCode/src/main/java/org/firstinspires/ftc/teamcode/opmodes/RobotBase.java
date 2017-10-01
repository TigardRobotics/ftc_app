package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;

import org.firstinspires.ftc.teamcode.controllers.HardwareController;
import org.firstinspires.ftc.teamcode.controllers.IColorIndicator;
import org.firstinspires.ftc.teamcode.controllers.LedController;
import org.firstinspires.ftc.teamcode.controllers.SensorModule;
import org.firstinspires.ftc.teamcode.controllers.TankDrive;
import org.firstinspires.ftc.teamcode.statemachines.State;

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

    //!TODO: Integrate Drive and Leds into HardwareControllers List
    public TankDrive Drive;
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
        HardwareController.Robot = this;
        State.Robot = this;
        io = hardwareMap.deviceInterfaceModule.get("Device Interface Module 1");
        Leds = new LedController(io, LedIoMap);
        leftDriveMotor = hardwareMap.dcMotor.get("motor_l");
        rightDriveMotor = hardwareMap.dcMotor.get("motor_r");
        Drive = new TankDrive(rightDriveMotor,leftDriveMotor);
        telemetry.addLine("Basic Hardware Initialized");
    }

    @Override
    public void stop() {
        Drive.stopDriveMotors();
    }
    public SensorModule Sensors() {
        throw new RuntimeException("Sensor Module does not Exist");
    }
}
