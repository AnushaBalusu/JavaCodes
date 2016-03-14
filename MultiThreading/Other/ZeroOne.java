/**
 * Implementation of class that creates 99 threads and prints
 * each thread's id sequentially without terminating the program.
 *
 * @version    $Id: ZeroOne.java, v 1 2015/26/10 $
 *
 * @author     Anusha Balusu, Pankhuri Roy
 **/
public class ZeroOne extends Thread {
   private String info;
   static int index;
   static Object o = new Object();
   final static int noOfThreads = 99;
   static Object[] oArray = new Object[noOfThreads];
   static boolean allAreRunning = false;

   static {
      for(int i = 0; i < noOfThreads; i++) {
         oArray[i] = new Object();
      }
   }
   public ZeroOne (String info) {
      this.info    = info;
   }

   /**
    * The run method, creates 99 threads and prints the id of
    * each thread in a sequential fashion endlessly.
    **/
   public void run () {
      boolean canPrint = true;
      while ( true ) {
         synchronized ( o ) {
            try {
               canPrint = true;
               if(allAreRunning && index != Integer.parseInt(info)) {
                  canPrint = false;
                  o.notify();
               }
               else if(allAreRunning){
                  o.notifyAll();
               }
               if(canPrint) {
                  index = (index+1)%noOfThreads;
                  System.out.print(info);
               }
               if ( ! allAreRunning )   {
                  ( new ZeroOne(index + "") ).start();
                  if(index == noOfThreads-1) allAreRunning = true;
               }
               sleep(300);
               o.wait();
            } catch ( Exception e ) { 
               e.printStackTrace();
            }
         } 
      }
   }

   /**
    * The main program, creates a thread and pass the string
    * "0" to the constructor.
    * 
    * @param   args     Ignored
    **/

   public static void main (String args []) {
      new ZeroOne("0").start();
   }
}
