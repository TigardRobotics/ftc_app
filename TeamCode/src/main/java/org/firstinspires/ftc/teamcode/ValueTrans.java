package org.firstinspires.ftc.teamcode;

/**
 * Created by Derek Williams of team 3965 on 11/16/2016.
 */

public class ValueTrans extends Transition {
    public boolean value = false;

    ValueTrans(String toStateName, String fromStateName){
        this.destination = toStateName;
        this.source = fromStateName;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public boolean test() {
        return value;
    }
}
