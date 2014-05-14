package base;

import components.TokenManager.Dialog;
import core.Token;
import views.VToken;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Денис on 13.03.14.
 * load tokens
 */
public class TokensBase {
    private static TokensBase _singleton;
    public static TokensBase GetTokenBase(){
        if (_singleton == null)
        {
            _singleton = new TokensBase();
            _singleton._loadTokens = new ArrayList<Token>();
        }
        return _singleton;
    }

    private TokensBase(){}

    public void Clear(){
        _loadTokens = new ArrayList<Token>();
    }

    private ArrayList<Token> _loadTokens;

    public void AddToken(Token token){

        try {

            boolean ok = false;

            for (Token t : _loadTokens)
                if (t.GetName().equals(token.GetName())) {
                    //JOptionPane.showMessageDialog(null, "Токен с таким именем существует. Не добавляем в базу!");
                    for (int i = 0; i < t.get_properties().size(); i++)
                        if (t.get_properties().get(i).getName().equals(token.get_properties().get(i).getName()))
                            if (t.get_properties().get(i).getValue().getStringValue().equals(token.get_properties().get(i).getValue().getStringValue())) {

                                ok = true;
                            }
                }

            if (ok){
                JOptionPane.showMessageDialog(null, "Такой токен уже существует. Не добавляем в базу!");
                return;
            }

        } catch (Exception r){}

        if (!_loadTokens.contains(token))
            _loadTokens.add(token);

        Dialog.getInstanse().UpdateGUI();
    }

    public ArrayList<Token> GetTokens(){
        return _loadTokens;
    }

    private ArrayList<JPanel> _tokensList;
    public void ShowTokensInSystem(){

        _tokensList = new ArrayList<JPanel>();
        for (int i=0;i<_loadTokens.size();i++)
            _tokensList.add(new VToken(_loadTokens.get(i)));

        JDialog p = new JDialog();
        p.setBounds(0, 0, 800, 600);
        JPanel pp = new JPanel();
        p.add(pp);

        for (int i=0;i<_tokensList.size();i++)
        pp.add(_tokensList.get(i));

        p.setVisible(true);

    }


}
