package core;

import application.CreateToken;
import com.sun.xml.internal.bind.v2.TODO;
import internalComponents.WWToken;

import javax.swing.*;

/**
 * Created by deanto on 18.03.14.
 *
 * the rool for transition when it can start
 * transition Rule can contain multiple rules. but wokrs the same. just process multiple rules and AND OR ... statements between rules
 */
public class TransitionRule {

    // we make patterns for states. if incoming state is the same as incoming pattern - the transition can start
    // pattern contains tokens (token patterns) - copy of original token or it's part. so we will compare token
    // and pattern - if all patterns values presents in token - we say out token is like our pattern

    // this means incoming token can stay in it's state, or it can be removed
    String _actionIncomingToken = INCOMING_TOKEN_REMOVE;

    // what we can do with incoming token
    public static String INCOMING_TOKEN_REMOVE = "INCOMING_TOKEN_REMOVE";
    public static String INCOMING_TOKEN_TRANSFER = "INCOMING_TOKEN_TRANSFER";
    public static String INCOMING_TOKEN_KEEP = "INCOMING_TOKEN_KEEP";
    public static String INCOMING_TOKEN_KEEP_TRANSFER = "INCOMING_TOKEN_KEEP_TRANSFER";


    // outgoing pattern may be null - it means rule does not put any token into outgoung state
    private Token _incomingTokenPattern, _outgoingToken;

    protected boolean _completed = false;
    public boolean isCompleted(){
        return _completed;
    }

    // analyze incoming state and if there is appropriate token - return true
    public boolean canStart(State state){

        for (int i=0;i<state.GetTokens().size();i++){
            if (TokenComparer.Equals(state.GetTokens().get(i),_incomingTokenPattern))
                return true;
        }

        return false;
    }

    public void constructRule(){
        WWToken Token = new WWToken();
        Token.ConstructToken();
        Token pattern = Token.GetToken();

        if (pattern!=null)
            _incomingTokenPattern = pattern;

        if (JOptionPane.showOptionDialog(null,"Should incoming token stays in incoming state?","Option",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,1) == 0)
            _actionIncomingToken = INCOMING_TOKEN_KEEP;

        int choise = JOptionPane.showOptionDialog(null,"Should incoming token be transfered?","Option",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,1);
        if (choise==0)
        {
            if (_actionIncomingToken.equals(INCOMING_TOKEN_KEEP))
                _actionIncomingToken = INCOMING_TOKEN_KEEP_TRANSFER;
            else _actionIncomingToken = INCOMING_TOKEN_TRANSFER;
        }

        int construct = JOptionPane.showOptionDialog(null,"Do you want to specify outgoung Token?","Option",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,1);
        if (construct==0){
            Token = new WWToken();
            Token.ConstructToken();
            Token outgoing = Token.GetToken();

            if (outgoing!=null)
                _outgoingToken = outgoing;
        }

        if (pattern!=null)
        _completed = true;
    }

    public Token get_outgoingToken(){
        if (_outgoingToken==null) return null;

        return _outgoingToken.Clone();
    }

    // to campare tokens



}


