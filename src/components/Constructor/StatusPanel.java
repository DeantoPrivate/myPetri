package components.Constructor;

import components.TokenManager.*;
import net.dynamic.changes.changePanel;
import net.dynamic.changes.changeStat;
import net.dynamic.statistic.statPanel;
import net.liveNet.LiveNet;
import net.netSaver.NetSaver;
import net.staticNet.netStaticImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by deanto on 20.04.14.
 */
public class StatusPanel extends JPanel {

    public static String StatusPanel = "StatusPanel";

    JButton openTokenManager,openNetControlPanel, saveNet, readNet, statisticPanel, changesPanel;

    public void Init(){
        setBorder(BorderFactory.createLineBorder(Color.ORANGE));
        setLayout(null);

        openTokenManager = new JButton("Open Token Manager");
        openTokenManager.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                components.TokenManager.Dialog.ShowDialog();
            }
        });

        openTokenManager.setBounds(0,0,290,30);
        add(openTokenManager);

        openNetControlPanel = new JButton("Open Net Control Pane");
        openNetControlPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LiveNet.GetInstance().ActivatePanel();
            }
        });
        openNetControlPanel.setBounds(0,30,290,30);
        add(openNetControlPanel);

        saveNet = new JButton("Save Net");
        saveNet.setBounds(0,60,145,30);
        saveNet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NetSaver.SaveNet(netStaticImpl.getNet());
            }
        });
        add(saveNet);

        readNet = new JButton("Read Net");
        readNet.setBounds(145,60,145,30);
        readNet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NetSaver.ReadNet(true);
            }
        });
        add(readNet);


        statisticPanel = new JButton("Stat Panel");
        statisticPanel.setBounds(145,90,145,30);
        statisticPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statPanel.ShowPanel();
            }
        });
        add(statisticPanel);


        changesPanel = new JButton("Changes Panel");
        changesPanel.setBounds(0,90,145,30);
        changesPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePanel.ShowPanel();
            }
        });
        add(changesPanel);

        setBackground(Color.GRAY);
        setVisible(true);
    }

    @Override
    public void removeAll() {
        super.removeAll();
        add(openTokenManager);
        add(openNetControlPanel);
        add(saveNet);
        add(readNet);
        add(statisticPanel);
        add(changesPanel);

    }
}
