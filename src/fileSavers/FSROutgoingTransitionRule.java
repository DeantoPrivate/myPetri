package fileSavers;

import core.IncomingTransitionRule;
import core.OutgoingTransitionRule;
import core.State;
import core.Token;
import net.netSaver.NetSaver;

import java.util.ArrayList;

/**
 * Created by deanto on 17.05.14.
 */
public class FSROutgoingTransitionRule {
    public static StringBuilder Save(OutgoingTransitionRule itr){

        String n = System.getProperty("line.separator");

        StringBuilder answer = new StringBuilder();
        answer.append(itr.getState()+n);
        answer.append(itr.getToken()+n);
        answer.append(itr.getTokenCount()+n);

        return answer;
    }

    public static OutgoingTransitionRule Read(ArrayList<String> strings){

        String stateName = strings.get(0);
        String tokenName = strings.get(1);
        int tokenCount = new Integer(strings.get(2));

        for (int i=0;i<3;i++)
            strings.remove(0);

        State newState = NetSaver.getState(stateName);
        Token newToken = NetSaver.getToken(tokenName);

        OutgoingTransitionRule newRule = new OutgoingTransitionRule();
        newRule.constructRule(newState,newToken,tokenCount);

        return newRule;
    }

}
