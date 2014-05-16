package fileSavers;

import components.GraphicalElements.TransactionElement;
import core.IncomingTransitionRule;
import core.Transition;
import net.staticNet.StateWrap;
import net.staticNet.TransactionWrap;

import java.util.ArrayList;

/**
 * Created by deanto on 16.05.14.
 */
public class FSRTransactionWrap {
    public static StringBuilder Save(TransactionWrap tw){
        String n = System.getProperty("line.separator");
        StringBuilder answer = new StringBuilder();

        TransactionElement te = (TransactionElement)tw.get_element();

        answer.append(te.get_name()+n);
        answer.append(te.getYcenter()+n);
        answer.append(te.getXcenter()+n);
        answer.append(te.getHeigth()+n);
        answer.append(te.getWidth()+n);

        answer.append(tw.get_transaction().get_incomingTransitionRules().size());

        for (IncomingTransitionRule itr : tw.get_transaction().get_incomingTransitionRules()){
            answer.append(FSRIncomingTransitionRule.Save(itr));
        }








        return answer;
    }

    public static TransactionWrap Read(ArrayList<String> strings){

        String tName = strings.get(0);
        int xCenter = new Integer(strings.get(1));
        int yCenter = new Integer(strings.get(2));
        int height = new Integer(strings.get(3));
        int width = new Integer(strings.get(4));

        int itrSize = new Integer(strings.get(5));

        for (int i=0;i<5;i++)
            strings.remove(0);

        ArrayList<IncomingTransitionRule> itrs= new ArrayList<IncomingTransitionRule>();
        for (int i=0;i<itrSize;i++)
            itrs.add(FSRIncomingTransitionRule.Read(strings));






        Transition newTransaction = new Transition();
        newTransaction.SetName(tName);

        for (IncomingTransitionRule itr:itrs)
        newTransaction.AddIncomingTransitionRule(itr);


    }
}
