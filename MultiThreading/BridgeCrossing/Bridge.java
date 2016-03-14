/**
 * This class has traffic (vector with weight of trucks) and 
 * total weight of all trucks present on the bridge.
 *
 * @version    $Id: Bridge.java, v 1 2015/2/11 $
 *
 * @author     Anusha Balusu, Pankhuri Roy
 **/
import java.util.Vector;

class Bridge {
   private Vector<Integer> traffic;
   private int wtOnBridge;

   public Bridge() {
      traffic = new Vector<Integer>();
      setWtOnBridge(0);
   }

   public Vector<Integer> getTraffic() {
      return traffic;
   }

   public void setTraffic(Vector<Integer> traffic) {
      this.traffic = traffic;
   }

   public int getWtOnBridge() {
      return wtOnBridge;
   }

   public void setWtOnBridge(int wtOnBridge) {
      this.wtOnBridge = wtOnBridge;
   }

   /**
    * The main program
    *
    * @param      args     ignored
    **/
   public static void main(String args[]) {
      Bridge bridge = new Bridge();
      ProducerTruck pTruck = new ProducerTruck(bridge);
      ConsumerTruck cTruck = new ConsumerTruck(bridge);
      pTruck.start();
      cTruck.start();

   }
}
