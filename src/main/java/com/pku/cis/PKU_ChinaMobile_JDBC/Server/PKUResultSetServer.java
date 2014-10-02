/**
 * PKUResultServer - This class implements PKUResultSetInterface.It acts as a 
 * remote ResultSet object and wrapper over  an array of ResultSet objects and
 * an array of ResultMetadata objects.
 */
package com.pku.cis.PKU_ChinaMobile_JDBC.Server;

import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
import java.sql.*;

import com.pku.cis.PKU_ChinaMobile_JDBC.Interface.PKUResultSetInterface;
import com.pku.cis.PKU_ChinaMobile_JDBC.Interface.PKUResultSetMetaDataInterface;

public class PKUResultSetServer extends UnicastRemoteObject
	implements PKUResultSetInterface
{
	
	
	private ResultSet[] rs ; //ResultSet objects of each databases;
	private ResultSetMetaData[] rsmds; //ResultSetMetaData objects of each databases;
	
	int colNum; //Column numbers of one query;
	int rsNum; //Number of ResultSet array; 
	Hashtable columnList; //Map of column index to column names in the ResultSet

	/**
	 * Constructor for creating PKUResultSetServer  with ResultSets of all databases.
	 * It extract the ResultSetMetadata for the ResultSet
	 */
	public PKUResultSetServer(ResultSet[] _rs) throws RemoteException,SQLException
	{
		//super();
		super(PKUServer.rmiJdbcListenerPort,
			     PKUServer.rmiClientSocketFactory, PKUServer.rmiServerSocketFactory);
		rs = _rs;
		rsNum = rs.length;
		rsmds = new ResultSetMetaData[rsNum];
		
		//extract the ResultSetMetadata for the ResultSet
		for(int i = 0; i < rsNum; i++)
			rsmds[i] = rs[i].getMetaData();
		
		//extract the Map of column index to column names
		colNum = rsmds[0].getColumnCount();
		columnList = new Hashtable(20,10);
		for(int i = 1; i <= colNum; i++)
		{
			String columnName = rsmds[0].getColumnName(i);
			Integer columnIndex = new Integer(i);
			columnList.put(columnName,columnIndex);
		}	
	}
	
	/**
	 * This method returns one ResultSet row in an array of Objects to client PKUResultSet.
	 * It returns null if all ResultSets does not have any more rows.
	 * 
	 * @return either the object array of one ResultSet row or null
	 */
	public Object[] getNextRow() throws RemoteException,SQLException
	{
		//Search the elements in ResultSet array one by one.
		for(int i = 0; i < rsNum; i++)
		{
			if(rs[i].next() == false)
				continue;
			Object []row = new Object[colNum];
			for(int j = 1; j <= colNum; j++)
			{
				row[j-1] = rs[i].getString(j);
			}
			
			return row;
			
		}
		//Return null if all data has already been iterated
		return null;
	}
	
	/**
	 * This method closes all the ResultSets
	 */
	public void close() throws RemoteException,SQLException
	{
		for(int i = 0; i < rsNum; i++)
		{
			rs[i].close();
		}
	}
	
	/**
	 * This method returns the map of Column Index to Column Name
	 * @return the map of Column Index to Column Name
	 */
	public Hashtable getColumnList() throws RemoteException,SQLException
	{
		return columnList;
	}
	
	/**
	 * This method returns the remote ResultSetMetaData object
	 * @return a remote ResultSetMetaData object
	 */
	public PKUResultSetMetaDataInterface getMetaData()
			  throws RemoteException, SQLException {
				//All ResultSet objects should have same structure, so we could only
				//use the first ResultSet object.
			    return new PKUResultSetMetaDataServer(rs[0].getMetaData());
			  }
}