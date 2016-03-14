import java.rmi.*;

public interface Connect4FieldControllerInterface extends Remote {
   public void addClient(PlayerClientInterface client) throws RemoteException;
   public boolean getGameIsOver() throws RemoteException;
   public int getPlayerCount() throws RemoteException;
   public String getGameStatus() throws RemoteException;
   public String getCurrentPlayer() throws RemoteException;
   public String playTheGame(String playerName, int column) throws RemoteException;
}
