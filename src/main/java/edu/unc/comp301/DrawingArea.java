package edu.unc.comp301;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DrawingArea extends JPanel implements KeyListener {
    public DrawingArea() {
        setFocusable(true);
        addKeyListener(this);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillRect(100, 100, 100, 100);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getID()) {
            case KeyEvent.VK_KP_UP:
                System.out.println("Up");
            case KeyEvent.VK_KP_DOWN:
                System.out.println("Down");
            case KeyEvent.VK_KP_RIGHT:
                System.out.println("Right");
            case KeyEvent.VK_KP_LEFT:
                System.out.println("Left");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
