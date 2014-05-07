package core;

import internalComponents.WWToken;

import javax.swing.*;

/**
 * Created by deanto on 04.05.14.
 */
public class IncomingTransitionRule extends TransitionRule{
    private State _state;
    private Token _tokenPattern;
    private boolean _completed = false;


    public String _actionIncomingToken = Transition.INCOMING_TOKEN_REMOVE;


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
                if (JOptionPane.showOptionDialog(null,"Should incoming token stays in incoming state?","Option",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,1) == 0)
                    _actionIncomingToken = Transition.INCOMING_TOKEN_KEEP;
            }
        }
    }

    public void Process(){
        if (_actionIncomingToken.equals(Transition.INCOMING_TOKEN_REMOVE))
            for (int i=0;i<_state.GetTokens().size();i++){
                if (TokenComparer.Equals(_state.GetTokens().get(i),_tokenPattern))
                {
                    Token tmp = _state.GetTokens().get(i);
                    _state.TokenGone(tmp);
                    return;
                }
            }
    }

    public boolean canStart(){

        for (int i=0;i<_state.GetTokens().size();i++){
            if (TokenComparer.Equals(_state.GetTokens().get(i),_tokenPattern))
                return true;
        }

        return false;
    }

}
