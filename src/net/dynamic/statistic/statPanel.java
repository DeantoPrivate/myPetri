package net.dynamic.statistic;

import com.sun.corba.se.spi.activation._InitialNameServiceImplBase;
import net.liveNet.LiveNet;
import net.staticNet.netStaticImpl;
import sun.security.krb5.internal.PAData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by deanto on 18.05.14.
 */
public class statPanel extends JDialog implements CountActionListener {

    private JPanel Panel;
    private Integer CurrentStep = 0;
    private JLabel CurrentStepLable = null;

    private static statPanel _instance;
    private statPanel(){
        _instance = this;
    }

    public static statPanel getPanel (){return _instance;}
    public static void ShowPanel(){
        if (_instance == null){
            _instance = new statPanel();
            _instance.Init();
        }

        _instance.setVisible(true);
    }

    public StringBuilder GetStatictics(){

        StringBuilder answer = new StringBuilder();
        String n = System.getProperty("line.separator");
        for (stateStat s : _stateStats)
            answer.append(s+n);

        for (transactionStat t : _transactionStats)
            answer.append(t+n);

        return answer;
    }

    private ArrayList<transactionStat> _transactionStats;
    private ArrayList<stateStat> _stateStats;

    public void Clear(){

        CurrentStep = 0;
        CurrentStepLable.setText("Текущий шаг в сети: "+CurrentStep.toString());

        for (stateStat sS : _stateStats)
            sS.Clear();

        for (transactionStat tS : _transactionStats){
            tS.Clear();
            tS.repaint();
        }


        Panel.repaint();
    }

    private int tPos = 30;
    private int sPos = 150;
    private void Init(){
        setTitle("Панель сбора статистики");
        setBounds(0, 400, 600, 300);


        Panel = new JPanel();
        Panel.setLayout(null);

            JLabel TransactionSectionTitle = new JLabel("Переходы:");
            TransactionSectionTitle.setBounds(0,0,100,20);
            Panel.add(TransactionSectionTitle);

            JButton AddTransaction = new JButton("+");
            AddTransaction.setBounds(100,0,50,20);
            AddTransaction.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    transactionStat ts = transactionStat.create();
                    if (ts.Consctuct()){
                        ts.setBounds(0,tPos,600,20);
                        tPos+=20;
                        _transactionStats.add(ts);
                        Panel.add(ts);
                        Panel.repaint();
                    }
                }
            });
            Panel.add(AddTransaction);

            CurrentStepLable = new JLabel("Текущий шаг в сети: "+CurrentStep.toString());
            CurrentStepLable.setBounds(400,0,300,20);
            Panel.add(CurrentStepLable);

            JLabel StatesSectionTitle = new JLabel("Состояния:");
            StatesSectionTitle.setBounds(0,sPos,100,20);

            Panel.add(StatesSectionTitle);

            JButton AddState = new JButton("+");
            AddState.setBounds(100,sPos,50,20);
            sPos+=20;
            AddState.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    stateStat sS = new stateStat();
                    if (sS.Consctuct()){
                        sS.setBounds(0,sPos,600,20);
                        sPos+=20;
                        _stateStats.add(sS);
                        Panel.add(sS);
                        Panel.repaint();
                    }
                }
            });
            Panel.add(AddState);

        _transactionStats = new ArrayList<transactionStat>();
        _stateStats = new ArrayList<stateStat>();

        add(Panel);

        LiveNet.GetInstance().AssignCountChangeListener(this);

    }


    @Override
    public void FireCountEventFromElement() {
        //  у нас такое событие посылает сеть когда произошел следующий шаг
        _instance.CurrentStep ++;
        CurrentStepLable.setText("Текущий шаг в сети: "+CurrentStep.toString());
        _instance.Panel.repaint();
    }
}
