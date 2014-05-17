package fileSavers;

import components.Constructor.GraphPanel;
import components.GraphicalElements.StateElement;
import core.State;
import net.staticNet.StateWrap;

import java.util.ArrayList;

/**
 * Created by deanto on 16.05.14.
 */
public class FSRStateWrap {
    public static StringBuilder Save(StateWrap sw){

        String n = System.getProperty("line.separator");
        StringBuilder answer = new StringBuilder();

        answer.append(sw.get_state().GetName()+n);
        answer.append(sw.get_element().getXcenter()+n);
        answer.append(sw.get_element().getYcenter()+n);
        answer.append(((StateElement)sw.get_element()).getRadius()+n);

        return answer;
    }

    public static StateWrap Read(ArrayList<String> strings){
        String stateName = strings.get(0);
        int xCenter = new Integer(strings.get(1));
        int yCenter = new Integer(strings.get(2));
        int radius = new Integer(strings.get(3));

        for (int i=0;i<4;i++)
            strings.remove(0);

        State newState = new State();
        newState.ChangeName(stateName);

        StateElement stateElement = new StateElement(GraphPanel.getJPanelForElements(),true);
        stateElement.setValues(stateName,radius,xCenter,yCenter);

        StateWrap newStateWrap = new StateWrap(newState,stateElement);

        return newStateWrap;

    }

}
