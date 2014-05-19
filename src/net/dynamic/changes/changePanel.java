package net.dynamic.changes;

import net.dynamic.statistic.stateStat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by deanto on 19.05.14.
 */
public class changePanel extends JDialog {

    private JPanel Panel;

    private static changePanel _instance;

    public changePanel getPanel (){return _instance;}
    public static void ShowPanel(){
        if (_instance == null){
            _instance = new changePanel();
            _instance.Init();
        }

        _instance.setVisible(true);
    }

    private int sPos = 430;

    private void Init(){
        setTitle("Панель изменений сети.");
        setBounds(10, 10, 1000, 800);

        Panel = new JPanel();
        Panel.setLayout(null);

        JLabel TransactionSectionTitle = new JLabel("Переходы:");
        TransactionSectionTitle.setBounds(0,0,100,20);
        Panel.add(TransactionSectionTitle);

        JButton AddTransaction = new JButton("+");
        AddTransaction.setBounds(100,0,50,20);
        Panel.add(AddTransaction);


        JLabel StatesSectionTitle = new JLabel("Состояния:");
        StatesSectionTitle.setBounds(0,400,100,20);
        Panel.add(StatesSectionTitle);

        JButton AddState = new JButton("+");
        AddState.setBounds(100,400,50,20);
        AddState.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeStat cS = new changeStat();
                cS.setBounds(100,sPos,800,100);
                sPos += 100;
                cS.setBorder(BorderFactory.createLineBorder(Color.RED));
                Panel.add(cS);
                Panel.repaint();
            }
        });
        Panel.add(AddState);

        add(Panel);
    }


}
