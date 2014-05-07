package net.staticNet;

import components.GraphicalElements.GElement;
import components.GraphicalElements.TransactionRuleElement;
import core.TransitionRule;

/**
 * Created by deanto on 07.05.14.
 */
public class TransactionRuleWrap {
    private TransitionRule _rule;
    private TransactionRuleElement _element;

    public TransactionRuleWrap(TransitionRule rule, TransactionRuleElement gRule){
        _rule = rule;
        _element = gRule;
    }

    public TransitionRule get_rule(){
        return _rule;
    }

    public GElement get_element(){
        return _element;
    }
}
