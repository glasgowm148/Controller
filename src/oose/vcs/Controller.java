package oose.vcs;

import vehicle.types.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class Controller {

	private Vehicle vehicle;
	private String[] vehicles = { "Boat", "Ship", "Truck", "Motorcycle", "Bus", "Car", "Bicycle", "Helicopter", "Airplane", "Tram", "Train"};
	private Simulator simulationPane;
	private JLabel speedLabel;

    private JButton bStart = new JButton("start");
    private JButton bAccel = new JButton("accelerate");
    private JButton bCruise = new JButton("cruise");
    private JButton bDecel = new JButton("decelerate");
    private JButton bStop = new JButton("stop");

    private JComboBox <String> comboBox;
	private JFrame frame;
	private Timer timer;

	private int currentVelocity = 1;
	private int maximumVelocity = 300;

	public static void main(String args[]) {
		new Controller();
	}

	public Controller() {

		EventQueue.invokeLater(() -> {
			try {
				UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
			} catch (Exception e) {
				e.printStackTrace();
			}
			frame = new JFrame("Vehicle Control System");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLayout(new BorderLayout());

			comboBox = new JComboBox <>(vehicles);
			comboBox.setSelectedIndex(6);
			comboBox.addActionListener(e -> initialiseVehicle(vehicles[comboBox.getSelectedIndex()]));

			speedLabel = new JLabel("          ");


            if (vehicle == null) {
                initialiseVehicle(vehicles[comboBox.getSelectedIndex()]);
                speedLabel.setText(vehicle.printSpeed());
            }
            if (simulationPane != null) frame.remove(simulationPane);

            simulationPane = new Simulator();
            frame.add(simulationPane, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();

            JToolBar toolBar = new JToolBar();
            toolBar.setRollover(true);

            config(bStart, Motion.FALSE);

            config(bAccel, Motion.ACCELERATE);
            config(bCruise, Motion.CRUISE);
            config(bDecel, Motion.DECELERATE);
            config(bStop, Motion.STOP);

            toolBar.add(comboBox);
            toolBar.add(speedLabel);
            toolBar.add(bStart);
            toolBar.add(bAccel);
            toolBar.add(bCruise);
            toolBar.add(bDecel);
            toolBar.add(bStop);


			frame.add(toolBar,BorderLayout.NORTH);
			frame.setPreferredSize(new Dimension(800,200));
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}

	private void setButton(JButton CurrentButton) {
        bStart.setBackground(Color.lightGray);
        bAccel.setBackground(Color.lightGray);
        bCruise.setBackground(Color.lightGray);
        bDecel.setBackground(Color.lightGray);
        bStop.setBackground(Color.lightGray);

		CurrentButton.setBackground(Color.green);
	}

	private void moveIt(Motion m){
		(new Thread(() -> {
			try {
                for (; m != Motion.STOP; speedLabel.setText(vehicle.printSpeed())) {
					Thread.sleep(2000);
					timer.setDelay(currentVelocity == 0 ? maximumVelocity : maximumVelocity / currentVelocity);

                    if ((m == Motion.ACCELERATE) && (currentVelocity <= maximumVelocity))
						++currentVelocity;
                    else if ((m == Motion.DECELERATE) && (currentVelocity >= 1))
						--currentVelocity;
					vehicle.setCurrentSpeed(currentVelocity);
				}
				currentVelocity = 0;
				vehicle.setCurrentSpeed(currentVelocity);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		})).start();
	}


    private void config(JButton button, Motion m) {
        button.addActionListener(e -> {
            moveIt(m);
            setButton(button);
		});

	}


	private void initialiseVehicle(String vehicleName) {
		switch (vehicleName) {
			case "Boat":
				vehicle = new Boat("Apollo ");
				break;
			case "Ship":
				vehicle = new Ship("Cruizz");
				break;
			case "Truck":
				vehicle = new Truck("Ford F-650");
				break;
			case "Motorcycle":
				vehicle = new Motorcycle("Suzuki");
				break;
			case "Bus":
				vehicle = new Bus("Aero");
				break;
			case "Car":
				vehicle = new Car("BMW");
				break;
			case "Bicycle":
				vehicle = new Bicycle("A-bike");
				break;
			case "Helicopter":
				vehicle = new Helicopter("Eurocopter");
				break;
			case "Airplane":
				vehicle = new Airplane("BA");
				break;
			case "Tram":
				vehicle = new Tram("EdinburghTram");
				break;
			case "Train":
				vehicle = new Train("Virgin", 4);
				break;
		}
	}


	public class Simulator extends oose.vcs.Simulator {

		private int direction = 1;
		private File file;

		public Simulator() {
			file = new File(System.getProperty("user.dir")+ "/img/" + vehicle.getClass().getSimpleName().toLowerCase() + ".png");
			try {
				boat = ImageIO.read(file);
				timer = new Timer(maximumVelocity/currentVelocity, e -> {
					xPos += direction;
					if (xPos + boat.getWidth() > getWidth()) {
						xPos = 0;
						direction *= -1;

					} else if (xPos < 0) {
						xPos = 0;
						direction *= -1;
					}
					repaint();
				});
				timer.setRepeats(true);
				timer.setCoalesce(true);
				timer.start();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

}

enum Motion {
	ACCELERATE, DECELERATE, CRUISE, STOP, FALSE
}
