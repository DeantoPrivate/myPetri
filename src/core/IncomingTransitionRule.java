package core;

import base.TokensBase;
import internalComponents.WWToken;

import javax.swing.*;

/**
 * Created by deanto on 04.05.14.
 */
public class IncomingTransitionRule extends TransitionRule{

    private State _state;
    private int _tokenCount;
    private Token _tokenPattern;
    private boolean _completed = false;

    public String getState(){return _state.GetName();}
    public String getToken(){return _tokenPattern.GetName();}
    public int getTokenCount(){return _tokenCount;}
    public String getAction(){return _actionIncomingToken;}

    public void constructRule(State state, Token token, int count, String action){
        _state = state;
        _tokenCount = count;
        _tokenPattern = token;
        _actionIncomingToken = action;
    }

    public String _actionIncomingToken = Transition.INCOMING_TOKEN_REMOVE;

    @Override
    public String toString() {

        String str = "";
        str += "Из [" + _state.GetName() + "] берем " + _tokenCount + " токен(ов) #" + _tokenPattern.GetName() +"#";

        if (_actionIncomingToken.equals(Transition.INCOMING_TOKEN_KEEP)){
            str += " и оставляем на месте";
        }

        return str;
    }

    public void setState(State state){
        _state = state;
    }

    public void constructRule(){

        JOptionPane.showMessageDialog(null,"Строим входящее правило транзакции");

        while(!_completed){

            WWToken Token = new WWToken();
            Token.ConstructToken();
            Token pattern = Token.GetToken();

            if (pattern!=null){
                _tokenPattern = pattern;
                _completed = true;

                TokensBase.GetTokenBase().AddToken(_tokenPattern);

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

                // TODO сделать нормальный диалог тут. формочку нормальную

                if (JOptionPane.showOptionDialog(null,"Should incoming token(s) stays in incoming state?","Option",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,1) == 0)
                    _actionIncomingToken = Transition.INCOMING_TOKEN_KEEP;

            }
        }
    }

    public void Process(){

        int deleted = 0;

        if (_actionIncomingToken.equals(Transition.INCOMING_TOKEN_REMOVE))
                for (int i=0;i<_state.GetTokens().size();i++){
                    if (TokenComparer.Equals(_state.GetTokens().get(i),_tokenPattern))
                    {
                        Token tmp = _state.GetTokens().get(i);
                        _state.TokenGone(tmp);
                        i--;
                        deleted ++;
                        if (deleted==_tokenCount)
                            return;
                    }
                }
    }

    public boolean canStart(){

        int col = 0;

        for (int i=0;i<_state.GetTokens().size();i++){
            if (TokenComparer.Equals(_state.GetTokens().get(i),_tokenPattern))
                col++;
        }

        if (col >= _tokenCount)
            return true;

        return false;
    }

}
