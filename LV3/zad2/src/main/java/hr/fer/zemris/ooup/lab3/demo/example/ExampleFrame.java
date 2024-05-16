package hr.fer.zemris.ooup.lab3.demo.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ExampleFrame extends JFrame implements KeyListener {

    public static String title = "Example app";

    public ExampleFrame() throws HeadlessException {
        this.initGUI();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {}

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
            this.dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {}

    private void initGUI() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocation(0, 0);
        this.setSize(600, 600);
        this.setPreferredSize(new Dimension(600, 600));
        this.setTitle(title);
        this.setFocusable(true);
        this.setLayout(new BorderLayout());
        this.addKeyListener(this);

        this.add(new ExampleComponent());
        this.pack();
        this.setVisible(true);
    }

}
