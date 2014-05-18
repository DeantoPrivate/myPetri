package net.dynamic.statistic;

import com.sun.corba.se.spi.activation._InitialNameServiceImplBase;
import sun.security.krb5.internal.PAData;

import javax.swing.*;
import java.awt.*;

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

    private void Init(){
        setTitle("Панель сбора статистики");
        setBounds(10, 10, 1000, 800);

        Panel = new JPanel();
        Panel.setLayout(null);

            JLabel TransactionSectionTitle = new JLabel("Переходы:");
            TransactionSectionTitle.setBounds(0,0,100,20);
            Panel.add(TransactionSectionTitle);

            JButton AddTransaction = new JButton("добавить");
            AddTransaction.setBounds(100,0,50,20);
            Panel.add(AddTransaction);

            CurrentStepLable = new JLabel(CurrentStep.toString());
            CurrentStepLable.setBounds(200,10,100,20);
            Panel.add(CurrentStepLable);

            JLabel StatesSectionTitle = new JLabel("Состояния:");
            StatesSectionTitle.setBounds(0,400,100,20);
            Panel.add(StatesSectionTitle);

            JButton AddState = new JButton("добавить");
            AddState.setBounds(100,400,50,20);
            Panel.add(AddState);


        add(Panel);

    }

}
