package com.pku.cis.PKU_ChinaMobile_JDBC.Interface;

import java.net.*;
import java.io.*;
import java.rmi.server.*;

/**
 * Default Socket Factory
 */
public class PKUClientSocketFactory implements RMIClientSocketFactory, Serializable {
    public Socket createSocket(String host, int port)
	throws IOException
	{
            return new Socket(host, port);
	}
}
