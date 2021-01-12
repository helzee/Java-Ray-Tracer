package gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMovementInput implements MouseMotionListener {

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Point.Double newPoint = new Point.Double((e.getXOnScreen() - Driver.cenX) / 1, (e.getYOnScreen() - Driver.cenY) / 1);
        if (newPoint != new Point.Double(0,0))
            Driver.mousePosition = newPoint;
    }

}
