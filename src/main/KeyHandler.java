package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed; // movement keys pressed
    boolean debug = false; // debugging

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Checks and updates if key is pressed
     * @param e KeyEvent
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = true;
            downPressed = false;
            leftPressed = false;
            rightPressed = false;
        } else if (code == KeyEvent.VK_S) {
            upPressed = false;
            downPressed = true;
            leftPressed = false;
            rightPressed = false;
        } else if (code == KeyEvent.VK_A) {
            upPressed = false;
            downPressed = false;
            leftPressed = true;
            rightPressed = false;
        } else if (code == KeyEvent.VK_D) {
            upPressed = false;
            downPressed = false;
            leftPressed = false;
            rightPressed = true;
        }

        // Debugging
        if (code == KeyEvent.VK_T) {
            debug = !debug;
        }
    }

    /**
     * Checks and updates if key is released
     * @param e KeyEvent
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        } else if (code == KeyEvent.VK_S) {
            downPressed = false;
        } else if (code == KeyEvent.VK_A) {
            leftPressed = false;
        } else if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}
