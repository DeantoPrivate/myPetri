package net.staticNet;

import com.sun.corba.se.spi.activation._InitialNameServiceImplBase;
import components.GraphicalElements.GElement;
import components.GraphicalElements.StateElement;
import components.GraphicalElements.TransactionElement;
import components.GraphicalElements.TransactionRuleElement;
import core.State;
import core.Transition;
import core.TransitionRule;

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
    }

    public void addState(State state, StateElement gState){
        _states.add(new StateWrap(state,gState));
    }

    public void addTransactionRule(TransitionRule rule, TransactionRuleElement gTransactionRule){
        _trasactionRules.add(new TransactionRuleWrap(rule,gTransactionRule));
    }

    public void addTransaction(Transition transaction, TransactionElement gTransaction){
        _transactions.add(new TransactionWrap(transaction,gTransaction));
    }

    

}
