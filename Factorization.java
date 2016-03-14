/**
 * Given a number, this program finds its prime factors.
 * 
 * @version    $Id: Factorization.java, v 1 2015/06/09 $
 *
 * @author     Anusha Balusu, Pankhuri Roy
 */

public class Factorization {
   
   /**
    * This is the main program
    *
    * @param args    number to factorize
    */

	public static void main(String[] args) {
		
		//Taking command line input from user
		int input = Integer.parseInt(args[0]);
      factorize(input);
   }

   /**
    * Finds the prime factors of a number and prints them
    *
    * @param   number   number to factorize
    */

   public static void factorize(int number) {   
		
      int counter;
      boolean isStar = false;
		if( number < 2 ){
			System.out.println("No prime factors");
		}
		else{
			while(number % 2 == 0){
            if( isStar ) System.out.print(" * ");
				System.out.print("2");
            isStar = true;   
				number = number / 2;	
		   }
			
		   for(counter = 3; counter <= number; counter += 2){
			   while(number % counter == 0){
               if( isStar ) System.out.print(" * ");
				   System.out.print(counter);
               isStar = true;   
				   number = number / counter;				
			   }				
		   }System.out.println();
			
		}
		
	}
}
 
