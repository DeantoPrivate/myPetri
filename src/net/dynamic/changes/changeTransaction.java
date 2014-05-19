package net.dynamic.changes;

import core.Transition;
import net.staticNet.netStaticImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.awt.event.ActionListener;


/**
 * Created by deanto on 19.05.14.
 */
public class changeTransaction extends JPanel {

    private Transition _transition;
    public void SetAssociatedState(Transition transaction){
        _transition = transaction;
        InputChangeInfoTransition();
    }

    public void SetAssociatedTransaction(Transition transition){
        _transition = transition;
        text.setText("Изменения для перехода ["+transition.getName()+"]");
    }
    public String getStateName(){return _transition.getName();}

    private JLabel text;
    private JButton newTransactionRule;

    private ArrayList<JPanel> transactionPanels;
    private ArrayList<Transition> _transactions;
    private TransactionChanges _transactionChanges;

    private int tPos = 100;



    private void InputChangeInfoTransition(){

        // посмотрим что за параметры и введем диапазоны для изменения
        int currentSleep = _transition.getSleepSteps();

        TransactionChanges transactionChanges = new TransactionChanges();
        transactionChanges.sleep = currentSleep;

        String count = JOptionPane.showInputDialog(this,"Текущая задержка - "+currentSleep+". Введите нижнюю границу");
        int col = 0;
        if (count!=null)
            try{
                Integer a = new Integer(count.toString());
                if (a!=null)
                    col=a;
            }catch (Exception e){
            }
        if (col <0 && col!=-9)
            col = 0;

        transactionChanges.sleepL=col;

        count = JOptionPane.showInputDialog(this,"Текущая задержка - "+currentSleep+". Введите верхнюю границу");
        col = currentSleep;
        if (count!=null)
            try{
                Integer a = new Integer(count.toString());
                if (a!=null)
                    col=a;
            }catch (Exception e){
            }
        if (col <currentSleep)
            col = currentSleep;

        transactionChanges.sleepR = col;


        String notWork = "";
        while(notWork.equals("")&&(!notWork.equals("да")&&!notWork.equals("нет")))
            notWork = JOptionPane.showInputDialog("Будет ли переход заблокирован","да / нет");

        if (notWork.equals("нет"))
            transactionChanges.notWork = false;

        if (notWork.equals("да")){
            transactionChanges.notWork = true;

            String c = JOptionPane.showInputDialog("С какого шага?","");
            int cc = 0;
            if (c!=null)
                try{
                    Integer a = new Integer(c.toString());
                    if (a!=null)
                        cc=a;
                }catch (Exception e){
                }

            transactionChanges.notWorkStepL = cc;

            c = JOptionPane.showInputDialog("По какой шаг?","");
            cc = 0;
            if (c!=null)
                try{
                    Integer a = new Integer(c.toString());
                    if (a!=null)
                        cc=a;
                }catch (Exception e){
                }

            transactionChanges.notWorkStepR = cc;
        }

        StringBuilder s = new StringBuilder();
        s.append("Переход #" + _transition.getName() +"#: параметры изменения - текущая задержка: " + transactionChanges.sleep
        + " диапазон изменения: ["+transactionChanges.sleepL+" , "+transactionChanges.sleepR+"]");

        if (transactionChanges.notWork){

        s.append(". Не работает с " + transactionChanges.notWorkStepL +" по "+transactionChanges.notWorkStepR +" шаги.");

        }

        text.setText(s.toString());
    }

    public changeTransaction(){
        super();
        setLayout(null);
        text = new JLabel("Изменения для перехода. Не сконфигурено.");
        text.setBounds(100,0,800,20);
        add(text);

        JButton Construct = new JButton("Новый");
        Construct.setBounds(0,0,100,20);
        Construct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String tName = "";
                while (tName.equals(""))
                    tName = JOptionPane.showInputDialog("Введите имя перехода","");

                Transition transaction = netStaticImpl.getNet().getTransition(tName);

                if (transaction == null) return;

                if (transactionPanels!=null)
                    for (JPanel p : transactionPanels)
                        remove(p);

                _transactions = new ArrayList<Transition>();
                _transactionChanges = new TransactionChanges();
                transactionPanels = new ArrayList<JPanel>();

                SetAssociatedState(transaction);


                newTransactionRule.setEnabled(true);


            }
        });
        add(Construct);


        newTransactionRule = new JButton("+");
        newTransactionRule.setBounds(0,20,50,20);
        newTransactionRule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // тут добавляем настройки для правила в переходе
                JPanel tokenPanel = null;
                if (tokenPanel!=null){
                    tokenPanel.setBounds(50,tPos,800,20);
                    tokenPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
                    tPos+=20;
                    transactionPanels.add(tokenPanel);
                    add(tokenPanel);
                    repaint();
                }
            }
        });
        add(newTransactionRule);

        newTransactionRule.setEnabled(false);


    }

    private class TransactionChanges{

        // задержка выполнения
        public int sleep = 0;
        public int sleepL= 0;
        public int sleepR= 0;


        // перерыв в работе
        public boolean notWork = false;
        public int notWorkStepL = 0;
        public int notWorkStepR = 0;

        public ArrayList<TransactionRuleChanges> _transactionRulesChanges;

    }

    private class TransactionRuleChanges{
        // в правиле уже определен шаблон. меняем параметры выполнения правила
        public int tokensInStep = 0;
    }
}
