 



import java.io.*;
public class Match implements Serializable {
	
	private Player player1;
	private Player player2;
	private int score1; 
	private int score2; 
	private Player winner; 

	
	/*
	 * Default constructor for Match Object
	 */
	public Match ()
	{
		player1 = null; 
		player2 = null;
		score1 = 0; 
		score2 = 0;
		winner = null; 
	}
	
	/*
	 * Constructor for match object with both players as parameters 
	 */
	
	public Match (Player player1, Player player2) {
		this.player1 = player1; 
		this.player2 = player2; 
		score1 = 0; 
		score2 = 0; 
		winner = null;
	}
	
	/*
	 * Set player1's score 
	 */
	
	public void setScore1( int score)
	{
		score1 = score; 
	}
	
	/*
	 * Set player2's score
	 */
	
	public void setScore2 (int score)
	{
		score2 = score;
	}
	
	/*
	 * Determine and set the winner of this match. 
	 */
	
	public void setWinner()
	{
		if (score1 > score2 ) winner = player1; 
		else if(score2 > score1) winner = player2; 

	}
	
	/*
	 * Returns identity of player1 
	 */
	public Player getPlayer1()
	{
		return player1; 
	}
	/*
	 * Returns the identity of player2
	 */
	public Player getPlayer2()
	{
		return player2; 
	}
	
	/* 
	 * Returns the identity of the winner 
	 */
	public Player getWinner()
	{
		return winner; 
	}
	

}
