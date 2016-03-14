/** This class is the Server class which binds the methods and 
 * allows the clients to access the method.
 *  
 * @version    $Id: Server.java, v 1 2015/23/11 $
 *
 * @author     Anusha Balusu, Pankhuri Roy
 * 
 */
import java.rmi.*;
import java.net.InetAddress;

public class Server {
	public static void main(String[] args) throws Exception {
		HelloInterface obj = new HelloImplementation();

		//Assigning caption to the object
		Naming.rebind("test", obj);

		System.out.println("Server Started. Server hostname: " + InetAddress.getLocalHost().getHostName());
	}

}
