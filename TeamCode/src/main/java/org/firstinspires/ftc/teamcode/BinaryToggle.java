package org.firstinspires.ftc.teamcode;

/**
 * Created by Robotics on 2/23/2018.
 */

public class BinaryToggle {
    private boolean state;
    private boolean previousUpdate;

    public BinaryToggle() {
        state = false;
        previousUpdate = false;
    }

    public boolean get() {
        return state;
    }

    public void set(boolean state) {
        this.state = state;
        this.previousUpdate = false;
    }

    public void set(boolean state, boolean condition) {
        if(condition) {
            set(state);
        }
    }

    public boolean toggle() {
        state = !state;
        return state;
    }

    public boolean update(boolean value) {
        if(!previousUpdate && value) {
            toggle();
        }
        previousUpdate = value;
        return get();
    }
}
