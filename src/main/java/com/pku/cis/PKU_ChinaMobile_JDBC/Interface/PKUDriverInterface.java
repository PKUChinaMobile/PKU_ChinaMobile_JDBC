/**
 * PKUDriverInterface - This interface exposes the basic Driver methods remotely
 */
package com.pku.cis.PKU_ChinaMobile_JDBC.Interface;

import java.rmi.*;
import java.sql.*;

public interface PKUDriverInterface extends Remote
{
	PKUConnectionInterface getConnection(String usr, String pwd) throws RemoteException,SQLException;    
}