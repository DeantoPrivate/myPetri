package components.Constructor;

import components.GraphicalElements.AbstractGElement;
import components.GraphicalElements.GElement;
import components.GraphicalElements.StateElement;

import javax.swing.*;
import javax.swing.colorchooser.ColorChooserComponentFactory;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by deanto on 20.04.14.
 */
public class GraphPanel extends JPanel implements MouseListener,ActionListener,MouseMotionListener {
    // buttons on top
    private JButton viewIncrease,viewReduce,viewLeft,viewRight,viewUp,viewDown;

    // button on bottom
    private JButton newState,newTransaction,deleteState,deleteTransaction;

    private ArrayList<GElement> _gElements;

    int buttonSize = 50;

    public void Init(){
        setLayout(null);

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
            newState.addActionListener(this);
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

        addMouseListener(this);
        addMouseMotionListener(this);

        _gElements = new ArrayList<GElement>();
    }

 public class BackGroundElement extends AbstractGElement{


     public BackGroundElement(GraphPanel gp) {
         super(gp);
     }

     @Override
     public void Drow() {
         _gp.getGraphics().setColor(Color.WHITE);
         _gp.getGraphics().fillRect(0,0,_gp.getWidth(),_gp.getHeight());
     }
 }

    @Override
    public void mouseClicked(MouseEvent e) {
        for(int i=0;i<_gElements.size();i++)
            if(_gElements.get(i).isOnElement(e.getPoint())){
                _gElements.get(i).ProcessMouseEvent(e);
            }
    }

    private boolean _mousePressed = false;
    private GElement elementSelected;

    @Override
    public void mousePressed(MouseEvent e) {
        for(int i=0;i<_gElements.size();i++)
            if(_gElements.get(i).isOnElement(e.getPoint())){
                elementSelected = _gElements.get(i);
                _mousePressed = true;

                    xPosLast = e.getX();
                    yPosLast = e.getY();

            }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        _mousePressed = false;
        elementSelected = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (_gElements.size()==0){
            _gElements.add(new BackGroundElement(this));
        }

        if (e.getSource()==newState){
            GElement newGState = new StateElement(this);
            _gElements.add(newGState);
            newGState.Drow();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if( elementSelected != null){
            elementSelected.ChangePos(e.getX()-xPosLast,e.getY()-yPosLast);
            xPosLast = e.getX();
            yPosLast = e.getY();
            repaintAllElements();
        }


    }

    private void repaintAllElements(){
        validate();
        repaint();

        _gElements.get(1).Drow();
           //for (int i=0;i<_gElements.size();i++)
           //     _gElements.get(i).Drow();
    }


    private int xPosLast,yPosLast;
    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
