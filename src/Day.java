 



import java.util.Calendar;
import java.util.ArrayList;
import java.io.*;
import java.text.*;

public class Day implements Serializable{

	private Calendar date;
	private String coach; // the name of the coach in charge of the day.
	private ArrayList<Rotation> rotation;
	private ArrayList<Player> tracked; // An ArrayList of the tracked players
	private BinaryTree players; 
	 private static final DateFormat sdf = new SimpleDateFormat("YYYY-'W'ww-u");

	/*
	 * Default constructor for the Day class
	 */
	public Day() {
		date =  Calendar.getInstance();
		coach = "";
		rotation = null;
		tracked = null; 
		players = new BinaryTree(); 
	}

	/*
	 * Constructor for the Day class with coach name and an Array List of rotations
	 * as the parameters.
	 */
	public Day(String coach, ArrayList<Rotation> rotation, ArrayList<Player> tracked, BinaryTree tree) {
		date =  Calendar.getInstance();
		this.coach = coach;
		this.rotation = rotation;
		this.tracked = tracked; 
		players = tree; 
	}

	/*
	 * Add a rotation to the day
	 */
	public void addRotation(Rotation rotation) {
		this.rotation.add(rotation);
	}

	/*
	 * Returns the value of the rotation of the specified index
	 */
	public Rotation getRotation(int index) {
		return rotation.get(index);
	}

	/*
	 * Returns the name of the coach
	 */
	public String getCoach() {
		return coach;
	}

	/*
	 * Returns the date object of this Day object
	 */
	public String getDate() {
		String out = "" + sdf.format(date.getTime());
		return out;
	}

	/*
	 * Returns the number of rotations in this day
	 */
	public int getNumOfRotations() {
		return rotation.size();
	}
	/*
	 * Returns a String representation of this Day
	 */
	public String toString()
	{
		return ""; 
		
	}
	/*
	 * Returns the Tree object with the players from this day
	 */
	public BinaryTree getPlayers()
	{
		return players; 
	}
	
	
	/*
	 * Returns an ArrayList of the tracked players
	 */
	public ArrayList<Player> getList()
	{
		return tracked; 
	}
}
