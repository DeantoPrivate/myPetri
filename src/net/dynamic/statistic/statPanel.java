package net.dynamic.statistic;

import com.sun.corba.se.spi.activation._InitialNameServiceImplBase;
import sun.security.krb5.internal.PAData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by deanto on 18.05.14.
 */
public class statPanel extends JDialog {

    private JPanel Panel;
    private Integer CurrentStep = 0;
    private JLabel CurrentStepLable;

    private static statPanel _instance;
    private statPanel(){
        _instance = this;
    }

    public statPanel getPanel (){return _instance;}
    public static void ShowPanel(){
        if (_instance == null){
            _instance = new statPanel();
            _instance.Init();
        }

        _instance.setVisible(true);
    }

    private ArrayList<JPanel> _transactionStats;

    private int tPos = 30;

    private void Init(){
        setTitle("Панель сбора статистики");
        setBounds(10, 10, 1000, 800);

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
                        ts.setBounds(0,tPos,1000,60);
                        tPos+=50;
                        _transactionStats.add(ts);
                        add(ts);
                        ts.repaint();
                    }
                }
            });
            Panel.add(AddTransaction);

            CurrentStepLable = new JLabel("Текущий шаг в сети: "+CurrentStep.toString());
            CurrentStepLable.setBounds(700,10,300,20);
            Panel.add(CurrentStepLable);

            JLabel StatesSectionTitle = new JLabel("Состояния:");
            StatesSectionTitle.setBounds(0,400,100,20);
            Panel.add(StatesSectionTitle);

            JButton AddState = new JButton("+");
            AddState.setBounds(100,400,50,20);
            Panel.add(AddState);

        _transactionStats = new ArrayList<JPanel>();

        add(Panel);

    }


}
