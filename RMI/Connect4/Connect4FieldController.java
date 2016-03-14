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
import java.rmi.*;
import java.rmi.registry.*;
import java.net.InetAddress;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;
import java.net.UnknownHostException;
import java.io.Serializable;

//@SuppressWarnings("serial")
public class Connect4FieldController extends UnicastRemoteObject 
   implements Runnable, Connect4FieldControllerInterface, Serializable{

   //   private static final long serialVersionUID = ;
   Connect4FieldModel fieldModel = new Connect4FieldModel();
   Connect4FieldView fieldView = new Connect4FieldView();
   PlayerView playerView = new PlayerView();
   int players = 0;
   String threadName = "";
   static boolean gameIsOver = false;
   final static Object o = new Object();
   private static Vector<PlayerClientInterface> clientList = new Vector<PlayerClientInterface>();
   static int noOfPlayers = 0;
   static char gamePiece[] = {'+','*'};
   static String currentPlayer = "";
   static String gameStatus = "START";

   public Connect4FieldController() throws RemoteException { }
/*
   public Connect4FieldController(Connect4FieldController fieldController, String playerName) 
      throws RemoteException{
      this.threadName = playerName;
      this.fieldModel = fieldController.fieldModel;
      this.fieldView = fieldController.fieldView;
      this.playerView = fieldController.playerView;
      this.players = fieldController.players;
      //      clientList = new Vector<PlayerClientInterface>();
   }
*/


   //method for client to call to add itself to its callback
   public void addClient(PlayerClientInterface aClient) throws RemoteException {
      clientList.addElement(aClient);
      noOfPlayers++;
      if(noOfPlayers == 2) {
         PlayerModel playerA = new PlayerModel(fieldModel, clientList.elementAt(0).getPlayerName(), gamePiece[0]);
         PlayerModel playerB = new PlayerModel(fieldModel, clientList.elementAt(1).getPlayerName(), gamePiece[1]);
         fieldModel.init(playerA, playerB);
         currentPlayer = clientList.elementAt(0).getPlayerName();
      }
   }
/*
   //callback
   private static void sendMessageToAllClients() throws RemoteException{
      System.out.println("num of clients" + clientList.size());
      for(int index = 0; index < clientList.size(); index++) {
         System.out.println("Send message to client"+index);
         PlayerClientInterface client = clientList.elementAt(index);
         client.displayMessage( "Server calling back to client " + index);       
      }
   }
*/
   /**
    * The main program, 2 player game, If only one player given 
    * then other is computer
    * Has references to model and view and registers the observers with 
    * Connect4Field observable.
    * 
    * @param   args     Ignored
    **/

   public static void main(String[] args) throws Exception{
      //stub
      Connect4FieldController fieldController = new Connect4FieldController();
      fieldController.fieldModel.addObserver(fieldController.fieldView);
      fieldController.fieldModel.addObserver(fieldController.playerView);

      String address = null;
      try {
         address = InetAddress.getLocalHost().getHostName();
      }catch(UnknownHostException e) {
         e.printStackTrace();
      }

      System.out.println("Server hostname: "+ address);
      Naming.rebind("serverObject",fieldController);

   }

   /**
    * This method attempts to drop the pieces of 2 players alternatively and checks a win or a draw
    * if the game is not draw or over it prints the winner
    * Handles both 2 player and 1 player game.
    **/ 
   public String playTheGame(String playerName, int column) throws java.rmi.RemoteException{
      System.out.println("Start");
      if(!gameIsOver) {
         // if draw, game over        
         int index = getIndex(playerName); 
         PlayerModelInterface playerObj = fieldModel.thePlayers[index];
         if ( fieldModel.isItaDraw() ) {
            fieldView.gameDraw();
            gameStatus = "DRAW";
            gameIsOver = true;
         } else {
            if(!fieldModel.checkIfPiecedCanBeDroppedIn(column)) {
               // cant drop, skip
            }else {
            //drop piece
            System.out.println(playerName + "'s move:");
            fieldModel.dropPieces(column, fieldModel.thePlayers[index].getGamePiece() );
            gameStatus = fieldView.toString(fieldModel.board);
            // if win, game over
            if ( fieldModel.didLastMoveWin() ) {
               fieldView.win(playerObj);
               gameStatus = "Player " + fieldModel.thePlayers[index].getName() + " WON";
               gameIsOver = true;
              }
            }
         }
         setNextPlayer();
      }

      return gameStatus;
   }

   public void setNextPlayer() throws java.rmi.RemoteException{
      int counter;
      for(int index = 0; index < noOfPlayers; index++) {
         counter = index;
         if(currentPlayer.equals(clientList.elementAt(counter).getPlayerName())) {
            currentPlayer = clientList.elementAt( (counter+1)%noOfPlayers ).getPlayerName();
            break;
         }
      }
   }

   public int getIndex(String playerName) throws java.rmi.RemoteException{
      int index = 0;
      if(currentPlayer.equals(clientList.elementAt(0).getPlayerName())) {
         index = 0;
      }else index = 1;
      return index;
   }

   public void run() {}

   public boolean getGameIsOver() throws java.rmi.RemoteException{
      return gameIsOver;
   }
   public int getPlayerCount() throws java.rmi.RemoteException{
      return noOfPlayers;
   }
   public String getCurrentPlayer() throws java.rmi.RemoteException{
      return currentPlayer;
   }
   public String getGameStatus() throws java.rmi.RemoteException{
      return gameStatus;
   }
}
