package oose.vcs;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Simulator extends JPanel {
    protected BufferedImage boat;
    protected int xPos = 0;

    @Override
    public Dimension getPreferredSize() {
        return boat == null ? super.getPreferredSize() : new Dimension(boat.getWidth() * 4, boat.getHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int y = getHeight() - boat.getHeight();
        g.drawImage(boat, xPos, y, this);

    }
}
