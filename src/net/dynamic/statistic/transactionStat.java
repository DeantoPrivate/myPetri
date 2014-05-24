package net.dynamic.statistic;

import core.Transition;
import net.staticNet.netStaticImpl;

import javax.swing.*;
import java.awt.*;

/**
 * Created by deanto on 18.05.14.
 */
public class transactionStat extends JPanel implements CountActionListener {

    public void Clear(){
        WorkCount = 0;
        Reassign();
        text.setText(toString());
    }

    public void Reassign(){
        Transition Newtransaction = netStaticImpl.getNet().getTransition(_transaction.getName());
        _transaction = Newtransaction;
        _transaction.AssignTransactionStat(this);
    }

    private Transition _transaction = null;
    public void SetAssociatedTransaction(Transition transition){
        _transaction = transition;
    }

    public String getTransactionName(){return _transaction.getName();}

    private int WorkCount = 0;
    public int getWorkCount(){return WorkCount;}
    public void FireCountEventFromElement(){
        WorkCount ++;
        text.setText(toString());
    }

    public boolean Consctuct(){
        String tName = "";
        while (tName.equals(""))
            tName = JOptionPane.showInputDialog("Введите имя транзакции","");

        int criticallly = -1;
        while(criticallly==-1) {
            String c = JOptionPane.showInputDialog("Критическое количество срабатывания перехода", "1");
            int col = 0;
            if (c != null)
                try {
                    Integer a = new Integer(c.toString());
                    if (a != null) {
                        col = a;

                        critical = col;
                        criticallly = col;
                    }
                } catch (Exception e) {

                }

        }


        _transaction = netStaticImpl.getNet().getTransition(tName);
        if (_transaction == null){
            JOptionPane.showMessageDialog(null,"нет такого!");
        return false;
        }
        text.setText(toString());
        _transaction.AssignTransactionStat(this);
        return true;
    }


    private int critical = 0;

    @Override
    public String toString() {

        String answer = "";
        if (_transaction!=null) {
            answer = "$: Переход [" + _transaction.getName() + "] сработал " + WorkCount + " раз.";
            if (WorkCount>critical)
                answer+=" !Критично.";
        }
        else return "нет статистики";

        return answer;
    }

    public static transactionStat create(){
        return new transactionStat();
    }

    private JLabel text;
    private transactionStat(){
        super();

        setLayout(null);
        text = new JLabel(toString());
        text.setBounds(0,0,1000,20);
        add(text);

    }

}
