 



import java.io.Serializable;

public class CourtMovement extends Statistic implements Serializable {

	private int start; // The court the player started at
	private int end; // The court the player ended on

	/*
	 * Default constructor for the Statistic class
	 */
	public CourtMovement() {
		super();
		start = 0;
		end = 0;
	}

	/*
	 * Constructor for the Statistic class with the starting court and the number of
	 * wins and rotations in a Day as the parameters
	 */
	public CourtMovement(int start, int end) {
		super();
		this.start = start;
		this.end = end;
		this.setValue(-(end - start));
	}

	/*
	 * Returns the starting court
	 */
	public int getStart() {
		return start;
	}

	/*
	 * Returns the ending court
	 */
	public int getEnd() {
		return end;
	}
	/*
	 * Adjusts the values of the courts based on if the player has won on top of
	 * court 1
	 */
	public void adjust (int currentCourt)
	{
		 
		if (currentCourt < 0)
		{
			end = 0; 
			this.setValue(end - start ); 
		}
	}
	
	public void adjust()
	{
		if (end < 0) end = 0; 
	}
}
