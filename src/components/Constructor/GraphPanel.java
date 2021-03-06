package components.Constructor;

import components.GraphicalElements.*;
import core.*;
import net.staticNet.netStaticImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by deanto on 20.04.14.
 */
public class GraphPanel extends JPanel implements MouseListener,ActionListener,MouseMotionListener,KeyListener {
    // buttons on top
    private JButton viewIncrease,viewReduce,viewLeft,viewRight,viewUp,viewDown;

    private netStaticImpl _net;

    // button on bottom
    private JButton newState,newTransaction,addArc,deleteState,deleteTransaction;
    private BufferedImage b2;
    private static JPanel panel;

    public static JPanel getJPanelForElements(){return panel;}

    private static ArrayList<GElement> _gElements;

    private static GraphPanel _instance;

    public static void SetElements(ArrayList<GElement> _newGElements){
        _gElements = _newGElements;
        _instance.repaintAllElements();
        _instance._net = netStaticImpl.getNet();
    }

    int buttonSize = 50;

    public void Init(){
        _instance = this;
        _net = netStaticImpl.getNet();

        setLayout(null);

        setBorder(BorderFactory.createLineBorder(Color.RED));

        panel = new JPanel();
        panel.setBounds(10,10,600-10*2,560-buttonSize);
        add(panel);

        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);

        panel.addKeyListener(this);

        b2 = new BufferedImage(800,800,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = b2.createGraphics();

        g2.setColor(Color.WHITE);
        g2.fillRect(0,0,800,800);

        int t = 800/2 - 3*buttonSize;

        viewIncrease = new JButton("+");
            viewIncrease.setBounds(t,0,buttonSize,buttonSize);
            //add(viewIncrease);
        viewReduce = new JButton("-");
            viewReduce.setBounds(t+buttonSize,0,buttonSize,buttonSize);
            //add(viewReduce);
        viewLeft = new JButton("<");
            viewLeft.setBounds(t+buttonSize*2,0,buttonSize,buttonSize);
            //add(viewLeft);
        viewRight = new JButton(">");
            viewRight.setBounds(t+buttonSize*3,0,buttonSize,buttonSize);
            //add(viewRight);
        viewUp = new JButton("^");
            viewUp.setBounds(t+buttonSize*4,0,buttonSize,buttonSize);
            //add(viewUp);
        viewDown = new JButton("v");
            viewDown.setBounds(t+buttonSize*5,0,buttonSize,buttonSize);
            //add(viewDown);


        int b = 600/2 - 2*buttonSize;

        newState = new JButton("+O");
            newState.setBounds(b,580-buttonSize,buttonSize,buttonSize);
            add(newState);
            newState.addActionListener(this);
        newTransaction = new JButton("++");
            newTransaction.setBounds(b+buttonSize,580-buttonSize,buttonSize,buttonSize);
            add(newTransaction);
            newTransaction.addActionListener(this);
        addArc = new JButton("->");
            addArc.setBounds(b+buttonSize*2,580-buttonSize,buttonSize,buttonSize);
            add(addArc);
            addArc.addActionListener(this);
        deleteState = new JButton("-O");
            deleteState.setBounds(b+buttonSize*3,580-buttonSize,buttonSize,buttonSize);
           // add(deleteState);
        deleteTransaction = new JButton("-+");
            deleteTransaction.setBounds(b+buttonSize*4,580-buttonSize,buttonSize,buttonSize);
            //add(deleteTransaction);




        //setBackground(Color.WHITE);
        setVisible(true);

        //addMouseListener(this);
        //addMouseMotionListener(this);

        _gElements = new ArrayList<GElement>();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int t=0;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isControlDown())
            TransactionAddingMode = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!e.isControlDown())
            TransactionAddingMode = false;
    }

    public class BackGroundElement extends AbstractGElement{


     public BackGroundElement(GraphPanel gp) {
         super(gp);
     }

    // @Override
    // public void Drow() {
    ////     panel.getGraphics().setColor(Color.WHITE);
    //     panel.getGraphics().fillRect(0,0,_gp.getWidth(),_gp.getHeight());
    // }
 }

    @Override
    public void mouseClicked(MouseEvent e) {

        boolean statusSet = false;

        for(int i=0;i<_gElements.size();i++)
            if(_gElements.get(i).isOnElement(e.getPoint())){
                if (TransactionAddingMode == false){

                    if (_gElements.get(i) instanceof StateElement)
                        GraphicalConstructor.ChangeStatusPanel(StateStatusPanel.StatusPanel);
                    else if (_gElements.get(i) instanceof TransactionElement)
                        GraphicalConstructor.ChangeStatusPanel(TransitionStatusPanel.StatusPanel);
                    else
                        GraphicalConstructor.ChangeStatusPanel(StatusPanel.StatusPanel);

                    statusSet = true;
                    _gElements.get(i).ProcessMouseEvent(e);

                }
                else {

                    if (forNewTransaction == null){
                        forNewTransaction = new ArrayList<GElement>();
                        forNewTransaction.add(_gElements.get(i));
                    } else {
                        if (_gElements.get(i)!=forNewTransaction.get(0)){

                            if ((_gElements.get(i) instanceof TransactionElement && forNewTransaction.get(0) instanceof StateElement) ||
                                    _gElements.get(i) instanceof StateElement && forNewTransaction.get(0) instanceof TransactionElement)
                            {
                                forNewTransaction.add(_gElements.get(i));
                                TransactionAddingMode = false;
                                AddTransaction();
                            } else {
                                JOptionPane.showMessageDialog(null,"clear adding history!");
                                forNewTransaction = null;
                                TransactionAddingMode = false;
                                addArc.setEnabled(true);
                            }

                        }
                        else {
                            JOptionPane.showMessageDialog(null,"clear adding history!");
                            forNewTransaction = null;
                            TransactionAddingMode = false;
                            addArc.setEnabled(true);
                        }
                    }


                }
            }

        if (!statusSet)
            GraphicalConstructor.ChangeStatusPanel(StatusPanel.StatusPanel);
        repaintAllElements();
    }

    private boolean _mousePressed = false;
    private GElement elementSelected;

    @Override
    public void mousePressed(MouseEvent e) {
        Point p = new Point(e.getX(),e.getY());

        for(int i=0;i<_gElements.size();i++)
            if(_gElements.get(i).isOnElement(p) && !(_gElements.get(i) instanceof TransactionRuleElement) ){
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
        if (e.getSource()==newState && TransactionAddingMode == false){
            repaintAllElements();
            StateElement newGState = new StateElement(panel,false);
            _gElements.add(newGState);
            newGState.Drow();

            State newState = new State();
            newState.ChangeName(newGState.get_name());

            _net.addState(newState,newGState);

        }
        if (e.getSource() == newTransaction && TransactionAddingMode == false){

            repaintAllElements();
            TransactionElement newTransition = new TransactionElement(panel,false);
            _gElements.add(newTransition);
            newTransition.Drow();

            Transition t = new Transition();
            t.SetName(newTransition.get_name());



            _net.addTransaction(t,newTransition);
        }

        if (e.getSource() == addArc)
        {
            addArc.setEnabled(false);
            TransactionAddingMode = true;
        }
    }

    private void AddTransaction(){
        TransactionRuleElement te = new TransactionRuleElement(panel);
        te.setStates(forNewTransaction.get(0),forNewTransaction.get(1));
        _gElements.add(te);
        newTransaction.setEnabled(true);
        forNewTransaction = null;
        addArc.setEnabled(true);
        repaintAllElements();

        if (te.isOutGoing()){
            OutgoingTransitionRule newRule = new OutgoingTransitionRule();
            newRule.setState(_net.getState((StateElement)te.getElement(1)));
            newRule.constructRule();

            _net.addTransactionRule(newRule,te);
        }else{
            IncomingTransitionRule newRule = new IncomingTransitionRule();
            newRule.setState(_net.getState((StateElement)te.getElement(0)));
            newRule.constructRule();

            _net.addTransactionRule(newRule,te);
        }

    }

    private boolean TransactionAddingMode = false;
    private ArrayList<GElement> forNewTransaction;

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

    panel.getGraphics().drawImage(b2, 0, 0, 800, 800, null);

    for (int i = 0; i < _gElements.size(); i++)
        _gElements.get(i).Drow();

    }

    public static void Repaint(){
        _instance.repaintAllElements();
    }

    private int xPosLast,yPosLast;
    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
