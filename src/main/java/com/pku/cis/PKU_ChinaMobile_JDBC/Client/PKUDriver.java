/**
 * PKUDriver - This class implements the java.sql.Driver Interface and act as the 
 * Driver Object of JDBC to the client. It communicate with the PKUDriverServer on
 * the server by RMI. 
 */
package com.pku.cis.PKU_ChinaMobile_JDBC.Client;

import java.util.*;
import java.util.logging.Logger;
import java.sql.*;
import java.rmi.*;

import com.pku.cis.PKU_ChinaMobile_JDBC.Interface.PKUConnectionInterface;
import com.pku.cis.PKU_ChinaMobile_JDBC.Interface.PKUDriverInterface;

/**
 * The Java SQL framework allows for multiple database drivers. Each driver
 * should supply a class that implements the Driver interface
 * 
 * <p>
 * The DriverManager will try to load as many drivers as it can find and then
 * for any given connection request, it will ask each driver in turn to try to
 * connect to the target URL.
 * 
 * <p>
 * It is strongly recommended that each Driver class should be small and
 * standalone so that the Driver class can be loaded and queried without
 * bringing in vast quantities of supporting code.
 * 
 * <p>
 * When a Driver class is loaded, it should create an instance of itself and
 * register it with the DriverManager. This means that a user can load and
 * register a driver by doing Class.forName("foo.bah.Driver")
 * 
 * @see DriverManager
 * @see Connection 
 */
public class PKUDriver implements java.sql.Driver
{
	//remote Driver Object
	static PKUDriverInterface remoteDriver = null; 
	
	//Driver URL prefix
	private static final String URL_PREFIX = "jdbc:PKUDriver:";
	
	//Driver version number
	private static final int MAJOR_VERSION = 1;
	private static final int MINOR_VERSION = 0;
	
	/**
	 * This part register the PKUDriver with DriverManager.
	 */
	static
	{
		try
		{
			
			PKUDriver driverInst = new PKUDriver();
			DriverManager.registerDriver(driverInst);
			//System.setSecurityManager(new RMISecurityManager());
		}
		catch(Exception e)
		{}
	}
	
	/**
	 * get the URL prefix of PKUDriver
	 */
	public static String getURLPrefix()
	{
		return URL_PREFIX;
	}
	
	/**
     * Try to make a database connection to the given URL.
     * The driver should return "null" if it realizes it is the wrong kind
     * of driver to connect to the given URL.  This will be common, as when
     * the JDBC driver manager is asked to connect to a given URL it passes
     * the URL to each loaded driver in turn.
     *
     * <P>The driver should raise a SQLException if it is the right 
     * driver to connect to the given URL, but has trouble connecting to
     * the database.
     *
     * <P>The java.util.Properties argument can be used to passed arbitrary
     * string tag/value pairs as connection arguments.
     * 
     * Here create a remote Connection Object- PKUConnectionServer, and return
     * the PKUConnection holding a remote Object.
     *
     * @param url The URL of the database to connect to
     *
     * @param loginProp a list of arbitrary string tag/value pairs as
     * connection arguments; normally at least a "user" and
     * "password" property should be included
     *
     * @return a Connection to the URL
     */
	public Connection connect(String url,Properties loginProp)
			throws SQLException
	{	
		PKUConnection localConInstance = null;
		String usr = loginProp.getProperty("user");
		String pwd = loginProp.getProperty("password");
		
		if(acceptsURL(url))
		{			
			try
			{	
				String serverName = url.substring(URL_PREFIX.length(),url.length());
								
				//connect to remote server only if not already connected
				if(remoteDriver == null)
				{	
					remoteDriver = (PKUDriverInterface)Naming.lookup("rmi://"+serverName+":1099"+"/RemoteDriver");
				}	
				
				//Get the remote Connection
				PKUConnectionInterface remoteConInstance = (PKUConnectionInterface)remoteDriver.getConnection(usr,pwd);
				
				//Create the local PKUConnection holding remote Connenction
				localConInstance = new PKUConnection(remoteConInstance);
				
			}
			catch(Exception ex)
			{
				throw(new SQLException(ex.getMessage()));
			}
		}
					
		return (Connection)localConInstance;
	}

	
	/**
	 * Typically, drivers will return true if they understand the subprotocol
	 * specified in the URL and false if they don't. This driver's protocols
	 * start with jdbc:PKUDriver:
	 * 
	 * @param url
	 *            the URL of the driver
	 * 
	 * @return true if this driver accepts the given URL
	 * 
	 * @exception SQLException
	 *                if a database-access error occurs
	 * 
	 * @see java.sql.Driver#acceptsURL
	 */
	public boolean acceptsURL(String url)
		   throws SQLException
	{						
		return url.startsWith(URL_PREFIX);
	}	

	/**
	 * Gets the drivers major version number
	 * 
	 * @return the drivers major version number
	 */
	public int getMajorVersion()
	{
		return MAJOR_VERSION;
	}
	
	/**
	 * Get the drivers minor version number
	 * 
	 * @return the drivers minor version number
	 */
	public int getMinorVersion()
	{
		return MINOR_VERSION;
	}

	/**
	   * Report whether the Driver is a genuine JDBC COMPLIANT (tm) driver.
	   * A driver may only report "true" here if it passes the JDBC compliance
	   * tests, otherwise it is required to return false.
	   *
	   * JDBC compliance requires full support for the JDBC API and full support
	   * for SQL 92 Entry Level.  It is expected that JDBC compliant drivers will
	   * be available for all the major commercial databases.
	   *
	   * This method is not intended to encourage the development of non-JDBC
	   * compliant drivers, but is a recognition of the fact that some vendors
	   * are interested in using the JDBC API and framework for lightweight
	   * databases that do not support full database functionality, or for
	   * special databases such as document information retrieval where a SQL
	   * implementation may not be feasible.
	   */
	public boolean jdbcCompliant()
	{
		return false;
	}
	
	/**
	   * <p>The getPropertyInfo method is intended to allow a generic GUI tool to 
	   * discover what properties it should prompt a human for in order to get 
	   * enough information to connect to a database.  Note that depending on
	   * the values the human has supplied so far, additional values may become
	   * necessary, so it may be necessary to iterate though several calls
	   * to getPropertyInfo.
	   *
	   * @param url The URL of the database to connect to.
	   * @param info A proposed list of tag/value pairs that will be sent on
	   *          connect open.
	   * @return An array of DriverPropertyInfo objects describing possible
	   *          properties.  This array may be an empty array if no properties
	   *          are required.
	   */
	public java.sql.DriverPropertyInfo[] getPropertyInfo(String url,Properties loginProps)
		throws SQLException
	{
		if(loginProps == null){
			loginProps = new Properties();
		}
		DriverPropertyInfo hostProp = new DriverPropertyInfo("HOST", loginProps.getProperty("HOST"));
		hostProp.required = true;
		hostProp.description = "Hostname of Database Server";
		
		DriverPropertyInfo userProp = new DriverPropertyInfo("user", loginProps.getProperty("user"));
		userProp.required = true;
		userProp.description = "Username to authenticate as";
		
		DriverPropertyInfo passwordProp = new DriverPropertyInfo("password", loginProps.getProperty("password"));
		passwordProp.required = true;
		passwordProp.description = "Password to use for authentication";
			
		DriverPropertyInfo[] dpi = new DriverPropertyInfo[3];
		return dpi;
	}
	
	//----------------JDBC 4.0 -----------------------------------------------
	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw(new SQLFeatureNotSupportedException("Not Supported"));
	}
}