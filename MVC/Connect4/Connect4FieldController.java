/**
 * This is controller for Connect4Field
 * It interacts with the model and the view
 *
 * @version    $Id: Connect4FieldController.java, v 1 2015/05/10 $
 *
 * @author     Anusha Balusu, Pankhuri Roy
 **/

import java.util.Observer;
import java.util.Observable;

public class Connect4FieldController {

   Connect4FieldModel fieldModel = new Connect4FieldModel();
   Connect4FieldView fieldView = new Connect4FieldView();
   PlayerView playerView = new PlayerView();
   int players = 0;
   
   /**
    * The main program, 2 player game, If only one player given 
    * then other is computer
    * Has references to model and view and registers the observers with 
    * Connect4Field observable.
    * 
    * @param   args     Ignored
    **/

   public static void main(String[] args) {

      Connect4FieldController fieldController = new Connect4FieldController();
      fieldController.fieldModel.addObserver(fieldController.fieldView);
      fieldController.fieldModel.addObserver(fieldController.playerView);

      fieldController.players = fieldController.fieldView.getNumberOfPlayers();
      String player2 = (fieldController.players == 2) ? "B" : "Computer";
      PlayerModel playerA = new PlayerModel(fieldController.fieldModel, "A", '+');
      PlayerModel playerB = new PlayerModel(fieldController.fieldModel, player2, '*');
      
      // initialize
      fieldController.fieldModel.init(playerA, playerB);
      fieldController.playTheGame();
   }

   /**
    * This method attempts to drop the pieces of 2 players alternatively and checks a win or a draw
    * if the game is not draw or over it prints the winner
    * Handles both 2 player and 1 player game.
    **/ 

   public  void playTheGame() {
      int column;
      boolean gameIsOver = false;
      do {
         for ( int index = 0; index < 2; index ++ ) {
            // if draw, game over
            boolean samePlayerMove = false;
            if ( fieldModel.isItaDraw() ) {
               fieldView.gameDraw();
               gameIsOver = true;
               break;
            } else {
               do {                 
                  if(samePlayerMove) {
                     System.out.println("Column is full");
                  }
                  column = (index == 0 || players == 2) ? 
                     playerView.nextMove(fieldModel.thePlayers[index]) : 
                     fieldModel.computerMove() ;
                  samePlayerMove = true;
               }while(!fieldModel.checkIfPiecedCanBeDroppedIn(column));
               //drop piece
               fieldModel.dropPieces(column, fieldModel.thePlayers[index].getGamePiece() );
               // if win, game over
               if ( fieldModel.didLastMoveWin() ) {
                  fieldView.win(fieldModel.thePlayers[index]);
                  gameIsOver = true;
                  break;
               }
            }
         }

      }  while ( ! gameIsOver  );
   }
}
