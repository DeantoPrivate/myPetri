package core;

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
        if (_actionIncomingToken.equals(Transition.INCOMING_TOKEN_REMOVE))


                for (int i=0;i<_state.GetTokens().size();i++){
                    if (TokenComparer.Equals(_state.GetTokens().get(i),_tokenPattern))
                    {
                        Token tmp = _state.GetTokens().get(i);
                        _state.TokenGone(tmp);
                        i--;
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
