package core;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by deanto on 19.04.14.
 */

// contains some transition rules. but we work with is as the same as with one transiction rule;
public class TransitionRules extends TransitionRule {

    ArrayList<TransitionRule> _rules;

    @Override
    public void constructRule() {
        boolean oneMore = true;
        _rules = new ArrayList<TransitionRule>();
        while (oneMore){
            TransitionRule one = new TransitionRule();
            one.constructRule();
            if (one.isCompleted())
                _rules.add(one);

            if (JOptionPane.showOptionDialog(null, "Any More?", "Option", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, 1) != 1)
                oneMore = false;
        }

        if (_rules.size()!=0)
            _completed=true;
    }

    @Override
    public boolean canStart(State state) {

        for (int i=0;i<_rules.size();i++)
            if (_rules.get(i).canStart(state))
                return true;

        return false;

    }

    ArrayList<Token> getOutgoungTokens(){
        ArrayList<Token> answer = new ArrayList<Token>();
        for (int i=0;i<_rules.size();i++)
            if (_rules.get(i).get_outgoingToken()!=null)
                answer.add(_rules.get(i).get_outgoingToken());

        if (answer.size()==0)
            answer = null;

        return answer;

    }
}
