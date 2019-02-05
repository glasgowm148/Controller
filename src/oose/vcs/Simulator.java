package oose.vcs;

import vehicle.types.Vehicle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


class Simulator extends JPanel {

    private static final int MAXIMUM_VELOCITY = 300;
    private static Timer timer;
    private BufferedImage currentObj;
    private int xPos = 0;
    private int direction = 1;

    public Simulator(Vehicle vehicle) {
        try {
            currentObj = ImageIO.read( new File( System.getProperty( "user.dir" ) + "/img/" + vehicle.getClass().getSimpleName().toLowerCase() + ".png" ) );

            int currentVelocity = 1;
            timer = new Timer( MAXIMUM_VELOCITY / currentVelocity, e -> {
                if (vehicle.getCurrentSpeed() > 0) {
                    xPos += direction;
                }

                if (xPos + currentObj.getWidth() > getWidth()) {
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


    static void updateTimer(int currentVelocity) {

        timer.setDelay( MAXIMUM_VELOCITY / currentVelocity );
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    static void StopTimer() {
        timer.stop();
    }

    @Override
    public Dimension getPreferredSize() {
        return currentObj == null ? super.getPreferredSize() : new Dimension( currentObj.getWidth() * 4, currentObj.getHeight() );
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int y = getHeight() - currentObj.getHeight();
        g.drawImage( currentObj, xPos, y, this );

    }

}