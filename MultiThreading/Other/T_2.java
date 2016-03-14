public class T_2 extends Thread    {
   int id = 1;
   static String  theValue  = "1";
   T_2(int id)       {
      this.id = id;
   }
   public void run () {
      if ( id == 1 )
         theValue = "3";
      else
         theValue = "2";
   }      

   public static void main (String args []) {
      new T_2(1).start();;
      new T_2(2).start();;

      System.out.println("theValue = " + theValue );
      System.out.println("theValue = " + theValue );
   }       
} 

/* 
Possible outputs

1. theValue = 2   // both prints execute just after thread2 changes theValue to 2
   theValue = 2

2. theValue = 3   // both prints execute just after thread1 changes theValue to 2
   theValue = 3

3. theValue = 3   // first thread1 enters run and changes theValue to 3 and main prints.
   theValue = 2   // now thread2 changes theValue to 2 and main prints.

4. theValue = 2   // first thread2 enters run and changes theValue to 2 and main prints.
   theValue = 3   // now thread1 changes theValue to 3 and main prints.

5. theValue = 1   // main method runs and both prints execute before both threads start
   theValue = 1

6. theValue = 1   // first main prints
   theValue = 2   // next print executes after thread2 changes value to 2

7. theValue = 1   // first main prints
   theValue = 3   // next print executes after thread1 changes value to 3
*/

