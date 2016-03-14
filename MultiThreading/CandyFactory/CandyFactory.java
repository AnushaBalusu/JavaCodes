/**
 * Produces Candy and Wrapper and filles boxes with wrapped candies
 * 
 * @version    $Id: CandyFactory.java, v 1 2015/26/10 $
 *
 * @author     Anusha Balusu, Pankhuri Roy
 **/

import java.util.List;
import java.util.LinkedList;
import java.util.Vector;

class CandyFactory {

   public static Vector<Boolean>  isFull = new Vector<Boolean>();
   public CandyFactory() {
   }

   /**
    * The main program
    * Creates all producer and consumer threads and starts them
    *
    * @param   args     Ignored
    **/ 
   public static void main(String[] args) {

      int k = 2;
      int l = 6;
      int m = 8;
      int n = 2;
      
      isFull.add(false);

      // creating all queues
      List<String> candyQueue = new LinkedList<String>();
      List<String> wrapperQueue = new LinkedList<String>();
      List<String> wrappedCandyQueue = new LinkedList<String>();
      List<String> emptyBoxQueue = new LinkedList<String>();
      List<String> filledBoxQueue = new LinkedList<String>();


      Producer candyProducer = new Producer("P1", candyQueue, k, isFull);
      Producer candyWrappingPaperProducer = new Producer("P2", wrapperQueue, l, isFull);
      Producer emptyBoxProducer = new Producer("P3", emptyBoxQueue, m, isFull);

      Consumer candyAndWrapperConsumer = new Consumer("C1", candyQueue, wrapperQueue, wrappedCandyQueue, k, m, isFull);
      Consumer wrappedCandyAndBoxConsumer = new Consumer("C2", wrappedCandyQueue, emptyBoxQueue, filledBoxQueue, m, n, isFull);

      // starting all producer and consumer threads
      candyProducer.start();
      candyWrappingPaperProducer.start();
      emptyBoxProducer.start();
      candyAndWrapperConsumer.start();
      wrappedCandyAndBoxConsumer.start();

   }
}
