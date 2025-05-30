package edu.unc.comp301;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;

public class Screen extends JFrame implements ActionListener {
    JButton jButton;
    public Screen() {
        super("UNCTale");
        initializeScreen();
        addComponentsToPane();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initializeScreen() {
        setVisible(true);
        setResizable(false);
        setSize(getToolkit().getScreenSize());
    }

    private void addComponentsToPane() {
        jButton = new JButton("Click to start!");
        jButton.setSize(100, 100);
        jButton.setVisible(true);
        jButton.addActionListener(this);
        getContentPane().add(jButton, BorderLayout.CENTER);
    }

    private void drawGame() {
        DrawingArea drawingArea = new DrawingArea();
        getContentPane().add(drawingArea);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Start button pressed!");
        jButton.setVisible(false);
        drawGame();
    }
}
