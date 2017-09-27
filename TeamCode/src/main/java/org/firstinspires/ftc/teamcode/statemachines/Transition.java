package org.firstinspires.ftc.teamcode.statemachines;

/**
 * Created by Derek on 9/18/17.
 */

public abstract class Transition /*extends StateMachineComponent*/{
    protected State source;
    protected String destination;
    private boolean initialized = false;

    public Transition(String destination) {
        this.destination = destination;
    }

    public void setSource(State source) {
        this.source = source;
        this.initialized = true;
    }

    public final String getSourceName() {
        return source.getName();
    }

    public final State getSource() {
        return source;
    }

    public final String getDestination() {
        return destination;
    }

    public boolean check() {
        if(initialized) {
            return test();
        }
        else throw new RuntimeException("Transition from "+source.getName()+" to "+destination+" is being checked before it's initialized");
    }

    abstract protected boolean test();
}
