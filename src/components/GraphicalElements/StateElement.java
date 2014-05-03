package components.GraphicalElements;

import components.Constructor.GraphPanel;
import org.w3c.dom.css.RGBColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Created by deanto on 02.05.14.
 */
public class StateElement extends AbstractGElement {

    private int radius=50;

    @Override
    public boolean isOnElement(Point p) {
        double r = Math.sqrt((p.getX()-xCenterPos)*(p.getX()-xCenterPos)+(p.getY()-yCenterPos)*(p.getY()-yCenterPos));
        if (r<radius){

            return true;
        }
        return false;
    }

    @Override
    public void ProcessMouseEvent(MouseEvent e) {
            JOptionPane.showMessageDialog(null,"!");
    }

    @Override
    public void Drow() {
        _gp.getGraphics().drawImage(img,xCenterPos-radius,yCenterPos-radius,radius*2,radius*2,null);
    }

    public StateElement(JPanel gp){

        super(gp);

        img = new BufferedImage(radius*2,radius*2,BufferedImage.TYPE_INT_ARGB);
        Graphics2D gg = img.createGraphics();

        gg.setColor(Color.BLACK);
        gg.fillOval(0,0,radius*2,radius*2);
        gg.setColor(Color.WHITE);
        gg.fillOval(5,5,radius*2-10,radius*2-10);

    }

    public int getXcenter(){return xCenterPos;}
    public int getYcenter(){return yCenterPos;}
    public int getRadius(){return radius;}

}
