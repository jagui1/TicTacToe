package proj4;

import java.util.ArrayList;

/**
 * A generic hashtable class, mapping keys to values. 
 * Uses the built in hashCode method on the keys. A 
 * generic hashtable class, mapping keys to values. Uses 
 * the built in hashCode method on the keys. Insert and 
 * get should be on average O(1), no matter what the user 
 * does. The hashtable should have a reasonable amount of 
 * memory usage; it should never have more than five times 
 * the number of slots as it has enteries. The hashtable should 
 * handle collisions gracefully in whatever way you choose. 
 * The number of collisions should be low relative to the 
 * size of the hashtable.
 * 
 * @param <K> - key
 * @param <V> - value
 */
public class Hashtable<K, V> {
	
	//INSTANCE VARIABLES
	private int collisions;
	private int entries;
	private int curSize;
	private ArrayList<K>[] keys;
	private ArrayList<V>[] values;

	/**
	 * Default constructor that initializes the key array,
	 * the values array and each ArrayList inside of them
	 */
	public Hashtable(){
		//Initializes variables
		collisions = 0;
		entries = 0;
		
		//Initializes arrays of ArrayLists
        values = new ArrayList[101];
        for( int i = 0; i < values.length; i++ ){
            values[ i ] = new ArrayList<V>( );
        }
        keys = new ArrayList[101];
        for( int i = 0; i < keys.length; i++ ){
        	keys[ i ] = new ArrayList<K>( );
        }
        
        //Sets size
        curSize = keys.length;
	}
	
	/**
	 * Constructor that initializes the key array,
	 * the values array to the next prime of the size
	 * entered and each ArrayList inside of them
	 * 
	 * Input: 
	 * @param size - the size prefered of the HashTable
	 */
	public Hashtable(int size){
		//Initializes variables
		collisions = 0;
		entries = 0;
		
		//Initializes arrays of ArrayLists
        values = new ArrayList[nextPrime(size)];
        for( int i = 0; i < values.length; i++ ){
            values[ i ] = new ArrayList<V>( );
        }
        keys = new ArrayList[nextPrime(size)];
        for( int i = 0; i < keys.length; i++ ){
        	keys[ i ] = new ArrayList<K>( );
        }
        
        //Sets size
        curSize = keys.length;
	}
	
	/**
	 * Retrieves the object associated with this key. 
	 * If the key maps to nothing, it should return null.
	 * 
	 * Input: 
	 * @param key - Key we're using for retrieval
	 * 
	 * Output:
	 * @return Associated object
	 */
	public V get(K key){
//		System.out.println("Get Key: " + key);
		//Gets the position to be hashed
		int hashVal = getPositionInHashtable(key);
//		System.out.println("Get Hash val: " + hashVal);
		//Gets the list specific to the position
		ArrayList<K> keyList = keys[ hashVal ];
		ArrayList<V> valList = values[ hashVal ];

		//Loops through the ArrayList until there is a match with the key
		for(int i = 0; i < keyList.size(); i++){
//			System.out.println("Key: " + key.toString() + "Cur: " + keyList.get(i));
			if(keyList.get(i).toString().equals(key.toString())){
				//Returns the value that matches the Key
				return valList.get(i);
			}
		}
		//Returns null if it does not exist
		return null;
	}
	
	/**
	 * Returns true if the key is contained in the table, false otherwise.
	 * 
	 * Input: 
	 * @param key - Key we're using for retrieval 
	 * 
	 * Output:
	 * @return true if key maps to value
	 */
	public boolean containsKey(K key){
		//Gets the position to be hashed
		int hashVal = getPositionInHashtable(key);
		//Gets the list specific to the position
		ArrayList<K> keyList = keys[ hashVal ];
		
		//Loops through the ArrayList until there is a match with the key
		for(int i = 0; i < keyList.size(); i++){
			
			if(keyList.get(i).toString().equals(key.toString())){
				//Returns true if the value that matches the Key
				return true;
			}
		}
		//Returns false if there is no match
		return false;
	}
	
	/**
	 * Puts a key value pair in the hashtable. 
	 * If this key already corresponds to a value, 
	 * that value is overwritten.
	 * 
	 * Input:
	 * @param key - The key used for retrieval later
	 * @return value - Corresponding value
	 * 
	 * Output: None 
	 */
	public void put(K key, V value){
		//Calls hash function on the Key's hashcode
		int hashVal = getPositionInHashtable(key);
//		System.out.println("Put key" + key);
//		System.out.println();
		
		//Gets the lists specific to the position
		ArrayList<K> keyList = keys[ hashVal ];
		ArrayList<V> valList = values[ hashVal ];
		
		//Checks if the key is in the hashtable
        if( !containsKey( key ) ){
        	
        	//Rehashes if an ArrayList in a slot is > half the size of the board
        	if( keyList.size() + 1 > getCurSize() / 2 ){
        		rehash( );
        		
        		hashVal = getPositionInHashtable(key);
        		keyList = keys[ hashVal ];
        		valList = values[ hashVal ];
        	} 
        	//If there is an ArrayList in the position update collision
        	if(keyList.size() > 0){
        		collisions++;
        	}
        	
        	//Increments entries
        	++entries;
        	//Adds the values
        	keyList.add( key );
        	valList.add(value);
  
        } 
        
	}
	
	/**
	 * Resises the array sizes of the hashtable.
	 * Called if there are too many colisions.
	 * 
	 * Input: None
	 * 
	 * Output: None
	 */
	private void rehash() {
		//Creates temp values of arrays
		ArrayList<K> [ ]  oldKeys = keys;
		ArrayList<V> [ ]  oldVals = values;
		int newSize = nextPrime( 2 * getCurSize() );

        // Create new double-sized, empty table
		keys = new ArrayList[newSize];
		values = new ArrayList[ newSize ];
				
        for( int i = 0; i < values.length; i++ ){
            values[ i ] = new ArrayList<V>( );
        }
        for( int i = 0; i < keys.length; i++ ){
        	keys[ i ] = new ArrayList<K>( );
        }
		
        //Resets entries
		entries = 0;
		
		//
		for(int i = 0; i < oldKeys.length; i++){
			ArrayList<K> curKeyList = oldKeys[i];
			ArrayList<V> curValList = oldVals[i];
			
			for(int j = 0; j < curKeyList.size(); j++){
				K curKey = curKeyList.get(j);
				V curVal = curValList.get(j);
				put(curKey, curVal);
			}
			
		}

	}
	
    /**
     * Finds a prime number that is larger than n
     * 
     * Input:
     * @param n - the starting number
     * 
     * Output:
     * @return a prime number larger than or equal to n.
     */
    private static int nextPrime( int n ){
        if( n % 2 == 0 )
            n++;

        for( ; !isPrime( n ); n += 2 );

        return n;
    }

    /**
     * Tests if the entered number is prime by cycling through
     * numbers
     * 
     * Input:
     * @param n - the number to test.
     * 
     * Output:
     * @return true if n is prime, false otherwise
     */
    private static boolean isPrime( int n ){
    	//Checks if n is the base case true
        if(n == 2 || n == 3)
            return true;

        //Checks if n is the base case false
        if( n == 1 || n % 2 == 0 )
            return false;

        //Cycles through half n to find a divisor
        for(int i = 3; i*i <= n; i += 2)
            if(n % i == 0)
                return false;
        
        //Returns true if no divisors
        return true;
    }
	
	/**
	 * Returns the size of the array used to back your 
	 * hashtable. The array slots need not be full to count.
	 * 
	 * Input: None
	 * 
	 * Output:
	 * @return Size of the hashtable array
	 */
	public int numSlots(){
		return values.length;
	}
	
	/**
	 * Returns the number of things actually stored in this hashtable.
	 * 
	 * Input: None
	 * 
	 * Output:
	 * @return Number of elements stored
	 */
	public int numEntries(){
		return entries;
	}
	
	/**
	 * Returns the number of times a collision has occurred. 
	 * If you are handling collisions using by storing multiple 
	 * entries on one cell, you may return the number of doubled 
	 * up entries. If you are handling collisions by rehashing 
	 * (using a secondary hash function when collisions are detected) 
	 * have an instance variable that keeps track of how often this 
	 * has happened.
	 * 
	 * Input: None
	 * 
	 * Output:
	 * @return Number of collisions
	 */
	public int numCollisions(){
		return collisions;
	}
	
	/**
	 * This method should tell you where in the hashtable this key 
	 * will be stored, ASSUMING NO COLLISIONS. Depending on how you 
	 * design your project, this may be as simple as 
	 * key.hashCode() % array.length.
	 * 
	 * Input:
	 * @param key - The key we're trying to figure out where to store
	 * 
	 * Output:
	 * @return The index in the array we would store this key
	 */
	public int getPositionInHashtable(K key){
		//Gets hash code
        int hashVal = key.hashCode();        
        String keyStr = key.toString();
        //Takes an array of the chars in the toString
        char[] keyChars = keyStr.toCharArray();
        
        //Adds each ascii val to hashVal * prime 53
        for(char c: keyChars){
            hashVal = 53 * hashVal + c;
        }

        //Compresses the code
        hashVal %= getCurSize();
        
        if( hashVal < 0 ){
            hashVal *= -1;
        }
        
//		return hashVal;
        return 0;
	}
	
	/**
	 * Calculates and returns the leng of the array
	 * 
	 * Input: None
	 * 
	 * Output:
	 * @return size of the array
	 */
	public int getCurSize(){
		curSize = keys.length;
		return curSize;
	}
	/**
	 * Overrides toString to fit the final results
	 */
	public String toString(){
		  
		return "The number of slots is: " + numSlots() + "\n" + 
				"The number of entries is: " + numEntries() + "\n" +
				"Num collisions: " + numCollisions() + " \n" +
				"The % full is: " + (double)(numEntries()/numSlots()) * 100;
	}
	
//TEST CODE
//	public static void main(String[] args){
////		Hashtable<String, String> HT = new Hashtable<String, String>();
////		
////		System.out.println(HT);
////		HT.put("Key1", "Value1");
////		System.out.println(HT);
////		HT.put("Key2", "Value2");
////		System.out.println(HT);
////		HT.put("Key3", "Value3");
////		System.out.println(HT);
////		HT.put("Key4", "Value4");
////		System.out.println(HT);
////		HT.put("Key5", "Value5");
////		System.out.println(HT);
////		HT.put("Key6", "Value6");
////		System.out.println(HT);
////		HT.put("Key7", "Value7");
////		System.out.println(HT);
////		HT.put("Key8", "Value8");
////		System.out.println(HT);
////		HT.put("Key11", "Value11");
////		System.out.println(HT);
////		HT.put("Key9", "Value9");
////		System.out.println(HT);
////		HT.put("Key10", "Value10");
////		System.out.println(HT);
////		HT.put("Key2", "Value2");
////		HT.put("Key2", "Value3");
////		System.out.println(HT);
////		System.out.println("Has Key3?: " + HT.containsKey("Key3"));
////		System.out.println("Has Key45?: " + HT.containsKey("Key45"));
//		
//		
////		Hashtable<TicTacToe, BoardStats> HT = new Hashtable<TicTacToe, BoardStats>();
//		
////		System.out.println(HT);
////		HT.put("Key1", "Value1");
////		System.out.println(HT);
////		HT.put("Key2", "Value2");
////		System.out.println(HT);
////		HT.put("Key3", "Value3");
////		System.out.println(HT);
////		HT.put("Key4", "Value4");
////		System.out.println(HT);
////		HT.put("Key5", "Value5");
////		System.out.println(HT);
////		HT.put("Key6", "Value6");
////		System.out.println(HT);
////		HT.put("Key7", "Value7");
////		System.out.println(HT);
////		HT.put("Key8", "Value8");
////		System.out.println(HT);
////		HT.put("Key11", "Value11");
////		System.out.println(HT);
////		HT.put("Key9", "Value9");
////		System.out.println(HT);
////		HT.put("Key10", "Value10");
////		System.out.println(HT);
////		HT.put("Key2", "Value2");
////		HT.put("Key2", "Value3");
//		
//		Hashtable<TicTacToe, BoardStats> HT = new Hashtable<TicTacToe, BoardStats>();
//		  
//	    TicTacToe t = new TicTacToe();
//	    System.out.println("This is our board:");
//	    System.out.println(t);
//	    TicTacToe t3 = new TicTacToe(t);
//	    BoardStats h = new BoardStats(t3);	    
//	    HT.put(t3, h);
//	    System.out.println(HT);
//	    t.move(0,1);
//	    System.out.println("This is our board:");
//	    System.out.println(t);
//	    h = new BoardStats(t);	    
//	    HT.put(t, h);
//	    System.out.println(HT);
//	    t.move(0,0);
//	    System.out.println("This is our board:");
//	    System.out.println(t);
//	    h = new BoardStats(t);	    
//	    HT.put(t, h);
//	    System.out.println(HT);
//	    t.move(2,0);
//	    
//	    System.out.println("This is our board:");
//	    System.out.println(t);
//	    
//	    HT.put(t, h);
//	    System.out.println(HT);
//	    System.out.println("Checking if the key we just used is in there: " + HT.containsKey(t));
//	    TicTacToe n = new TicTacToe();
//	    System.out.println("Checking if a brand new board that hasn't been inserted, is in: "+HT.containsKey(n));
////	    System.out.println("Checking if a brand new board that hasn't been inserted, is in: "+HT.containsKey(t3));
//	    System.out.println("T3: " + t3);
//	    System.out.println("n: " + n);
//	}
}
