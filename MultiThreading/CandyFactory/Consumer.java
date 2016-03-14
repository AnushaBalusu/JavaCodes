/**
 * Implements the Consumer thread 
 * Depending on the type of Producer, removes jobs from 2 queues and puts 
 * a combined job in another queue
 *
 * @version    $Id: Consumer.java, v 1 2015/26/10 $
 *
 * @author     Anusha Balusu, Pankhuri Roy
 **/ 

import java.util.List;
import java.util.Vector;

class Consumer extends Thread { 
   private String name;
   private final List<String> sharedSourceQueue1;
   private final List<String> sharedSourceQueue2;
   private final List<String> sharedTargetQueue;
   private final int sourceSize;
   private final int targetSize;
   private Vector<Boolean> isFilledBoxQueueFull;
   private String job1;
   private String job2;
   
   public Consumer(String name, List<String> sourceQueue1, 
         List<String> sourceQueue2, List<String> targetQueue, 
         int sourceSize, int targetSize, Vector<Boolean> isFilledBoxQueueFull) {
      this.name = name;
      this.sharedSourceQueue1 = sourceQueue1;
      this.sharedSourceQueue2 = sourceQueue2;
      this.sharedTargetQueue = targetQueue;
      this.sourceSize = sourceSize;
      this.targetSize = targetSize;
      this.isFilledBoxQueueFull = isFilledBoxQueueFull;
   }

   /**
    * Thread's run method
    *
    **/

   public void run() {
      while(true) {
         consume();            
      } 
   }

   /**
    * Consumes the jobs from the required queues and puts combined job into 
    * target queue
    * If queue is empty, Consumer thread waits
    * Notifies all after adding to target queue
    **/

   private void consume() {
      // synchronize on source queue 1
      synchronized (sharedSourceQueue1) {
         if(!name.equals("C2")) {
            while(sharedSourceQueue1.size() == 0) {
               try {
                  sharedSourceQueue1.wait();
               } catch (InterruptedException e) {
                  e.printStackTrace();
               }
            }

            job1 = sharedSourceQueue1.remove(0);
            System.out.println("----------------------" + name + " removed " + job1 + " | current size = " + sharedSourceQueue1.size());
            sharedSourceQueue1.notifyAll();
         }else{
            while(sharedSourceQueue1.size() < 4) {
               try {
                  sharedSourceQueue1.wait();
               } catch (InterruptedException e) {
                  e.printStackTrace();
               }
            }
            for(int counter=0; counter < 4; counter++) {
               job1 = sharedSourceQueue1.remove(0);
               System.out.println("----------------------" + name + " removed " + job1 + " | current size = " + sharedSourceQueue1.size());
            }
            sharedSourceQueue1.notifyAll();

         }
      }

      // synchronize on source queue 2
      synchronized (sharedSourceQueue2) {
         while(sharedSourceQueue2.size() == 0) {
            try {
               sharedSourceQueue2.wait();
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }

         job2 = sharedSourceQueue2.remove(0);
         System.out.println("----------------------" + name + " removed " + job2+ " | current size = " + sharedSourceQueue2.size());
         sharedSourceQueue2.notifyAll();
      }

      // synchronize on target queue
      synchronized (sharedTargetQueue) {
         String job = getConsumerJobName(job1, job2);
         sharedTargetQueue.add(job);
         sharedTargetQueue.notifyAll();
         System.out.println("----------------------" + name + " added : " + job + " | current size : " + sharedTargetQueue.size());
         if(name.equals("C2") && sharedTargetQueue.size() == targetSize){
            isFilledBoxQueueFull.set(0, true);
            System.out.println("DONE");
            System.exit(0);
         }

      }

   }

   /**
    * Gets the type of job to be added in queue based on the Consumer thread
    *
    * @param      name     name of Producer thread
    *
    * @return              type of item to be added in queue
    **/

   public String getConsumerJobName(String job1, String job2) {
      String jobName = null;
      if( job1.equals("Candy") && job2.equals("Wrapping Paper") ) {
         jobName = "Wrapped Candy";
      }else if(job1.equals("Wrapped Candy") && job2.equals("Empty Box")) {
         jobName = "Filled Box";
      }
      return jobName;
   }
}
