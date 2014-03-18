package core;

/**
 * Created by deanto on 18.03.14.
 *
 * the rool for transition when it can start
 */
public class TransitionRule {

    // we make patterns for states. if incoming state is the same as incoming pattern - the transition can start
    // pattern contains tokens (token patterns) - copy of original token or it's part. so we will compare token
    // and pattern - if all patterns values presents in token - we say out token is like our pattern

    private State _incomingStatePattern, _outgoingStatePattern;

    private boolean _completed;
    public boolean isCompleted(){
        return _completed;
    }
    public boolean canStart(Transition transition){

        // here, we look at incoming state and try to compare it with our incoming state pattern

        return false;
    }
}
