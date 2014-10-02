/**
 * PKUConnectionServer - This class implements PKUConnectionInterface.It acts as a 
 * remote Connection object and wrapper over the ConnectionManager.
 * It is a remarkable fact that this class doesn't contain the real connections
 * to database. The real connections to database will be obtained by calling
 * ConnectionManager's method - getConnection.
 */
package com.pku.cis.PKU_ChinaMobile_JDBC.Server;
import java.rmi.*;
import java.rmi.server.*;
import java.sql.*;

import com.pku.cis.PKU_ChinaMobile_JDBC.Interface.PKUConnectionInterface;
import com.pku.cis.PKU_ChinaMobile_JDBC.Interface.PKUStatementInterface;

public class  PKUConnectionServer extends UnicastRemoteObject
	implements PKUConnectionInterface
{

	private ConnectionManager conm;
	int conNum;
	
	/**
	 * Constructor to create PKUConnectionServer with ConnectionManager.
	 */
	public PKUConnectionServer(ConnectionManager _conm) throws RemoteException
	{
		//super();
		super(PKUServer.rmiJdbcListenerPort,
			     PKUServer.rmiClientSocketFactory, PKUServer.rmiServerSocketFactory);
		conm = _conm;
	}
	
	/**
	 * This method creates a remote statement and object then returns reference to 
	 * the interface of PKUStatementServer object holding a ConnectionManager.
	 * It is a remarkable fact that this class doesn't contain the real statement
	 * to database, because we haven't create real connections here.
	 * 
	 * @return a remote Statement Object
	 */
	public PKUStatementInterface createStatement() throws RemoteException,SQLException
	{
		PKUStatementServer StmtImplInstance =  new PKUStatementServer(conm);
		return  (PKUStatementInterface)StmtImplInstance;
	}
	
	/**
	 * This method closes all the Connections in ConnectionManager.
	 */
	public void closeConnection() throws RemoteException,SQLException
	{
		conm.close();
	}	
}