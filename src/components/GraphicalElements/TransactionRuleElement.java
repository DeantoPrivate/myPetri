package components.GraphicalElements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

/**
 * Created by deanto on 03.05.14.
 */
public class TransactionRuleElement extends AbstractGElement {

    private GElement s1,s2;
    public void setStates(GElement incoming, GElement outgoing){
        s1 = incoming;
        s2 = outgoing;
    }

    public GElement getElement(int index){
        if (index == 0) return s1;
        if (index == 1) return s2;
        return null;
    }

    public boolean isOutGoing(){
        if (s1 instanceof TransactionElement) return true;
        return false;
    }

    public TransactionRuleElement(JPanel gp) {
        super(gp);
    }

    private int s1x,s1y,s2x,s2y;
    private int pcenterx,pcentery,radius=5;

    public boolean isOnElement(Point p) {
        double r = Math.sqrt((p.getX()-pcenterx)*(p.getX()-pcenterx)+(p.getY()-pcentery)*(p.getY()-pcentery));
        if (r<radius){
            return true;
        }
        return false;
    }

    private void UpdateCoords(){

        // general line between states
        s1x=s1.getXcenter();
        s1y=s1.getYcenter();

        s2x=s2.getXcenter();
        s2y=s2.getYcenter();

        // perpendicular line


        pcenterx = s1x + (s2x-s1x)/2;
        pcentery = s1y + (s2y-s1y)/2;



    }

    @Override
    public void ChangePos(int xChange, int yChange) {

    }

    @Override
    public void ProcessMouseEvent(MouseEvent e) {
        if (e.equals(MouseEvent.MOUSE_CLICKED)){
            JOptionPane.showMessageDialog(null,"clicked on transaction");
        }
    }

    @Override
    public void Drow() {
        UpdateCoords();

        Graphics2D g = (Graphics2D)_gp.getGraphics();
        g.setColor(Color.LIGHT_GRAY);
        g.setStroke(new BasicStroke(2));
        g.drawLine(s1x, s1y, s2x, s2y);

        g.fillOval(pcenterx-radius,pcentery-radius,radius*2,radius*2);

    }
}
