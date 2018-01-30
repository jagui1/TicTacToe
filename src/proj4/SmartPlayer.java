package proj4;

import java.util.ArrayList;

/**
 * A smart(er) TicTacToe player. It keeps track of moves it has already made, 
 * and when it can, tries take the one that has resulted in the most wins. 
 * Failing that, it will take an action that has resulted in the least losses. 
 * If all actions are equal, it will choose one at random. This object must be 
 * told when a new game has started (and what player it is playing) and also it 
 * must see the game when it is finished (otherwise there is no way for it to 
 * know if an option resulted in a win or a loss. This class must have a Hashtable 
 * that maps TicTacToe boards to statistics about the results of the game
 *
 */
public class SmartPlayer{
	
	//INSTANCE VARIABLES
	private Hashtable<TicTacToe, BoardStats> moveTable;
	private int player;
	private int totWins;
	private int totTies;
	private int totLosses;
	private int gamesPlayed;
	private double winRate;
	private ArrayList<TicTacToe> moveArray;
	
	/**
	 * Default constructor that initializes the Hashtable
	 * and the ArrayLists that will contain all the moves 
	 * made in the current game.
	 * 
	 */
	public SmartPlayer(){
		//Hash table of moves made
		moveTable = new Hashtable<TicTacToe, BoardStats>(4);
		//Moves made in one game
		moveArray = new ArrayList<TicTacToe>(0);
	}
	
	/**
	 * Makes sure the smart player is ready to start playing / recording. 
	 * Should be called before any new games. Should log which player the 
	 * smart player is playing as.
	 * 
	 * @param player - Which player the SmartPlayer is playing as this game.
	 */
	public void newGame(int player){
		//Player the SmartPlayer will be
		this.player = player;
		//Resets the moveArray
		this.moveArray = new ArrayList<TicTacToe>(0);
		
	}
	
	/**
	 * Makes a move based on past experince. 
	 * Tries take the one that has resulted in the most wins. 
	 * Failing that, it will take an action that has resulted 
	 * in the least losses. If all actions are equal, it will 
	 * choose one at random.
	 * 
	 * Input:
	 * @param t - the current board
	 * 
	 * Output: None
	 */
	public void move(TicTacToe t){
		//Gets the best move or an unknown move
		int[] move = getMove(t);
		//Makes the move
		t.move(move[0], move[1]);		
		
	}
	/**
	 * Cycles through every possible move that
	 * the SmartPlayer can make based on the current
	 * board. If the SmartPlayer has not made that move
	 * yet then it returns that move, otherwise returns the
	 * move with the highest win ratio.
	 * 
	 * Input:
	 * @param curBoard - the current TicTacToe board
	 * 
	 * Output:
	 * @return an array of the row and collumn of the best move
	 */
	private int[] getMove(TicTacToe curBoard){
		//Initializes ratio for comparrison
		float bestRatio = -1;
		//Initializes bestMove
		int[] bestMove = {-1, -1};
		//Initializes the possible move as a copy of the current board
		TicTacToe possibleMove = new TicTacToe(curBoard);
		//Initializes another copy of current board
		TicTacToe bestMoveBoard = new TicTacToe(curBoard);
		
		//Loops through each row and column of the total board
		for(int i = 0; i < curBoard.getLength(); i++){
			for(int j = 0; j < curBoard.getLength(); j++){
				//Checks if the current spot is empty
				if(curBoard.playerAt(i, j) == 0){
					//Resets the possible move
					possibleMove = new TicTacToe(curBoard);
					//Makes the empty move
					possibleMove.move(i, j);
					
					//Checks if the SmartPlayer has made the empty move before
					if(moveTable.containsKey(possibleMove)){
						//Gets the Stats of the current move
						BoardStats curStats = moveTable.get(possibleMove);

						//Checks if the ratio of that move is better
						if(curStats.getWinRatio() > bestRatio){
							//Updates ratio and saves the move
							bestRatio = curStats.getWinRatio();
							bestMove[0] = i;
							bestMove[1] = j;
						}
						
					} else {
						//Makes the unkown move 
						bestMove[0] = i;
						bestMove[1] = j;
						moveArray.add(possibleMove);
						return bestMove;
					}
										
				}
			}
		}
		//Takes the best move and returns it and adds it to the move list
		bestMoveBoard.move(bestMove[0], bestMove[1]);
		moveArray.add(bestMoveBoard);
		return bestMove;
	}
	
	/**
	 * Let's smartAI know that the game has ended. 
	 * This method will probably require going back 
	 * through all the moves in this game so far and 
	 * updating them with whether they ended in a win, 
	 * a loss, or a draw.
	 * 
	 * Input:
	 * @param finalBoard - 
	 * 
	 * Output: None
	 */
	public void endGame(TicTacToe finalBoard){
		//Current board
		TicTacToe curBoard;
		//Increments the amount of games played
		gamesPlayed++;

		//Increments the total amount of results for SmartPlayer
		if(finalBoard.getWinner() == player){
			totWins++;
		} else if(finalBoard.getWinner() == 0){
			totTies++;
		} else {
			totLosses++;
		}
		
		//Loops through each move made in the current game
		for(int i = 0; i < moveArray.size(); i++){
			//Takes the move 
			curBoard = moveArray.get(i);
			
			//Hashes the move if it has not been hashed yet
			if(!moveTable.containsKey(curBoard)){
				moveTable.put(curBoard, new BoardStats(curBoard));
			}
			
			//Adds the successor to each move
			if(moveArray.size() > i + 1){
				moveTable.get(curBoard).addPrevMove(moveArray.get(i + 1));
			} 

			//Increments the game result in the Hashtable
			if(finalBoard.getWinner() == player){
				moveTable.get(curBoard).incrementWins();
			} else if(finalBoard.getWinner() == 0){
				moveTable.get(curBoard).incrementTies();
			} else {
				moveTable.get(curBoard).incrementLosses();
			}
			
		}

	}
	
	/**
	 * How many times we've played this particular board
	 * 
	 * Input: 
	 * @param t - the board we're looking at
	 * 
	 * Output:
	 * @return Number of times smartAI has played on it
	 */
	public int numberOfTimesSeen(TicTacToe t){
		int times = moveTable.get(t).getTimesSeen();
		return times;
	}
	
	/**
	 * This should return every board we've played that came after the board t.
	 * 
	 * Input:
	 * @param t - the current board
	 * 
	 * Output:
	 * @return Every board we've experienced that is one more after t.
	 */
	public TicTacToe[] getSuccessors(TicTacToe t){
		ArrayList<TicTacToe> successors = moveTable.get(t).getPrevMoves();
		return (TicTacToe[]) successors.toArray();
		
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
	public double getWinRate(){
		if(gamesPlayed > 0){
			winRate = (double)totWins / gamesPlayed; 
		} else {
			winRate = 0;
		}
		
		return winRate;
	}
	
	/**
	 * Calculates and returns the loss rate that
	 * is found by total losses / total games played
	 * 
	 * Input: None
	 * 
	 * Output:
	 * @return a double of the loss ratio
	 */
	public double getLossRate(){
		if(gamesPlayed > 0){
			winRate = (double)totLosses / gamesPlayed; 
		} else {
			winRate = 0;
		}
		
		return winRate;
	}
	
	/**
	 * Prints the total results of the games he's played 
	 * 
	 * Input: None
	 * 
	 * Output: None
	 */
	public void printResults() {
		//Initialization
		TicTacToe temp = new TicTacToe();
		int[] favMove = getMove(temp);
		temp.move(favMove[0], favMove[1]);
		BoardStats  bs;

		//Prints hash results
		System.out.println("FINAL REPORT: \n" + 
							moveTable 
							//Total games results
							+ "\nSmart player has won " + totWins + " times " +
									"which is " + getWinRate() * 100 + " percent"
							+ "\nRandom player has won " + totLosses + " times " +
							"which is " + getLossRate() * 100 + " percent");
		
		//Board Stats results
		if(player == 1){
			bs = moveTable.get(temp);
		} else {
			temp.move(1, 1);
			bs = moveTable.get(temp);
		}
		bs.printStats();
	}
}
