/**
 * PKUDriverServer - This class implements PKUDriverInterface. The client driver
 * connects to the object of this class via RMI and then gets the Connection.
 * Since the client doesn't offer any information of which Database he want to
 * Connect before executing queries, the getConnection method will wrap a
 * ConnectionManager object over a remote Connection object(PKUConnectServer) 
 * instead of getting a real Connection to database. 
 */

package com.pku.cis.PKU_ChinaMobile_JDBC.Server;

import java.rmi.*;
import java.rmi.server.*;
import java.sql.*;

import com.pku.cis.PKU_ChinaMobile_JDBC.Interface.PKUConnectionInterface;
import com.pku.cis.PKU_ChinaMobile_JDBC.Interface.PKUDriverInterface;

public class PKUDriverServer extends UnicastRemoteObject
	implements PKUDriverInterface
{
	private static UserManager userManager = new UserManager();
	
	public PKUDriverServer() throws RemoteException
	{
		//super();
		super(PKUServer.rmiJdbcListenerPort,
			     PKUServer.rmiClientSocketFactory, PKUServer.rmiServerSocketFactory);
  	}
	/**
     * This method create a remote Connection Object- PKUConnectionServer, and return
     * the PKUConnection holding a remote Object.
     * Since the client doesn't offer any information of which Database he wants to
     * Connect before executing queries, this method will wrap a ConnectionManager
     * object over a remote Connection object(PKUConnectServer) instead of getting
     * a real Connection to database. 
     *
     * @param usr the user name of client
     *
     * @param pwd the password of the user
     *
     * @return a remote Connection Object
     */
	public PKUConnectionInterface getConnection(String usr, String pwd) throws RemoteException,SQLException
	{
		//Login the user in UserManager by user name and password.
		User user = userManager.login(usr, pwd);
		//Get the user's ConnectionManager.
		ConnectionManager conm = new ConnectionManager(user);
		//Create a remote Connection object which wrap over the user's ConnectionManager.
		PKUConnectionServer ConnectionInstance = new PKUConnectionServer(conm);
		return (PKUConnectionInterface)ConnectionInstance;
	}
}