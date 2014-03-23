package components.Constructor;

import core.State;
import org.omg.DynamicAny._DynValueStub;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by deanto on 23.03.14.
 */
public class Constructor extends JDialog implements ActionListener{
    private JPanel buttons;
    private Panel panel;
    private JButton addToken,AddState,AddTransaction,AddRule;

    private class Panel extends JPanel implements MouseListener{
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2d = (Graphics2D)g;

            if (stateConstructors == null)
                stateConstructors = new ArrayList<StateConstructor>();

            for (int i=0;i<stateConstructors.size();i++)
                stateConstructors.get(i).paint(g2d);
        }


        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }



    public Constructor(){
        panel = new Panel();
        buttons = new JPanel();

        panel.addMouseListener(panel);

        setLayout(null);
        setBounds(0, 0, 700, 500);

        panel.setBounds(0,0,500,500);
        panel.setBackground(Color.DARK_GRAY);

        add(panel);

        buttons = new JPanel();
        buttons.setBounds(500,0,200,500);
        buttons.setBackground(Color.BLUE);
        buttons.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        addToken = new JButton("Add Token");
        //c.weightx = 0.2;

        //c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 0;

        addToken.setEnabled(false);
        buttons.add(addToken,c);

        AddState = new JButton("Add State");
        AddState.addActionListener(this);
        //c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 2;

        buttons.add(AddState,c);


        getContentPane().add(panel);
        getContentPane().add(buttons);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(AddState)){
            addStateConstructor(new StateConstructor());
        }
    }

    public class StateConstructor{

        private State _state;
        private BufferedImage _img;
        // center of _img on containing panel and rectangle for img
        private int x=100,y=100,height=100,widht=100;
        public int getX(){return x;}
        public int getY(){return y;}
        public void setX(int X){X=x;}
        public void setY(int X){X=y;}

        public BufferedImage GetImg(){
            if (_img==null) BuildImg();
            return _img;
        }

        private void BuildImg(){
            _img = new BufferedImage(widht,height,BufferedImage.TYPE_INT_ARGB);
            Graphics2D gimg = _img.createGraphics();
            gimg.setColor(Color.YELLOW);
            gimg.fillOval(0, 0, widht, height);
            gimg.setColor(Color.GREEN);
            gimg.drawString("state name",0,0);
        }

        public void SetState(State state){
            _state = state;
        }
        public State GetState(){
            return _state;
        }

        public void paint(Graphics2D g2d){
            g2d.drawImage(GetImg(),x-height/2,y-widht/2,null);
        }

    }


    private ArrayList<StateConstructor> stateConstructors;
    private void addStateConstructor(StateConstructor stateConstructor){
        if (stateConstructor == null) stateConstructors = new ArrayList<StateConstructor>();
        stateConstructors.add(stateConstructor);

        panel.validate();
        panel.repaint();
    }

}
