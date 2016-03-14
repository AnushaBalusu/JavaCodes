/** This class (Remote class) implements the test method 
 * 
 * @version    $Id: HelloImplementation.java, v 1 2015/23/11 $
 *
 * @author     Anusha Balusu, Pankhuri Roy
 */

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HelloImplementation extends UnicastRemoteObject implements HelloInterface {

	protected HelloImplementation() throws RemoteException {
		super();
		
	}

	@Override
	public InetAddress test(String s) throws RemoteException, UnknownHostException {
		return InetAddress.getLocalHost();
	}
	

}
