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
public class Hashtable2<K, V> {
 
 private int collisions;
 private int entries;
 private int curSize;
 private ArrayList<K>[] keys;
 private ArrayList<V>[] values;

 
 public Hashtable2(){
  collisions = 0;
  entries = 0;
  
        values = new ArrayList[5];
        for( int i = 0; i < values.length; i++ ){
            values[ i ] = new ArrayList<V>( );
        }
        keys = new ArrayList[5];
        for( int i = 0; i < keys.length; i++ ){
         keys[ i ] = new ArrayList<K>( );
        }
        
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
  //Obtaining the hash value of the key
  int hashVal = getPositionInHashtable(key);
  //Obtaining the list of keys at the hashed index
  ArrayList<K> keyList = keys[ hashVal ];
  //
  int valIndex = keyList.indexOf(key);
  //Obtaining the list of values at the hashed index
  ArrayList<V> valList = values[ hashVal ];
  return valList.get(valIndex);
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
  System.out.println("Key: " + key);
  //Obtaining the hash value of the key
  int hashVal = getPositionInHashtable(key);
  System.out.println("Hash val: " + hashVal);
  //Obtaining the list of keys at the hashed index
  ArrayList<K> keyList = keys[ hashVal ];
  
  //looping through each key
  for(int i = 0; i < keyList.size(); i++){
   System.out.println("Cur: " + keyList.get(i));
   //checking to see if the keys match
   if(keyList.get(i).toString().equals(key.toString())){
   //keys match so return true
    return true;
   }
  }
  //couldn't find matching key
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
 //Obtaining the hash value of the key
  int hashVal = getPositionInHashtable(key);
  
  //the list of keys at the hashed index
  ArrayList<K> keyList = keys[ hashVal ];
  //the list of values at the hashed index
  ArrayList<V> valList = values[ hashVal ];
  
  //if the key isn't already in the hashed table
        if( !containsKey( key ) ){
         System.out.println("Adding at index: " + hashVal);
   //adding the key to list of keys
            keyList.add( key );
   //adding the value to list of values
            valList.add(value);
            
   //checking if need to resize/rehash
            if( ++entries > keys.length )
                rehash( );
        } else {//else hashtable does contain the key
  //updating the amount of collisions
         collisions++;
         
         System.out.println("Collision!\n" + "Key: " + key + 
              " New value: " + value + " with: " + get(key)); 
        }
  
 }
 
 private void rehash() {
   
  //list containing the current keys
  ArrayList<K> [ ]  oldKeys = keys;
  //list containing the current values
  ArrayList<V> [ ]  oldVals = values;

   // creating empty lists with a prime double the size
  keys = new ArrayList[nextPrime( 2 * keys.length ) ];
  values = new ArrayList[ nextPrime( 2 * values.length ) ];
  
  //looping through each element
  for( int j = 0; j < keys.length; j++ ){
  //copying over the keys and values to the new arraylists
   keys[ j ] = new ArrayList<K>( );
   values[ j ] = new ArrayList<V>( );
  }   
  
   // Copy table over
  entries = 0;
  
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
     * Internal method to find a prime number at least as large as n.
     * @param n the starting number (must be positive).
     * @return a prime number larger than or equal to n.
     */
    private static int nextPrime( int n ){
        if( n % 2 == 0 )
            n++;

        for( ; !isPrime( n ); n += 2 )
            ;

        return n;
    }

    /**
     * Internal method to test if a number is prime.
     * Not an efficient algorithm.
     * @param n the number to test.
     * @return the result of the test.
     */
    private static boolean isPrime( int n )
    {
        if( n == 2 || n == 3 )
            return true;

        if( n == 1 || n % 2 == 0 )
            return false;

        for( int i = 3; i * i <= n; i += 2 )
            if( n % i == 0 )
                return false;

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
  //overriding the hashcode
        int hashVal = key.hashCode();
        //overriding the toString of key
        String keyStr = key.toString();
  //generating an array of chars from the key string
        char[] keyChars = keyStr.toCharArray();
        
  //for each character in this char list
        for(char c: keyChars){
            hashVal = 53 * hashVal + c;
        }

  //compressing the hashcode to obtain..
        
      //compressing the hashcode to obtain an index for hashtable
        hashVal %= values.length;

        if( hashVal < 0 ){
            hashVal += values.length;
        }
        
  return hashVal;
 }
 public String toString(){
  return "Num collisions: " + numCollisions() + " \n" +
    "Num slots: " + numSlots() + "\n" + 
    "Num entries: " + numEntries() + "\n";
 }
}