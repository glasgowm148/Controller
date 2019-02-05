package oose.vcs;

import vehicle.types.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Controller {

    private Simulator simulationPane;
    private static final int MAXIMUM_VELOCITY = 300;
    private JFrame frame;
    private static int currentVelocity = 1;
    private static Vehicle vehicle;
    private static JLabel speedLabel;
    private final String[] vehicles = {"Boat", "Ship", "Truck", "Motorcycle", "Bus", "Car", "Bicycle", "Helicopter", "Airplane", "Tram", "Train"};
    private JComboBox <String> comboBox;


    Controller() {

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
            if (simulationPane != null) {
                frame.remove( simulationPane );
            }


            simulationPane = new Simulator( vehicle );
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

            frame = new ButtonSetup().createAndShowButton( frame, toolBar );

            frame.setPreferredSize( new Dimension( 800, 200 ) );
            frame.pack();
            frame.setLocationRelativeTo( null );
            frame.setVisible( true );


        } );
    }

    public static void main(String args[]) {
        new Controller();
    }

    private static void setVelocity(JToggleButton currentButton, ActionEvent e) {
        new Thread( () -> {
            try {

                for (; !currentButton.getText().equals( "Stop" ) && currentButton.isSelected(); speedLabel.setText( vehicle.printSpeed() )) {
                    Thread.sleep( 2000 );
                    if ((currentButton.getText().equals( "Accelerate" )) && currentButton.isSelected() && (currentVelocity <= MAXIMUM_VELOCITY))
                        currentVelocity++;
                    else if ((currentButton.getText().equals( "Decelerate" )) && currentButton.isSelected() && (currentVelocity >= 1))
                        currentVelocity--;
                    else if ((currentButton.getText().equals( "Start" )) && currentButton.isSelected())
                        currentVelocity = 1;
                    vehicle.setCurrentSpeed( currentVelocity );
                    if (currentVelocity != 0) {
                        Simulator.updateTimer( currentVelocity );
                    } else {
                        Simulator.StopTimer();
                    }
                }
                if (currentButton.getText().equals( "Stop" ) && currentButton.isSelected()) {
                    currentVelocity = 0;
                    vehicle.setCurrentSpeed( currentVelocity );
                    Simulator.StopTimer();
                }

            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        } ).start();
    }


    static void config(JToggleButton currentButton) {
        currentButton.addActionListener( e -> setVelocity( currentButton, e ) );

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


}