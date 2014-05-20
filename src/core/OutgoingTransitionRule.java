package core;

import base.TokensBase;
import internalComponents.WWToken;

import javax.swing.*;

/**
 * Created by deanto on 04.05.14.
 */
public class OutgoingTransitionRule implements TransitionRule{
    private State _state;
    private Token _token;
    private boolean _completed = false;
    private int _tokenCount;

    public String getState(){return _state.GetName();}
    public String getToken(){return _token.GetName();}
    public int getTokenCount(){return _tokenCount;}

    public void constructRule(State state, Token token, int count){
        _state = state;
        _tokenCount = count;
        _token = token;
    }

    public void setState(State state){
        _state = state;
    }

    public String toString() {

        String str = "";
        str += "в состояние [" + _state.GetName() + "] помещаем " + _tokenCount + " токен(ов) #" + _token.GetName() + "#";

        return str;
    }

    @Override
    public void setCount(int count) {
        _tokenCount = count;
    }

    public void constructRule(){

        JOptionPane.showMessageDialog(null,"Строим исходящее правило транзакции");

        while(!_completed){

            WWToken Token = new WWToken();
            Token.ConstructToken();
            Token pattern = Token.GetToken();

            if (pattern!=null){
                _token = pattern;
                _completed = true;

                TokensBase.GetTokenBase().AddToken(_token);

                String count = JOptionPane.showInputDialog(this,"Сколько таких токенов должно быть?");
                int col = 1;
                if (count!=null)
                    try{
                        Integer a = new Integer(count.toString());
                        if (a!=null)
                            col=a;
                    }catch (Exception e){

                    }
                _tokenCount = col;

            }
        }
    }

    public void Process(){
        for (int i=0;i<_tokenCount;i++)
        _state.LocateToken(_token.Clone());
    }
}
