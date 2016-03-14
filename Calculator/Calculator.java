/**
 * Calculates the infix expression ...
 *
 * @version $Id: Calculator.java, v 1 2015/26/08 $
 *                                v 2 2015/08/09 $
 *
 * @author Anusha Balusu, Pankhuri Roy
 *
 */

import java.util.Arrays;
import java.util.Stack;
import java.util.Vector;

public class Calculator {

	// Stack for storing numbers found in the expression
	Stack<String> numberStack = new Stack<String>();

	// Stack for storing operators found in the expression
	Stack<String> operatorStack = new Stack<String>();

	public static void main(String[] args) {

      Vector<String> inputExpression = null;
		// Input expression
		if (null != args && args.length > 0) {
			inputExpression = new Vector<String>(Arrays.asList(args));
		} else {
		   inputExpression = new Vector<String>();
			inputExpression.add("5");
			inputExpression.add("*");
			inputExpression.add("2");
		}

		System.out.println("Expression being evaluated :" 
            + inputExpression.toString().replaceAll(",", ""));
      Calculator calculatorObj = new Calculator();
		calculatorObj.calcOperations(inputExpression);

	}

	/**
	 * Perform calculation operation on user input
	 *
	 * @param   inputExpression  Contains the expression
	 *
	 */

	private void calcOperations(Vector<String> inputExpression) {

		int length = inputExpression.size();

		// String containing all operators considered valid for this program
		StringBuilder operatorString = new StringBuilder("+-%*/^");
		int iterator = 1;

		while (iterator <= 2) {
			System.out.println();
			System.out.println("Oerator precedence: " + operatorString);
			boolean validExpression = true;
			boolean validMathOperation = true;
			boolean lastOpearandNumber = false;
			boolean lastOpearandParanthesis = false;
         boolean lastOperandParanthesisLeft = false;
			
			int leftParanCounter=0;
			int rightParanCounter=0;
			// Loop to take each array value and push into Number or Operator
			// stack

			for (int counter = 0; counter < length; counter++) {
				String arrayValue = inputExpression.get(counter).trim();

				// removing quotes from * operator
				arrayValue = arrayValue.replaceAll("\'", "");

				// Checking if the Array value is a number
				boolean isNumber = isValidNumber(arrayValue);
				// If a number adding to the number stack

            if (counter == length-1 && rightParanCounter != leftParanCounter
                  && !arrayValue.equalsIgnoreCase(")") && !arrayValue.equalsIgnoreCase(")")){
						System.out.println("Opening and Closeing Parenthesis count must match");
						validExpression = false;
						break;
				} 
				if (isNumber) {

					// if a number checking that previous value should have
					// been
					// an operator
					if (lastOpearandNumber) {

						System.out.println("Invalid expression, number shoule be followed by an operator: " + arrayValue);
						validExpression = false;
						break;
					}

					numberStack.push(arrayValue);
					lastOpearandNumber = true;
					lastOpearandParanthesis = false;
               lastOperandParanthesisLeft = false;

				} else if (arrayValue.equalsIgnoreCase("(") 
                  || arrayValue.equalsIgnoreCase(")")) {
               if( arrayValue.equalsIgnoreCase("(") ) {
                  lastOperandParanthesisLeft = true;
                  ++leftParanCounter;
               }
               if( arrayValue.equalsIgnoreCase(")") ) {
                  ++rightParanCounter;
               }
					if (arrayValue.equalsIgnoreCase("(") && lastOpearandNumber 
                     && !lastOpearandParanthesis) {
						System.out.println("Number cannot be followed by Opening Parenthesis");
						validExpression = false;
						//++leftParanCounter;
						break;
					} else if (arrayValue.equalsIgnoreCase(")") && 
                     !lastOpearandNumber && !lastOpearandParanthesis) {
						System.out.println("Opearator cannot be followed by Closing Parenthesis");
						validExpression = false;
						//++rightParanCounter;
						break;
					}else if (counter == length-1 && rightParanCounter != leftParanCounter){
						System.out.println("Opening and Closeing Parenthesis count must match");
						validExpression = false;
						break;
				   }else if( arrayValue.equalsIgnoreCase(")") && lastOperandParanthesisLeft 
                     && lastOpearandParanthesis) {
						System.out.println("Opening Paranthesis cannot be followed by Closing Paranthesis");
						validExpression = false;
						break;
               } 
					lastOpearandNumber = false;
					lastOpearandParanthesis = true;
				} else if (isOperatorValid(length, operatorString, 
                     lastOpearandNumber, lastOpearandParanthesis, counter, 
                     arrayValue)) {
					validExpression = true;
					lastOpearandNumber = false;
					lastOpearandParanthesis = false;
               lastOperandParanthesisLeft = false;
				} else {
					// Exiting the function as operator not valid
					validExpression = false;
					break;
				}

				if (!lastOpearandNumber) {
					validMathOperation = operatorOperations(operatorString, arrayValue);
					if (!validMathOperation) {
						break;
					}
				}

			}

			// if expression was valid emptying the stacks for the final result
			if (validMathOperation && validExpression) {
				emptyStacks();
			} else if (!validExpression) {
				break;
			}
			operatorStack.removeAllElements();
			numberStack.removeAllElements();

			// Reversing precedense
			operatorString.reverse();
			++iterator;

		}
	}

	/**
	 * Checking the validity of the operator
	 *
	 * @param length
	 *            length of the expression being evaluated
	 * @param operatorString
	 *            operators with precedence as the index
	 * @param lastOpearandNumber
	 *            True if the previous value is a number
	 * @param counter
	 *            index of the operator
	 * @param arrayValue
	 *            operator being evaluated
	 *
	 * @return True if operator is valid
	 *
	 */
	private boolean isOperatorValid(int length, StringBuilder operatorString, 
         boolean lastOpearandNumber, boolean lastOpearandParanthesis, 
         int counter, String arrayValue) {
		if (!lastOpearandNumber && !lastOpearandParanthesis) {
			// if not a number checking if it is following a number
			// defined
			// and operator should not follow another operator
			System.out.println("Invalid Expression, operator should follow a number: " + arrayValue);
			return false;
		} else if (counter == 0 || counter == length - 1) {
			// if a operator checking that operator should not be in
			// first
			// or last position of the expression
			System.out.println("expression should start and end with a number or Paranthesis");
			return false;
		} else if (operatorString.indexOf(arrayValue) == -1) {
			// if valid operator adding to the operator stack after
			// checking
			System.out.println("Invalid operator in the expression");
			return false;
		} 
		return true;
	}

	/**
	 * Add operators found to operator stack after checking its precedence
	 *
	 * @param operatorString
	 *            operators with precedence as the index
	 * @param currentOperator
	 *            operator found at the current position
	 *
	 * @return True if operator is valid and is successfully pushed into the
	 *         stack
	 *
	 */

	private boolean operatorOperations(StringBuilder operatorString, String currentOperator) {
		String lastOperator="";
		// If operator stack empty then to add directly to stack
		if (operatorStack.isEmpty() || currentOperator.equals("(")) {
			operatorStack.push(currentOperator);
			return true;
		} else {
			// If operator stack not empty then checking before adding
			// Reading the operator at the top of the stack and finding its
			// position in Operator string
			int lastOpertorPrecedence = operatorString.indexOf(operatorStack.peek());

			// Reading the current operator from array and finding its position
			// in Operator sting
			int currentOpertorPrecedence = operatorString.indexOf(currentOperator);

			// If current operator position is less than stack operator then
			// doing calculations on stacks. Checking for all operators in the
			// operator stack
			while (((currentOpertorPrecedence <= lastOpertorPrecedence 
                     || currentOperator.equals(")")))
					&& !operatorStack.isEmpty() && !lastOperator.equals("(")) {
            if( currentOperator.equals("^") && operatorStack.peek().equals("^")) {
               break;
            }else{
				   if (!calculations()) {
					   return false;
				   } else if (!operatorStack.isEmpty()) {
					   lastOpertorPrecedence = operatorString.indexOf(operatorStack.peek());
					   lastOperator=operatorStack.peek();
				   }
            }
         }

			// Pushing the operator from array in the operator stack
			
			operatorStack.push(currentOperator);
		}

		return true;
	}

	// Method to empty the number and operator stacks
	private void emptyStacks() {
		boolean result = true;
		while (!operatorStack.isEmpty() && result) {
			result = calculations();
		}

		if (result) {
			System.out.println("Expression Result: " + numberStack.pop());
		} else {

		}

	}

	/**
	 * Checks if number is valid
	 *
	 * @param character
	 *            Number found at the current position
	 *
	 * @return True if number is valid
	 *
	 */

	private boolean isValidNumber(String character) {

		try {
			// Converting the array value to a double. If no exception
			// then returning true
			Double.parseDouble(character.trim());
			return true;
		} catch (NumberFormatException e) {
			// if not a valid double then returning false from the catch block
			return false;
		}
	}

	// Method to do calculations
	private boolean calculations() {
		double calculatedValue = 0;
		if (operatorStack.peek().equals(")") || operatorStack.peek().equals("(")) {

		} else {
			// Reading top number from stack as number on right of the expression
			double secondNumber = Double.parseDouble(numberStack.pop());
			// Reading second number from the top of the stack as number on the 
         // left of the expression
			double firstNumber = Double.parseDouble(numberStack.pop());
			// Switch Case to do the operation based on the opeartor returned
			// from the operator stack
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
					System.out.println("Cannot divide by 0");
					return false;
				}
			case "^":
				calculatedValue = Math.pow(firstNumber, secondNumber);
				break;

			}

			// Pushing the calculation result in the number stack
			numberStack.push(calculatedValue + "");
			
		}
		// Popping the operator as its operation already done
		operatorStack.pop();

		return true;
	}

}
