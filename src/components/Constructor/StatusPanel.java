package components.Constructor;

import components.TokenManager.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by deanto on 20.04.14.
 */
public class StatusPanel extends JPanel {

    public static String StatusPanel = "StatusPanel";

    JButton openTokenManager;

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

        setBackground(Color.GRAY);
        setVisible(true);
    }
}
