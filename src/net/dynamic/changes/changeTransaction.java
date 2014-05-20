package net.dynamic.changes;

import core.IncomingTransitionRule;
import core.OutgoingTransitionRule;
import core.Transition;
import core.TransitionRule;
import net.dynamic.analyze.ChangeOneRule;
import net.dynamic.analyze.ChangeOneTransitionDelay;
import net.dynamic.analyze.ChangeOneTransitionWorking;
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

    private ArrayList<JLabel> transactionRulePanels;
    private ArrayList<Transition> _transactions;
    private TransactionChanges _transactionChanges;

    public TransactionChanges get_transactionChanges(){return _transactionChanges;}

    private int tPos = 20;


    private ArrayList<TransitionRule> _rules;
    private ArrayList<TransitionRuleChanges> _rulesChanges;

    public ArrayList<TransitionRule> get_rules(){return _rules;}
    public ArrayList<TransitionRuleChanges> get_rulesChanges(){return _rulesChanges;}

    private class TransitionRuleChanges{
        public int tokenCountNow = 0;
        public int tokenCountL = 0;
        public int getTokenCountR = 0;
    }

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


        _transactionChanges = transactionChanges;
        text.setText(s.toString());
    }

    private JLabel constructRuleChanges(){

        // выберем правила из транзакции для которых мы можем конфигурить изменения
        ArrayList<TransitionRule> rules = new ArrayList<TransitionRule>();
        for (IncomingTransitionRule itr : _transition.get_incomingTransitionRules()){
            if(!_rules.contains(itr))
                rules.add(itr);
        }

        for (OutgoingTransitionRule otr : _transition.get_outgoingTransitionRules()){
            if(!_rules.contains(otr))
                rules.add(otr);
        }

        if (rules.size()!=0){

            String n = System.getProperty("line.separator");

            StringBuilder s = new StringBuilder();
            s.append("Доступные правила:" + n);
            int i = 1;
            for (TransitionRule tr : rules)
                s.append(i++ + ": " + tr.toString() + n);


            if (rules.size() == 0)
                return null;

            i=0;

            while (!(i>0 && i<=rules.size())){
            String choice = JOptionPane.showInputDialog(s.toString()+"Укажите номер");
            i = 0;
            if (choice!=null)
                try{
                    Integer a = new Integer(choice.toString());
                    if (a!=null)
                        i=a;
                }catch (Exception e){
                }
            }

            // выбрано i правило. его редактируем

            TransitionRule selectedRule = rules.get(i - 1);
            _rules.add(selectedRule);
            TransitionRuleChanges trc = new TransitionRuleChanges();
            _rulesChanges.add(trc);

            int currentTokenIssue = -1;
            if(selectedRule instanceof IncomingTransitionRule)
                currentTokenIssue = ((IncomingTransitionRule) selectedRule).getTokenCount();
            else
                currentTokenIssue = ((OutgoingTransitionRule) selectedRule).getTokenCount();

            trc.tokenCountNow = currentTokenIssue;

            String count = JOptionPane.showInputDialog(this,"Текущее количество токенов - "+currentTokenIssue+". Введите нижнюю границу изменения");
            int col = 0;
            if (count!=null)
                try{
                    Integer a = new Integer(count.toString());
                    if (a!=null)
                        col=a;
                }catch (Exception e){
                }
            if (col <=0 )col=0;

            trc.tokenCountL = col;



            count = JOptionPane.showInputDialog("Введите верхнюю границу изменения");
            if (count!=null)
                try{
                    Integer a = new Integer(count.toString());
                    if (a!=null)
                        col=a;
                }catch (Exception e){
                }
            if (col <0)
                col = 0;

            trc.getTokenCountR = col;



            // инфа собрана.строим панельку.


            JLabel answer = new JLabel();
            answer.setLayout(null);
            answer.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
            answer.setBackground(Color.BLACK);

            String ss = "[" + selectedRule + "] => диапазон изменения количества токенов: ["+ trc.tokenCountL +" , " +trc.getTokenCountR + "]";

            answer.setText(ss);

            return answer;

        }

        return null;
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

                if (transactionRulePanels!=null)
                    for (JLabel p : transactionRulePanels)
                        remove(p);

                repaint();

                _transactions = new ArrayList<Transition>();
                _transactionChanges = new TransactionChanges();
                transactionRulePanels = new ArrayList<JLabel>();

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
                // тут добавляем настройки для правил в переходе
                JLabel transactionRulePanel = constructRuleChanges();
                if (transactionRulePanel!=null){
                    transactionRulePanel.setBounds(50,tPos,800,20);
                    tPos+=20;
                    transactionRulePanels.add(transactionRulePanel);
                    add(transactionRulePanel);
                    repaint();
                }
            }
        });
        add(newTransactionRule);

        newTransactionRule.setEnabled(false);
        _rules = new ArrayList<TransitionRule>();
        _rulesChanges = new ArrayList<TransitionRuleChanges>();

    }

    public class TransactionChanges{

        // задержка выполнения
        public int sleep = 0;
        public int sleepL= 0;
        public int sleepR= 0;


        // перерыв в работе
        public boolean notWork = false;
        public int notWorkStepL = 0;
        public int notWorkStepR = 0;

        public ArrayList<TransitionRuleChanges> _transactionRulesChanges;

    }

    public ArrayList<ChangeOneTransitionDelay> getChangeOneTransitionDelays(){

        ChangeOneTransitionDelay cos;
        ArrayList<ChangeOneTransitionDelay> answer = new ArrayList<ChangeOneTransitionDelay>();

        for (int i=_transactionChanges.notWorkStepL;i<=_transactionChanges.notWorkStepR;i++){
            cos = new ChangeOneTransitionDelay();
            cos.delay = i;
            cos.TransitionName = _transition.getName();
            answer.add(cos);
        }

        return answer;

    }


    public ArrayList<ChangeOneTransitionWorking> getChangeOneTransitionWorkings(){
        ChangeOneTransitionWorking cos;
        ArrayList<ChangeOneTransitionWorking> answer  = new ArrayList<ChangeOneTransitionWorking>();

        if (_transactionChanges.notWork){
            // если он не работает. есть выбор. тестим когда работает и тестим когда не работает

            cos = new ChangeOneTransitionWorking();
            cos.notWork = true;
            cos.stepL = _transactionChanges.sleepL;
            cos.stepR = _transactionChanges.sleepR;
            cos.TransitionName = _transition.getName();

            answer.add(cos);
        }

        // всегда есть вариант что он работает.
        cos = new ChangeOneTransitionWorking();
        cos.notWork = false;

        answer.add(cos);

        return answer;

    }

    public ArrayList<ChangeOneRule> getChangeOneRules(){
        // тут просмотрим все правила и вернем для каждого несколько(из диапазона)
        ChangeOneRule cor = new ChangeOneRule();
        ArrayList<ChangeOneRule> answer = new ArrayList<ChangeOneRule>();

        for (int i=0;i<_rules.size();i++)
            for (int j=_rulesChanges.get(i).tokenCountL;j<=_rulesChanges.get(i).getTokenCountR;j++){
                cor = new ChangeOneRule();
                cor.param = j;
                cor.RuleString = _rules.get(i).toString();
                cor.TransitionName = _transition.getName();

                answer.add(cor);
        }

        return answer;
    }

    // класс сам дает все возможные комбинации по очереди. исходя из диапазонов




}
