package components.GraphicalElements;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

/**
 * Created by deanto on 03.05.14.
 */
public class TransactionElement extends AbstractGElement {

    private StateElement s1,s2;
    public void setStates(StateElement incoming, StateElement outgoing){
        s1 = incoming;
        s2 = outgoing;
    }

    public TransactionElement(JPanel gp) {
        super(gp);
    }

    private int s1x,s1y,s2x,s2y;

    private void UpdateCoords(){
        s1x=s1.getXcenter();
        s1y=s1.getYcenter();

        s2x=s2.getXcenter();
        s2y=s2.getYcenter();

    }

    @Override
    public void Drow() {
        UpdateCoords();
        ((Graphics2D)_gp.getGraphics()).setStroke(new BasicStroke(5));
        _gp.getGraphics().setColor(Color.GREEN);
        ((Graphics2D) _gp.getGraphics()).draw(new Line2D.Float(s1x, s1y, s2x, s2y));
    }
}
