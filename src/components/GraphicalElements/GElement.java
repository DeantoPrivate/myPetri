package components.GraphicalElements;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by deanto on 02.05.14.
 *
 * представляет всеэлементы на панели
 *
 */
public interface GElement {
    boolean isOnElement(Point p);
    void ProcessMouseEvent(MouseEvent e);
    void Drow();
    void ChangePos(int xChange, int yChange);
    int getXcenter();
    int getYcenter();
}
