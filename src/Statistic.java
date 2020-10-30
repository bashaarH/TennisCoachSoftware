 



import java.io.Serializable;
import java.util.Calendar;

public class Statistic implements Serializable{
	private String playerName;
	private String playerID; 
	private double value; 
	private Calendar date; 
	
	/*
	 * Default constructor for the Statistic class
	 */
	public Statistic()
	{
		playerName = null; 
		playerID = null; 
		value = 0; 
		date = Calendar.getInstance(); 
	}
	/*
	 * Constructor for the Statistic class with the name and ID of the player and the date as the parameters
	 */
	public Statistic(String name, String ID, Calendar date)
	{
		this.playerName = name; 
		this.playerID = ID; 
		value = 0; 
		this.date = Calendar.getInstance();
	}
	
	/*
	 * Returns the date this statistic was taken
	 */
	public String getDate()
	{
		return (date.YEAR +"-" + date.MONTH + "-" + date.DAY_OF_MONTH) ; 
	}
	/*
	 * Returns the name of the player to whom this Statistic belongs
	 */
	public String getName()
	{
		return playerName; 
	}
	/*
	 * Returns the ID of the player to whom this Statistic belongs 
	 */
	public String getID()
	{
		return playerID;
	}
	/*
	 * Returns the value of this statistic
	 */
	public double getValue()
	{
		return value; 
	}
	/*
	 * Sets the value of this statistic to the given value 
	 */
	public void setValue(double value)
	{
		this.value = value; 
	}
	
	/*
	 * Sets the names of the player to whom this Statistic belongs 
	 */
	public void setName(String name)
	{
		playerName = name;
	}
	
}
