package core;

import com.sun.corba.se.spi.activation._ActivatorImplBase;

import java.util.ArrayList;

/**
 * Created by Денис on 17.03.14.
 */
public class Transition {

    public static String INCOMING_TOKEN_REMOVE = "INCOMING_TOKEN_REMOVE";
    public static String INCOMING_TOKEN_KEEP = "INCOMING_TOKEN_KEEP";

    private ArrayList<IncomingTransitionRule> _incomingTransitionRules;
    private ArrayList<OutgoingTransitionRule> _outgoingTransitionRules;

    public boolean canBeActivated(){

        for (IncomingTransitionRule a : _incomingTransitionRules){
            if (!a.canStart())
                return false;
        }

        return true;
    }

    private String _name;
    public void SetName(String name){
        _name = name;
    }

    public Transition(){
        _incomingTransitionRules = new ArrayList<IncomingTransitionRule>();
        _outgoingTransitionRules = new ArrayList<OutgoingTransitionRule>();
    }

    public void AddIncomingTransitionRule(IncomingTransitionRule rule){
        _incomingTransitionRules.add(rule);
    }
    public void AddOutgoingTransitionRule(OutgoingTransitionRule rule){
        _outgoingTransitionRules.add(rule);
    }



    private boolean _active = false;
    public void Activate(){
        _active = true;
    }
    public void Deactivate(){
        _active = false;
    }
    public boolean isActive(){
        return _active;
    }

    // indicates transaction was started
    private boolean _wasStarted = false;
    // process transaction
    public boolean Exec(){
        _wasStarted = true;

        // additional make sure we can start
        if (!canBeActivated()) return false;

        // make sure transaction was activated
        if (!isActive()) return false;

        // process rule(s)
        for (IncomingTransitionRule rule : _incomingTransitionRules)
            rule.Process();

        for (OutgoingTransitionRule rule : _outgoingTransitionRules)
            rule.Process();

        _active = false;
        _wasStarted = false;
        return true;
    }

}
