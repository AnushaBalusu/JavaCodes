/**
 * This program determines all possible subsets of n people attending a party.
 *
 * @version    $Id: Subsets.java, v 1 2015/02/09 $
 *
 * @author     Anusha Balusu, Pankhuri Roy
 *
 */

import java.util.Scanner;
import java.lang.Math;

class Subsets {

   /**
    * The main program.
    *
    * @param   args           command line arguments (ignored)
    */
   
   public static void main( String args[] ) {
      try {
         int people = Integer.parseInt(args[0]);
         if( people < 0 ){
            throw new Exception();
         }
         String[] subsetArray = findAllSubsets( people );
         printSubsets( subsetArray );
      }catch(Exception e) {
         System.out.println("Invalid Input! Give a positive integer.");
      }
   }

   /**
    * Given the subsets of n-1, this program finds the subsets of n.
    *
    * @param   number         number n whose subsets are required
    * @param   subsetArray    list of subsets of n-1
    *
    * @return                 list of subsets of n (excluding null set)
    */

   public static String[] createSubsets( int number, String[] subsetArray ) {
      // subtracting 1 coz null set is not in index 0.
      int insertAtIndex = findPowerSetCardinality(number-1) - 1; 
      subsetArray[insertAtIndex] = Integer.toString(number);
      int location = insertAtIndex;
      for(int index = 0; index < insertAtIndex; index++ ) {
         // the elements in the subset are separated by comma and stored.
         subsetArray[++location] = subsetArray[index]+ ", " + number;
      }
      return subsetArray;
   }

   /**
    * Computes the cardinality of power set of a set with n elements.
    *
    * @param   value          cardinality of set:n
    *
    * @return                 cardinality of power set:2^n
    */

   public static int findPowerSetCardinality( int value ) {
      int product = 1;
      while( value != 0 ) {
         product *= 2;
         value--;            
      }
      return product;
   }

   /**
    * Finds all subsets of a set with n number of elements including null set.
    *
    * @param   number         cardinality of set:n
    *
    * @return                 list of all subsets of n with null set at the end
    */

   public static String[] findAllSubsets( int number ) {
      int noOfSubsets = findPowerSetCardinality( number );
      String[] subsetArray = new String[noOfSubsets];
      for( int counter = 1; counter <= number; counter++ ) {
         subsetArray = createSubsets( counter, subsetArray );
      }
      subsetArray[subsetArray.length - 1] = "";
      return subsetArray;
   }

   /**
    * Prints all subsets of a set with n number of elements with set brackets.
    *
    * @param   subsetArray    list of all subsets
    */

   public static void printSubsets( String[] subsetArray ) {
      System.out.print( "{ " );
      for( int index = 0; index < subsetArray.length; index++ ) {
         if( index == subsetArray.length - 1) {
            System.out.print( "{" + subsetArray[index] + "} " );
         }else {
            System.out.print( "{" + subsetArray[index] + "}, " );
         }
      }
      System.out.println( "}" );
   }

}
