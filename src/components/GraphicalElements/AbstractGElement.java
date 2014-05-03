package components.GraphicalElements;

import components.Constructor.GraphPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by deanto on 02.05.14.
 */
public class AbstractGElement implements GElement {

    protected int xCenterPos=100, yCenterPos=100;
    protected JPanel _gp;
    public AbstractGElement(JPanel gp){
        _gp=gp;
    }

    @Override
    public boolean isOnElement(Point p) {
        return false;
    }

    @Override
    public void ProcessMouseEvent(MouseEvent e) {

    }

    @Override
    public void Drow() {

    }

    @Override
    public void ChangePos(int xChange, int yChange) {
        xCenterPos+=xChange;
        yCenterPos+=yChange;
    }
}
