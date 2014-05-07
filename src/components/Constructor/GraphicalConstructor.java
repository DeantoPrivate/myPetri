package components.Constructor;

import net.staticNet.netStaticImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by deanto on 20.04.14.
 */
public class GraphicalConstructor extends JDialog implements ActionListener {

    public static String GraphicalConstructorTitle = "Construcror";

    private GraphPanel graphPanel;
    private static StatusPanel statusPanel;
    private static StateStatusPanel stateStatusPanel;

    public void Show(){
        Init();
        setVisible(true);
    }

    private void Init(){
        setTitle(GraphicalConstructorTitle);

        setLayout(null);

        graphPanel = new GraphPanel();
        graphPanel.setBounds(0,0,800,800);
        graphPanel.Init();

            add(graphPanel);

            statusPanel = new StatusPanel();
            statusPanel.setBounds(800,0,480,800);
            statusPanel.Init();
            add(statusPanel);

        stateStatusPanel = new StateStatusPanel();
        add(stateStatusPanel);

        setBounds(0, 0, 1286, 828);
        setResizable(false);

        _instance = this;

    }

    private static GraphicalConstructor _instance;
    public static GraphicalConstructor getGraphicalConstructor(){
        return _instance;
    }

    public static void ChangeStatusPanel(String panel){
        if (panel.equals(StatusPanel.StatusPanel))
        {
            stateStatusPanel.setVisible(false);
            statusPanel.setVisible(true);
            statusPanel.repaint();
        }
        if (panel.equals(StateStatusPanel.StatusPanel))
        {
            stateStatusPanel.setVisible(true);
            statusPanel.setVisible(false);
            stateStatusPanel.repaint();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
