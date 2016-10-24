package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchImpl;

/**
 * Created by Derek Williams of team 3965 on 10/23/2016.
 */

public class ModernRoboticsSensorModule extends SensorModule {
    protected RobotBase robot;
    protected I2cDevice frontRangeSensor;
    protected I2cDeviceSynch frontRangeReader;
    protected byte[] frontRangeCache;
    protected I2cAddr frontRangeAddress = new I2cAddr(0x14);
    protected static final int FRONT_RANGE_REG_START = 0x04;
    protected static final int FRONT_RANGE_READ_LENGTH = 2;

    ModernRoboticsSensorModule(RobotBase robot) {
        this.robot = robot;
        frontRangeSensor = robot.hardwareMap.i2cDevice.get("front_range");
        frontRangeReader = new I2cDeviceSynchImpl(frontRangeSensor, frontRangeAddress, false);
        frontRangeReader.engage();
    }

    @Override
    public double getOdsRange() {
        frontRangeCache = frontRangeReader.read(FRONT_RANGE_REG_START, FRONT_RANGE_READ_LENGTH);
        return frontRangeCache[1] & 0xFF;
    }

    @Override
    public double getUsRange() {
        frontRangeCache = frontRangeReader.read(FRONT_RANGE_REG_START, FRONT_RANGE_READ_LENGTH);
        return frontRangeCache[0] & 0xFF;
    }
}
