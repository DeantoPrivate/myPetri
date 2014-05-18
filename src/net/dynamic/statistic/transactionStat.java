package net.dynamic.statistic;

import core.Transition;

import javax.swing.*;
import java.awt.*;

/**
 * Created by deanto on 18.05.14.
 */
public class transactionStat extends JPanel implements CountActionListener {

    private Transition _transaction;
    public void SetAssociatedTransaction(Transition transition){
        _transaction = transition;
    }

    public String getTransactionName(){return _transaction.getName();}

    private int WorkCount = 0;
    public int getWorkCount(){return WorkCount;}
    public void FireCountEventFromElement(){
        WorkCount ++;
    }

    @Override
    public String toString() {

        String answer = "";
        if (_transaction!=null)
            answer = "$: Переход [" + _transaction.getName()+"] сработал " + WorkCount + " раз.";
        else return "нет статистики";

        return answer;
    }
    private JLabel text;
    private transactionStat(){
        super();
        text = new JLabel(toString());
        add(text);
    }

    @Override
    public void paint(Graphics g) {
        text.setText(toString());
        super.paint(g);
    }
}
