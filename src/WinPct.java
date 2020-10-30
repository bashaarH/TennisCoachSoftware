 


import java.io.Serializable;
public class WinPct extends Statistic implements Serializable{

	public	double servingWins; // Number of serving rotations that were won
	public double servingRotation; // The number of serving rotations in a Day
	public double feedingWins; // Number of feed and play rotations that were won
	public double feedingRotation; // Number of feed and play rotations that were played

	/*
	 * Default constructor for Statistic class
	 */
	public WinPct() {
		super();
		servingWins = 0;
		servingRotation = 0;
		feedingWins = 0;
		feedingRotation = 0;
		this.setValue(0);
		if (feedingRotation != 0)this.setValue(feedingWins/ feedingRotation);
	}
	
	/*
	 * Adds a serving rotation and takes if the rotation was won as a parameter
	 */
	public void addServing (boolean won)
	{
		servingRotation = servingRotation + 1; 
		if (won) servingWins = servingWins + 1; 
	}
	
	/*
	 * Adds a feed and play rotations and takes if the rotation was won as a parameter
	 */
	public void addFeeding(boolean won)
	{
		feedingRotation = feedingRotation + 1; 
		if (won) feedingWins++; 
		this.setValue(feedingWins/ feedingRotation);
		
	}
	
	/*
	 * Returns the serving rotation win percentage
	 */
	public double getServing() {
		
		Double out =(servingWins/ servingRotation);
		if (servingRotation == 0)
		{
			return 0;
		}
		return out;
		
	}
	
	/*
	 * Returns the feed and play win percentage 
	 */
	public double getFeeding()
	{
		return (feedingWins/ feedingRotation);
	}
	
}
