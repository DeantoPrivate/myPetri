package components.Constructor;

import core.State;
import org.omg.DynamicAny._DynUnionStub;
import views.BToken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by deanto on 20.04.14.
 */
public class StateStatusPanel extends StatusPanel {

    public static String StatusPanel = "StateStatusPanel";

    private static State _state;

    private static StateStatusPanel _instance;

    public static void ShowState(State state){
        _state = state;
        Update();
    }

    private static JLabel _textName;
    private static ArrayList<JButton> _tokensList;

    public static void UpdateUI(){
        if (_state!=null){
            Update();

            _instance.repaint();
        }
    }

    private static void Update(){

        _instance.removeAll();
        _instance.add(_textName);

        _textName.setText("Состояние: "+_state.GetName());

        _tokensList = new ArrayList<JButton>();
        for (int i=0;i<_state.GetTokens().size();i++)
            _tokensList.add(new BToken(_state.GetTokens().get(i)));

        int ii=0;

        for (int i=0;i<_tokensList.size();i++){
            JButton tmp = _tokensList.get(i);
            tmp.setBounds(100,230+ii,300,20);
            ii+=20;
            _instance.add(_tokensList.get(i));
        }

    }

    @Override
    public void Init() {
        super.Init();

        setBackground(Color.WHITE);

        _textName = new JLabel("Состояние: ");
        _textName.setBounds(100,200,300,20);

        add(_textName);
        _instance = this;

    }
}
