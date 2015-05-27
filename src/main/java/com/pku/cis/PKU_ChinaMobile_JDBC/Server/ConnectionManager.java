/***
 * ConnectionManager: 网关部分
 * 接口：getConnections、close方法，四个变量
 */
package com.pku.cis.PKU_ChinaMobile_JDBC.Server;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectionManager {
	public ArrayList<Connection> cons; //保存真实Connection对象
	public ArrayList<String> dbs; //保存对应数据库名
	public User usr;//用户对象
	int conNum;//连接总数
	
	public static int dst;//测试用，在TestForSelect样例内设置 ：0-全数据库；1-Oracle；2-Mysql；3-Teradata；4-hive
	public static String dbName[] = {"","mysql","oracle","teradata","hive"};
	ConnectionManager(User _usr)
	{
		usr = _usr;
	}
	/**
	 * 关闭所有连接即可
	 * @throws SQLException
	 */
	public void close() throws SQLException
	{
		for(int i = 0; i < conNum; i++)
			cons.get(i).close();
		cons = null;
		dbs = null;
	}
	/**
	 * 要求根据SQLParse解析的结果，判断出需要的连接，建立这些连接保存在数组中返回
	 * @param sp SQLParse对象
	 * @return 连接数组
	 * @throws SQLException
	 */
	public Connection[] getConnections(SQLParse sp) throws SQLException
	{
		cons = new ArrayList<Connection>();
		dbs = new ArrayList<String>();
		
		
		conNum = 0;

		for(int i=0; i<usr.dbNum; ++i){
			try{
				
				if(dst != 0 && !dbName[dst].equals(usr.dbName[i]))//测试用，用于TestForSelect样例
					continue;
				
				Connection conn = (Connection)DriverManager.getConnection(usr.URLS[i], usr.username[i], usr.password[i]);
				cons.add(conn);
				dbs.add(usr.dbName[i]);
				conNum++;
			}catch(SQLException e){
				System.out.println("Connection for "+usr.dbName[i]+" failed.");
				throw e;
			}
		}

		return (Connection[])cons.toArray(new Connection[conNum]);
	}
	
	/**
	 * 要求返回用户所能够连接的所有连接；
	 * 正常情况下，用户等到执行SQL语句时会建立连接，此种情况下直接返回Connection对象数组；
	 * 有些情况下，需要在执行SQL语句之前（或者没有打算执行SQL语句）确定连接，
	 * 此时建立并返回用户拥有权限的所有连接对象
	 * @return 连接数组
	 * @throws SQLException
	 */
	public Connection[] getAllConnection() throws SQLException
	{
		if(cons != null && !cons.isEmpty())
			return (Connection[])cons.toArray(new Connection[conNum]);
		
		conNum = 0;
		
		for(int i=0; i<usr.dbNum; ++i){
			try{
				if(dst != 0 && !dbName[dst].equals(usr.dbName[i]))//测试用，用于TestForSelect样例
					continue;
				Connection conn = (Connection)DriverManager.getConnection(usr.URLS[i], usr.username[i], usr.password[i]);
				cons.add(conn);
				dbs.add(usr.dbName[i]);
				conNum++;
			}catch(SQLException e){
				System.out.println("Connection for "+usr.dbName[i]+" failed.");
				throw e;
			}
		}
		
//		Connection temp =  (Connection)DriverManager.getConnection(usr.URLS[0], "root", "06948859");
//		Connection temp2 =  (Connection)DriverManager.getConnection(usr.URLS[1], "SYSTEM", "oracle1ORACLE");
//		Connection temp3 =  (Connection)DriverManager.getConnection(usr.URLS[2], "hadoop", "");
//
//		cons.add(temp);
//		cons.add(temp2);
//		cons.add(temp3);
//		dbs.add(usr.dbName[0]);
//		dbs.add(usr.dbName[1]);
//		dbs.add(usr.dbName[2]);


		return (Connection[])cons.toArray(new Connection[conNum]);
	}
}
