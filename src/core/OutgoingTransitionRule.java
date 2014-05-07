package core;

import internalComponents.WWToken;

import javax.swing.*;

/**
 * Created by deanto on 04.05.14.
 */
public class OutgoingTransitionRule extends TransitionRule{
    private State _state;
    private Token _token;
    private boolean _completed = false;

    public void setState(State state){
        _state = state;
    }

    public void constructRule(){

        JOptionPane.showMessageDialog(null,"Строим висходящее правило транзакции");

        while(!_completed){

            WWToken Token = new WWToken();
            Token.ConstructToken();
            Token pattern = Token.GetToken();

            if (pattern!=null){
                _token = pattern;
                _completed = true;
            }
        }
    }

    public void Process(){
        _state.LocateToken(_token);
    }
}
