/**
 * This is class for Connect4Field Player (client) threads
 * 
 * @version    $Id: PlayerClient.java, v 1 2015/09/11 $
 *
 * @author     Anusha Balusu, Pankhuri Roy
 **/
import java.util.Observer;
import java.util.Observable;
import java.util.Scanner;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.io.Serializable;

public class PlayerClient extends Thread implements PlayerClientInterface, 
       PlayerViewInterface, Observer, Serializable{

   String threadName = "";
   String playerName = "";
   String serverHostName = "anushas-mbp.wireless.rit.edu";
   static Connect4FieldControllerInterface stub = null;

   public PlayerClient(String hostname, String playerName) throws Exception{
      this.playerName = playerName;
   }

   // Main method, starts player thread
   public static void main(String [] args) throws Exception{
      PlayerClient pc = new PlayerClient(args[0], args[1]); //server hostname, playerName
      String name = "rmi://" + args[0] + "/serverObject";
      stub = (Connect4FieldControllerInterface)Naming.lookup(name); // lookup server object
      stub.addClient(pc); //adding client to server's list
      pc.start();
   }

   
   public void run() {
      int column=0;
      try {
         boolean isBoardPrintedOnce = false;
         while(!stub.getGameIsOver()) {
            if(stub.getPlayerCount() == 2){
               if(stub.getCurrentPlayer().equals(playerName)) { // current player 
                  if(!stub.getGameIsOver()) {
                     System.out.println(stub.getGameStatus()); 
                     column = nextMove();
                     stub.playTheGame(playerName, column);
                  }else {
                     break;
                  }
               }
            }
                  
         }
         System.out.println(stub.getGameStatus());
      }catch(RemoteException re) {
         re.printStackTrace();
      }
   }

   public int nextMove(PlayerModelInterface p) {
      return -1;
   }

   public int nextMove() throws java.rmi.RemoteException{
      int column = -1;
      try {  
         printPlayerPrompt(playerName);
            Scanner playerInput = new Scanner(System.in);
            column = playerInput.nextInt();
      }catch(Exception e) {
         System.out.println("Input not an integer");
         column = nextMove();      
      }                          
      return column;              
   }

   public void update(Observable o, Object obj) {
      Connect4FieldModel field = ((Connect4FieldModel) o);
      if(field.getChanged().equals("column")) {
         printPlayerPrompt("Computer", field.getComputerMove()); 
      }
   }
   public void printPlayerPrompt(String name) {
      System.out.print(name + "'s move : ");
   }

   public void printPlayerPrompt(String name,int column) {
      System.out.println(name + "'s move : " + column);
   }

   public String getPlayerName() throws java.rmi.RemoteException{
      return playerName;
   }

}
