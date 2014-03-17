package application;

import internalComponents.WWToken;
import core.Token;
import fileSavers.FSRToken;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Денис on 13.03.14.
 */
public class CreateToken extends JFrame implements ActionListener{

    static CreateToken me;
    static JButton newToken;
    static void Init(){
        if (me == null)
        {
            me = new CreateToken();
            me.setBounds(200,100,200,100);
            newToken = new JButton();
            newToken.setText("Создать токен");
            me.add(newToken);
            newToken.addActionListener(me);
        }
    }



    public static void CreateTokens(){
        Init();
        me.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newToken){
            WWToken Token = new WWToken();
            Token.ConstructToken();
            Token newToken = Token.GetToken();

            //TokensBase.GetTokenBase().AddToken(newToken);

            FSRToken.SaveToFile(newToken);
        }
    }
}
