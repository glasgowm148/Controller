package oose.vcs;

import vehicle.types.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;



public class Controller {

    private static Vehicle vehicle;
    private static JLabel speedLabel;
    private static Timer timer;
    private static int currentVelocity = 1;


    private JComboBox <String> comboBox;
    private static int MAXIMUM_VELOCITY = 300;
    private String[] vehicles = {"Boat", "Ship", "Truck", "Motorcycle", "Bus", "Car", "Bicycle", "Helicopter", "Airplane", "Tram", "Train"};
    private Simulator simulationPane;
    private JFrame frame;

    public Controller() {

        EventQueue.invokeLater( () -> {
            try {
                UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
            } catch (Exception e) {
                e.printStackTrace();
            }

            comboBox = new JComboBox <>( vehicles );
            comboBox.setSelectedIndex( 6 );
            comboBox.addActionListener( e -> initialiseVehicle( vehicles[comboBox.getSelectedIndex()] ) );

            speedLabel = new JLabel( "          " );

            if (vehicle == null) {
                initialiseVehicle( vehicles[comboBox.getSelectedIndex()] );
                speedLabel.setText( vehicle.printSpeed() );
            }
            if (simulationPane != null) frame.remove( simulationPane );


            simulationPane = new Simulator();
            frame = new JFrame( "Vehicle Control System" );
            frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
            frame.setLayout( new BorderLayout() );

            frame.add( simulationPane, BorderLayout.CENTER );
            frame.revalidate();
            frame.repaint();

            JToolBar toolBar = new JToolBar();
            toolBar.setRollover(true);
            toolBar.add(comboBox);
            toolBar.add(speedLabel);

            frame = new TestButton().createAndShowGUI( frame, toolBar );

            frame.setPreferredSize( new Dimension( 800, 200 ) );
            frame.pack();
            frame.setLocationRelativeTo( null );
            frame.setVisible( true );


        } );
    }

    public static void main(String args[]) {
        new Controller();
    }

    private static void moveIt(JButton currentButton) {
        new Thread( () -> {
            try {
                for (; !currentButton.getText().equals( "Stop" ); speedLabel.setText( vehicle.printSpeed() )) {
                    Thread.sleep( 2000 );

                    if ((currentButton.getText().equals( "Accelerate" )) && (currentVelocity <= MAXIMUM_VELOCITY))
                        ++currentVelocity;
                    else if ((currentButton.getText().equals( "Decelerate" )) && (currentVelocity >= 1))
                        --currentVelocity;
                    vehicle.setCurrentSpeed( currentVelocity );
                }
                currentVelocity = 0;
                vehicle.setCurrentSpeed( currentVelocity );
                timer.setDelay( currentVelocity == 0 ? MAXIMUM_VELOCITY : MAXIMUM_VELOCITY / currentVelocity );
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        } ).start();
    }


    protected static void config(JButton currentButton) {
        currentButton.addActionListener( e -> {
            moveIt( currentButton );
        } );

    }


    private void initialiseVehicle(String vehicleName) {
        switch (vehicleName) {
            case "Boat":
                vehicle = new Boat( "Apollo " );
                break;
            case "Ship":
                vehicle = new Ship( "Cruizz" );
                break;
            case "Truck":
                vehicle = new Truck( "Ford F-650" );
                break;
            case "Motorcycle":
                vehicle = new Motorcycle( "Suzuki" );
                break;
            case "Bus":
                vehicle = new Bus( "Aero" );
                break;
            case "Car":
                vehicle = new Car( "BMW" );
                break;
            case "Bicycle":
                vehicle = new Bicycle( "A-bike" );
                break;
            case "Helicopter":
                vehicle = new Helicopter( "Eurocopter" );
                break;
            case "Airplane":
                vehicle = new Airplane( "BA" );
                break;
            case "Tram":
                vehicle = new Tram( "EdinburghTram" );
                break;
            case "Train":
                vehicle = new Train( "Virgin", 4 );
                break;
        }
    }


    public class Simulator extends oose.vcs.Simulator {

        private int direction = 1;
        private File file;

        public Simulator() {
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

    }
}