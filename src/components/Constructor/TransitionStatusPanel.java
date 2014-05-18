package components.Constructor;

import com.sun.jndi.url.corbaname.corbanameURLContextFactory;
import core.IncomingTransitionRule;
import core.OutgoingTransitionRule;
import core.Transition;

import javax.swing.*;
import javax.swing.text.html.ObjectView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by deanto on 20.04.14.
 */
public class TransitionStatusPanel extends StatusPanel {

    public static String StatusPanel = "TransitionStatusPanel";


    private static TransitionStatusPanel _instance;
    private static Transition _transaction;

    private ArrayList<JTextArea> textAreas;

    public static void ShowTransaction(Transition transaction){
        _transaction = transaction;
        UpdateGUI();
    }

    public static void UpdateUI(){
        if (_transaction!=null)
            UpdateGUI();
    }

    private static void UpdateGUI(){

        _instance.removeAll();
        _instance.textAreas = new ArrayList<JTextArea>();

        JTextArea tmp;

        int r = 0;

        for (IncomingTransitionRule itr : _transaction.get_incomingTransitionRules()){
            tmp = new JTextArea(itr.toString());
            tmp.setBounds(10,100+r,390,20);
            tmp.setColumns(390);
            _instance.textAreas.add(tmp);
            r+=20;
        }

        for (OutgoingTransitionRule itr : _transaction.get_outgoingTransitionRules()){
            tmp = new JTextArea(itr.toString());
            tmp.setBounds(10,100+r,390,20);
            tmp.setColumns(390);
            _instance.textAreas.add(tmp);
            r+=20;
        }

        for (int i=0;i<_instance.textAreas.size();i++)
            _instance.add(_instance.textAreas.get(i));

        JTextArea sleep = new JTextArea("задержка = "+_transaction.getSleepSteps());
        sleep.setBounds(10,100+r,390,20);
        r+=20;
        _instance.add(sleep);

        JButton changeTransactionSleep  = new JButton("sleep change");
        changeTransactionSleep.setBounds(10, 100 + r, 390, 20);
        changeTransactionSleep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String count = JOptionPane.showInputDialog("Укажите задержку","0,1 ...  или \"работает один раз\"");
                int col = 0;
                if (count!=null)
                    try{
                        if (count.equals("работает один раз")){
                            col = -9;
                        }

                        Integer a = new Integer(count.toString());
                        if (a!=null)
                            col=a;
                    }catch (Exception r){

                    }

                if (col<0 && col!=-9) col = 0;

                _transaction.SetSleepSteps(col);
                UpdateGUI();
                _instance.repaint();
            }
        });

        _instance.add(changeTransactionSleep);

    }

    public TransitionStatusPanel(){

        super.Init();

        setBounds(800,0,480,800);
        setBackground(Color.ORANGE);

        _instance = this;
    }

}
