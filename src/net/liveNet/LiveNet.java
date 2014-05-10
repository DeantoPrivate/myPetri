package net.liveNet;

import components.Constructor.WorkingNetStatusPanel;
import core.Transition;
import net.staticNet.netStaticImpl;
import sun.awt.windows.ThemeReader;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

    ProcessNetThread newThread;

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
            goForward.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (newThread!=null)
                        newThread.setDaemon(true);

                    newThread = new ProcessNetThread();
                    newThread.start();
                }
            });
            ControlPanel.add(goForward);

            stop = new JButton("Stop");
            stop.setBounds(10,60,130,30);
            stop.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setProcessing(false);

                }
            });
            ControlPanel.add(stop);

            nextStep = new JButton("step");
            nextStep.setBounds(140,30,60,60);
            nextStep.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    doNextNetStep();
                }
            });
            ControlPanel.add(nextStep);

        }

        ControlPanel.setVisible(true);
    }

    private JButton nextStep,goForward,stop;
    private JTextArea statusText;
    private JTextArea speed;

    private class ProcessNetThread extends Thread{
        @Override
        public void run() {
            ProcessNet();
            interrupt();
        }

        private void ProcessNet(){
            int step = 100; // miliseconds
            try{
            Integer a = new Integer(speed.getText());
                if (a!=null)
                    step=a;
            }catch (Exception e){

            }

            while(isProcessing()){
                try{
                    Thread.sleep(step);

                    doNextNetStep();

                } catch (Exception e){
                    setProcessing(false);
                    JOptionPane.showMessageDialog(null,"some errors while waiting!");
                }
            }

        }
    }

    private boolean processing = true;
    private synchronized boolean isProcessing(){return processing;}
    private synchronized void setProcessing(boolean flag){processing = flag;}



    private void doNextNetStep(){
        // алгоритм
        // сначала надо определиться какие переходы сейчас смогут сработать.

        statusText.setText("step start");
        nextStep.setEnabled(false);

        ArrayList<Transition> _transitions;
        _transitions = _staticNet.getTransitionsWhichCanBeActivated();
        // активируем эти переходы
        for (Transition t : _transitions){
            t.Activate();
            t.Exec();
            t.Deactivate();
        }
        // обновляем статусы и прочее

        _staticNet.RepaintNet();

        //TODO wrap должен быть лисонером у объекта. объект когда меняется сообщает wrap  - а тот сообщает графической части что надо перерисоваться

        statusText.setText("step done");
        nextStep.setEnabled(true);


    }


}
