package net.liveNet;

import com.sun.jndi.url.corbaname.corbanameURLContextFactory;
import components.Constructor.StateStatusPanel;
import components.Constructor.TransitionStatusPanel;
import components.Constructor.WorkingNetStatusPanel;
import core.Transition;
import net.dynamic.analyze.Analyzer;
import net.dynamic.statistic.CountActionListener;
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

    public static void Reload(){
        if ( _instance.ControlPanel!=null)
            _instance.ControlPanel.setVisible(false);
        _instance = new LiveNet(netStaticImpl.getNet());
        _instance.ActivatePanel();
        _instance.currentStep = 0;
        _instance.statusText.setText("Статус:> начало работы");
        _instance.ControlPanel.repaint();

    }

    static CountActionListener cl;
    public void AssignCountChangeListener(CountActionListener listener){
        cl = listener;
    }

    private static netStaticImpl _staticNet;

    public LiveNet (netStaticImpl constructedNet){
        _staticNet = constructedNet;
        _statusPanel = WorkingNetStatusPanel.getInstance();
    }

    private WorkingNetStatusPanel _statusPanel;

    ProcessNetThread newThread;

    private JDialog ControlPanel;

    private static JLabel analyzeStatus;

    public void ActivatePanel(){

        if (ControlPanel==null){

            ControlPanel = new JDialog();
            ControlPanel.setAlwaysOnTop(true);
            ControlPanel.setTitle("Управления сетью.");
            ControlPanel.setLayout(null);
            ControlPanel.setBounds(900,0,225,300);

            statusText = new JTextArea();
            statusText.setBounds(10,5,190,20);
            statusText.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
            statusText.setText("Статус:> начало работы");
            ControlPanel.add(statusText);

            speed = new JTextArea();
            speed.setBounds(10,30,70,20);
            speed.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
            speed.setText("100");
            ControlPanel.add(speed);


            goForward = new JButton("->");
            goForward.setBounds(80,30,60,20);
            goForward.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {


                setProcessing(true);

                if (newThread!=null){
                    newThread.interrupt();
                    newThread.setDaemon(true);
                }
                newThread = new ProcessNetThread();
                newThread.start();



                goForward.setEnabled(false);
                nextStep.setEnabled(false);

                }
            });
            ControlPanel.add(goForward);

            stop = new JButton("Стоп");
            stop.setBounds(10,60,130,30);
            stop.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setProcessing(false);
                    goForward.setEnabled(true);
                    nextStep.setEnabled(true);
                }
            });
            ControlPanel.add(stop);

            nextStep = new JButton("шаг");
            nextStep.setBounds(140,30,60,60);
            nextStep.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    doNextNetStep();
                }
            });
            ControlPanel.add(nextStep);


            analyze = new JButton("Анализ");
            analyze.setBounds(10,150,190,20);
            analyze.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (_analyzer!=null)
                        JOptionPane.showMessageDialog(null,"Анализатор уже запущен");

                    else{
                        _analyzer = new Analyzer();
                        analyze.setEnabled(false);
                        _analyzer.Analyze();
                    }
                }
            });

            ControlPanel.add(analyze);

            analyzeStatus = new JLabel();
            analyzeStatus.setBounds(10, 170, 190, 40);
            analyzeStatus.setText("статус выполнения : ");
            ControlPanel.add(analyzeStatus);

        }

        ControlPanel.setVisible(true);
    }

    // он не меняется в процессе работы. его не надо сбрасывать при перезагрузке
    private static Analyzer _analyzer;

    private static int currentAnalyzeStep = 0;
    public static void setNextAnalyzeStep(){
        analyzeStatus.setText("статус выполнения: шаг "+ currentAnalyzeStep++);
    }

    private JButton nextStep,goForward,stop, analyze;
    private JTextArea statusText;
    private JTextArea speed;

    private class ProcessNetThread extends Thread{

        @Override
        public void run() {
            super.run();
            ProcessNet();
        }

        private void ProcessNet(){

            int step = 100; // miliseconds
            try{
            Integer a = new Integer(speed.getText());
                if (a!=null)
                    step=a;
            }catch (Exception e){

            }

           // netStaticImpl.startProcessing();

            while(isProcessing()){
                try{
                    if (step!=0)
                        Thread.sleep(step);

                    doNextNetStep();

                } catch (Exception e){
                    setProcessing(false);
                    netStaticImpl.stopProcessing();
                    JOptionPane.showMessageDialog(null,"some errors while waiting!");
                }
            }

            //netStaticImpl.stopProcessing();

        }
    }

    private boolean processing = false;
    private synchronized boolean isProcessing(){return processing;}
    private synchronized void setProcessing(boolean flag){processing = flag;}

    private int currentStep = 0;

    public void NextStep(){
        doNextNetStep();
    }

    private void doNextNetStep(){

        netStaticImpl.startProcessing();

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


        netStaticImpl.stopProcessing();
        StateStatusPanel.UpdateUI();
        TransitionStatusPanel.UpdateUI();
        if (cl!=null)
            cl.FireCountEventFromElement();

        currentStep ++;
        statusText.setText("step done : " +currentStep);
        if (!isProcessing())
        nextStep.setEnabled(true);


    }


}
