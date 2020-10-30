
import java.io.*;
import java.util.Arrays;

public class Player implements Serializable {

	private String name;

	private int startCourt; // The court this player started on
	private int currentCourt; // The current court this player is on
	private int currentPoints; // Keeps track of the players points
	private Queue[] crtMvmnt;
	private Queue[] winPct;

	final int last5 = 0; // Index for the queue that stores the last 5 statistics
	final int last10 = 1; // Index for the queue that stores the last 10 statistics
	final int allTime = 2; // Index for the queue that stores all the statistics

	/*
	 * Default constructor for the Player class
	 */
	public Player(String name) {
		this.name = name;
		
		startCourt = 0;
		currentCourt = 0;
		currentPoints = 0;

		crtMvmnt = new Queue[3];
		winPct = new Queue[3];

		for (int i = 0; i < crtMvmnt.length; i++) {
			crtMvmnt[i] = new Queue();
			winPct[i] = new Queue();
		}

	}

	/*
	 * Returns the name of this Player
	 */
	public String getName() {
		return name;
	}


	/*
	 * Resets the appropriate value for this player when a new Day begins
	 */
	public void newDay(int startCourt) {
		this.startCourt = startCourt;
		currentCourt = startCourt;
		currentPoints = 0;
		System.out.println("NEW DAY: " + startCourt);
	}

	/*
	 * Returns this player's current court
	 */
	public int getCurrentCourt() {
		return currentCourt;
	}

	/*
	 * Returns the court this Player started on
	 */
	public int getStartCourt() {
		return startCourt;
	}

	/*
	 * Adjust the Current Court value depending if the Player won the last match
	 */
	public void courtChange(boolean win) {
		if (win) {
			currentCourt--;
		} else {
			currentCourt++;
		}
		if (currentCourt < 0)
			currentCourt = 0;
	}

	/*
	 * Adds the given score to the currentPoints field
	 */
	public void setPoints(int points) {
		currentPoints = currentPoints + points;
	}

	/*
	 * Returns the number of points the player currently has
	 */
	public int getPoints() {
		return currentPoints;
	}

	/*
	 * Prints a String representation of this Player object
	 */
	public void getStats() {
		/*
		 * Object[] temp = statistic[0][0].getValues(); double x; for ( int i = 0; i <
		 * temp.length; i++) { System.out.println((temp[i])); //
		 * System.out.println(((Statistic)temp[i]).getValue()); }
		 * System.out.println(Arrays.toString(temp)); temp =
		 * statistic[1][0].getValues(); for ( int i = 0; i < temp.length; i++) { // x=
		 * (temp[i]); System.out.println(temp[i]); }
		 * System.out.println(Arrays.toString(temp));
		 */

		Object[] temp;
		for (int i = 0; i < 3; i++) {
			temp = crtMvmnt[i].getValues();
			for (int n = 0; n < temp.length; n++) {
				System.out.println("CRT " + ((Statistic) temp[n]).getValue());
			}
		}

		for (int i = 0; i < 3; i++) {
			temp = winPct[i].getValues();
			for (int n = 0; n < temp.length; n++) {
				System.out.println("WIN " + ((Statistic) temp[n]).getValue());
			}
		}
		// statistic[1][0].print();
	}

	/*
	 * Returns an array containing the objects stored in the specified queue of the
	 * winPct array
	 */
	public Object[] getWinPct(int i) {

		return winPct[i].getValues();

	}
	
	/*
	 * Returns an array containing the objects stored in the specified queue of the
	 * crtMvmnt array
	 */
	public Object[] getCrtMvmnt(int i) {
		System.out.println(i);
		return crtMvmnt[i].getValues();

	}

	/*
	 * Adds a Win Percentage statistic and adjusts the Queues appropriately
	 */
	public void addWinPct(WinPct pct) {
		// System.out.println(statistic[winPctLoc][last5]);
		winPct[last5].enqueue(pct);
		if (winPct[last5].getSize() > 5) {
			winPct[last5].dequeue();
		}

		winPct[last10].enqueue(pct);
		if (winPct[last10].getSize() > 10) {
			winPct[last10].dequeue();
		}
		winPct[allTime].enqueue(pct);

	}

	/*
	 * Adds a Court Movement statistic and adjusts the Queues appropriately
	 */
	public void addCrtMvmnt(CourtMovement mvmnt) {

		crtMvmnt[last5].enqueue(mvmnt);
		// System.out.println(statistic[crtMvmntLoc][last5]);
		if (crtMvmnt[last5].getSize() > 5) {
			crtMvmnt[last5].dequeue();
		}

		crtMvmnt[last10].enqueue(mvmnt);
		System.out.println(crtMvmnt[last10].getSize());
		if (crtMvmnt[last10].getSize() > 10) {
			crtMvmnt[last10].dequeue();
		}
		crtMvmnt[allTime].enqueue(mvmnt);

	}
}
