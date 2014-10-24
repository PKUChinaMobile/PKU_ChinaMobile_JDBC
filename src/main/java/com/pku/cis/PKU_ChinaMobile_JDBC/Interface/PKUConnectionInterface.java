/**
 * PKUConnectionInterface - This interface exposes the basic Connection methods remotely
 */
package com.pku.cis.PKU_ChinaMobile_JDBC.Interface;

import java.rmi.*;
import java.sql.*;

public interface PKUConnectionInterface extends Remote
{
	PKUStatementInterface createStatement() throws RemoteException,SQLException;
	void closeConnection() throws RemoteException,SQLException;
	//void setAutoCommit(boolean autoCommit) throws java.rmi.RemoteException, SQLException;
}