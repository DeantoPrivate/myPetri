package net.dynamic.analyze;

/**
 * Created by deanto on 20.05.14.
 */
public class ChangeOneTransitionDelay implements ChangeOne{
    public String TransitionName;
    public int delay;

    @Override
    public String getString() {
        return "задержка перехода "+TransitionName + " = "+ delay;
    }
}
