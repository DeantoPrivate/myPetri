package net.staticNet;

import components.GraphicalElements.GElement;
import components.GraphicalElements.TransactionElement;
import components.GraphicalElements.TransactionElementInfo;
import core.Transition;

/**
 * Created by deanto on 07.05.14.
 */
public class TransactionWrap {
    private Transition _transaction;
    private TransactionElement _element;
    private TransactionElementInfo _info;

    public TransactionWrap(Transition transaction, TransactionElement gTransaction){
        _transaction = transaction;
        _element = gTransaction;
        _info = new TransactionElementInfo();
    }

    public Transition get_transaction(){
        return _transaction;
    }

    public GElement get_element(){
        return _element;
    }

    public void UpdateUI(){
        _info.SetActivated();
        _element.setInfo(_info);
        _element.Drow();
    }
}
