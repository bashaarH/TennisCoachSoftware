
import java.awt.*;
import java.util.EmptyStackException;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Stack;
import java.io.*;
import java.text.*;

public class DayPanel implements ActionListener, Serializable, WindowListener {

	private ArrayList<Player> tracked = new ArrayList<Player>();
	private String coach;
	private ArrayList<Rotation> rotation = new ArrayList<Rotation>();
	private ArrayList<Match> match;
	private int numRot = -1; // the number of rotation played
	private Rotation currentRotation;
	private JButton enter = new JButton("enter");
	private JButton done = new JButton("Next");
	private JTextField field = new JTextField(50);
	private JFrame frame = new JFrame();
	private JPanel coachScreen = new JPanel();
	private JPanel playerScreen = new JPanel();
	private JPanel show = new JPanel();
	private JPanel main, courtScreen;
	private JLabel view; // Panel to view which players have already been selected
	private CardLayout cards;
	private JComboBox box;
	private String display = "";
	private ArrayList<String> name = new ArrayList<String>(); // ArrayList of the names of the tracked players
	private Stack<Player> player = new Stack<Player>(); // Used for temporary of the player objects
	private JButton[] court = new JButton[14];
	private boolean crtScreen = false; // Whether the program is currently on the courtScreen or not
	private JLabel courtLabel;
	private JButton next;
	private JPanel rotationScreen;
	private JPanel playerStatus; // This panel shows the status of the players when they are in a rotation
	private JLabel[] status; // Stores the status of all the players being tracked
	private JButton startRot, endRot; // Buttons for starting and ending the rotations
	private JPanel butPanel; // The panel for the start and end rotation buttons
	private JFrame scoreFrame; // The frame that holds the panel that is used to record scores
	private JPanel scorePanel; // The panel for recording the scores from the matches
	private JFrame serveFrame;
	private JPanel servePanel;
	// Comboboxes used to collect names for players and their opponents. clubBox
	// has the names of all the players in the club whereas oppBox has the name
	// of tracked players
	private JComboBox playerBox, oppBox, clubBox, serveBox;
	private JTextField score1, score2; // Text fields to record the scores
	private JButton endDay;
	private JButton serveButton;
	private JButton addMatch; // Button to add a match to a rotation;

	public DayPanel() {
		{

			main = new JPanel();
			cards = new CardLayout();
			main.setLayout(cards);

			frame.setVisible(true);
			frame.add(main);
			frame.setSize(500, 600);
			
			JLabel msg = new JLabel("Please enter your name: ");
			msg.setFont(new Font("Arial", Font.PLAIN, 15));

			box = new JComboBox(Club.getNames());
			box.addActionListener(this);
			
			coachScreen.add(msg);
			coachScreen.add(field);
			coachScreen.add(enter);
			main.add(coachScreen, "coachScreen");
			cards.show(main, "coachScreen");

			view = new JLabel();
			playerScreen.setLayout(new BorderLayout());
			view.setSize(200, 300);
			
		

			JLabel playerMsg = new JLabel("Which players are you tracking?");
			playerMsg.setFont(new Font("Arial", Font.PLAIN, 15));

			JPanel temp = new JPanel();
			temp.add(playerMsg);
			temp.add(box);

			playerScreen.add(temp, BorderLayout.NORTH);
			playerScreen.add(view, BorderLayout.CENTER);
			playerScreen.add(done, BorderLayout.EAST);

			

			field.addActionListener(this);
			enter.addActionListener(this);
			done.addActionListener(this);

			courtScreen = new JPanel();

			
			main.add(playerScreen, "playerScreen");
			main.add(courtScreen, "courtScreen");
			
			frame.pack();
		}
	}

	/*
	 * Utility method for the panel used in assigning starting court locations
	 */
	private void courtScreen() {

		courtScreen.setLayout(new BorderLayout());
		JPanel temp = new JPanel();
		temp.setLayout(new GridLayout(7, 2));
		// Push all the players to be tracked into the stack
		for (int i = 0; i < name.size(); i++) {
			player.push(Club.players.search(name.get(i)));
		}

		String buttonLabel;
		courtLabel = new JLabel("What court is " + player.peek().getName() + " starting on?");
		courtLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		courtScreen.add(courtLabel, BorderLayout.NORTH);

		for (int i = 0; i < court.length; i++) {
			buttonLabel = Club.getCourt(i);
			court[i] = new JButton(buttonLabel);
			court[i].setText(buttonLabel);
			court[i].setFont(new Font("Arial", Font.PLAIN, 15));
			court[i].addActionListener(this);
			temp.add(court[i]);
		}
		courtScreen.add(temp, BorderLayout.CENTER);
		next = new JButton("Next");
		next.addActionListener(this);
		courtScreen.add(next, BorderLayout.SOUTH);
	}

	/*
	 * Utility method for the panel that is used once rotations have begun
	 */
	private void rotationScreen() {
		rotationScreen = new JPanel();
		rotationScreen.setLayout(new BorderLayout());
		main.add(rotationScreen, "rotationScreen");
		cards.show(main, "rotationScreen");
		playerStatus = new JPanel();
		playerStatus.setLayout(new GridLayout(tracked.size(), 1));
		status = new JLabel[tracked.size()];
		String temp = "";
		for (int i = 0; i < tracked.size(); i++) {
			temp = tracked.get(i).getName() + " is on " + Club.getCourt(tracked.get(i).getCurrentCourt()) + " and has "
					+ Club.players.search(tracked.get(i).getName()).getPoints() + " points";
			status[i] = new JLabel(temp);
			System.out.println(temp);
			status[i].setFont(new Font("Arial", Font.PLAIN, 30));
			playerStatus.add(status[i]);
		}
		rotationScreen.add(playerStatus, BorderLayout.CENTER);
		startRot = new JButton("Start Rotation");
		endRot = new JButton("End Rotation");
		startRot.addActionListener(this);
		endRot.addActionListener(this);
		butPanel = new JPanel(new GridLayout(2, 1));
		startRot.setVisible(true);
		endRot.setVisible(false);
		butPanel.add(startRot);
		butPanel.add(endRot);
		rotationScreen.add(butPanel, BorderLayout.EAST);
		endDay = new JButton("End Day");
		endDay.addActionListener(this);
		rotationScreen.add(endDay, BorderLayout.SOUTH);
		frame.addWindowListener(this);
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

	}

	/*
	 * Utility method for the panel used to collect the scores and record them as
	 * matches
	 */
	private void scorePanel() {
		scoreFrame = new JFrame("Score Frame");
		scoreFrame.setVisible(true);
		scoreFrame.setSize(300, 400);
		String[] names = new String[name.size() + 1];
		names[0] = "Select Player";
		for (int i = 0; i < name.size(); i++) {
			names[i + 1] = name.get(i);
		}

		playerBox = new JComboBox(names);
		oppBox = new JComboBox(names);
		clubBox = new JComboBox((Club.getNames()));

		scorePanel = new JPanel();

		score1 = new JTextField(10);
		score2 = new JTextField(10);

		scorePanel.setLayout(new GridLayout(4, 1));
		scorePanel.add(playerBox);
		scorePanel.add(score1);
		scorePanel.add(oppBox);
		scorePanel.add(score2);
		scorePanel.add(clubBox);
		scoreFrame.add(scorePanel);
		addMatch = new JButton("Add Match");
		addMatch.addActionListener(this);
		scorePanel.add(addMatch);
		// cards.show(main, "scorePanel");

	}

	/*
	 * Utility method for the panel that will ask if a rotation is serving or not
	 */
	private void servePanel() {
		serveFrame = new JFrame();
		servePanel = new JPanel();	

		String[] options = { "Feed and Play", "Serve" };
		serveBox = new JComboBox(options);
		serveFrame.add(servePanel);
		servePanel.add(new JLabel("Is this a serving or feed and play rotation"));
		servePanel.add(serveBox);
		serveBox.addActionListener(this);

		serveButton = new JButton("Enter");
		servePanel.add(serveButton);
		serveButton.addActionListener(this);
		serveFrame.setVisible(true);
		serveFrame.add(servePanel);
		serveFrame.setSize(200, 300);

	}

	/*
	 * Utility method to reset the ArrayList of the names of the players being
	 * tracked
	 */
	private void resetName() {
		name = new ArrayList<String>();
		String temp = "";
		for (int i = 0; i < tracked.size(); i++) {
			temp = tracked.get(i).getName();
			name.add(temp);
		}
	}

	/*
	 * Utility method for printing the names of the players and their scores on the
	 * Rotation screen
	 */
	private void printNames() {

		for (int i = 0; i < tracked.size(); i++) {

			playerStatus.remove(status[i]);
		}

		status = new JLabel[tracked.size()];
		String temp = "";
		for (int i = 0; i < tracked.size(); i++) {
			temp = tracked.get(i).getName() + " is on " + Club.getCourt(tracked.get(i).getCurrentCourt()) + " and has "
					+ Club.players.search(tracked.get(i).getName()).getPoints() + " points";
			status[i] = new JLabel(temp);
			System.out.println(temp);
			status[i].setFont(new Font("Arial", Font.PLAIN, 30));
			playerStatus.add(status[i]);
		}
		playerStatus.revalidate();
		playerStatus.repaint();
		frame.pack();

	}
	/*
	 * This method detects and handles events
	 */

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == enter) {

			coach = field.getText();
			cards.show(main, "playerScreen");
			frame.pack();
		}

		if (e.getSource() == box) {

			

			if (!((String) box.getSelectedItem()).equals("Select Player")) {
				display = (String) display + "<html><br/><html>" + (String) box.getSelectedItem();
				view.setText(display);
				view.setFont(new Font("Arial", Font.PLAIN, 30));
				playerScreen.add(view, BorderLayout.CENTER);
				name.add((String) box.getSelectedItem());
				frame.pack();
			} else
				Utility.displayErrorMsg("Please select an appropriate player");

		}
		if (e.getSource() == done) {
			if (name.isEmpty()) {
				Utility.displayErrorMsg("No players have been selected, please try again.");
			} else {

				cards.show(main, "courtScreen");
				crtScreen = true;
				courtScreen();
				frame.pack();
			}
		}
		if (crtScreen) {
			Player temp;
			for (int i = 0; i < 14; i++) {

				if (e.getSource() == court[i]) {
					try {
						temp = player.pop();
						// Set the appropriate court values for the player
						temp.newDay(i);
						tracked.add(temp);

					} catch (EmptyStackException exception) {
						exception.printStackTrace();
						Utility.displayErrorMsg("No more players to add, please press next.");
					}
					// Update the name of the player who is being assigned a court on the screen 
					try {
						courtLabel.setText("What court is " + player.peek().getName() + " starting on?");
					}

					catch (Exception ex) {
						ex.printStackTrace();

					}
				}
			}
		}

		if (e.getSource() == next) {
			if (!player.isEmpty()) {
				Utility.displayErrorMsg("Not all players have been assigned a court yet.");
			} else {
				crtScreen = false;
				rotationScreen();
				frame.pack();
			}
		}

		if (e.getSource() == startRot) {
			startRot.setVisible(false);

			currentRotation = new Rotation();
			rotation.add(currentRotation);
			servePanel();
			frame.pack();

		}
		if (e.getSource() == endRot) {
			startRot.setVisible(true);
			endRot.setVisible(false);
			resetName();
			scorePanel();
			currentRotation.setEnd();
			System.out.println(currentRotation);
			numRot++;
			frame.pack();
		}

		if (e.getSource() == addMatch) {
			boolean trackedOpp = true; // Whether the opponent is also a tracked player
			String player1 = (String) playerBox.getSelectedItem();
			String player2 = (String) oppBox.getSelectedItem();
			if (player2.equals("Select Player")) {
				player2 = (String) clubBox.getSelectedItem();
				trackedOpp = false;
			}

			Match temp = new Match();
			boolean valid;
			int num1, num2; // The scores recorded from the text fields

			try {
				num1 = Integer.parseInt(score1.getText());
				num2 = Integer.parseInt(score2.getText());
				valid = true;
			} catch (NumberFormatException ex) {
				ex.printStackTrace();
				Utility.displayErrorMsg("Please enter a number.");
				num1 = 0;
				num2 = 0;
				valid = false;
			}

			try {
				temp = new Match(Club.players.search(player1), Club.players.search(player2));
				(Club.players.search(player1)).courtChange(num1 > num2);
				valid = true;
			} catch (NullPointerException ex) {
				ex.printStackTrace();
				Utility.displayErrorMsg("Please enter an appropriate player.");
				valid = false;
			}
			if (player1.equals(player2)) {
				valid = false;
				Utility.displayErrorMsg("Please select unique players.");
			}
			if (valid) {

				temp.setScore1(num1);
				temp.setScore2(num2);

				Club.players.search(player1).setPoints(num1);
				System.out.println(player1);
				System.out.println(player2);
				temp.setWinner();
				rotation.get(numRot).addMatch(temp);
				if (trackedOpp) {
					temp = new Match(Club.players.search(player2), Club.players.search(player1));
					temp.setScore1(num2);
					temp.setScore2(num1);
					temp.setWinner();
					rotation.get(numRot).addMatch(temp);
					(Club.players.search(player2)).courtChange(num2 > num1);
					Club.players.search(player2).setPoints(num2);
				}
				scoreFrame.dispose();
				name.remove(player1);
				name.remove(player2);

				System.out.println(name.size());
				if (name.size() > 0) {
					scorePanel();
				}
				printNames();
			}

		}

		if (e.getSource() == endDay) {
			Day day = new Day(coach, rotation, tracked, Club.players);
			JFileChooser j = new JFileChooser();
			j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int val = j.showOpenDialog(null);
			if (val == JFileChooser.APPROVE_OPTION) {
				String fileLoc = j.getSelectedFile().getAbsolutePath() + "\\";

				File file = new File(fileLoc + day.getCoach() + "_" + day.getDate() + ".ser");
				try {
					FileOutputStream fileOut = new FileOutputStream(file);
					ObjectOutputStream out = new ObjectOutputStream(fileOut);
					out.writeObject(day);
					frame.dispose();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		}

		if (e.getSource() == serveButton) {
			// Check if the rotation is serving or not
			rotation.get(numRot + 1).setServing(((String) serveBox.getSelectedItem()).equals("Serve"));
			serveFrame.dispose();
			
			// Make end rotation button visible
			endRot.setVisible(true);
		}

	}


	public void windowActivated(WindowEvent e) {

	}

	public void windowClosed(WindowEvent e) {

	}

	public void windowClosing(WindowEvent e) {

		boolean daySaved = false;

		Day day = new Day(coach, rotation, tracked, Club.players);
		JFileChooser j = new JFileChooser();
		j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int val = j.showOpenDialog(null);
		if (val == JFileChooser.APPROVE_OPTION) {
			String fileLoc = j.getSelectedFile().getAbsolutePath() + "\\";

			File file = new File(fileLoc + day.getCoach() + "_" + day.getDate() + ".ser");
			try {
				FileOutputStream fileOut = new FileOutputStream(file);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(day);
				daySaved = true;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if (daySaved)
			frame.dispose();

	}

	public void windowDeactivated(WindowEvent e) {

	}

	public void windowDeiconified(WindowEvent e) {

	}

	public void windowIconified(WindowEvent e) {

	}

	public void windowOpened(WindowEvent e) {

	}

}
