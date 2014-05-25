package net.dynamic.changes;

import net.dynamic.statistic.stateStat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by deanto on 19.05.14.
 */
public class changePanel extends JDialog {

    private JPanel Panel;

    private static changePanel _instance;

    public static changePanel getPanel (){return _instance;}
    public static void ShowPanel(){
        if (_instance == null){
            _instance = new changePanel();
            _instance.Init();
        }

        _instance.setVisible(true);
    }

    public StringBuilder GetChanges(){
        StringBuilder answer = new StringBuilder();
        String n = System.getProperty("line.separator");

        for (changeStat s : _statesChanges)
            answer.append(s.toStrings().toString()+n);

        for (changeTransaction t : _transitionsChanges)
            answer.append(t);

        return answer;
    }

    private int sPos = 420;
    private int tPos = 20;

    private ArrayList<changeStat> _statesChanges;
    private ArrayList<changeTransaction> _transitionsChanges;

    public ArrayList<changeStat> get_statesChanges(){return  _statesChanges;}
    public ArrayList<changeTransaction> get_transitionsChanges(){return _transitionsChanges;}

    private void Init(){
        setTitle("Панель изменений сети.");
        setBounds(200, 0, 800, 770);

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
                changeTransaction cT = new changeTransaction();
                cT.setBounds(0,tPos,790,100);
                tPos +=100;
                cT.setBorder(BorderFactory.createLineBorder(Color.BLUE));
                Panel.add(cT);
                Panel.repaint();
                _transitionsChanges.add(cT);

            }
        });
        Panel.add(AddTransaction);


        JLabel StatesSectionTitle = new JLabel("Состояния:");
        StatesSectionTitle.setBounds(0,sPos,100,20);
        Panel.add(StatesSectionTitle);

        JButton AddState = new JButton("+");
        AddState.setBounds(100,sPos,50,20);
        sPos +=20;
        AddState.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeStat cS = new changeStat();
                cS.setBounds(0,sPos,790,100);
                sPos += 100;
                cS.setBorder(BorderFactory.createLineBorder(Color.RED));
                Panel.add(cS);
                Panel.repaint();
                _statesChanges.add(cS);
            }
        });
        Panel.add(AddState);

        add(Panel);
        _statesChanges = new ArrayList<changeStat>();
        _transitionsChanges = new ArrayList<changeTransaction>();
    }
}
