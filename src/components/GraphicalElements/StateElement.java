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

    private int radius=100;
    private BufferedImage img;


    @Override
    public boolean isOnElement(Point p) {
        double r = Math.sqrt((p.getX()-xCenterPos)*(p.getX()-xCenterPos)+(p.getY()-yCenterPos)*(p.getY()-yCenterPos));
        if (r<radius) return true;
        return false;
    }

    @Override
    public void ProcessMouseEvent(MouseEvent e) {
            JOptionPane.showMessageDialog(null,"!");
    }

    @Override
    public void Drow() {
        _gp.getGraphics().drawImage(img, xCenterPos - radius / 2, yCenterPos - radius / 2, null);
    }

    public StateElement(GraphPanel gp){

        super(gp);

        xCenterPos+=radius/2;
        yCenterPos+=radius/2;

        img = new BufferedImage(radius,radius,BufferedImage.TYPE_INT_ARGB);
        Graphics2D gg = img.createGraphics();

        gg.setColor(Color.BLACK);
        gg.fillOval(0,0,radius,radius);
        gg.setColor(Color.WHITE);
        gg.fillOval(5,5,radius-10,radius-10);

    }

}
