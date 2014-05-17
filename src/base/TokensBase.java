package base;

import components.TokenManager.Dialog;
import core.Property;
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

    private boolean second = false;

    public void AddToken(Token token){

        // имена одинаковые
        boolean names =false;

        try {

            boolean exist = false; // в базе нет такого токена

            for (Token t : _loadTokens){

                String a = t.GetName();
                String b = token.GetName();

                boolean wasProcess = false;

                if (a.equals(b)){
                    names = true;
                    boolean allEquals = true;

                    for (Property ap : t.get_properties()){
                        for (Property bp : token.get_properties()){


                            wasProcess = true;

                            if (ap.getName().equals(bp.getName()) && ap!=null && bp != null){
                                if (!ap.equals(bp)){
                                    allEquals = false;
                                    break;
                                }
                            }

                            if (!allEquals) break;

                        }
                    }

                    if (allEquals && wasProcess && names || names&&t.get_properties().size()==0&&token.get_properties().size()==0)
                        exist = true;

                }


            }
            if (exist && !second){
                //JOptionPane.showMessageDialog(null, "Такой токен уже существует. Не добавляем в базу!");
                return;
            }

        } catch (Exception r){}

        if (!_loadTokens.contains(token))
        {
            // если имена одинаковые то приписать к новому имени чтонить
            if (names){


                token.ChangeName(token.GetName()+"#");
                second = true;
                AddToken(token);

            }
            if (!_loadTokens.contains(token)){
                _loadTokens.add(token);
                Dialog.getInstanse().UpdateGUI();
            }
        }

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
