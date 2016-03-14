/**
 * Multithreading
 *
 * @version    $Id: MultiplierThread.java, v 1 2015/19/10 $
 *
 * @author     Anusha Balusu, Pankhuri Roy
 **/

import java.awt.Color;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame {

   private final int MAX  = 5000;
   private final int LENGTH    = 800;
   private final double ZOOM     = 1000;
   private BufferedImage theImage;
   private int[] colors = new int[MAX];

   public Mandelbrot() {
      super("Mandelbrot Set");

      initColors();
      setBounds(100, 100, LENGTH, LENGTH);
      setResizable(false);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
   }

   public int getLength() {
      return LENGTH;
   }
   public double getZoom() {
      return ZOOM;
   }
   public int getMax() {
      return MAX;
   }
   public BufferedImage getImage() {
      return theImage;
   }
   public int getColor(int index) {
      return colors[index];
   }

   /*
    * Creates threads and maintains the number of threads
    * Joins the remaining threads that are alive in the end
    */

   public void createSet() {
      theImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
      int noOfThreads = Runtime.getRuntime().availableProcessors();
      int threadCounter = 0;
      Thread[] threadArray = new Thread[getHeight() * getWidth()];
      
      for (int y = 0; y < getHeight(); y++) {
         for (int x = 0; x < getWidth(); x++) {
            while(Thread.activeCount() > noOfThreads) {
            }
            threadArray[threadCounter] = new Thread(new PixelThread(x, y, theImage));
            threadArray[threadCounter].start();
            threadCounter++;
         }
      }
      for(Thread thread : threadArray) {
         try {
            if( thread != null && thread.isAlive() )
               thread.join();
         }catch(InterruptedException e) {
            e.printStackTrace();
         }
      }
      repaint();
   }

   public void initColors() {
      for (int index = 0; index < MAX; index++) {
         colors[index] = Color.HSBtoRGB(index/256f, 1, index/(index+8f));
      }
   }

   @Override
      public void paint(Graphics g) {
         g.drawImage(theImage, 0, 0, this);
      }

   public static void main(String[] args) {
      Mandelbrot aMandelbrot = new Mandelbrot();
      aMandelbrot.setVisible(true);
      aMandelbrot.createSet();
   }
}
