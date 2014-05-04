package components.GraphicalElements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Created by deanto on 04.05.14.
 */
public class TransactionElement extends AbstractGElement {
    public TransactionElement(JPanel gp) {
        super(gp);

        x1=xCenterPos-width/2;
        x2=xCenterPos+width/2;

        y1=yCenterPos-heigth/2;
        y2=yCenterPos+heigth/2;


        img = new BufferedImage(width,heigth,BufferedImage.TYPE_INT_ARGB);
        Graphics2D gg = img.createGraphics();

        gg.setColor(Color.BLACK);
        gg.fillRect(0,0,width,heigth);
        gg.setColor(Color.WHITE);
        gg.fillRect(4,4,width-4*2,heigth-4*2);

    }

    @Override
    public void ProcessMouseEvent(MouseEvent e) {
        JOptionPane.showMessageDialog(null,"Transition!");
    }

    @Override
    public void Drow() {
        _gp.getGraphics().drawImage(img,xCenterPos-width/2,yCenterPos-heigth/2,width,heigth,null);
        UpdateCoords();
    }

    private void UpdateCoords(){
        x1=xCenterPos-width/2;
        x2=xCenterPos+width/2;

        y1=yCenterPos-heigth/2;
        y2=yCenterPos+heigth/2;
    }

    private int heigth = 50,width = 20;
    private int x1,y1,x2,y2;

    @Override
    public boolean isOnElement(Point p) {
        if (p.getX()>x1 && p.getX()<x2 && p.getY() > y1 && p.getY() < y2){
            return true;
        }
        return false;
    }
}
