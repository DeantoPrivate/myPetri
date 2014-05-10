package net.staticNet;

import components.GraphicalElements.StateElement;
import components.GraphicalElements.TransactionElement;
import components.GraphicalElements.TransactionRuleElement;
import core.*;

import java.util.ArrayList;

/**
 * Created by deanto on 07.05.14.
 *
 * This class describes Petri net using core elements and graphical elements
 *
 *
 */
public class netStaticImpl {

    private ArrayList<StateWrap> _states;
    private ArrayList<TransactionRuleWrap> _trasactionRules;
    private ArrayList<TransactionWrap> _transactions;
    private ArrayList<Token> _tokens;

    private static netStaticImpl _instance;
    public static netStaticImpl getNet(){
        if (_instance == null)
            _instance = new netStaticImpl();

        return _instance;
    }

    private netStaticImpl(){
        _states = new ArrayList<StateWrap>();
        _transactions = new ArrayList<TransactionWrap>();
        _trasactionRules = new ArrayList<TransactionRuleWrap>();
        _tokens = new ArrayList<Token>();
    }

    public void addState(State state, StateElement gState){
        _states.add(new StateWrap(state,gState));
        for (int i=0;i<state.GetTokens().size();i++)
            _tokens.add(state.GetTokens().get(i));
    }

    public State getState(StateElement gState){
        for (int i=0;i<_states.size();i++)
            if (_states.get(i).get_element() == gState)
                return _states.get(i).get_state();
        return null;
    }

    public Transition getTransition(TransactionElement gTransaction){
        for (int i=0;i<_transactions.size();i++)
            if (_transactions.get(i).get_element() == gTransaction)
                return _transactions.get(i).get_transaction();

        return null;
    }

    public void addTransactionRule(TransitionRule rule, TransactionRuleElement gTransactionRule){
        _trasactionRules.add(new TransactionRuleWrap(rule,gTransactionRule));

        if (rule instanceof OutgoingTransitionRule){
            getTransition((TransactionElement)gTransactionRule.getElement(0)).AddOutgoingTransitionRule((OutgoingTransitionRule)rule);
        } else{
            getTransition((TransactionElement)gTransactionRule.getElement(1)).AddIncomingTransitionRule((IncomingTransitionRule) rule);
        }
    }

    public void addTransaction(Transition transaction, TransactionElement gTransaction){
        _transactions.add(new TransactionWrap(transaction,gTransaction));
    }


    // далее часть для работы с динамической частью

    public ArrayList<Transition> getTransitionsWhichCanBeActivated(){

        ArrayList<Transition> answer = new ArrayList<Transition>();

        for (TransactionWrap a : _transactions){
            Transition t = a.get_transaction();
            if (t.canBeActivated()){
                answer.add(t);
            }
        }

        return answer;
    }

    //TODO это уберем когда реализуем экшн лисонеров объект - его wrap - графическая часть
    public void RepaintNet(){
        for (StateWrap sw : _states){
            sw.get_element().Drow();
        }

        for (TransactionWrap tw : _transactions){
            tw.get_element().Drow();
        }

    }

}
