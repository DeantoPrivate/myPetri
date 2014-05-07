package net.staticNet;

import components.GraphicalElements.GElement;
import components.GraphicalElements.TransactionElement;
import core.Transition;

/**
 * Created by deanto on 07.05.14.
 */
public class TransactionWrap {
    private Transition _transaction;
    private TransactionElement _element;

    public TransactionWrap(Transition transaction, TransactionElement gTransaction){
        _transaction = transaction;
        _element = gTransaction;
    }

    public Transition get_transaction(){
        return _transaction;
    }

    public GElement get_element(){
        return _element;
    }
}
