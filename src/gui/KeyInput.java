package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class KeyInput implements KeyListener {

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W)
            View.activeKeys[0] = true;

        if (e.getKeyCode() == KeyEvent.VK_S)
            View.activeKeys[2] = true;

        if (e.getKeyCode() == KeyEvent.VK_A)
            View.activeKeys[1] = true;

        if (e.getKeyCode() == KeyEvent.VK_D)
            View.activeKeys[3] = true;

        if (e.getKeyCode() == KeyEvent.VK_SHIFT)
            View.activeKeys[4] = true;

        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            View.activeKeys[5] = true;

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            View.requestStop = true;

        if (e.getKeyCode() == KeyEvent.VK_P) {
            try {
                ImageFactory.create();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            System.out.println("Created new image.");
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W)
            View.activeKeys[0] = false;

        if (e.getKeyCode() == KeyEvent.VK_S)
            View.activeKeys[2] = false;

        if (e.getKeyCode() == KeyEvent.VK_A)
            View.activeKeys[1] = false;

        if (e.getKeyCode() == KeyEvent.VK_D)
            View.activeKeys[3] = false;

        if (e.getKeyCode() == KeyEvent.VK_SHIFT)
            View.activeKeys[4] = false;

        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            View.activeKeys[5] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO: some sort of command line?
    }

}
