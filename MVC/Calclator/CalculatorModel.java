/**
 * This is a model class for Calculator
 *
 * @version    $Id: CalculatorModel.java, v 1 2015/05/10 $
 *
 * @author     Anusha Balusu, Pankhuri Roy
 **/

import java.util.Observable;
import java.util.Stack;

public class CalculatorModel extends Observable{

   public boolean isExpressionValid;
   private double result;
   public String changed;
   public String errorMessage;
   private String[] expression;
   private final String precedence = "+-%*/^";
   private Stack<String> numberStack = new Stack<String>();
   private Stack<String> operatorStack = new Stack<String>();

   public double getResult() {
      return result;
   }

   /**
    *  Removes quotes from * operator
    **/

   public String[] removeQuotes(String [] exp) {
      for(int index =0; index < exp.length; index++) {
         exp[index] = exp[index].replaceAll("\'", "");
      }
      return exp;
   }

   /**
    * Validates the input expression
    *
    * @return     true if the expression is valid
    **/

   public boolean validateExpression(String[] exp) {
      boolean isValid = true;
      boolean isOpenParan = false;
      int paranCounter = 0;

      exp = removeQuotes(exp);
      for(int index = 0; index < exp.length; index++) {
         if(isValidNumber(exp[index])) {           // number 
            if( (index != 0 && isCloseParanthesis(exp[index-1])) || 
                  (index < exp.length-1 && (isOpenParanthesis(exp[index+1]) 
                                            || isValidNumber(exp[index+1]))) ) {
               isValid = false;
               errorMessage = "Number after closing paranthesis or before opening paranthesis";
               break;
                                            }
         }else if(isValidOperator(exp[index])) {   // operator
            if( index == 0 || index == exp.length-1) {
               isValid = false;
               errorMessage = "Operator in the begining or end";
               break;
            }
            else if( isValidOperator(exp[index-1]) || 
                  isValidOperator(exp[index+1]) ) {
               isValid = false;
               errorMessage = "Two adjacent operators";
               break;
                  }         
         }else if(isCloseParanthesis(exp[index])) {// closing paranthesis
            if( paranCounter == 0 ) isOpenParan = false;
            if( !isOpenParan || index == 0 || 
                  ( index < exp.length-1 && (isOpenParanthesis(exp[index+1]) 
                                             || isValidNumber(exp[index+1])))) {
               isValid = false;
               errorMessage = "Closing paranthesis at begining or just before opening paranthesis or number";
               break;
                                             }
            paranCounter--;

         }else if(isOpenParanthesis(exp[index])) {// opening paranthesis
            paranCounter++;
            isOpenParan = true;
            if( index == exp.length-1 || ( index!= 0 && 
                     isValidNumber(exp[index-1]) ) )  {
               isValid = false;
               errorMessage = "Opening paranthesis at the end or after number ";
               break;
                     }

         }else {                                // other
            isValid = false;
            errorMessage = "Not a valid number or operator or paranthesis";
            break;
         }
         // unequal number of opening and closing paranthesis
         if(index == exp.length-1 && paranCounter != 0) { 
            isValid = false;
            errorMessage = "Unequal opening and closing paranthesis";
            break;
         }
      }

      expression = exp;
      isExpressionValid = isValid;
      changed = "validity";
      setChanged();
      notifyObservers();
      return isValid;
   }

   /**
    * Validates if input is number
    *
    * @return     true if valid number
    **/

   private boolean isValidNumber(String value) {
      try {
         Double.parseDouble(value.trim());
         return true;
      } catch (NumberFormatException e) {
         return false;
      }
   }

   private boolean isValidOperator(String value) {
      return (precedence.contains(value));
   }

   private boolean isOpenParanthesis(String value) {
      return value.equals("(");
   }

   private boolean isCloseParanthesis(String value) {
      return value.equals(")");
   }

   private boolean isCaret(String value) {
      return value.equals("^");
   }

   /**
    * Pushes the operators and numbers onto the respective stacks and 
    * performs calculations
    * Empties the stack at the end if any entries left
    **/

   public void calculateExpression() {
      int currentPrecedence = -1;
      int lastPrecedence = -1;
      for(int index = 0; index < expression.length; index++) {
         // push if number
         if( isValidNumber(expression[index]) ) {
            numberStack.push( expression[index] );
         }
         else if( operatorStack.isEmpty() || isOpenParanthesis(expression[index])) {
            operatorStack.push( expression[index] );
         }else {           // operator or closing paranthesis
            currentPrecedence = precedence.indexOf(expression[index]);
            lastPrecedence = precedence.indexOf(operatorStack.peek());
            while( !operatorStack.isEmpty() && 
                  currentPrecedence <= lastPrecedence && 
                  !isOpenParanthesis(operatorStack.peek())){

               if( isCaret(expression[index]) && isCaret(operatorStack.peek()) ) break; 
               if( !calculate() ) {
                  isExpressionValid = false;                  
                  break;
               }
               if(!operatorStack.isEmpty()) {
                  lastPrecedence = precedence.indexOf(operatorStack.peek());
               }
                  } // end while
            if( !isExpressionValid ) {
               changed = "validity";
               setChanged();
               notifyObservers();
               break;
            }
            if( !isCloseParanthesis(expression[index]) ) {
               operatorStack.push(expression[index]);
            }
         }
      } // end for
      if( isExpressionValid ) {
         emptyStacks();
      }
   }

   /**
    * Pops the numbers and operator and performs calculations and pushes
    * the result on the stack
    **/ 

   public boolean calculate() {
      double firstNumber;
      double secondNumber;
      double calculatedValue = 0;
      if( !isOpenParanthesis(operatorStack.peek()) ) {
         secondNumber = Double.parseDouble(numberStack.pop());
         firstNumber = Double.parseDouble(numberStack.pop());

         switch (operatorStack.peek().toString()) {
            case "+":
               calculatedValue = firstNumber + secondNumber;
               break;
            case "-":
               calculatedValue = firstNumber - secondNumber;
               break;
            case "*":
               calculatedValue = firstNumber * secondNumber;
               break;
            case "%":
               calculatedValue = firstNumber % secondNumber;
               break;
            case "/":
               if (secondNumber != 0) {
                  calculatedValue = firstNumber / secondNumber;
                  break;
               } else {
//                  System.out.println("Cannot divide by 0");
                  errorMessage = "Cannot divide by 0";                 
                  return false;
               }
            case "^":
               calculatedValue = Math.pow(firstNumber, secondNumber);
               break;

         }
         numberStack.push(calculatedValue + "");

      }
      operatorStack.pop();
      return true;
   }

   /**
    * Empties the operator stack and computes the result
    * Notifies the observer
    **/

   private void emptyStacks() {
      while (!operatorStack.isEmpty() && isExpressionValid) {
         isExpressionValid = calculate();
      }
      try {
      if (isExpressionValid) {
         
            result = Double.parseDouble(numberStack.pop());
         
         changed = "result";
         setChanged();
         notifyObservers();
      }else {
         changed = "validity";
         setChanged();
         notifyObservers();
      } 
      }catch(Exception e) {
         changed = "validity";
         errorMessage = "Empty expression";
         setChanged();
         notifyObservers();
      }

   }

}
