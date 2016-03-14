/**
 * Implementation of class that extends Thread 
 * Each thread is given a separate index and computes all multiples
 * of this index and marks them false 
 *
 * @version    $Id: MultiplierThread.java, v 1 2015/19/10 $
 *
 * @author     Anusha Balusu, Pankhuri Roy
 **/

public class MultiplierThread extends Thread {
   int multiplierIndex;
   int MAX;
   boolean [] numbers;

   public MultiplierThread(int max, int index, boolean [] numbers) {
      this.MAX = max;
      this.multiplierIndex = index;
      this.numbers = numbers;
   }

   /*
    * Each thread is given a separate index and computes all multiples
    * of this index and marks them false
    */

   public void run() {
      if ( multiplierIndex < MAX && numbers[multiplierIndex] )   {
         int counter = 2;
         while ( (multiplierIndex * counter) < MAX )  {
//         System.out.println("Inside run: index " +  multiplierIndex + " counter: " + counter + " prod: " + multiplierIndex * counter);
         numbers[multiplierIndex * counter] = false;
         counter++;
         }
      }
   }
}
