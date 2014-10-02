package com.pku.cis.PKU_ChinaMobile_JDBC.Interface;

import java.net.*;
import java.io.*;
import java.rmi.server.*;

/**
 * Default ServerSocket Factory
 */
public class PKUServerSocketFactory implements RMIServerSocketFactory, Serializable {
    public ServerSocket createServerSocket(int serverPort)
	throws IOException
	{ 
            return new ServerSocket(serverPort);
        }
}
