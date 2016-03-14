/**
 * This is the view class which observes the Connect4Field model
 * and prints the current board or displays appropriate message.
 *
 * @version    $Id: Connect4FieldView.java, v 1 2015/05/10 $
 *
 * @author     Anusha Balusu, Pankhuri Roy
 **/

import java.util.Observer;
import java.util.Observable;
import java.util.Scanner;

public class Connect4FieldView implements Observer, Connect4FieldViewInterface{

   public static final int ROW_SIZE = 9;
   public static final int COL_SIZE = 25;
   char[][] board = new char[ROW_SIZE][COL_SIZE];


   public void update(Observable o, Object obj) {
      Connect4FieldModel field = ((Connect4FieldModel) o);
      if(field.getChanged().equals("board")) {
         System.out.println(toString(field.board));
      }else if(field.getChanged().equals("error")) {
         System.out.println(field.getErrorMessage());
      }
   }
   
   /**
    * Gets the number of human players for the game.
    *
    * @return        number of human players
    **/ 

   public int getNumberOfPlayers() {
      int num = 0;
      do {
      System.out.println("Enter number of players: ");
      Scanner input = new Scanner(System.in);
      try {
         num = input.nextInt();
         if(num < 1 || num > 2) System.out.println("Enter 1 or 2 players"); 
      }catch(Exception e) {
         System.out.println("Input not an integer");
      }
      }while(num < 1 || num > 2);
      return num;
   }

   /**
    * Converts the board as a string
    * 
    * @return     board string
    **/

   public String toString(char[][] board){
      StringBuilder boardString = new StringBuilder();
      for(int row = 0; row < ROW_SIZE; row++) {
         for(int col = 0; col < COL_SIZE; col++) {
            boardString.append(board[row][col]);
            //      System.out.print(board[row][col]);
         }
         boardString.append("\n");
         //    System.out.println();
      }
      return boardString.toString();
   }

   /**
    * Method prints if game is draw
    **/ 

   public void gameDraw() {
      System.out.println("Draw");
   }

   /**
    * Method prints if game is win
    **/

   public void win(PlayerModelInterface p){
      System.out.println("The winner is: " + p.getName());
   }

}
