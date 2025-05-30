package edu.unc.comp301;
import javax.swing.*;

public class Screen extends JFrame {

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
        DrawingArea drawingArea = new DrawingArea();
        getContentPane().add(drawingArea);
    }
}
