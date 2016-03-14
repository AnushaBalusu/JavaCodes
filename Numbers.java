import java.util.ArrayList;

/**
 * Definitions:
 *   - n: is a integer in the range between 2 and 100000 
 *   - m: is the mirror of n (n=73, m=37) 
 *   - bN: is the binary representation of n 
 *   - bM: is the binary representation of m
 * m and n satisfy all the following properties:
 *   - n is the k.st prime number (73 is the 21 prime number) 
 *   - m is mirror of k.st prime number (37 is the 12 prime number) 
 *   - bN is a palindrome
 *
 * @version    $Id: Numbers.java, v 1 2015/26/08 $
 *
 * @author     Anusha Balusu, Pankhuri Roy
 *
 */

public class Numbers{
   
   /**
    * Find prime numbers less than maxRange
    *
    * @param    maxRange    upper limit
    *
    * @return               list of prime numbers
    */

   public static ArrayList<Integer> findPrimeNumbers( int maxRange ) {
      ArrayList<Integer> primeArray = new ArrayList<Integer>();
      int primeIndex = 0;
      primeArray.add( 2 );
      for( int num = 3 ; num < maxRange ; num += 2 ) {
         for( primeIndex = 1 ; primeIndex < primeArray.size() ; primeIndex++ ) {
            if( num % primeArray.get(primeIndex) == 0 ) {
               break;
            }
         } // End for loop
         
         if( primeIndex == primeArray.size() ) {
            primeArray.add( num );
         }
      } // End for loop 
      return primeArray;
   } // End findPrimeNumbers

   /**
    * Find reverse of a number.
    *
    * @param    num    number
    *
    * @return          reverse of number
    */

   public static int reverseInt(int num) {
      int reverseNum = 0;
      for(int i = num ; i != 0 ; i /= 10 ) {
         reverseNum = reverseNum * 10 + i % 10;
      }
      return reverseNum;
   }

   /**
    * Checks if the binary of a number is palindrome.
    *
    * @param    num    number
    *
    * @return          true if binary of number is palindrome else false
    */

   public static boolean checkBinaryPalindrome( int num ) {
      boolean result = false;
      String binaryNum = Integer.toBinaryString(num);
      StringBuffer binaryString = new StringBuffer(binaryNum);
      if( binaryString.toString().equals(binaryString.reverse().toString()) ) {
         result = true;
      }
      return result;
   }

   /**
    * The main program.
    *
    * @param    args    command line arguments (ignored)
    */

   public static void main( String args[] ) {
//      long startTime = System.nanoTime();
      ArrayList<Integer> primeNumbers =  findPrimeNumbers( 10000 );
      int reverseOfNumber = 0;
      int indexOfReverseNum = -1;
      boolean isOutputOver = false;
      for( int counter = 0 ; counter < primeNumbers.size() ; counter++ ) {
         reverseOfNumber = reverseInt(primeNumbers.get(counter));
         indexOfReverseNum = primeNumbers.indexOf(reverseOfNumber);
         if( indexOfReverseNum != -1 
             && indexOfReverseNum + 1 == reverseInt(counter + 1) 
             && checkBinaryPalindrome(primeNumbers.get(counter)) ) {
            System.out.println( "..." );
            System.out.println( primeNumbers.get(counter) );
            isOutputOver = true;
         }
      } //End for loop
      if( isOutputOver ) {
            System.out.println( "..." );
      }else { 
            System.out.println( "No such numbers!" );
      }
//      long endTime   = System.nanoTime();
//      System.out.println(endTime - startTime);
   } //End main
}
