package components.Constructor;

import javax.swing.*;
import java.awt.*;

/**
 * Created by deanto on 20.04.14.
 */
public class GraphPanel extends JPanel {
    // buttons on top
    private JButton viewIncrease,viewReduce,viewLeft,viewRight,viewUp,viewDown;

    // button on bottom
    private JButton newState,newTransaction,deleteState,deleteTransaction;

    int buttonSize = 50;

    public void Init(){
        setLayout(null);
        setBounds(0,0,800,800);

        setBorder(BorderFactory.createLineBorder(Color.RED));

        int t = 800/2 - 3*buttonSize;

        viewIncrease = new JButton("+");
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
            viewDown.setBounds(t+buttonSize*5,0,buttonSize,buttonSize);
            add(viewDown);


        int b = 800/2 - 2*buttonSize;

        newState = new JButton("+O");
            newState.setBounds(b,800-buttonSize,buttonSize,buttonSize);
            add(newState);
        newTransaction = new JButton("++");
            newTransaction.setBounds(b+buttonSize,800-buttonSize,buttonSize,buttonSize);
            add(newTransaction);
        deleteState = new JButton("-O");
            deleteState.setBounds(b+buttonSize*2,800-buttonSize,buttonSize,buttonSize);
            add(deleteState);
        deleteTransaction = new JButton("-+");
            deleteTransaction.setBounds(b+buttonSize*3,800-buttonSize,buttonSize,buttonSize);
            add(deleteTransaction);

        setBackground(Color.WHITE);
        setVisible(true);
    }
}
