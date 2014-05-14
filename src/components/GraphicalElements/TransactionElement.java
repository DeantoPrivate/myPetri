package components.GraphicalElements;

import components.Constructor.TransitionStatusPanel;
import net.staticNet.netStaticImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Created by deanto on 04.05.14.
 */
public class TransactionElement extends AbstractGElement {

    private String _name = "Transaction";
    public String get_name(){return _name;}

    private TransactionElementInfo _info;
    public void setInfo(TransactionElementInfo info){
        _info = info;
    }

    BufferedImage activated;

    public TransactionElement(JPanel gp) {
        super(gp);

        x1=xCenterPos-width/2;
        x2=xCenterPos+width/2;

        y1=yCenterPos-heigth/2;
        y2=yCenterPos+heigth/2;

        activated = new BufferedImage(width,heigth,BufferedImage.TYPE_INT_ARGB);
        Graphics2D gg2 = activated.createGraphics();
        gg2.setColor(Color.RED);
        gg2.fillRect(0,0,width,heigth);

        img = new BufferedImage(width+300,heigth,BufferedImage.TYPE_INT_ARGB);
        Graphics2D gg = img.createGraphics();

        gg.setColor(Color.BLACK);
        gg.fillRect(0,0,width,heigth);
        gg.setColor(Color.WHITE);
        gg.fillRect(4,4,width-4*2,heigth-4*2);

        String newName = JOptionPane.showInputDialog("Укажите имя для нового перехода.");
        if (newName!=null && !newName.equals("")){
            _name=newName;
        }

        gg.setColor(Color.BLACK);
        Font font1 = new Font("Arial", Font.PLAIN, 20);
        gg.setFont(font1);
        gg.drawString(_name, 20, heigth);

        _info = new TransactionElementInfo();

    }

    @Override
    public void ProcessMouseEvent(MouseEvent e) {
        TransitionStatusPanel.ShowTransaction(netStaticImpl.getNet().getTransition(this));
    }

    @Override
    public void Drow() {

        if (_info.isChanged()){
            _info.ChangesConsidered();
            _gp.getGraphics().drawImage(activated,xCenterPos-width/2,yCenterPos-heigth/2,width,heigth,null);
            try{
                //TODO sleep is a bad way. it should be several threads for working with difference parts of the program
            Thread.sleep(100);
            }catch (Exception e){}
        }

        _gp.getGraphics().drawImage(img,xCenterPos-width/2,yCenterPos-heigth/2,width+300,heigth,null);
        UpdateCoords();
    }

    @Override
    public int getYcenter() {
        return yCenterPos;
    }

    @Override
    public int getXcenter() {
        return xCenterPos;
    }

    private void UpdateCoords(){
        x1=xCenterPos-width/2;
        x2=xCenterPos+width/2;

        y1=yCenterPos-heigth/2;
        y2=yCenterPos+heigth/2;
    }

    private int heigth = 70,width = 10;
    private int x1,y1,x2,y2;

    @Override
    public boolean isOnElement(Point p) {
        if (p.getX()>x1 && p.getX()<x2 && p.getY() > y1 && p.getY() < y2){
            return true;
        }
        return false;
    }
}
