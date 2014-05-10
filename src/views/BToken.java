package views;

import core.Token;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by deanto on 10.05.14.
 */
public class BToken extends JButton{
    private String bTokenName;
    private VToken vToken;

    public BToken(Token token){

        vToken = new VToken(token);
        bTokenName = token.GetName();

        setText(bTokenName);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog();
                dialog.setBounds(200,200,300,200);
                dialog.setModal(true);
                dialog.add(vToken);
                dialog.setVisible(true);
            }
        });
    }




}
