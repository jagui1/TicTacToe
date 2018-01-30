package proj4;
import java.util.Random;

/**
 * An AI that acts randomly. If it can, it will 
 * make some legal move on the board it is given.
 */
public class RandomAI {
	//INSTANCE VARIABLE
	private Random rand;
	
	/**
	 * Constructor that initializes the Random()
	 */
	public RandomAI(){
		rand = new Random();

	}
	
	/**
	 * Takes a given board and makes a random move. If the board is full does nothing.
	 * 
	 * Input:
	 * @param t - The board we are moving on
	 * 
	 * Output: None
	 */
	public void move(TicTacToe t){
		//Only moves if the board ism't empty and the game isn't over
		if(!t.isFull() && !t.isOver()){
			//Has not made move yet
			boolean madeMove = false;
			
			//Generates random numbers until the move is valid
			while(!madeMove){
				int row = rand.nextInt(3);
				int col = rand.nextInt(3);
				//Checks validity of move
				madeMove = t.move(row, col);
			}//End make move while loop
			
		} else {
			//If the Board is full or game is over tells the user
			if(t.isFull()){
				System.out.println("Cannot move: Board is full");
			} else if(t.isOver()){
				System.out.println("Cannot move: Game is over");
			}
		}
	} 
	
//	/**
//	 * Main for testing
//	 */
//	public static void main(String[] args){
//		TicTacToe t = new TicTacToe();
//		
//		RandomAI hank = new RandomAI();
//		
//		System.out.println("Move 1");
//		hank.move(t);
//		t.printBoard();
//		System.out.println("Move 2");
//		hank.move(t);
//		t.printBoard();
//		System.out.println("Move 3");
//		hank.move(t);
//		t.printBoard();
//		System.out.println("Move 4");
//		hank.move(t);
//		t.printBoard();
//		System.out.println("Move 5");
//		hank.move(t);
//		t.printBoard();
//		System.out.println("Move 6");
//		hank.move(t);
//		t.printBoard();
//		System.out.println("Move 7");
//		hank.move(t);
//		t.printBoard();
//		System.out.println("Move 8");
//		hank.move(t);
//		t.printBoard();
//		System.out.println("Move 9");
//		hank.move(t);
//		t.printBoard();
//		System.out.println("Move 10");
//		hank.move(t);
//		t.printBoard();
//		
//	}

}
