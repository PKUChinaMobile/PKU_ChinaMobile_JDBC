/**
 * PKUConnection - This class implements the java.sql.Connection Interface and act as the 
 * Connection Object of JDBC to the client. It communicate with the PKUConnectionServer on
 * the server by RMI. 
 */
package com.pku.cis.PKU_ChinaMobile_JDBC.Client;

import java.sql.*;
import java.util.*;
import java.util.concurrent.Executor;
import java.rmi.*;

import com.pku.cis.PKU_ChinaMobile_JDBC.Interface.PKUConnectionInterface;
import com.pku.cis.PKU_ChinaMobile_JDBC.Interface.PKUStatementInterface;

/**
 * A Connection represents a session with a specific database. Within the
 * context of a Connection, SQL statements are executed and results are
 * returned.
 * <P>
 * A Connection's database is able to provide information describing its tables,
 * its supported SQL grammar, its stored procedures, the capabilities of this
 * connection, etc. This information is obtained with the getMetaData method.
 * </p>
 * 
 * @see java.sql.Connection
 */
public class PKUConnection implements Connection
{
	//remote Connection object
	private PKUConnectionInterface  remoteConnection; 
	
	/**
	 * constructor for creating the PKUConnection instance with PKUConnectionInterface
	 */
	public PKUConnection(PKUConnectionInterface remCon)
	{
		remoteConnection = remCon;
	}


	/**
	 * SQL statements without parameters are normally executed using Statement
	 * objects. If the same SQL statement is executed many times, it is more
	 * efficient to use a PreparedStatement
	 * 
	 * Here create a remote Statement Object- PKUStatementServer, and return
     * the PKUStatement holding a remote Object.
     * 
	 * @return a new Statement object
	 * @throws SQLException
	 *             passed through from the constructor
	 */
	public Statement createStatement()
	     throws SQLException
	{
		try
		{
			PKUStatementInterface remStmt = (PKUStatementInterface)remoteConnection.createStatement();
			PKUStatement localStmtInstance = new PKUStatement(remStmt);
			return (Statement)localStmtInstance;
		
		}		
		catch(Exception ex)
		{
			throw(new SQLException("RemoteException:" + ex.getMessage()));
		}
	}

	/**
     * In some cases, it is desirable to immediately release a
     * Connection's database and JDBC resources instead of waiting for
     * them to be automatically released; the close method provides this
     * immediate release. 
     *
     * <P><B>Note:</B> A Connection is automatically closed when it is
     * garbage collected. Certain fatal errors also result in a closed
     * Connection.
     */
	public void close() throws SQLException
	{
		try
		{
		 	remoteConnection.closeConnection();
		}
		catch(RemoteException ex)
		{
			throw ((new SQLException("RemoteException:" + ex.getMessage())));
		}
	}

	/**
     * A driver may convert the JDBC sql grammar into its system's
     * native SQL grammar prior to sending it; nativeSQL returns the
     * native form of the statement that the driver would have sent.
     *
     * @param sql a SQL statement that may contain one or more '?'
     * parameter placeholders
     *
     * @return the native form of this statement
     */
	public String nativeSQL(String sql)
	 	throws SQLException
	{
		return sql; //本项目驱动发送给透明网关的sql语句在发送过程中没有改变
	}

	 public void setAutoCommit(boolean autoCommit)
	 	throws SQLException
	{
	 	throw(new SQLException("Not Supported"));
	}

	public boolean getAutoCommit()
	 	throws SQLException
	{
	 	throw(new SQLException("Not Supported"));
	}

	public void commit() throws SQLException
	{
	 	throw(new SQLException("Not Supported"));
	}

	public void rollback() throws SQLException
	{
	 	throw(new SQLException("Not Supported"));
	}

	public boolean isClosed()throws SQLException
	{
			throw(new SQLException("Not Supported"));
	}

	public DatabaseMetaData getMetaData()
         throws SQLException
	{
			throw(new SQLException("Not Supported"));
	}

	public void setReadOnly(boolean readOnly)
	      throws SQLException
	{
			throw(new SQLException("Not Supported"));
	}

	public boolean isReadOnly()
	       throws SQLException
	{
			throw(new SQLException("Not Supported"));
	}

	public void setCatalog(String catalog)
           throws SQLException
	{
			throw(new SQLException("Not Supported"));
	}

	public String getCatalog()
	     throws SQLException
	{
			throw(new SQLException("Not Supported"));
	}
	public void setTransactionIsolation(int level)
         throws SQLException
	{
			throw(new SQLException("Not Supported"));
	}

	public int getTransactionIsolation()
	      throws SQLException
	{
			throw(new SQLException("Not Supported"));
	}

	public SQLWarning getWarnings()
	      throws SQLException
	{
			 throw(new SQLException("Not Supported"));
	}

	public void clearWarnings()
	      throws SQLException
	{
			 throw(new SQLException("Not Supported"));
	}

	public PreparedStatement prepareStatement(String sql)
	              throws SQLException
	{
		   		 throw(new SQLException("Not Supported"));
	}

	public CallableStatement prepareCall(String sql)
		          throws SQLException
	{
		   	   	 throw(new SQLException("Not Supported"));
	}
	
	public PreparedStatement prepareStatement(String sql,int resultSetType,int resultSetConcurrency)
	         throws SQLException
	{
		throw(new SQLException("Not Supported"));
	}

	public CallableStatement prepareCall(String sql,int resultSetType,int resultSetConcurrency)
	        throws SQLException
	{
		throw(new SQLException("Not Supported"));
	}

	public Statement createStatement(int resultSetType,int resultSetConcurrency)
	         throws SQLException
    {
		throw(new SQLException("Not Supported"));
	}

	/////////////////////////////////////////////////////////////
	//These method would have to be implemented for JDK1.2 and higher
	
	public void setTypeMap(Map map) throws SQLException
	{
		throw(new SQLException("Not Supported"));
	}
	
	public Map getTypeMap() throws SQLException
	{
		throw(new SQLException("Not Supported"));
	}


	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void abort(Executor arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Array createArrayOf(String arg0, Object[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Blob createBlob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Clob createClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public NClob createNClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public SQLXML createSQLXML() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Statement createStatement(int arg0, int arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Struct createStruct(String arg0, Object[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Properties getClientInfo() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getClientInfo(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getNetworkTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public String getSchema() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean isValid(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public CallableStatement prepareCall(String arg0, int arg1, int arg2,
			int arg3) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public PreparedStatement prepareStatement(String arg0, int arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public PreparedStatement prepareStatement(String arg0, int[] arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public PreparedStatement prepareStatement(String arg0, String[] arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public PreparedStatement prepareStatement(String arg0, int arg1, int arg2,
			int arg3) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void releaseSavepoint(Savepoint arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void rollback(Savepoint arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setClientInfo(Properties arg0) throws SQLClientInfoException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setClientInfo(String arg0, String arg1)
			throws SQLClientInfoException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setHoldability(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setNetworkTimeout(Executor arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Savepoint setSavepoint() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Savepoint setSavepoint(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setSchema(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}







