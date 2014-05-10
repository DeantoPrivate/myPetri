package views;

import core.Token;

import javax.swing.*;

/**
 * Created by deanto on 10.05.14.
 */
public class BToken extends JButton {
    private String bTokenName;
    private VToken vToken;

    public BToken(Token token){

        vToken = new VToken(token);
        

    }
}
