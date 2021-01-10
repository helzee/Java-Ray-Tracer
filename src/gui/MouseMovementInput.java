package gui;

import mathematics.Vec3;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMovementInput implements MouseMotionListener {

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int cenX = View.WIDTH / 2 + View.window.frame.getX(), cenY = View.HEIGHT / 2 + View.window.frame.getY();

        View.mousePosition = new Point(e.getXOnScreen() - cenX, e.getYOnScreen() - cenY);
        View.robot.mouseMove(cenX, cenY);
    }

}
