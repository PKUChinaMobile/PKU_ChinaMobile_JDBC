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

	
	public void setReadOnly(boolean readOnly) throws RemoteException,
			SQLException {
		Connection cons[] = conm.getAllConnection();
		for(int i = 0; i < cons.length; i++)
			cons[i].setReadOnly(readOnly);
		if(!readOnly) //如果不是要开启只读模式，直接返回即可
			return;
		//如果是要开启只读模式，进一步判读是否所有的数据库都支持这种设置；
		//如果不是，回滚这些操作；
		int i;
		for(i = 0; i < cons.length; i++)
			if(!cons[i].isReadOnly())
				break;
		if(i != cons.length)
		{
			//有连接不能开启只读模式；
			for(i = 0; i < cons.length; i++)
				cons[i].setReadOnly(false);//撤销之前的开启操作
		}
		
	}

	/**
	 * If one of the Connections is not read only, return false.
	 */
	public boolean isReadOnly() throws RemoteException, SQLException {
		Connection cons[] = conm.getAllConnection();
		for(int i = 0; i < cons.length; i++)
		{
			if(!cons[i].isReadOnly())
				return false;
		}
		return true;
	}

	public void setCatalog(String catalog) throws RemoteException, SQLException {
		Connection cons[] = conm.getAllConnection();
		for(int i = 0; i < cons.length; i++)
			cons[i].setCatalog(catalog);
	}

	public String getCatalog() throws RemoteException, SQLException {
		String result = "";
		Connection cons[] = conm.getAllConnection();
		for(int i = 0; i < cons.length; i++)
			result += conm.dbs.get(i) + ": " + cons[i].getCatalog() + "\n";
		return result;
	}	
	
}