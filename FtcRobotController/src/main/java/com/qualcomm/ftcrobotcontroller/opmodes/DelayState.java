package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * State that delays for a fixed time then moves to the specified state
 */
public class DelayState extends OpState {

    private OpMode opMode;
    private int DelayCounter = 0;
    private int DelayCount;
    private String NextStateName;

    /**
     * Constructor
     *
     * @param name       State Name
     * @param opmode     OpMode
     * @param count      Number of times to flash
     * @param next_state Next State Name
     */
    DelayState(String name, OpMode opmode, int count, String next_state) {
        super(name);
        opMode = opmode;
        DelayCount = count;
        NextStateName = next_state;
    }

    @Override
    public void OnEntry() {
        super.OnEntry();
        DelayCounter = 0; //reset the delay count
    }

    @Override
    public void Do() {
        DelayCounter++;
        opMode.telemetry.addData(Name, "Delaying");
        opMode.telemetry.addData(Name, "Count = " + String.format("%d", DelayCounter));
        if(DelayCounter>=DelayCount) SetCurrentState(NextStateName);
    }
}
