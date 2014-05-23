package net.dynamic.analyze;

/**
 * Created by deanto on 20.05.14.
 */
public class ChangeOneRule implements ChangeOne{
    public String TransitionName,RuleString;
    public int param;

    @Override
    public String getString() {

        return "параметр для правила "+RuleString + " в переходе " +TransitionName +" = "+param;
    }
}
