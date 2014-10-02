/**
 * PKUResultSetInterface - This interface exposes the basic ResultSet methods remotely
 */
package com.pku.cis.PKU_ChinaMobile_JDBC.Interface;


import java.rmi.*;
import java.util.*;
import java.sql.*;

public interface PKUResultSetInterface extends Remote
{	   
	Object[] getNextRow() throws RemoteException,SQLException;
	void close() throws RemoteException,SQLException;
	Hashtable getColumnList() throws RemoteException,SQLException;
    PKUResultSetMetaDataInterface getMetaData() throws java.rmi.RemoteException, SQLException;

}