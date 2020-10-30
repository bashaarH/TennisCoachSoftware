 


import java.util.ArrayList;
import java.io.*; 

public class Rotation implements Serializable{
	
	private ArrayList<Match> match; 
	private long length; // The time duration of the rotation
	private long start; // The time at which the rotation started
	private long end; // The time at which the rotation ended
	private int matchNum; // The number of matches in played in the rotation
	private boolean serving; // Whether this Rotation is serving or feed and play
	/*
	 * Default constructor for the Rotation class
	 */
	public Rotation ()
	{
		match =  new ArrayList<Match>();
		length = 0; 
		start = System.nanoTime();
		end = System.nanoTime(); 
		matchNum = 0; 
		serving = false; 
	}
	
	/*
	 * Constructor for the rotation class with a match as a parameter
	 */
	public Rotation (Match match)
	{
		this.match =  new ArrayList<Match>();
		this.match.add(match);
		length = 0; 
		start = System.nanoTime();
		end = System.nanoTime(); 
		matchNum = 0; 
		serving = false; 
	}
	
	
	/*
	 * Set the length of the rotation 
	 */
	
	public void setLength()
	{
		length = end - start; 
	}
	
	/*
	 * Determine the time at which the rotation ended
	 */
	
	public void setEnd()
	{
		end = System.nanoTime(); 
		length = end - start; 
	}
	
	/*
	 * Sets the serving value to the given value 
	 */
	public void setServing(boolean serving)
	{
		this.serving = serving; 
	}
	
	/*
	 * Add a match to this rotation object 
	 */
	
	public void addMatch (Match match)
	{
		this.match.add(match);
		matchNum++; 
	}
	/*
	 * Returns the match stored in the specified index 
	 */
	
	public Match getMatch (int index)
	{
		return this.match.get(index);
	}
	
	/*
	 * Returns the number of matches in this rotation 
	 */
	public int getMatchNum ()
	{
		return matchNum;
	}
	
	/*
	 * Returns the length of this rotation
	 */
	public long  getLength()
	{
		return length; 
	}
	
	/*
	 * Returns whether this rotation is serving
	 */
	public boolean isServing()
	{
		return serving; 
	}
	/*
	 * Returns a String representation of this Rotation object
	 */
	public String toString()
	{
		
		System.out.println(match);
		System.out.println(length);
		System.out.println(start);
		System.out.println(end);
		System.out.println(matchNum);
		return ""; 
	}

}
