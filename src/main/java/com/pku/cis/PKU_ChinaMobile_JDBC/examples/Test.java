package com.pku.cis.PKU_ChinaMobile_JDBC.examples;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


import com.pku.cis.PKU_ChinaMobile_JDBC.Client.PKUConnection;
import com.pku.cis.PKU_ChinaMobile_JDBC.Client.PKUDriver;
import com.pku.cis.PKU_ChinaMobile_JDBC.Client.PKUResultSet;
import com.pku.cis.PKU_ChinaMobile_JDBC.Client.PKUResultSetMetaData;
import com.pku.cis.PKU_ChinaMobile_JDBC.Client.PKUStatement;

public class Test {
	public static void main(String args[])
	{
		   String userName ="C##MYTEST";
		   String userPasswd ="123456";
		   String tableName ="person";
		  
		   String urlPrefix = "jdbc:PKUDriver:";
		   String IP = "127.0.0.1"; //中间件IP
		   
		   
		   PKUDriver d = new PKUDriver();
		   
		   
		   String fullURL = urlPrefix + IP;
		   System.out.println("Attempt to connect " + fullURL);
		   PKUConnection con;
		   try {
			   //DriverManager会从已经注册的Driver列表中选择一个，调用其Connect方法
			   con = (PKUConnection)DriverManager.getConnection(fullURL, userName, userPasswd);
			   
			   String sql ="SELECT * FROM "+tableName;
			   System.out.println("Creating new Statement");
			   PKUStatement stmt = null;
			   stmt = (PKUStatement)con.createStatement();
			   System.out.println("Executing " + sql);
			   PKUResultSet rs = (PKUResultSet)stmt.executeQuery(sql);
			   PKUResultSetMetaData rmeta = (PKUResultSetMetaData) rs.getMetaData();
			   int numColumns = rmeta.getColumnCount();
			   for( int i = 1;i<= numColumns;i++ ) {
		           if( i < numColumns )
		               System.out.print( rmeta.getColumnName(i)+"->" );
		           else
		               System.out.println( rmeta.getColumnName(i) );
		       }
			   
		       while( rs.next() ){
		           for( int i = 1;i <= numColumns;i++ ){
		               if( i < numColumns ) 
		                   System.out.print(new String((rs.getString(i).trim()).getBytes("ISO-8859-1")) + "->");
		               else
		                   System.out.println(new String((rs.getString(i).trim()).getBytes("ISO-8859-1")));
		           }
		       } 
		       rs.close(); 
		       con.close();
		   }
		   catch( Exception ex ){
			      System.out.println( ex );
			      System.exit(0);
		}
	}
		
}
