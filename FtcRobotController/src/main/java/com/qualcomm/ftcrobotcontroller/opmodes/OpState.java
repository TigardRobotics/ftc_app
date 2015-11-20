/*
 * State concept for supporting Autonomous Control
 * Created by Mark on 10/27/2015.
 */
package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftccommon.DbgLog;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

/**
 * Operation State base class
 */
public abstract class OpState {

    /**
     * The state the is currently active
     */
    private static OpState CurrentState = null;

    /**
     * Change the the specified state
     * This will call OnExit of the previous state the OnEntry of the selected state
     * @param state_name Name of the new State
     */
    public final static void SetCurrentState( String state_name ){
        OpState state = GetOpState(state_name);
        if ( CurrentState != null) CurrentState.OnExit();
        CurrentState = state;
        if ( CurrentState != null) CurrentState.OnEntry();
    }

    /**
     * Gets the Name of the Current State
     * @return Name of the Current State
     */
    public final static String GetCurrentState(){
        if ( CurrentState != null) {
            return CurrentState.Name;
        }
        else{
            return null;
        }
    }

    /**
     * Run the Do method for the current state
     */
    public final static void DoCurrentState() {
        if ( CurrentState != null) {
            DbgLog.msg("Doing OpState '" + CurrentState.Name + "'");
            CurrentState.Do();
        }
    }

    /**
     * Dictionary for all the states that have been contructed
     */
    private static Map<String, OpState> StateList = new HashMap<String, OpState>();

    /**
     * Get an OpState by name
     * @param name Name of the OpState
     * @return OpState
     */
    private final static OpState GetOpState(String name){
        return StateList.get(name);
    }

    /**
     * Set the Current State to null and remove all the States from the State Dictionary
     */
    public final static void ClearAllStates() {
        SetCurrentState(null);
        StateList.clear();
    }

    /**
     * State Name
     */
    public final String Name;

    /**
     * Constructor
     * Adds this OpState to the StateList
     * @param name Name of the OpState
     */
    public OpState(String name){
        Name = name;
        StateList.put(Name, this);
        DbgLog.msg("Created OpState '" + Name + "'");
    }

    /**
     * Implement a finalize to remove this OpState from the StateList
     * @throws Throwable
     */
    protected void finalize() throws Throwable {
        StateList.remove(Name);
        DbgLog.msg("Removed OpState '" + Name + "'");
        super.finalize();
    }

    /**
     * Called on the entry to this state
     * This can be overridden to implement something you only want to do when you enter the state
     */
    protected void OnEntry(){
        DbgLog.msg("Entering OpState '"+Name+"'");
    }

    /**
     * Called to perform control when in this state
     * This must be overridden to implement what is done in this state
     */
    protected abstract void Do();

    /**
     * Called on exit of this state
     * This can be overridden to implement something you only want to do when you exit the state
     */
    protected void OnExit(){
        DbgLog.msg("Exiting OpState '"+Name+"'");
    }
}
