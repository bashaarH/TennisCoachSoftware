 



import java.io.*;
import java.util.*;

public class Club implements Serializable {
	static Player play1 = new Player("Tom");
	public static BinaryTree players = new BinaryTree();

	// public static void test() 
	{

		Player play2 = new Player("Dan");
		Player play3 = new Player("LMAO");
		//players.addPlayer(play1);
		players.addPlayer(play2);
		players.addPlayer(play3);
		ArrayList<Player> t = players.getPlayerList();
		for (int i = 0 ; i < t.size(); i++)
		{
		System.out.println(t.get(i).getName());
		}

	}
	 


	/*
	 * Returns an Array of Strings with the names of all the players in this Club
	 */
	public static String[] getNames() {
		Object[] temp = players.getPlayerList().toArray();
		String[] names = new String[temp.length + 1];
		names[0] = "Select Player";
		for (int i = 0; i < temp.length; i++) {
			names[i + 1] = ((Player) temp[i]).getName();
		}
		return names;
	}

	/*
	 * Returns the appropriate String representation of a court
	 */
	public static String getCourt(int court) {
		String output = "";
		if (court % 2 == 0)
			output = "Top Of ";
		else
			output = "Bottom Of ";
		output = output + (court / 2 + 1);
		return output;
	}
	
	/*
	 * Takes a Day as the input and updates the statistics appropriately 
	 */
	public static void update(Day day)
	{
		calcCrtMvmnt(day);
		calcWinPct(day);
		
	}
	/*
	 * Saves the BinaryTree of players to the designated file
	 */
	public static void save() throws IOException
	{
	
		File file = new File("players.ser");
		FileOutputStream fileOut = new FileOutputStream(file);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(players);
	}

	/*
	 * Gets the most updated BinaryTree of players from the appropriate file
	 * This method is called when the application is run 
	 */
	public static void start()
	{
		
		try {
		FileInputStream fileIn= new FileInputStream("players.ser");
		ObjectInputStream in = new ObjectInputStream(fileIn); 
		players = (BinaryTree) in.readObject();
		if (players == null)
		{
			Utility.displayErrorMsg("There are no saved players, please add players or import a file containing the players.");
		}
		}
		catch (IOException e)
		{
			Utility.displayErrorMsg("Oops! Something went wrong, please try again. There may not be any players saved, try importing a file containing the players or adding players and restarting the application. ");
		}
		catch (ClassNotFoundException c)
		{
			Utility.displayErrorMsg("Unfortunately this file is incompatible with this application.");

		}
		catch (NullPointerException n)
		{
			Utility.displayErrorMsg("There are no saved players, please add players or import a file containing the players.");
		}
	
		catch (Exception ex)
		{
			Utility.displayErrorMsg("There are no saved players, please add players or import a file containing the players.");
		}
	 // test(); 
		
	}
	
	/*
	 * Stores the BinaryTree in this Club to the appropriate file
	 * This method is called when the application is closed
	 */
	public static boolean end()
	{
		try {
		FileOutputStream fileOut = new FileOutputStream("players.ser");
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(players);
		return true;
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			return false; 
		}
	
	}
	/*
	 * A utility method for calculating the win percentages for the tracked players
	 * in a given Day object
	 */
	private static void calcWinPct(Day day)
	{
		Match temp; 
		WinPct stat; 
		int numPlayers = day.getList().size(); 
		int numRot = day.getNumOfRotations();
		// Run through each player in the day
		for (int i = 0; i < numPlayers; i++)
		{
			stat = new WinPct();
			// Run through each rotation in the day
			for (int n = 0; n < numRot; n++)
				
				// Run through each match in a rotation 
				for (int x = 0; x < day.getRotation(n).getMatchNum(); x++)
				{
					temp = day.getRotation(n).getMatch(x);
					if((temp.getPlayer1().getName()).equals(day.getList().get(i).getName()))
					{
						if(day.getRotation(n).isServing())
						{
							// Parameter is a boolean test if Player one is the winner
							stat.addServing(temp.getWinner().getName().equals(temp.getPlayer1().getName()));
						}
						else 
						{
							// Parameter is a boolean test if Player one is the winner
							stat.addFeeding(temp.getWinner().getName().equals(temp.getPlayer1().getName()));
							
						}
						// Set which player this statistic object belongs to
						stat.setName(temp.getPlayer1().getName());
						break; 
					}
					
					
				}
			// Add the stat object to the player
			players.search((day.getList().get(i)).getName()).addWinPct(stat);
		}
	}
	
	/*
	 * A utility method to calculate the court movement for each player for a Day
	 */
	private static void calcCrtMvmnt(Day day)
	{
		ArrayList<Player> temp = day.getList(); 
		CourtMovement stat; 
		BinaryTree player = day.getPlayers();
		int numPlayers = day.getList().size(); 
		int start; 
		int end; 
		// Run through each player
		for (int i = 0; i < numPlayers; i++)
		{
			start = player.search(temp.get(i).getName()).getStartCourt();
			end = player.search(temp.get(i).getName()).getCurrentCourt();
			stat = new CourtMovement(start, end);
			stat.setName(temp.get(i).getName());
			stat.adjust(players.search((day.getList().get(i)).getName()).getCurrentCourt());
			players.search((day.getList().get(i)).getName()).addCrtMvmnt(stat);
			
		}
	}
}
