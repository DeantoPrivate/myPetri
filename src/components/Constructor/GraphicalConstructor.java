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
    private StatusPanel statusPanel;

    Integer a;

    Integer getA(){
        return a;
    }

    Integer getB(){
        return new Integer(2);
    }

    public void Show(){
        if (getA()==getB())
        {
            int t=0;
        }
        int e=0;
        //Init();
        //setVisible(true);
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



        setBounds(0, 0, 1286, 828);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
