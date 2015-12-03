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
 * This wraps the HashMap to collect additional log data
 */
class StateMap
{
    private Map<String, OpState> StateList;

    /**
     * Constructor
      */
    public StateMap(){
        Create();
    }

    /**
     * Create the Map
     */
    private void Create(){
        StateList = new HashMap<String, OpState>();
        DbgLog.msg("Created StateMap");
    }

    /**
     * Get an OpState from the Map
     * @param name State name
     * @return OpState
     */
    public OpState get(String name) {
        if (name == null || StateList == null) return null;
        OpState state = StateList.get(name);
        if (state == null) {
            DbgLog.msg("Could not find OpState'" + name + "'");
            DbgLog.msg("StateMap has " + StateList.size() + " members");
            if (StateList.size() != 0) {
                DbgLog.msg("StateMap:" + StateList.keySet().toArray().toString());
            }
        } else if (state.Name != name) {
            DbgLog.msg("OpState'" + state.Name + "' does not match'" + name + "'");
            DbgLog.msg("StateMap has " + StateList.size() + " members");
            if (StateList.size() != 0) {
                DbgLog.msg("StateMap:" + StateList.keySet().toArray().toString());
            }
        }
        else {
            DbgLog.msg("Got '" + name + "' from StateMap");
        }
        return state;
    }

    /**
     * Add an entry to the StateList
     * @param name State Name
     * @param state OpState
     */
    public void put(String name, OpState state) {
        if (StateList==null) Create();
        StateList.put(name, state);
        DbgLog.msg("Added '"+name+"' to StateMap");
    }

    /**
     * Remove an entry to the StateList
     * @param name
     */
    public void remove(String name) {
        if (StateList==null) Create();
        StateList.remove(name);
        DbgLog.msg("Removed '"+name+"' from StateMap");
    }

    /**
     * List has been Garbage Collected
     * @throws Throwable
     */
    protected void finalize() throws Throwable {
        DbgLog.msg("Removed StateMap");
        super.finalize();
    }

    /**
     * Clear the StateList
     */
    public void clear() {
        DbgLog.msg("Clearing all States");
        if (StateList != null) StateList.clear();
    }
}

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
        else {
            DbgLog.msg("No OpState to Do");
        }
    }

    /**
     * Dictionary for all the states that have been contructed
     */
    private static StateMap StateList = new StateMap();

    /**
     * Get an OpState by name
     * @param name Name of the OpState
     * @return OpState
     */
    private final static OpState GetOpState(String name){
        OpState state = StateList.get(name);
        return state;
    }

    /**
     * Set the Current State to null and remove all the States from the State Dictionary
     */
    public final static void ClearAllStates() {
        DbgLog.msg("Clearing all States");
        SetCurrentState(null);
        if (StateList != null) StateList.clear();
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
    public OpState(String name) {
        Name = name;
        StateList.put(Name, this);
        DbgLog.msg("Created OpState '" + Name + "'");
    }

    /**
     * Called on the entry to this state
     * This can be overridden to implement something you only want to do when you enter the state
     */
    protected void OnEntry() {
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
        DbgLog.msg("Exiting OpState '" + Name + "'");
    }
}
