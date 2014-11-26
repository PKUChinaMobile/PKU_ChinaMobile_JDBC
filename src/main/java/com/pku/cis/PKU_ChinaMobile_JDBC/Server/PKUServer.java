/**
 * PKUServer - This class performs the role of main RMI server in Type-III
 * Driver. The client driver connects to the object of this class via RMI and then gets 
 * the Connection. 
 */
package com.pku.cis.PKU_ChinaMobile_JDBC.Server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;

import com.pku.cis.PKU_ChinaMobile_JDBC.Interface.PKUClientSocketFactory;
import com.pku.cis.PKU_ChinaMobile_JDBC.Interface.PKUServerSocketFactory;

public class PKUServer {
	//Set communication type defaul id normal sockets
	public static RMIClientSocketFactory rmiClientSocketFactory = new PKUClientSocketFactory();
	public static RMIServerSocketFactory rmiServerSocketFactory = new PKUServerSocketFactory();
	
	//Default value of the RMI port for remote objects
	private static final int rmiJdbcDefaultPort = 1099;
	
	//Default value of the listener port for remote objects
	public  static  int rmiJdbcListenerPort = 0;
	
	public static void main(String [] args)
	{
		//System.setSecurityManager(new RMISecurityManager());
		
		//Set host name.
		System.setProperty("java.rmi.server.hostname","localhost");
		
		//Load JDBC Drivers of all kind.
		try {
			System.out.println("Loading JDBC Driver...");
			Class.forName("oracle.jdbc.OracleDriver").newInstance();
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Class.forName("org.apache.hive.jdbc.HiveDriver").newInstance();
		} catch (Exception e) {
			System.err.println("Could not found JDBC Driver!");
			System.err.println("Error Message is " + e.getMessage());
		} 
		
		PKUServer theServer = new PKUServer();
		String rmiRef = "rmi://localhost:1099/RemoteDriver";
		PKUDriverServer theDriver;
		try {
			//Build the driver server object. 
			theDriver = new PKUDriverServer();
			
			//Register the server.
			Registry registry = LocateRegistry.createRegistry(1099);
		    registry.rebind("RemoteDriver", theDriver);
			Naming.rebind(rmiRef,theDriver);			
			
		    
		    System.out.println();
			System.out.println("Remote Driver server has started successfully...");
		    Thread tt = new Thread();
	        tt.suspend();
		} catch (Exception e) {
			e.printStackTrace();
		}
				

	}
}
