public class T_1 extends Thread  {
   static int x = 1;
   String info = "";

   public T_1 (String info) {
      this.info = info;
      x++;                                         // Line 1
   }

   public void run () {
      x++;                                         // Line 2
      String output = x + " " + info + ": " + x;   // Line 3
      System.out.println(output);
   }

   public static void main (String args []) {
      new T_1("a").start();
      new T_1("b").start();
   }
}
/*

Possible outputs

3 a/b: 3    5 b/a: 5    // One thread enters run and prints, then next one enters run
3 a/b: 4    5 b/a: 5    // One thread enters run and stops at line 3, other did not start. the first one prints, then 2nd prints
3 a/b: 5    5 b/a: 5    // One enters, assigns 1st x to 3 at Line3 and halts, 2nd thread enters and makes x 5 then 1st prints followed by 2nd
4 a/b: 4    5 b/a: 5    // One enters and makes x=4 stops at line3 and prints. 2nd prints
4 a/b: 5    5 b/a: 5    // One enters run x=4 , assigns 1st x to 3 at Line3 and halts. 2nd runs x=5, 1st thread prints, 2nd prints
5 a/b: 5    5 b/a: 5    // Both run and stop at line 3, x = 5,  then any thread prints.

and reverse of all the above.

Eg: Reverse of 4 a/b: 4    5 b/a: 5 is
5 a/b: 5    4 b/a: 4    // One thread enters run and x=4, output =4 a/b: 4 and stops, 2nd enters and its output= 5 b/a: 5 and prints, 1st prints its output
*/
