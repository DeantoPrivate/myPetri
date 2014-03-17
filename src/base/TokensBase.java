package base;

import core.Token;
import javafx.scene.layout.Pane;
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
        if (!_loadTokens.contains(token))
            _loadTokens.add(token);
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
