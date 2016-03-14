/**
 * Interface HelloInterface
 *
 * @version    $Id: HelloInterface.java, v 1 2015/23/11 $
 *
 * @author     Anusha Balusu, Pankhuri Roy
 **/

import java.rmi.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public interface HelloInterface extends Remote{

	public InetAddress test(String s) throws RemoteException, UnknownHostException;
	

}
