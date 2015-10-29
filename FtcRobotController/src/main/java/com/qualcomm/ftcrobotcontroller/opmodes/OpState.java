/*
 * State concept for supporting Autonomous Control
 * Created by Mark on 10/27/2015.
 */
package com.qualcomm.ftcrobotcontroller.opmodes;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

/**
 * Operation State base class
 */
public abstract class OpState {

    private static OpState CurrentState = null;

    public static void SetCurrentState( String state_name ){
        OpState state = GetOpState(state_name);
        if ( CurrentState != null) CurrentState.OnExit();
        CurrentState = state;
        CurrentState.OnEntry();
    }

    public static OpState GetCurrentState(){
        return CurrentState;
    }

    public static void DoCurrentState(){
        CurrentState.Do();
    }

    private static Map<String, OpState> StateList = new HashMap<String, OpState>();

    /**
     * Get an OpState by name
     * @param name Name of the OpState
     * @return OpState
     */
    public static OpState GetOpState(String name){
        return StateList.get(name);
    }

    public String Name;

    /**
     * Constructor
     * Adds this OpState to the StateList
     * @param name Name of the OpState
     */
    public OpState(String name){
        Name = name;
        StateList.put(Name, this);
    }

    /**
     * Implement a finalize to remove this OpState from the StateList
     * @throws Throwable
     */
    protected void finalize() throws Throwable {
        StateList.remove(Name);
        super.finalize();
    }

    /**
     * Called on the entry to this state
     * This can be overridden to implement something you only want to do when you enter the state
     */
    protected void OnEntry(){}

    /**
     * Called to perform control when in this state
     * This must be overridden to implement what is done in this state
     */
    protected abstract void Do();

    /**
     * Called on exit of this state
     * This can be overridden to implement something you only want to do when you exit the state
     */
    protected void OnExit(){}
}
