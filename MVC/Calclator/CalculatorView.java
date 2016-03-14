/**
 *
 * This is the view class for Calculator
 * It observes for any changes in the calculator model.
 *
 * @version    $Id: CalculatorView.java, v 1 2015/05/10 $
 *
 * @author     Anusha Balusu, Pankhuri Roy
 **/
import java.util.Scanner;
import java.util.Observer;
import java.util.Observable;

public class CalculatorView implements Observer {

   private String[] exp;
   private boolean isExpressionValid;

   /**
    * Gets the user input
    *
    * @return     expression
    **/

   public String[] getInput() {
      System.out.println("Enter the expression to be calculated: ");
      Scanner sc = new Scanner(System.in);
      exp = sc.nextLine().split(" ");
      sc.close();
      return exp;
   }

   /**
    * Prints the result
    *
    * @param      observable     the Calculator model
    * @param      arg            the Calculator model object
    **/

   public void update(Observable observable, Object arg) {
      isExpressionValid = ((CalculatorModel)observable).isExpressionValid;
      double result = ((CalculatorModel)observable).getResult();

      if(((CalculatorModel)observable).changed.equals("result"))
      {
         System.out.println("Result: " + result);
      }else {
         String error = ((CalculatorModel)observable).errorMessage;         
         if(error != null) {
            System.out.println(error);
         }
      }
   }
}
