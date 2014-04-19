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
        _rule = rule;
    }

    public boolean canBeActivated(){

        if (_rule.canStart(_incomingState)){
            return true;
        }

        return false;
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
        if (_rule instanceof TransitionRules){
            for (int i=0;i<((TransitionRules) _rule)._rules.size();i++)
                processTransitionRule(((TransitionRules) _rule)._rules.get(i));

            // TODO this is potential place for errors. we change states while working with rules... need to check
        }
        if (_rule instanceof  TransitionRule){
            processTransitionRule(_rule);
        }

        return true;
    }

    private void processTransitionRule(TransitionRule rule){
        // find token related to this rule
        Token token = rule.specifyToken(_incomingState);
        boolean delete = false;
        if (rule._actionIncomingToken.equals(TransitionRule.INCOMING_TOKEN_REMOVE) || rule._actionIncomingToken.equals(TransitionRule.INCOMING_TOKEN_TRANSFER))
            delete = true;

        if (delete)
            _incomingState.TokenGone(token);

        boolean copy = false;
        if (rule._actionIncomingToken.equals(TransitionRule.INCOMING_TOKEN_TRANSFER) || rule._actionIncomingToken.equals(TransitionRule.INCOMING_TOKEN_KEEP_TRANSFER))
            copy = true;

        if (copy)
            _outgoingState.LocateToken(token);

        Token outgoing = _rule.get_outgoingToken();
        if (outgoing!=null)
            _outgoingState.LocateToken(outgoing);
    }

    // revert back
    public boolean Revert(){
        if (!_wasStarted) return true;

        return false;
    }
}
