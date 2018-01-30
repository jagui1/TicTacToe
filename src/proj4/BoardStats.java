package proj4;

import java.util.ArrayList;

/**
 * Keeps track of the wins, losses and other
 * stats for a certain board. This will be the value
 * stored in the SmartPlayer's hashTable
 */
public class BoardStats {
	//INSTANCE VARIABLES
	private TicTacToe board;
	private ArrayList<TicTacToe> prevMoves;
	private int wins;
	private int losses;
	private int ties;
	private int totGames;

	/**
	 * Constructor that takes in a board that it wil store
	 * and take in the stats of
	 * 
	 * Input:
	 * @param t - the board to be stored
	 */
	public BoardStats(TicTacToe t){
		this.board = t;
		this.wins = 0;
		this.losses = 0;
		this.ties = 0;
		this.totGames = 1;
		this.prevMoves = new ArrayList<TicTacToe>();
	}
	
	/**
	 * Adds a move that came after the stored board
	 * to the array of moves
	 * 
	 * Input:
	 * @param t - successor to be added
	 * 
	 * Output: None
	 */
	public void addPrevMove(TicTacToe t){
		prevMoves.add(t);
	}
	
	/**
	 * Total games that this board has been in
	 * 
	 * Input: None
	 * 
	 * Output:
	 * @return - the number of times this board has been seen
	 */
	public int getTimesSeen(){
		//Calculates total games
		updateTotGames();
		return totGames; 
	}
	
	/**
	 * Getter for the ArrayList of successors
	 * 
	 * Input: None
	 * 
	 * Output:
	 * @return - ArrayList of TicTactoes that come after the stored board
	 */
	public ArrayList<TicTacToe> getPrevMoves(){
		return prevMoves;
	}

	/**
	 * Calculates and returns the win rate that
	 * is found by total wins / total games played
	 * 
	 * Input: None
	 * 
	 * Output:
	 * @return a double of the win ratio
	 */
	public float getWinRatio(){
		if(getTimesSeen() > 0){
			return (wins / (float)(getTimesSeen())) * 100;
		} else {
			return 0;
		}
	}
	
	/**
	 * Calculates and updates the instance of 
	 * total games
	 */
	private void updateTotGames() {
		totGames = wins + ties + losses;
	}

	/**
	 * Increments the amount of wins for this board
	 * by one
	 */
	public void incrementWins() {
		wins++;
	}

	/**
	 * Increments the amount of ties for this board
	 * by one
	 */
	public void incrementTies() {
		ties++;
	}

	/**
	 * Increments the amount of losses for this board
	 * by one
	 */
	public void incrementLosses() {
		losses++;
	}
	
	/**
	 * toString override that returns the toString of the board it is storing
	 */
	public String toString(){
		return board.toString();
	}
	
	/**
	 * hashCode override that returns the hashCode of the board it is storing
	 */
	public int hashCode(){
		return board.hashCode();
	}

	/**
	 * Prints the final results of this currnt board
	 */
	public void printStats() {
		System.out.println("My favorite first move is: ");
		board.printBoard();
		System.out.println("Won " + wins + " out of " + getTimesSeen() + " which is "
							+ getWinRatio() + "%");

	}


}
