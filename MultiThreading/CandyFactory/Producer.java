/**
 * Implements the Producer thread
 * Depending on the type of Producer, creates jobs and puts them in queue
 *
 * @version    $Id: Producer.java, v 1 2015/26/10 $
 *
 * @author     Anusha Balusu, Pankhuri Roy
 **/

import java.util.List;
import java.util.Random;
import java.util.Vector;

class Producer extends Thread { 
   private String name;
   private final List<String> sharedQueue;
   private final int size;
   private Vector<Boolean> isFilledBoxQueueFull;
   public Producer(String name, List<String> sharedQueue, int size,Vector<Boolean> isFilledBoxQueueFull) {
      this.name = name;
      this.sharedQueue = sharedQueue;
      this.size = size;
      this.isFilledBoxQueueFull = isFilledBoxQueueFull;
   }

   /**
    * Puts the job (Candy / Wrapping paper/ empty box) in the corresponding 
    * queue
    *
    * @param      job      type of item put in queue
    *
    **/ 

   public void makeWork(String job) {
      synchronized(sharedQueue) {
         //queue full
         boolean isSharedQueueFull = checkIfQueueFull(); 

         while(isSharedQueueFull) {
            try {
               // wait if queue is full
               sharedQueue.wait();
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
            isSharedQueueFull = checkIfQueueFull();
         }

         if(!name.equals("P2")) {
            sharedQueue.add(job);
            System.out.println(name + " added " + job + " | current size: " + sharedQueue.size());
         }else{
            sharedQueue.add(job);
            System.out.println(name + " added " + job + " | current size: " + sharedQueue.size());
            sharedQueue.add(job);
            System.out.println(name + " added " + job + " | current size: " + sharedQueue.size());
            sharedQueue.add(job);
            System.out.println(name + " added " + job + " | current size: " + sharedQueue.size());
         }
         sharedQueue.notifyAll();
      }
   }

   /**
    * Thread's run method
    * Synchronized over the shared queue (fixed size)
    * If the queue is full, producer waits else adds the job
    **/

   public void run() {
      int i=0;
      String jobItem;
      Random random = new Random();
      while( !isFilledBoxQueueFull.get(0) ) {
         jobItem = getProducerJobName(name);
         makeWork( jobItem );            
         try {
            Thread.sleep(random.nextInt(1000));
         }
         catch(InterruptedException e) {
            e.printStackTrace();
         }
      }
      System.out.println(Thread.activeCount());
   }

   /**
    * Gets the type of job to be added in queue based on the Producer thread
    *
    * @param      name     name of Producer thread
    *
    * @return              type of item to be added in queue
    **/  

   public String getProducerJobName(String name) {
      String jobName = null;
      if(name.equals("P1")) jobName = "Candy";
      else if(name.equals("P2")) jobName = "Wrapping Paper";
      else if(name.equals("P3")) jobName = "Empty Box";
      return jobName;
   }

   /**
    * Checks if queue is full
    *
    * @return        true if queue is full otherwise false
    **/

   public boolean checkIfQueueFull() {

         return (name.equals("P2")) ? (sharedQueue.size() > size-3) : (sharedQueue.size() == size);
   }
}
