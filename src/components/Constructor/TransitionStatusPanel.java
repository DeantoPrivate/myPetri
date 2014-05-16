package components.Constructor;

import core.IncomingTransitionRule;
import core.OutgoingTransitionRule;
import core.Transition;

import javax.swing.*;
import java.awt.*;
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

    }

    public TransitionStatusPanel(){

        super.Init();

        setBounds(800,0,480,800);
        setBackground(Color.ORANGE);

        _instance = this;
    }

}
