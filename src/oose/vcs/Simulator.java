package oose.vcs;

import vehicle.types.Vehicle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


class Simulator extends JPanel {

    static int MAXIMUM_VELOCITY = 300;
    private static Timer timer;
    private final int currentVelocity = 1;
    private BufferedImage boat;
    private int xPos = 0;
    private int direction = 1;
    private File file;

    public Simulator(Vehicle vehicle) {
        file = new File( System.getProperty( "user.dir" ) + "/img/" + vehicle.getClass().getSimpleName().toLowerCase() + ".png" );
        try {
            boat = ImageIO.read( file );

            timer = new Timer( MAXIMUM_VELOCITY / currentVelocity, e -> {
                xPos += direction;
                if (xPos + boat.getWidth() > getWidth()) {
                    xPos = 0;
                    direction *= -1;

                } else if (xPos < 0) {
                    xPos = 0;
                    direction *= -1;
                }
                repaint();
            } );
            timer.setRepeats( true );
            timer.setCoalesce( true );
            timer.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public static void updateTimer(int currentVelocity) {
        timer.setDelay( MAXIMUM_VELOCITY / currentVelocity );
    }

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