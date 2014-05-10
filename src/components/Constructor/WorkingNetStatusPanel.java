package components.Constructor;

import java.awt.*;

/**
 * Created by deanto on 20.04.14.
 */
public class WorkingNetStatusPanel extends StatusPanel {

    private static WorkingNetStatusPanel _instance;
    public static WorkingNetStatusPanel getInstance(){return _instance;}

    @Override
    public void Init() {
        super.Init();

        _instance = this;

        setBackground(Color.RED);
    }
}
