/**
 * Multithreading
 *
 * @version    $Id: SieveOfEratosthenes.java, v 1 2015/19/10 $
 *
 * @author     Anusha Balusu, Pankhuri Roy
 **/


public class SieveOfEratosthenes {

   static final int soManyThreads  = Runtime.getRuntime().availableProcessors();
   final static int FIRSTpRIMEuSED = 2;
   static int MAX;
   final boolean[] numbers;
   private int limit;
   private static final Object lock = new Object();

   // Constructor
   public SieveOfEratosthenes(int max) {
      numbers = new boolean[max];
      this.MAX = max;
      limit = (MAX > 10 ? (int)Math.sqrt(MAX) + 1 : 3);
      System.out.println("limit " + limit);
      for (int counter = 1; counter < MAX; counter ++ )  {
         numbers[counter] = true;
      }
   }

   /*
    * Prints all the numbers less than MAX and 
    * for each number prints true if prime otherwise false.
    */

   public void testForPrimeNumber()  {
      //int[] test = { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, MAX - 1, MAX};
      int [] test = new int[MAX];
      for (int index = 0; index < test.length; index ++ )   {
         test[index] = index + 2;
      }
      for (int index = 0; index < test.length; index ++ )   {
         if ( test[index] < MAX )   {
            System.out.println(test[index] + " = " + numbers[test[index]]);
         }
      }
   }

   /*
    * Creates n threads as given by user and maintains same number of threads.
    * 
    * @param   noOfThreads    user input, max number of threads = min number 
    *                                                 of threads at any time
    */

   public void createThreads(int noOfThreads) {
      int threadCounter = 0;
      MultiplierThread[] threadArray = new MultiplierThread[limit];

      for(int index = 2; index < limit; index++) {
         while(Thread.activeCount() > noOfThreads) {
         }
         threadArray[threadCounter] = new MultiplierThread( MAX, index, numbers );
         threadArray[threadCounter].start();
         threadCounter++;

      }
      for(Thread thread : threadArray) {
         try {
            if( thread != null && thread.isAlive() ) 
               thread.join();
         }catch(InterruptedException e) {
            e.printStackTrace();
         }
      }
   }

   /*
    * Validates user input whether number of threads is integer and > 0
    * and < available processors
    */

   public int validateNoOfThreads(String num) {
      int noOfThreads = 0;
      try {
         noOfThreads = Integer.parseInt(num);
         if (noOfThreads <= 0) {
            System.out.println("Maximum number of multiplier threads cannot be zero or negative");
         }else if(noOfThreads > soManyThreads) {
            System.out.println("Maximum number of multiplier threads exceed available processors");
            noOfThreads = -1;
         }
      }catch(Exception e) {
         System.out.println("Maximum number of multiplier threads not an integer");
      }
      return noOfThreads;
   }

   /*
    * The main program
    *
    * @param      args     number of threads
    */

   public static void main( String[] args ) {

      SieveOfEratosthenes aSieveOfEratosthenes = new SieveOfEratosthenes(20);
      if(args != null && args.length > 0) { 
         int noOfThreads = aSieveOfEratosthenes.validateNoOfThreads(args[0]);
         if(noOfThreads > 0) aSieveOfEratosthenes.createThreads(noOfThreads);
         aSieveOfEratosthenes.testForPrimeNumber();
      }else {
         System.out.println("Maximum number of multiplier threads not given");
      }

      System.exit(0);
   }
}


