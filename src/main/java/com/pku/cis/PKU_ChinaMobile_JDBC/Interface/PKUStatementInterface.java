/**
 * PKUStatementInterface - This interface exposes the basic Statement methods remotely
 */
package com.pku.cis.PKU_ChinaMobile_JDBC.Interface;

import java.rmi.*;
import java.sql.*;

public interface PKUStatementInterface extends Remote
{
	PKUResultSetInterface executeQuery(String Query) throws RemoteException,SQLException;
	int  executeUpdate(String Query) throws RemoteException,SQLException;
	boolean execute(String sqlQuery) throws RemoteException, SQLException;
	void close() throws RemoteException,SQLException;
}