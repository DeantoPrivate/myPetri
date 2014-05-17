package fileSavers;

import components.Constructor.GraphPanel;
import components.GraphicalElements.TransactionElement;
import core.IncomingTransitionRule;
import core.OutgoingTransitionRule;
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

        answer.append(tw.get_transaction().get_outgoingTransitionRules().size());

        for (OutgoingTransitionRule otr : tw.get_transaction().get_outgoingTransitionRules()){
            answer.append(FSROutgoingTransitionRule.Save(otr));
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

        for (int i=0;i<6;i++)
            strings.remove(0);

        ArrayList<IncomingTransitionRule> itrs= new ArrayList<IncomingTransitionRule>();
        for (int i=0;i<itrSize;i++)
            itrs.add(FSRIncomingTransitionRule.Read(strings));


        int otrSize = new Integer(strings.get(0));
        strings.remove(0);


        ArrayList<OutgoingTransitionRule> otrs= new ArrayList<OutgoingTransitionRule>();
        for (int i=0;i<otrSize;i++)
            otrs.add(FSROutgoingTransitionRule.Read(strings));


        Transition newTransaction = new Transition();
        newTransaction.SetName(tName);

        for (IncomingTransitionRule itr:itrs)
            newTransaction.AddIncomingTransitionRule(itr);

        for (OutgoingTransitionRule otr:otrs)
            newTransaction.AddOutgoingTransitionRule(otr);


        TransactionElement transactionElement = new TransactionElement(GraphPanel.getJPanelForElements());
        transactionElement.setValues(tName,height,width,xCenter,yCenter);
        TransactionWrap transactionWrap = new TransactionWrap(newTransaction,transactionElement);

        return transactionWrap;
    }
}
