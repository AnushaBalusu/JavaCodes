/**
 * Producer truck takes care of truck entry
 * Waits if 4 trucks are on the bridge
 * Allows trucks to enter bridge if less than 4
 *
 * @version    $Id: ProducerTruck.java, v 1 2015/2/11 $
 *
 * @author     Anusha Balusu, Pankhuri Roy
 **/
import java.util.Random;
import java.util.Vector;

class ProducerTruck extends Thread {

   private final static int MAXtRUCKS = 4;
   private final static int MAXwEIGHT = 200000;
   private final static int MINwEIGHTrANGE = 100;
   private final static int MAXwEIGHTrANGE = 100000;
   Bridge bridge;

   public ProducerTruck(Bridge bridgeDetails) {
      this.bridge = bridgeDetails;
   }

   /**
    * Allows truck to get on bridge.
    **/
   public void run() {
      synchronized (bridge) {
         while (true) {
            try {
               Random r = new Random();
               int wtOfCurrentTruck = r.nextInt(MAXwEIGHTrANGE - MINwEIGHTrANGE + 1) + MINwEIGHTrANGE;

               // wait if total weight >= max weight OR trucks >= max trucks
               while (bridge.getWtOnBridge() + wtOfCurrentTruck >= MAXwEIGHT
                     || bridge.getTraffic().size() >= MAXtRUCKS) {
                  System.out.println("#####Truck " + wtOfCurrentTruck + " waiting to enter |"
                        + " Total weight = " + bridge.getWtOnBridge() + " | Trucks = "
                        + bridge.getTraffic().size());
                  sleep(1000);
                  bridge.notify();
                  bridge.wait();
               }

               bridge.getTraffic().addElement(wtOfCurrentTruck);
               bridge.setWtOnBridge(bridge.getWtOnBridge() + wtOfCurrentTruck);
               System.out.println("---->Truck " + wtOfCurrentTruck + " got on bridge"
                     + " | Total weight = " + bridge.getWtOnBridge() + " | Trucks = "
                     + bridge.getTraffic().size());
               bridge.notify();
               sleep(1000);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
      }
   }

}

