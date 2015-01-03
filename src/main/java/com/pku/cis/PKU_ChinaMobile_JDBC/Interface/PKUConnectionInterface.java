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
	public void setReadOnly(boolean readOnly) throws RemoteException,SQLException;
	public boolean isReadOnly() throws RemoteException,SQLException;
	public void setCatalog(String catalog) throws RemoteException,SQLException;
	public String getCatalog() throws RemoteException,SQLException;
	public SQLWarning getWarnings() throws RemoteException,SQLException;
	public void clearWarnings() throws RemoteException,SQLException;
	public boolean isClosed()throws RemoteException, SQLException;
	public void setDst(int index) throws RemoteException, SQLException;
}