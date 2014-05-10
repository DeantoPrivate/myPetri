package components.Constructor;

import components.TokenManager.*;
import net.liveNet.LiveNet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by deanto on 20.04.14.
 */
public class StatusPanel extends JPanel {

    public static String StatusPanel = "StatusPanel";

    JButton openTokenManager,openNetControlPanel;

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

        setBackground(Color.GRAY);
        setVisible(true);
    }

    @Override
    public void removeAll() {
        super.removeAll();
        add(openTokenManager);
        add(openNetControlPanel);
    }
}
