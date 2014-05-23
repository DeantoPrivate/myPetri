package net.dynamic.analyze;

import core.State;
import core.Token;

/**
 * Created by deanto on 20.05.14.
 */
public class ChangeOneState implements ChangeOne{
    public String State,Token;
    public boolean loose;
    public boolean appearance;
    public int count;
    public int step;

    @Override
    public String getString() {
        StringBuilder answer = new StringBuilder();
        answer.append("Состояние "+ State);
        if (loose)
            answer.append(", теряет ");
        if (appearance)
            answer.append(", приобретает ");

        answer.append(count + " токен(a) ["+ Token + "], каждые "+step+" шага.");
        return answer.toString();
    }
}
