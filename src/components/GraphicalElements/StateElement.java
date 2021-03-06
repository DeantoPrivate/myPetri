package components.GraphicalElements;

import com.sun.org.apache.regexp.internal.recompile;
import components.Constructor.GraphPanel;
import components.Constructor.StateStatusPanel;
import net.staticNet.netStaticImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Created by deanto on 02.05.14.
 */
public class StateElement extends AbstractGElement {

    private int radius=30;
    private String _name = "State";
    public String get_name(){return _name;}

    private StateElementInfo _lastInfo;
    public void SetInfo(StateElementInfo newInfo){
        _lastInfo = newInfo;
    }

    public void setValues(String name,int r,int xC,int yC){
        _name = name;
        //radius = r;

        xCenterPos = xC;
        yCenterPos = yC;
    }

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

        /*
        components.TokenManager.Dialog d = Dialog.getInstanse();
        if (d!=null){
        Token t = d.selectAndGetToken();
        netStaticImpl.getNet().getState(this).LocateToken(t);

        }*/
        StateStatusPanel.ShowState(netStaticImpl.getNet().getState(this));
    }

    @Override
    public void Drow() {

        if (_lastInfo.isChanged()){
            UpdateImg();
        }

       _gp.getGraphics().drawImage(img,xCenterPos-radius,yCenterPos-radius,radius*2+200,radius*2,null);
    }

    private void UpdateImg(){
        img = new BufferedImage(radius*2+200,radius*2,BufferedImage.TYPE_INT_ARGB);
        Graphics2D gg = img.createGraphics();

        gg.setColor(Color.BLACK);
        gg.fillOval(0,0,radius*2,radius*2);
        gg.setColor(Color.WHITE);
        gg.fillOval(3,3,radius*2-6,radius*2-6);

        String tmpName = _name;

        if (_lastInfo.isChanged()){
            tmpName += " ::"+_lastInfo.getTokens();
            _lastInfo.ChangesConsidered();
        }

        gg.setColor(Color.BLACK);
        Font font1 = new Font("Arial", Font.PLAIN, 15);
        gg.setFont(font1);
        gg.drawString(tmpName,radius*2,radius);
    }

    public StateElement(JPanel gp, boolean read){

        super(gp);

        img = new BufferedImage(radius*2+200,radius*2,BufferedImage.TYPE_INT_ARGB);
        Graphics2D gg = img.createGraphics();

        gg.setColor(Color.BLACK);
        gg.fillOval(0,0,radius*2,radius*2);
        gg.setColor(Color.WHITE);
        gg.fillOval(3,3,radius*2-6,radius*2-6);

        if (!read){
            String newName = JOptionPane.showInputDialog("Укажите имя для нового состояния.");
            if (newName!=null && !newName.equals("")){
                _name=newName;
            }
        }

        gg.setColor(Color.BLACK);
        Font font1 = new Font("Arial", Font.PLAIN, 15);
        gg.setFont(font1);
        gg.drawString(_name,radius*2, radius);

        _lastInfo = new StateElementInfo();
    }

    public int getXcenter(){return xCenterPos;}
    public int getYcenter(){return yCenterPos;}
    public int getRadius(){return radius;}

}
