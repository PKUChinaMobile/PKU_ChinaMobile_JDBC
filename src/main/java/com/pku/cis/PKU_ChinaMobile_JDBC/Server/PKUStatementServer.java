/**
 * PKUStatementServer - This class implements PKUStatementInterface.It acts as a 
 * remote Statement object and wrapper over the ConnectionManager and an array of
 * Statement object.
 */
package com.pku.cis.PKU_ChinaMobile_JDBC.Server;

import com.pku.cis.PKU_ChinaMobile_JDBC.Interface.PKUResultSetInterface;
import com.pku.cis.PKU_ChinaMobile_JDBC.Interface.PKUStatementInterface;

import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
import java.sql.*;
import java.io.*;

public class PKUStatementServer extends UnicastRemoteObject
	implements PKUStatementInterface
{

	private Statement[] stmt; //Statement objects of each databases.
	private ConnectionManager conm;
	
	/**
	 * Constructor to create PKUConnectionServer with ConnectionManager.
	 * When this class first created, it dosen't contains real statement object.
	 * It is when we call the executeXXX methods that the real statement objects
	 * will be created.
	 */
	public PKUStatementServer(ConnectionManager _conm) throws RemoteException
	{
		//super();
		super(PKUServer.rmiJdbcListenerPort,
			     PKUServer.rmiClientSocketFactory, PKUServer.rmiServerSocketFactory);
		conm = _conm;
	}

	/**
	 * Execute a SQL statement on all databases and returns a single Remote 
	 * ResultSet object.
	 * Before we calling this method, we can't decide which databases client will use.
	 * By parsing the query, we can find databases needed by table name, time hint, 
	 * local hint and so on. 
	 * Now we can create real connections to databases and save it in ConnectionManger.
	 * Then we create statement for each connections and save it in PKUStatementServer.
	 * Finally we execute the query on each database, and save each java.sql.ResultSet in an
	 * array. And wrap it over PKUResultSetServer and return the PKUResultSetServer.
	 *  
	 * @param Query typically this is a static SQL SELECT statement
	 * @return a remote ResultSet object; never null 
	 */
	public PKUResultSetInterface executeQuery(String Query) throws RemoteException,SQLException
	{
		
		//Parse the query and get SQLParse object which save the parsing result.
		SQLParse sp = new SQLParse(Query);
		
		//Get real connections of client.
		Connection cons[] = conm.getConnections(sp);
		
		stmt= new Statement[conm.conNum];
		ResultSet[] rs = new ResultSet[conm.conNum];
		
		for(int i = 0; i < conm.conNum; i++)
		{
			//Create the Statement object of corresponding Connection object.
			stmt[i] = cons[i].createStatement();
			String dbType = cons[i].getMetaData().getDatabaseProductName();
			//Translate the SQL query into the corresponding type.
			String dialect = SQLTranslate.translate(sp, dbType);
			//Execute the query.
			System.out.println(dialect);
			rs[i] = stmt[i].executeQuery(dialect);
		}
		PKUResultSetServer remoteRs = new PKUResultSetServer(rs);
		return (PKUResultSetInterface)remoteRs;
	}

	/**
	 * Execute a SQL INSERT, UPDATE or DELETE statement. In addition,
	 * SQL statements that return nothing such as SQL DDL statements
	 * can be executed.
	 * 
	 * Before we calling this method, we can't decide which databases client will use.
	 * By parsing the query, we can find databases needed by table name, time hint, 
	 * local hint and so on. 
	 * Now we can create real connections to databases and save it in ConnectionManger.
	 * Then we create statement for each connections and save it in PKUStatementServer.
	 * Finally we execute the query on each database, and save each java.sql.ResultSet in an
	 * array. And wrap it over PKUResultSetServer and return the PKUResultSetServer.
	 *  
	 * @param Query a SQL INSERT, UPDATE or DELETE statement or a SQL
	 * statement that returns nothing
	 * @return either the row count for INSERT, UPDATE or DELETE or 0
	 * for SQL statements that return nothing
	 */
	public int executeUpdate(String Query) throws RemoteException,SQLException
	{
		int result = 0;
		
		//Parse the query and get SQLParse object which save the parsing result.
		SQLParse sp = new SQLParse(Query);
		
		//Get real connections of client.
		Connection cons[] = conm.getConnections(sp);
		stmt= new Statement[conm.conNum];
		for(int i = 0; i < conm.conNum; i++)
		{
			//Create the Statement object of corresponding Connection object.
			if(conm.dbs.get(i).equals("hive"))
				continue;
			stmt[i] = cons[i].createStatement();
			String dbType = cons[i].getMetaData().getDatabaseProductName();
			//Translate the SQL query into the corresponding type.
			String dialect = SQLTranslate.translate(sp, dbType);
			//Execute the query.
			result += stmt[i].executeUpdate(dialect);
		}
		return result;
	}

	public boolean execute(String Query) throws RemoteException,SQLException
	{
		boolean result = true;
		
		//Parse the query and get SQLParse object which save the parsing result.
		SQLParse sp = new SQLParse(Query);
		
		//Get real connections of client.
		Connection cons[] = conm.getConnections(sp);
		stmt= new Statement[conm.conNum];
		for(int i = 0; i < conm.conNum; i++)
		{
			//Create the Statement object of corresponding Connection object.
			stmt[i] = cons[i].createStatement();
			String dbType = cons[i].getMetaData().getDatabaseProductName();
			//Translate the SQL query into the corresponding type.
			String dialect = SQLTranslate.translate(sp, dbType);
			//Execute the query.
			if(!stmt[i].execute(dialect))
				result = false;
		}
		return result;
	}
	
	/**
	 * This method closes all the Statement objects.
	 */
	public void close() throws RemoteException,SQLException
	{
		for(int i = 0; i < conm.conNum; i++)
		{
			stmt[i].close();
		}
	}
}