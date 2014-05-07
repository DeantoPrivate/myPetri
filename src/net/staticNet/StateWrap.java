package net.staticNet;

import components.GraphicalElements.GElement;
import components.GraphicalElements.StateElement;
import core.State;

/**
 * Created by deanto on 07.05.14.
 */
public class StateWrap {
    private State _state;
    private StateElement _element;

    public StateWrap(State state, StateElement gState){
        _state = state;
        _element = gState;
    }

    public State get_state(){
        return _state;
    }

    public GElement get_element(){
        return _element;
    }
}
