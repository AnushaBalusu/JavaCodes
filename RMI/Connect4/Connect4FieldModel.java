/**
 * This is a model class which is being observed
 * Implements all methods needed for game play.
 *
 * @version    $Id: Connect4FieldModel.java, v 1 2015/05/10 $
 *
 * @author     Anusha Balusu, Pankhuri Roy
 **/ 

import java.util.Observable;
import java.util.Random;

public class Connect4FieldModel extends Observable implements Connect4FieldModelInterface{

   public static final int ROW_SIZE = 9;
   public static final int COL_SIZE = 25;
   private int playerIndex=0;
   private int computerMove=0;
   private int rowOfLastMove = 0;
   private int colOfLastMove = 0;
   private char pieceOfLastMove = 'o';
   public char[][] board = new char[ROW_SIZE][COL_SIZE];
   private String changed = "";
   private String errorMessage = "";
   PlayerModelInterface[] thePlayers = new PlayerModelInterface[2];

   /**
    * Gets the column index of the computer move.
    **/

   public int getComputerMove() {
      return this.computerMove;
   }
   
   /**
    * Gets the error message in case of any error.
    **/ 

   public String getErrorMessage() {
      return this.errorMessage;
   }

   /**
    * Gets the type of state change, Possible values: board, column, error
    * Board, Column for Connect4Field observer
    * Error for player observer 
    **/ 

   public String getChanged() {
      return this.changed;
   }

   /**
    * Constructor: Initializes the board
    **/  

   public Connect4FieldModel() {
      for(int row = 0; row < ROW_SIZE; row++) {
         for(int col = 0; col < COL_SIZE; col++) {
            board[row][col] = (col >= row && col < (COL_SIZE - row)) ? 'o' : ' ';
         }
      }
   }

   /**
    * Initializes and sets the players and notifies the Connect4Field View.
    *
    * @param   playerA     1st player object
    * @param   playerB     2nd player object
    **/ 

   public void init( PlayerModelInterface playerA, PlayerModelInterface playerB ) {
      thePlayers[0] = playerA;
      thePlayers[1] = playerB;
      changed = "board";
      setChanged();
      notifyObservers();
   }

   public void init( PlayerModelInterface playerA, PlayerModelInterface playerB,
        PlayerModelInterface playerC, PlayerModelInterface playerD ) {
      thePlayers[0] = playerA;
      thePlayers[1] = playerB;
      thePlayers[2] = playerC;
      thePlayers[3] = playerD;
      changed = "board";
      setChanged();
      notifyObservers();
   }

   /**
    * Checks if the piece can be dropped in a column
    *
    * @param      Column      Column Number
    **/

   public boolean checkIfPiecedCanBeDroppedIn(int column) {
      boolean canBeDropped = false;
      if( column < 1 || column > COL_SIZE ) {
         errorMessage = "Column value out of limits!";
         changed = "error";
         setChanged();
         notifyObservers();
      }else if(board[0][column-1] != 'o') {
         errorMessage = "Column is full";
         changed = "error";
     //    setChanged();
     //    notifyObservers();
      }else {
         canBeDropped = true;
      }
      return canBeDropped;
   }

   /**
    *
    * Drop the piece at the first available place from bottom in given column.
    *
    * @param      column      Column Number
    * @param      gamePiece   + or *
    *
    **/

   public void dropPieces(int column, char gamePiece) {
      int row = 0;
      if(checkIfPiecedCanBeDroppedIn(column)) {
         while( row < ROW_SIZE && board[row][column-1] == 'o' && board[row][column-1] != ' ' ) {
            row++;
         }
         board[row-1][column-1] = gamePiece;
         changed = "board";
         setChanged();
         notifyObservers();

         rowOfLastMove = row - 1;
         colOfLastMove = column - 1;
         pieceOfLastMove = gamePiece;
      }else {
         System.out.println("Cannot drop at column " + column + ". It's full.");
      }
   }

   /**
    * Updates the row, column, piece of last move as if the piece was dropped at column
    * The piece is not dropped.
    * 
    * @param      column      Column Number
    * @param      gamePiece   + or *
    **/

   public void dropPiecesAndSee(int column, char gamePiece) {
      int row = 0;
      if(checkIfPiecedCanBeDroppedIn(column)) {
         while( row < ROW_SIZE && board[row][column-1] == 'o' && board[row][column-1] != ' ' ) {
            row++;
         }
         //board[row-1][column-1] = gamePiece;
         rowOfLastMove = row - 1;
         colOfLastMove = column - 1;
         pieceOfLastMove = gamePiece;
      }else {
         System.out.println("Cannot drop at column " + column + ". It's full.");
      }
   }


   /**
    * Checks the 4 horizontal adjacent occurences of the same piece
    *
    * @return        true if 4 adjacent horizontal pieces occur
    **/

   public boolean check4Horizontal() {
      int index = colOfLastMove;
      int count = 1;
      // left side
      while( --index > 0 && board[rowOfLastMove][index] == pieceOfLastMove) {
         count++;
      }
      index = colOfLastMove;
      //right side
      while( ++index < COL_SIZE && board[rowOfLastMove][index] == pieceOfLastMove) {
         count++;
      }
      return ( (count == 4) ? true : false );
   }

   /**
    * Checks the 4 vertical adjacent occurences of the same piece
    *
    *  @return        true if 4 adjacent vertical pieces occur
    **/

   public boolean check4Vertical() {
      int index = rowOfLastMove;
      int count = 1;
      // up side
      while( --index > 0 && board[index][colOfLastMove] == pieceOfLastMove) {
         count++;
      }
      index = rowOfLastMove;
      //down side
      while( ++index < ROW_SIZE && board[index][colOfLastMove] == pieceOfLastMove) {
         count++;
      }
      return ( (count == 4) ? true : false );
   }

   /**
    * Checks the 4 (left) diagonally adjacent occurences of the same piece
    *
    * @return        true if 4 adjacent left diagonal pieces occur
    **/

   public boolean check4LeftDiagonal() {
      int count = 1;
      int rowIndex = rowOfLastMove;
      int colIndex = colOfLastMove;
      // up side: left diagonal
      while( --rowIndex > 0 && --colIndex > 0 && board[rowIndex][colIndex] == pieceOfLastMove ){
         count++;
      }
      rowIndex = rowOfLastMove;
      colIndex = colOfLastMove;
      // down side: left diagonal
      while( ++rowIndex < ROW_SIZE && ++colIndex < COL_SIZE && board[rowIndex][colIndex] == pieceOfLastMove ){
         count++;
      }
      return ( (count == 4) ? true : false );
   }

   /**
    * Checks the 4 (right) diagonally adjacent occurences of the same piece
    *
    * @return        true if 4 adjacent right diagonal pieces occur
    **/

   public boolean check4RightDiagonal() {
      int count = 1;
      int rowIndex = rowOfLastMove;
      int colIndex = colOfLastMove;
      // up side: right diagonal
      while( --rowIndex > 0 && ++colIndex < COL_SIZE && board[rowIndex][colIndex] == pieceOfLastMove ){
         count++;
      }
      rowIndex = rowOfLastMove;
      colIndex = colOfLastMove;
      // down side: left diagonal
      while( ++rowIndex < ROW_SIZE && --colIndex > 0 && board[rowIndex][colIndex] == pieceOfLastMove ){
         count++;
      }
      return ( (count == 4) ? true : false );
   }

   /**
    * Checks the particular number of horizontal adjacent occurences of the same piece
    *
    * @param      soMany   number of pieces to be checked
    * @return              true if so many adjacent horizontal pieces occur
    **/

   public boolean check4Horizontal(int soMany) {
      int index = colOfLastMove;
      int count = 1;
      //left side
      while( --index > 0 && board[rowOfLastMove][index] == pieceOfLastMove) {
         count++;
      }
      index = colOfLastMove;
      //right side
      while( ++index < COL_SIZE && board[rowOfLastMove][index] == pieceOfLastMove) {
         count++;
      }
      return ( (count == soMany) ? true : false );
   }

   /**
    * Checks the particular number of vertical adjacent occurences of the same piece
    *
    * @param      soMany   number of pieces to be checked
    * @return              true if so many adjacent vertical pieces occur
    **/

   public boolean check4Vertical(int soMany) {
      int index = rowOfLastMove;
      int count = 1;
      //up side
      while( --index > 0 && board[index][colOfLastMove] == pieceOfLastMove) {
         count++;
      }
      index = rowOfLastMove;
      //down side
      while( ++index < ROW_SIZE && board[index][colOfLastMove] == pieceOfLastMove) {
         count++;
      }
      return ( (count == soMany) ? true : false );
   }

   /**
    * Checks the particular number of left diagonal  adjacent occurences of the same piece
    *
    * @param      soMany   number of pieces to be checked
    * @return              true if so many adjacent left diagonal pieces occur
    **/

   public boolean check4LeftDiagonal(int soMany ) {
      int count = 1;
      int rowIndex = rowOfLastMove;
      int colIndex = colOfLastMove;
      //up side: left diagonal
      while( --rowIndex > 0 && --colIndex > 0 && board[rowIndex][colIndex] == pieceOfLastMove ){
         count++;
      }
      rowIndex = rowOfLastMove;
      colIndex = colOfLastMove;
      //down side: left diagonal
      while( ++rowIndex < ROW_SIZE && ++colIndex < COL_SIZE && board[rowIndex][colIndex] == pieceOfLastMove ){
         count++;
      }
      return ( (count == soMany ) ? true : false );
   }

   /**
    * Checks the particular number of right diagonal adjacent occurences of the same piece
    *
    * @param      soMany   number of pieces to be checked
    * @return              true if so many adjacent right diagonal pieces occur
    **/

   public boolean check4RightDiagonal(int soMany ) {
      int count = 1;
      int rowIndex = rowOfLastMove;
      int colIndex = colOfLastMove;
      //up side: right diagonal
      while( --rowIndex > 0 && ++colIndex < COL_SIZE && board[rowIndex][colIndex] == pieceOfLastMove ){
         count++;
      }
      rowIndex = rowOfLastMove;
      colIndex = colOfLastMove;
      //down side: left diagonal
      while( ++rowIndex < ROW_SIZE && --colIndex > 0 && board[rowIndex][colIndex] == pieceOfLastMove ){
         count++;
      }
      return ( (count == soMany ) ? true : false );
   }

   /**
    * Checks if four places are occupied horizontally, vertically, diagonally left
    * or diagonally right
    * Returns true if 4 places are occupied by the same piece.
    *
    **/

   public boolean didLastMoveWin() {
      boolean isWin = false;
      if( check4Horizontal() || check4Vertical() || check4LeftDiagonal() || 
            check4RightDiagonal() ) {
         isWin = true;
            }
      return isWin;
   }
   /**
    * Checks if four places are occupied horizontally, vertically, diagonally left
    * or diagonally right
    *
    * @param      count    number of adjact pieces to be checked
    *
    * @returns             true if count no. of places are occupied by the same piece.
    **/

   public boolean didLastMoveWin(int count) {
      boolean isWin = false;
      if( check4Horizontal(count) || check4Vertical(count) || check4LeftDiagonal(count) || 
            check4RightDiagonal(count) ) {
         isWin = true;
            }
      return isWin;
   }

   /**
    * Checks if the last move was a draw and returns false if the last
    * move was a draw. 
    *
    * @return        true if game is draw
    **/

   public boolean isItaDraw() {
      boolean isDraw = true;
      for(int index = 1; index <= COL_SIZE; index++ ) {
         if(checkIfPiecedCanBeDroppedIn(index)) {
            isDraw = false;
            break;
         }
      }
      if( isDraw && didLastMoveWin() ) {
         isDraw = false;
      }
      return isDraw;
   }

   /**
    * Decides the computer move
    * If by putting next piece, comp wins, plays that move
    * If by putting (other player's) piece, comp loses, then plays that move
    * Otherwise randomly plays the next move.
    *
    * @return     column number of next move
    **/

   public int computerMove() {
      Random rand = new Random();
      int rowTemp;
      int colTemp;
      char pieceTemp;
      int column = 0;
      boolean moved = false;
      // check comp win
      for(int index=1; index<= COL_SIZE;index++) {
         if( checkIfPiecedCanBeDroppedIn(index) ) {
            rowTemp = rowOfLastMove;
            colTemp = colOfLastMove;
            pieceTemp = pieceOfLastMove;
            dropPiecesAndSee(index, thePlayers[1].getGamePiece());
            if( didLastMoveWin() ){
               column = index;
               moved = true;
               break;
            }
            pieceOfLastMove = pieceTemp;
            colOfLastMove = colTemp;
            rowOfLastMove = rowTemp; 

         }
      }
      // check human win for 4 coins
      for(int index=1; index<= COL_SIZE;index++) {
         if( checkIfPiecedCanBeDroppedIn(index) ) {
            rowTemp = rowOfLastMove;
            colTemp = colOfLastMove;
            pieceTemp = pieceOfLastMove;
            dropPiecesAndSee(index, thePlayers[0].getGamePiece());
            if( didLastMoveWin() ){
               column = index;
               System.out.println("For 4 "+ column);
               moved = true;
               break;
            }
            pieceOfLastMove = pieceTemp;
            colOfLastMove = colTemp;
            rowOfLastMove = rowTemp; 
         }
      }

      if(!moved) {
         // check human win for 3 coins
         for(int index=1; index<= COL_SIZE;index++) {
            if( checkIfPiecedCanBeDroppedIn(index) ) {
               rowTemp = rowOfLastMove;
               colTemp = colOfLastMove;
               pieceTemp = pieceOfLastMove;
               dropPiecesAndSee(index, thePlayers[0].getGamePiece());
               if( didLastMoveWin(3) ) {
                  column = index;
                  moved = true;
                  break;

               }
               pieceOfLastMove = pieceTemp;
               colOfLastMove = colTemp;
               rowOfLastMove = rowTemp; 
            }
         }}
      if( !moved ) {
         column = rand.nextInt(COL_SIZE + 1);
         while( !checkIfPiecedCanBeDroppedIn(column) ) {
            column++;
         }
      }
      computerMove = column;
      changed = "column";
      setChanged();
      notifyObservers();
      return column;
   }

}
