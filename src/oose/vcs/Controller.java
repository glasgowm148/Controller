package oose.vcs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.Timer;
import javax.swing.UIManager;

import vehicle.types.Airplane;
import vehicle.types.Bicycle;
import vehicle.types.Boat;
import vehicle.types.Bus;
import vehicle.types.Car;
import vehicle.types.Helicopter;
import vehicle.types.Motorcycle;
import vehicle.types.Ship;
import vehicle.types.Train;
import vehicle.types.Tram;
import vehicle.types.Truck;
import vehicle.types.Vehicle;


public class Controller {

	private Vehicle vehicle;
	private String[] vehicles = { "Boat", "Ship", "Truck", "Motorcycle", "Bus", "Car", "Bicycle", "Helicopter", "Airplane", "Tram", "Train"};
	private Simulator simulationPane;
	private JLabel speedlabel;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;
	private JButton button5;
	private JComboBox<String> combobox;
	private JFrame frame;

	private boolean accelerate, decelerate, cruise,stop;
	int currentvelocity = 1;
	int maximumvelocity = 300;

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

			combobox = new JComboBox <>(vehicles);
			combobox.setSelectedIndex(6);
			combobox.addActionListener(e -> {
				int selectedIndex = combobox.getSelectedIndex();
				String vehicleName = vehicles[selectedIndex];
				initialiseVehicle(vehicleName);
			});

			speedlabel = new JLabel("          ");

			configStart();
			configAccelerate();
			configDecelerate();
			configCruise();
			configStop();

			JToolBar toolBar =new JToolBar();
			toolBar.setRollover(true);

			toolBar.add(combobox);
			toolBar.add(speedlabel);
			toolBar.add(button1);
			toolBar.add(button2);
			toolBar.add(button3);
			toolBar.add(button4);
			toolBar.add(button5);

			frame.add(toolBar,BorderLayout.NORTH);
			frame.setPreferredSize(new Dimension(800,200));
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
	
	private void configStart() {
		button1 = new JButton("start");
		button1.setBackground(Color.lightGray);
		button1.addActionListener(e -> {
			if(vehicle == null) {
				int selectedIndex = combobox.getSelectedIndex();
				String vehicleName = vehicles[selectedIndex];
				initialiseVehicle(vehicleName);
				speedlabel.setText(vehicle.printSpeed());
			}
			if(simulationPane !=null) {
				frame.remove(simulationPane);
			}
			accelerate = false;
			decelerate = false;
			cruise = false;
			stop = false;
			button1.setBackground(Color.GREEN);
			button2.setBackground(Color.lightGray);
			button3.setBackground(Color.lightGray);
			button4.setBackground(Color.lightGray);
			button5.setBackground(Color.lightGray);

			simulationPane = new Simulator();
			frame.add(simulationPane,BorderLayout.CENTER);
			frame.revalidate();
			frame.repaint();
		});
	}

	private void configAccelerate() {
		button2 = new JButton("accelerate");
		button2.setBackground(Color.lightGray);
		button2.addActionListener(e -> {
			accelerate = true;
			decelerate = false;
			cruise = false;
			stop = false;

			button1.setBackground(Color.lightGray);
			button2.setBackground(Color.green);
			button3.setBackground(Color.lightGray);
			button4.setBackground(Color.lightGray);
			button5.setBackground(Color.lightGray);

			Thread thread = new Thread(() -> {
				try {
					while(accelerate) {
						Thread.sleep(2 * 1000);

						if(currentvelocity<=maximumvelocity) {
							currentvelocity = currentvelocity +1;
							vehicle.setCurrentSpeed(currentvelocity);
							speedlabel.setText(vehicle.printSpeed());
							simulationPane.updateTimer();
						}
					}
				}
				catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			});

			thread.start();
		});

	}
	
	private void configCruise() {
		button3 = new JButton("cruise");
		button3.setBackground(Color.lightGray);
		button3.addActionListener(e -> {
			accelerate = false;
			decelerate = false;
			cruise = true;
			stop = false;

			button1.setBackground(Color.lightGray);
			button2.setBackground(Color.lightGray);
			button3.setBackground(Color.green);
			button4.setBackground(Color.lightGray);
			button5.setBackground(Color.lightGray);

		});
	}
	private void configDecelerate() {
		button4 = new JButton("decelerate");
		button4.setBackground(Color.lightGray);
		button4.addActionListener(e -> {
			accelerate = false;
			decelerate = true;
			cruise = false;
			stop = false;

			button1.setBackground(Color.lightGray);
			button2.setBackground(Color.lightGray);
			button3.setBackground(Color.lightGray);
			button4.setBackground(Color.green);
			button5.setBackground(Color.lightGray);

			Thread thread = new Thread(() -> {
				try {
					while(decelerate) {
						Thread.sleep(2 * 1000);

						if(currentvelocity >1) {
							currentvelocity = currentvelocity -1;
							vehicle.setCurrentSpeed(currentvelocity);
							speedlabel.setText(vehicle.printSpeed());
							simulationPane.updateTimer();
						}
					}
				}
				catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			});

			thread.start();
		});
	}
	
	private void configStop() {
		button5 = new JButton("stop");
		button5.setBackground(Color.lightGray);
		button5.addActionListener(e -> {
			accelerate = false;
			decelerate = false;
			cruise = false;
			stop = true;

			button1.setBackground(Color.lightGray);
			button2.setBackground(Color.lightGray);
			button3.setBackground(Color.lightGray);
			button4.setBackground(Color.lightGray);
			button5.setBackground(Color.green);

			currentvelocity = 1;
			vehicle.setCurrentSpeed(currentvelocity);
			speedlabel.setText(vehicle.printSpeed());
			simulationPane.updateTimer();
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
		private Timer timer;
		public Simulator() {
			file = new File(System.getProperty("user.dir")+ "/img/" + vehicle.getClass().getSimpleName().toLowerCase() + ".png");
			try {	
				boat = ImageIO.read(file);
				timer = new Timer(maximumvelocity/currentvelocity, e -> {
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

		public void updateTimer() {
			timer.setDelay(maximumvelocity/currentvelocity);
		}


	}

}
