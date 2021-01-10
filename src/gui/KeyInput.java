package gui;

import mathematics.Vec3;
import shapes.Scene;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W')
            View.activeKeys[0] = true;

        if (e.getKeyChar() == 's' || e.getKeyChar() == 'S')
            View.activeKeys[2] = true;

        if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A')
            View.activeKeys[1] = true;

        if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D')
            View.activeKeys[3] = true;

        if (e.getKeyCode() == KeyEvent.VK_SHIFT)
            View.activeKeys[4] = true;

        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            View.activeKeys[5] = true;

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            View.requestStop = true;
            View.window.frame.setVisible(false);
            View.window.frame.dispose();
        }

        if (e.getKeyChar() == 'g')
            Scene.flag = true;

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W')
            View.activeKeys[0] = false;

        if (e.getKeyChar() == 's' || e.getKeyChar() == 'S')
            View.activeKeys[2] = false;

        if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A')
            View.activeKeys[1] = false;

        if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D')
            View.activeKeys[3] = false;

        if (e.getKeyCode() == KeyEvent.VK_SHIFT)
            View.activeKeys[4] = false;

        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            View.activeKeys[5] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}
