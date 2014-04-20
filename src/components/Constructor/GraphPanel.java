package components.Constructor;

import javax.swing.*;
import java.awt.*;

/**
 * Created by deanto on 20.04.14.
 */
public class GraphPanel extends JPanel {
    private JButton viewIncrease,viewReduce,viewLeft,viewRight,viewUp,viewDown;

    int buttonSize = 40;

    public void Init(){
        setLayout(null);
        setBounds(0,0,800,800);

        int t = 800/2 - 3*buttonSize;

        viewIncrease = new JButton("+");
            viewIncrease.setLayout(null);
            viewIncrease.setBounds(t,0,buttonSize,buttonSize);
            add(viewIncrease);
        viewReduce = new JButton("-");
            viewReduce.setBounds(t+buttonSize,0,buttonSize,buttonSize);
            add(viewReduce);
        viewLeft = new JButton("<");
            viewLeft.setBounds(t+buttonSize*2,0,buttonSize,buttonSize);
            add(viewLeft);
        viewRight = new JButton(">");
            viewRight.setBounds(t+buttonSize*3,0,buttonSize,buttonSize);
            add(viewRight);
        viewUp = new JButton("^");
            viewUp.setBounds(t+buttonSize*4,0,buttonSize,buttonSize);
            add(viewUp);
        viewDown = new JButton("v");
            viewDown.setBounds(440,0,20,20);
            add(viewDown);


        setBackground(Color.WHITE);
        setVisible(true);
    }
}
