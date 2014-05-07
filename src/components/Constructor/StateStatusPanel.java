package components.Constructor;

import core.State;
import net.staticNet.StateWrap;

import javax.swing.*;
import java.awt.*;

/**
 * Created by deanto on 20.04.14.
 */
public class StateStatusPanel extends StatusPanel {

    public static String StatusPanel = "StateStatusPanel";

    public static StateWrap _stateWrap;
    public static void setState(StateWrap stateWrap){
        _stateWrap = stateWrap;
    }

    @Override
    public void Init() {
        super.Init();

        setBackground(Color.GREEN);
    }
}
