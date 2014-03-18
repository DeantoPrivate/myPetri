package core;

/**
 * Created by Денис on 17.03.14.
 */
public class Transition {
    private State _incomingState,_outgoingState;
    private TransitionRule _rule;

    public void buildTransition(State incomingState, State outgoingState){
        _incomingState = incomingState;
        _outgoingState = outgoingState;
    }

    public void assignTransitionRule(TransitionRule rule){
        _rule = _rule;
    }


}
