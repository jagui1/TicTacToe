package proj4;

import java.util.Scanner;

/**
 * A very simple class representing a game of TicTacToe. The first player (X) 
 * is player 1, the second player (O) is player 2. Moves are indexed starting 
 * at zero. You may not call the built in hashCode() function at any point in 
 * this file.
 */
public class TicTacToe {
	//CONSTANTS
	//Amount needed to win
	private int WIN_AMT = 3;
	//Characters that represent players
	private char[] PLAYERS = {'-', 'X', 'O'};
	//Character for empty space
	private char EMPTY_SPACE = PLAYERS[0];
	//Integer representation of each
	private int PLAYER1_INT = 1;
	private int PLAYER2_INT = 2;
	private int EMPTY_INT = 0;
	
	//INSTANCE VARIABLES
	private char[][] board;
	private int curPlyr;
	private int winner;
	
	//TEST CODE
//	private Scanner input;

	
	/**
	 * Default constructor that initializes all
	 * values as default values
	 */
	public TicTacToe(){
		//TEST CODE
//		this.input = new Scanner(System.in);
		
		//2 dimensional array of characters represents the board
		board = new char[3][3];		

		//Initializes the board
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board.length; j++){
				board[i][j] = EMPTY_SPACE;
			}
		}
		//Initializes counter		
		curPlyr = 1;
		winner = -1;
	}
	
	/**
	 * Copy constructor that initializes the 
	 * variables based on the entered board
	 * 
	 * Input:
	 * @param t - board to be copied
	 * 
	 * Output: None
	 */
	public TicTacToe(TicTacToe t){
		//TEST CODE
//		this.input = new Scanner(System.in);

		
		this.board = new char[3][3];		
		
		//Copies board
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board.length; j++){
				this.board[i][j] = PLAYERS[t.playerAt(i, j)];
			}
		}
		
		//Copies variables
		this.curPlyr = t.curPlyr;
		this.winner = t.winner;
		
	}
	
//	/**
//	 * (NOT FOR PROJECT)
//	 * Plays the game with 2 humans
//	 */
//	public void playGame(){
//		boolean gameRunning = true;
//		
//		while(gameRunning){
//			
//			printBoard();
//			System.out.println("HashCode: " + hashCode());
//			
//			getMove(curPlyr);
//			
//			gameRunning = !isOver();
//		}
//	}
//
//	
//	/**
//	 * (NOT FOR PROJET)
//	 * Gets the move from the player
//	 */
//	private void getMove(int curPlyr){
//		boolean invalid = true;
//		int row = -1;
//		int col = -1;
//		System.out.println("Player " + curPlyr + "'s turn!!");
//		
//		while(invalid){
//			
//			try{
//				System.out.print("Enter a row (1-3): ");
//				
//				String temp = input.next();				
//				row = Integer.parseInt(temp);
//				
//				if(row < 1 || row > 3){
//					throw new Exception();
//				}
//				
//				System.out.print("Enter a column (1-3): ");
//				col = input.nextInt();
//				
//				if(col < 1 || col > 3){
//					throw new Exception();
//				}		
//				
//				if(move(row - 1, col - 1) == true){
//					winCheck();
//					invalid = false;
//				} else {
//					System.out.println("Invalid move. Try again.\n");
//				}
//				
//			} catch(Exception e) {
//				System.out.println("Invalid input. Try again.\n");
//			}
//
//		}
//	}
	
	/**
	 * Makes a move in the given cell if it is empty 
	 * (returns false if this is not possible). Cell indexing starts at 0.
	 * 
	 * Input:
	 * @param row - the row of the move
	 * @param col - the column of the move
	 * 
	 * Output:
	 * @return Whether or not the move was made successfully
	 */
	public boolean move(int row, int col){
		//Checks if move is valid
		if(playerAt(row, col) == EMPTY_INT){
			//Makes move and changes player
			board[row][col] = PLAYERS[curPlyr];
			
			if(curPlyr == PLAYER1_INT){
				curPlyr = PLAYER2_INT;
			} else if(curPlyr == PLAYER2_INT){
				curPlyr = PLAYER1_INT;
			}
			winCheck();
			return true;
			
		} else {
			//Invalid move, returns false
			winCheck();
			return false;
		}
	}
	
	/**
	 * Tells you who won the game.
	 * 
	 * Input: None
	 * 
	 * Output: 
	 * @return Returns 1 if player 1 won, 2 if player 2 won, 0 if it is a draw and -1 if game is not over.
	 */
	public int getWinner(){
		return winner;
	}
	
	/**
	 * Checks if anyone won
	 */
	private void winCheck(){
		//Checks the win count of each row collumn and diagonal
		for(int i = 0; i < board.length; i++){
			
			if(rowWin(PLAYER1_INT) || colWin(PLAYER1_INT) || diagWin(PLAYER1_INT)){
				winner = PLAYER1_INT;
				
			} else if(rowWin(PLAYER2_INT) || colWin(PLAYER2_INT) || diagWin(PLAYER2_INT)){
				winner = PLAYER2_INT;
			} else if(isFull()){
				winner = EMPTY_INT;
			}
			
		}
		
	}
	/**
	 * Couts the amount of moves the player made
	 * in each row and returns true if they have 
	 * 3 in a row
	 * 
	 * Input: 
	 * @param player - the number of the current player
	 * 
	 * Output:
	 * @return true if player has 3 in a row, false otherwise
	 */
	private boolean rowWin(int player){
		int counter = 0;
		
		//Loops through rows
		for(int row = 0; row < board.length; row++){
			//Resets counter
			counter = 0;
			
			//Loops through columns
			for(int col = 0; col < board.length; col++){
				if(board[row][col] == PLAYERS[player]){
					counter++;
				}
			}
			
			if(counter == WIN_AMT){
				return true;
			}
			
		}		
		//False if counter never reaches 3
		return false;	
	}
	
	/**
	 * Couts the amount of moves the player made
	 * in each collumn and returns true if they have 
	 * 3 in a row
	 * 
	 * Input: 
	 * @param player - the number of the current player
	 * 
	 * Output:
	 * @return true if player has 3 in a collumn, false otherwise
	 */
	private boolean colWin(int player){
		int counter  = 0;
		
		//Loops through collumns
		for(int col = 0; col < board.length; col++){
			//Resets counter
			counter = 0;

			//Loops through rows
			for(int row = 0; row < board.length; row++){
				if(board[row][col] == PLAYERS[player]){
					counter++;
				}
			}
			
			if(counter == WIN_AMT){
				return true;
			} 
		}
		//Returns false if counter never reaches 3
		return false;
	}
	
	/**
	 * Couts the amount of moves the player made
	 * in each diagonal and returns true if they have 
	 * 3 in a row
	 * 
	 * Input: 
	 * @param player - the number of the current player
	 * 
	 * Output:
	 * @return true if player has 3 in a diagonal, false otherwise
	 */
	private boolean diagWin(int player){
		int counter  = 0;
	
		//Loops through first diagonal
		for(int i = 0; i < board.length; i++){
			if(board[i][i] == PLAYERS[player]){
				counter++;
			}
		}
		//If they won return true
		if(counter == WIN_AMT){
			return true;
		} 
	
		else {
			//Loops through other diagonal
			counter  = 0;
			int row = 2;
			
			for(int i = 0; i < board.length; i++){
				if(board[row][i] == PLAYERS[player]){
					counter++;
				}
				
				row -= 1;
			}
			
			if(counter == WIN_AMT){
				return true;
			} 
			
		}
		//Returns false if counter never reaches 3
		return false;

	}
	
	/**
	 * Test if the game is over
	 * 
	 * Input: None
	 * 
	 * Output:
	 * @return True if the game has ended, false otherwise
	 */
	public boolean isOver(){
		if(winner == -1){
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Checks if there are moves in every space on the board
	 */
	public boolean isFull(){
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board.length; j++){
				if(board[i][j] == EMPTY_SPACE){
					return false;
				}
			}
		}
		
		return true;
	}
	/**
	 * Returns the current move made at the given location
	 * 
	 * Input: 
	 * @param row - the row
	 * @param col - the column
	 * 
	 * Output:
	 * @return A 1, 2 or a 0 based on the player's move. -1 if error
	 */
	public int playerAt(int row, int col){

		if(board[row][col] == PLAYERS[1]){
			return PLAYER1_INT;
		} else if(board[row][col] == PLAYERS[2]){
			return PLAYER2_INT;
		} else if(board[row][col] == EMPTY_SPACE){
			return EMPTY_INT;
		} else {
			return -1;
		}
			
	}
	
	/**
	 * String representation of this object.
	 * 
	 * Overrides: toString in class java.lang.Object
	 * 
	 * Input: None
	 * 
	 * Output:
	 * @return Object in string form. Should be -'s for spaces, X and O for the player moves. 
	 * For example: XO-XXO---
	 */
	public java.lang.String toString(){
		String curStr = "";
		
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board.length; j++){
				curStr += board[i][j];
			}
		}
		return curStr;
	}
	
	/**
	 * Prints the current board
	 * 
	 * Input: None
	 * 
	 * Output: None
	 */
	public void printBoard(){
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board.length; j++){
				if(board[i][j] == EMPTY_SPACE){
					System.out.print(".");
				} else {
					System.out.print(board[i][j]);
				}
			}
			System.out.println();
		}
	}
	
	/**
	 * Returns a hashcode for this object. 
	 * Code this however you like, as long as you don't use the 
	 * hashCode method for anything else in java. Your hashtable 
	 * will use this to find slots for the object.
	 * 
	 * Overrides: hashCode in class java.lang.Object
	 * 
	 * Input: None
	 * 
	 * Output: 
	 * @return hash code
	 */ 
	public int hashCode(){
		String curTable = toString();
		String hashString = "";

		//Creates a string of integers based on plater at the position
		for(char car: curTable.toCharArray()){
			if(car == PLAYERS[1]){
				hashString += PLAYER1_INT;
			} else if(car == PLAYERS[2]){
				hashString += PLAYER2_INT;
			} else {
				hashString += 0;
			}
		}
		//Converts that string to an integer and returns
		return Integer.parseInt(hashString);
	}
	
	/**
	 * Returns the length of the board
	 * 
	 * Input: None
	 * 
	 * Output:
	 * @return - the length of the board
	 */
	public int getLength(){
		return board.length;
	}
	
	//TEST MAIN
//	public static void main(String[] args){
//		TicTacToe t = new TicTacToe();	
//
////		t.playGame();
//		
//	}
}
