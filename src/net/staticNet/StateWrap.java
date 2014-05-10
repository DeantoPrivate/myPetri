package net.staticNet;

import components.GraphicalElements.GElement;
import components.GraphicalElements.StateElement;
import components.GraphicalElements.StateElementInfo;
import core.State;

/**
 * Created by deanto on 07.05.14.
 */
public class StateWrap {
    private State _state;
    private StateElement _element;
    private StateElementInfo _stateInfo;
    public StateWrap(State state, StateElement gState){
        _state = state;
        _element = gState;
        _stateInfo = new StateElementInfo();
        _stateInfo.SetTokens(_state.GetTokens().size());
        _element.SetInfo(_stateInfo);
    }

    public State get_state(){
        return _state;
    }

    public GElement get_element(){
        return _element;
    }

    public void UpdateUI(){
        _stateInfo.SetTokens(_state.GetTokens().size());
        _element.Drow();
    }
}
