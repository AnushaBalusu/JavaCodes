/** This class implements the test method locally and remotely
 * and prints the result.
 * 
 * @version    $Id: HelloC.java, v 1 2015/23/11 $
 *
 * @author     Anusha Balusu, Pankhuri Roy
 */
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HelloC implements HelloInterface {
	
	/**
	 * The localRemoteTest() method determines if test(strings) 
	 * is executed locally or remotely.
	 * 
	 * @param obj
	 */
	public static void localRemoteTest(HelloInterface obj) throws UnknownHostException	{
		try {
			InetAddress address = obj.test("Hello World");
			
			if (address == InetAddress.getLocalHost()){
				System.out.println("Local method");
			}
			else{
				System.out.println("Remote Method");
			}
							
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
/**
 *  The main program.
 *  
 * @param    args    command line arguments (ignored)
 */
	
public static void main(String args[] ) {

	try {
		localRemoteTest((HelloInterface)Naming.lookup("rmi://"+args[0]+"/test") );
		localRemoteTest(new HelloImplementation());
		System.exit(0);
	}catch (Exception e) {
		e.printStackTrace();
	}

    }

@Override
public InetAddress test(String s) throws RemoteException, UnknownHostException {
	return InetAddress.getLocalHost();
	}

}
