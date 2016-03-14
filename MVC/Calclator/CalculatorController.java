/**
 * This is a controller class for Calculator
 *
 * @version    $Id: CalculatorController.java, v 1 2015/05/10 $
 *
 * @author     Anusha Balusu, Pankhuri Roy
 **/
import java.util.Observer;
import java.util.Observable;

public class CalculatorController {//implements Observer{
   String [] exp;
   CalculatorModel calcModel = new CalculatorModel();
   CalculatorView calcView = new CalculatorView();
   
   /**
    * The main program
    *
    * @param      args     Ignored
    **/

   public static void main(String [] args) {
      
      CalculatorController calcController = new CalculatorController();
      calcController.calcModel.addObserver(calcController.calcView);
      
      calcController.requestInput();
      calcController.processInput(calcController.exp);
   }
 
   /**
    * This method gets the user input from the view.
    **/  

   public void requestInput() {
      exp = calcView.getInput();
   }

   /**
    * Validates and computes the expression
    **/

   public void processInput(String[] exp) {
      if( calcModel.validateExpression(exp) ) {
         calcModel.calculateExpression();
      }
   }
   
}
