package proj4;

/**
 * The main driver class that runs a game of tic tac toe between
 * a SmartPlayer and a RandomAI that will repeat the amount of times
 * entered
 *
 */
public class Project4 {
	
	/**
	 * It will play the two player objects against each other 
	 * (One SmartPlayer and one RandomAI) numGames number of times. 
	 * After the game, play will print a final summary of the series 
	 * of games. Since SmartPlayer is first in the constructor, he is player 1.
	 * 
	 * @param s - SmartPlayer that will learn based on his previous moves
	 * @param r - RandomAI that makes a random move ever time
	 * @param numGames - number of times the game will repeat
	 */
	public void play(SmartPlayer s, RandomAI r, int numGames){
		
		//Loops the game for the number of games entered
		for(int i = 0; i < numGames; i++){
			//Creates a new instance of the board for each game
			TicTacToe t = new TicTacToe();
			//Tells smart player what position he is and resets data
			s.newGame(1);
			//Boolean to keep track of the loop
			boolean stillPlaying = !t.isOver();

			//Loops until the current game ends
			while(stillPlaying){
				//Player one moves
				s.move(t);
				//Checks if player 1 ended the game
				stillPlaying = !t.isOver();
				
				//Player 2 moves if the game isn't over
				if(stillPlaying){
					r.move(t);
				}				
				
			}//End curGame while loop
			
			//Tells player 1 the game ended and passes in the finalBoard
			s.endGame(t);
			
		}//End TicTacToe for loop
		
		//Prints the results of all the games
		s.printResults();
	}
	
	/**
	 * It will play the two player objects against each other 
	 * (One SmartPlayer and one RandomAI) numGames number of times. 
	 * After the game, play will print a final summary of the series 
	 * of games. Since SmartPlayer is first in the constructor, he is player 1.
	 * 
	 * @param s - SmartPlayer that will learn based on his previous moves
	 * @param r - RandomAI that makes a random move ever time
	 * @param numGames - number of times the game will repeat
	 */
	public void play(RandomAI r, SmartPlayer s, int numGames){
		
		//Loops the game for the number of games entered
		for(int i = 0; i < numGames; i++){
			//Creates a new instance of the board for each game
			TicTacToe t = new TicTacToe();
			//Tells smart player what position he is and resets data
			s.newGame(2);
			//Boolean to keep track of the loop
			boolean stillPlaying = !t.isOver();

			//Loops until the current game ends
			while(stillPlaying){
				//Player one moves
				r.move(t);
				//Checks if player 1 ended the game
				stillPlaying = !t.isOver();
				
				//Player 2 moves if the game isn't over
				if(stillPlaying){
					s.move(t);
				}				
				
			}//End curGame while loop
			
			//Tells player 2 the game ended and passes in the finalBoard
			s.endGame(t);
			
		}//End TicTacToe for loop
		
		//Prints the results of all the games
		s.printResults();
	}
	

	/**
	 * Main function
	 */
	public static void main(String[] args){
		//Creates the Driver
		Project4 proj4 = new Project4();
		//Creates players
		SmartPlayer s = new SmartPlayer();
		RandomAI r = new RandomAI();
		//Plays Game
		//Smart player 1
		proj4.play(s, r, 1000);
		//Smart player2
//		proj4.play(r, s, 1000);
		
	}


}
