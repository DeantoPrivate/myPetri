package components.Constructor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by deanto on 20.04.14.
 */
public class GraphicalConstructor extends JDialog implements ActionListener {

    public static String GraphicalConstructorTitle = "Construcror";

    private GraphPanel graphPanel;

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

        setBounds(0, 0, 1280, 800);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
