package hr.fer.zemris.ooup.lab3.demo.example;

import javax.swing.*;
import java.awt.*;

public class ExampleComponent extends JComponent {

    public ExampleComponent() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.RED);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        g2d.drawLine(0, centerY, getWidth(), centerY);
        g2d.drawLine(centerX, 0, centerX, getHeight());

        g2d.setColor(Color.BLACK);
        g2d.drawString("Prvi red teksta", 20, centerY - 20);
        g2d.drawString("Drugi red teksta", 20, centerY + 20);
    }

}
