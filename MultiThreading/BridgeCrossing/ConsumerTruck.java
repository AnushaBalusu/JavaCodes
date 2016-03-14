/**
 * Consumer truck takes care of trucks exiting from bridge
 * If bridge is empty, waits
 * If trucks present removes one, notifies producer and waits
 *
 * @version    $Id: ConsumerTruck.java, v 1 2015/2/11 $
 *
 * @author     Anusha Balusu, Pankhuri Roy
 **/ 
class ConsumerTruck extends Thread {

   private final static int MAXtRUCKS = 4;
   Bridge bridge;

   public ConsumerTruck(Bridge bridgeDetails) {
      this.bridge = bridgeDetails;
   }

   /**
    * Allows truck to get off the bridge
    **/
   public void run() {
      synchronized (bridge) {
         while (true) {
            try {
               // wait if no trucks on empty
               while (bridge.getTraffic().isEmpty() ) {
                  bridge.wait();
               }
               // add truck, update total weight
               Integer wtOftruckLeavingBridge = (Integer) bridge.getTraffic().firstElement();
               bridge.getTraffic().removeElement(wtOftruckLeavingBridge);
               bridge.setWtOnBridge(bridge.getWtOnBridge() - wtOftruckLeavingBridge);
               System.out.println("<----Truck " + wtOftruckLeavingBridge
                     + " leaving | Total weight = " + bridge.getWtOnBridge() + " | Trucks = "
                     + bridge.getTraffic().size());
               sleep(1000);
               bridge.notify();
               bridge.wait();

            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
      }
   }

}
