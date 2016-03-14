/**
 * Implementation of class that implements Runnable
 * Each thread is given a separate x and y value and computes
 * the color and sets to the buffer.
 *
 * @version    $Id: MultiplierThread.java, v 1 2015/19/10 $
 *
 * @author     Anusha Balusu, Pankhuri Roy
 **/

import java.awt.image.BufferedImage;

public class PixelThread extends Mandelbrot implements Runnable{
   int x;
   int y;
   private BufferedImage theImage;

   public PixelThread(int x, int y, BufferedImage theImage) {
      this.x = x;
      this.y = y;
      this.theImage = theImage;
   }

   /*
    * Each thread is given a separate x and y value and computes
    * the color and sets to the buffer.
    */

   public void run() {
      double zx, zy, cX, cY;
      zx = zy = 0;
      cX = (x - getLength()) / getZoom();
      cY = (y - getLength()) / getZoom();
      int iter = 0;
      double tmp;
      while ( (zx * zx + zy * zy < 10 ) && ( iter < getMax() - 1 ) ) {
         tmp = zx * zx - zy * zy + cX;
         zy = 2.0 * zx * zy + cY;
         zx = tmp;
         iter++;
      }
      if ( iter > 0 ) {
         theImage.setRGB(x, y, getColor(iter));
      }
      else
         theImage.setRGB(x, y, iter | (iter << 8));
   }
}
