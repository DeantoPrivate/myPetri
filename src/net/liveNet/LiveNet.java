package net.liveNet;

import components.Constructor.WorkingNetStatusPanel;
import net.staticNet.netStaticImpl;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

/**
 * Created by deanto on 10.05.14.
 */
public class LiveNet {

    private static LiveNet _instance;
    public static LiveNet GetInstance(){
        if (_instance == null){
            _instance = new LiveNet(netStaticImpl.getNet());
        }
        return _instance;
    }

    private netStaticImpl _staticNet;

    public LiveNet (netStaticImpl constructedNet){
        _staticNet = constructedNet;
        _statusPanel = WorkingNetStatusPanel.getInstance();
    }

    private WorkingNetStatusPanel _statusPanel;


    private JDialog ControlPanel;

    public void ActivatePanel(){
        if (ControlPanel==null){

            ControlPanel = new JDialog();
            ControlPanel.setAlwaysOnTop(true);
            ControlPanel.setTitle("Управления сетью.");
            ControlPanel.setLayout(null);
            ControlPanel.setBounds(600,400,225,150);

            statusText = new JTextArea();
            statusText.setBounds(10,5,190,20);
            statusText.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
            statusText.setText("Status:> just opened.");
            ControlPanel.add(statusText);

            speed = new JTextArea();
            speed.setBounds(10,30,70,20);
            speed.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
            speed.setText("speed");
            ControlPanel.add(speed);


            goForward = new JButton("->");
            goForward.setBounds(80,30,60,20);
            ControlPanel.add(goForward);

            stop = new JButton("Stop");
            stop.setBounds(10,60,130,30);
            ControlPanel.add(stop);

            nextStep = new JButton("step");
            nextStep.setBounds(140,30,60,60);
            ControlPanel.add(nextStep);

        }

        ControlPanel.setVisible(true);
    }

    private JButton nextStep,goForward,stop;
    private JTextArea statusText;
    private JTextArea speed;




}
