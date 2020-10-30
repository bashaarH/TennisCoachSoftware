 


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.Arrays;
import java.util.Calendar;

public class GUI implements ActionListener, WindowListener {
	private DateFormat sdf = new SimpleDateFormat("YYYY-'W'ww-u");
	private JFrame frame = new JFrame();
	private JTabbedPane pane = new JTabbedPane();
	private JPanel home; // The home screen
	private JPanel butPanel; // The panel that will display the buttons on the home screen
	private JButton impor, export, start, get;
	private JPanel playerPanel;
	private JComboBox playerBox;
	private JButton getPlayer;
	private JPanel add; // Panel for adding players
	private JButton addPlayer;
	private JTextField name; // Text field for entering a player's name
	private JComboBox statBox, timeBox; // Combo boxes for selecting which statistic to view and which time period.

	public GUI() {
		
		//Make frame and establish its properties
		frame.setVisible(true);
		frame.setSize(500, 500);
		frame.addWindowListener(this);
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(this);
		frame.add(pane);
		
		//Make components to be viewed on the Home Screen 
		home = new JPanel();
		home.setLayout(new BorderLayout());
		impor = new JButton("Import a Day");
		export = new JButton("Export Players");
		start = new JButton("Start a Day");
		get = new JButton("Import Players");
		butPanel = new JPanel();
		butPanel.setLayout(new GridLayout(4, 1));
		butPanel.add(impor);
		butPanel.add(export);
		butPanel.add(get);
		butPanel.add(start);
		home.add(butPanel, BorderLayout.EAST);
		pane.add("Home", home);

		// Add action listeners to all buttons on the butPanel panel 
		impor.addActionListener(this);
		export.addActionListener(this);
		start.addActionListener(this);
		get.addActionListener(this);

		playerPanel();
		addPlayer();

	}


	/*
	 * A method that detects and handles events appropriately
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == start) {
			new DayPanel();
		}

		if (e.getSource() == impor) {
			JFileChooser j = new JFileChooser();
			j.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int val = j.showOpenDialog(null);
			if (val == JFileChooser.APPROVE_OPTION) {
				Day day = null;
				;
				try {
					//Get selected file
					FileInputStream fileIn = new FileInputStream(j.getSelectedFile().getAbsolutePath());
					ObjectInputStream in = new ObjectInputStream(fileIn);
					day = (Day) in.readObject();
				} catch (IOException ex) {
					Utility.displayErrorMsg("Make sure you are importing the write file type" );
				} catch (ClassNotFoundException c) {
					System.out.println("This file is incompatible with this application");
				}
				try {
					Club.update(day);
				} catch (NullPointerException ex) {
					Utility.displayErrorMsg("This file does not contain any useful data.");
				}
			}

		}
		if (e.getSource() == export) {
			Calendar date = Calendar.getInstance();
			String fileName = "\\ players" + "_" + sdf.format(date.getTime()) + ".ser";
			JFileChooser j = new JFileChooser();
			j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int val = j.showOpenDialog(null);
			if (val == JFileChooser.APPROVE_OPTION) {
				System.out.println(j.getSelectedFile().getAbsolutePath());
				fileName = j.getSelectedFile().getAbsolutePath() + fileName;
				try {
					File file = new File(fileName);
					FileOutputStream fileOut = new FileOutputStream(file);
					ObjectOutputStream out = new ObjectOutputStream(fileOut);
					out.writeObject(Club.players);
				} catch (IOException ex) {
					ex.printStackTrace();
				}

			}
		}
		
		if (e.getSource() == get)
		{
			JFileChooser j = new JFileChooser();
			j.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int val = j.showOpenDialog(null);
			if (val == JFileChooser.APPROVE_OPTION) {
				BinaryTree players = null;
				;
				try {
					//Get selected file
					FileInputStream fileIn = new FileInputStream(j.getSelectedFile().getAbsolutePath());
					ObjectInputStream in = new ObjectInputStream(fileIn);
					players = (BinaryTree) in.readObject();
				} catch (IOException ex) {
					Utility.displayErrorMsg("Make sure you are importing the write file type" );
				} catch (ClassNotFoundException c) {
					System.out.println("This file is incompatible with this application");
				}
				catch (ClassCastException exc)
				{
					Utility.displayErrorMsg("This is the wrong file type, please enter an appropriate file.");
				}
				try {
					Club.players = players;
				} catch (NullPointerException ex) {
					Utility.displayErrorMsg("This file does not contain any useful data.");
				}
			}
		}

		if (e.getSource() == addPlayer) {
			String text = (String) name.getText();
			if (!name.getText().isEmpty()) {
				Player temp = new Player(name.getText());
				Club.players.addPlayer(temp);
				name.setText("");
			} else {
				Utility.displayErrorMsg("You have not entered an appropriate value, please try again.");
			}

		}

		if (e.getSource() == getPlayer) {

			int statVal = 0;
			try {
				String name = (String) playerBox.getSelectedItem();
				switch ((String) timeBox.getSelectedItem()) {
				case "Last 5 classes":
					statVal = 0;
					break;
				case "Last 10 classes":
					statVal = 1;
					break;
				case "All classes":
					statVal = 2;
					break;
				}
				switch ((String) statBox.getSelectedItem()) {
				case "Court Movement": // "Court Movement" , "Win Percentage"

					LineGraph.createCrtGraph(Club.players.search(name).getCrtMvmnt(statVal));
					break;

				case "Win Percentage":
					LineGraph.createPctGraph(Club.players.search(name).getWinPct(statVal));
	
				}
			} catch (NullPointerException ex) {
				Utility.displayErrorMsg(
						"Please select a player. If a player has been selected, this player does not have this statistic.");
			}
		}

	}

	public void windowClosing(WindowEvent event) {
		if (Club.end()) {
			frame.dispose();
			System.exit(0); 
		}
	}

	public void windowOpened(WindowEvent event) {
	}

	public void windowClosed(WindowEvent event) {
	}

	public void windowIconified(WindowEvent event) {
	}

	public void windowDeiconified(WindowEvent event) {
	}

	public void windowActivated(WindowEvent event) {
	}

	public void windowDeactivated(WindowEvent event) {
	}

	/*
	 * Utility method for establishing and operating the playerPanel
	 */
	private void playerPanel() {
		// Establish a new Panel 
		playerPanel = new JPanel();
		playerPanel.setLayout(new GridLayout(5, 1, 50, 50));
		pane.add("Players", playerPanel);
		playerBox = new JComboBox(Club.getNames());

		//Create button and add ActionListener 
		getPlayer = new JButton("Get Player");
		getPlayer.addActionListener(this);

		// Output options to the user in the form of JComboBoxes 
		String[] stats = { "Court Movement", "Win Percentage" };
		statBox = new JComboBox(stats);
		String time[] = { "Last 5 classes", "Last 10 classes", "All classes" };
		timeBox = new JComboBox(time);

		JLabel instruc = new JLabel(" Please select a player, a statistic and time period.");
		instruc.setFont(new Font("Arial", Font.PLAIN, 20));

		// Add components to the panel 
		playerPanel.add(instruc);
		playerPanel.add(playerBox);
		playerPanel.add(statBox);
		playerPanel.add(timeBox);
		playerPanel.add(getPlayer);
	}


	/*
	 * Utility method for establishing the adding a player panel
	 */
	private void addPlayer() {

		// Establish a new panel 
		add = new JPanel();
		add.setLayout(new GridLayout(3, 1, 30, 30));
		
		//Create panel for outputting instructions 
		JPanel panel = new JPanel(new GridLayout(2, 1));
		String temp = "Please enter the new player's name.";
		JLabel msg1 = new JLabel(temp);
		JLabel msg2 = new JLabel("The program will have to be restarted to view the new player.");
		panel.add(msg1);
		panel.add(msg2);
		msg1.setFont(new Font("Arial", Font.PLAIN, 20));
		msg2.setFont(new Font("Arial", Font.PLAIN, 20));
		add.add(panel);
		
		// Establish TextField to receive the Player's name from
		name = new JTextField(30);
		addPlayer = new JButton("Add Player");
		add.add(name);
		add.add(addPlayer);
		pane.addTab("Add Player", add);
		addPlayer.addActionListener(this);

		frame.pack();
	}
}
