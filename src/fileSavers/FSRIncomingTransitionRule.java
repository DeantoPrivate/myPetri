package fileSavers;

import core.IncomingTransitionRule;
import core.State;
import core.Token;
import net.netSaver.NetSaver;
import net.staticNet.StateWrap;

import java.util.ArrayList;

/**
 * Created by deanto on 16.05.14.
 */
public class FSRIncomingTransitionRule {
    public static StringBuilder Save(IncomingTransitionRule itr){

        String n = System.getProperty("line.separator");

        StringBuilder answer = new StringBuilder();
        answer.append(itr.getState()+n);
        answer.append(itr.getToken()+n);
        answer.append(itr.getTokenCount()+n);
        answer.append(itr.getAction()+n);

        return answer;
    }

    public static IncomingTransitionRule Read(ArrayList<String> strings){

        String stateName = strings.get(0);
        String tokenName = strings.get(1);
        int tokenCount = new Integer(strings.get(2));
        String action = strings.get(3);

        for (int i=0;i<4;i++)
            strings.remove(0);

        State newState = NetSaver.getState(stateName);
        Token newToken = NetSaver.getToken(tokenName);

        IncomingTransitionRule newRule = new IncomingTransitionRule();
        newRule.constructRule(newState,newToken,tokenCount,action);

        return newRule;
    }

    }
