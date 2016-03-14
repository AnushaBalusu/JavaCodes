public interface PlayerClientInterface extends java.rmi.Remote {
   public String getPlayerName() throws java.rmi.RemoteException;
   public int nextMove() throws java.rmi.RemoteException;
}
