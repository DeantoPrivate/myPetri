package net.dynamic.analyze;

/**
 * Created by deanto on 20.05.14.
 */
public class ChangeOneTransitionWorking implements ChangeOne{
    public String TransitionName;
    public boolean notWork;
    public int stepL,stepR;

    @Override
    public String getString() {
        StringBuilder answer = new StringBuilder();
        answer.append("переход "+ TransitionName);
        if (notWork)
            answer.append(" не работает с "+stepL +"шага по "+ stepR + "шаг");
        else answer.append(" работает всегда");

        return answer.toString();
    }
}
