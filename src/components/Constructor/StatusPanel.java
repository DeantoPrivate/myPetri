package components.Constructor;

import components.TokenManager.*;
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

    JButton openTokenManager,openNetControlPanel, saveNet, readNet;

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

        openTokenManager.setBounds(0,0,480,30);
        add(openTokenManager);

        openNetControlPanel = new JButton("Open Net Control Pane");
        openNetControlPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LiveNet.GetInstance().ActivatePanel();
            }
        });
        openNetControlPanel.setBounds(0,30,480,30);
        add(openNetControlPanel);

        saveNet = new JButton("Save Net");
        saveNet.setBounds(0,60,240,30);
        saveNet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NetSaver.SaveNet(netStaticImpl.getNet());
            }
        });
        add(saveNet);

        readNet = new JButton("Read Net");
        readNet.setBounds(240,60,240,30);
        readNet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                netStaticImpl.changeInstance(NetSaver.ReadNet());
            }
        });
        add(readNet);

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

    }
}
