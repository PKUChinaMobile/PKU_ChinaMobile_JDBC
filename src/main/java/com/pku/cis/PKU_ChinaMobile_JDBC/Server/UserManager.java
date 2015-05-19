package com.pku.cis.PKU_ChinaMobile_JDBC.Server;
/**
 * UserManager: 一个管理所有用户的类
 * TODO: 能够从配置文件中读取一系列信息，格式可以自己定。但每个用户要有对应的密码，能够访问的数据库列表，
 * 数据库对应的URL。
 * 接口：Login方法
 */
public class UserManager {
	UserManager()
	{
	}
	/**
	 * 要求能够根据用户名和密码，构造一个User对象，成功返回该user对象，错误返回null
	 * @param usr 用户名
	 * @param pwd 密码
	 * @return 返回User对象
	 */
	User login(String usr, String pwd)
	{
		if(usr.equals("C##MYTEST") && pwd.equals("123456"))
			return new User(usr, pwd);
		return null;
	}

}
/**
 * 代表一个用户
 * 至少要包含以下的变量
 *
 */
class User
{
	String URLS[];//用户所拥有的数据库列表对应的URL列表
	String dbName[];//用户拥有的数据库名
	int dbNum;//用户拥有的数据库总数
	String username[];//用户所拥有的数据库对应用户名
	String password[];//用户所拥有的数据库对应密码
	String name;
	String pwd;
	
	User(String _name, String _pwd)
	{
		name = _name;
		pwd = _pwd;
		/*
		dbNum = 4;
		URLS = new String[dbNum];
		dbName = new String[dbNum];
		username = new String[dbNum];
		password = new String[dbNum];
		
		URLS[0] = "jdbc:mysql://162.105.71.165:3306/test";
		URLS[1] = "jdbc:oracle:thin:@162.105.71.165:1521:mytest";
		URLS[2] = "jdbc:teradata://162.105.71.127/vmtest";
		URLS[3] = "jdbc:hive2://162.105.71.243:10000/test";
		
		dbName[0] = "mysql";
		dbName[1] = "oracle";
		dbName[2] = "teradata";
		dbName[3] = "hive";
		
		username[0] = "root";
		username[1] = "SYSTEM";
		username[2] = "vmtest";
		username[3] = "hadoop";
		
		password[0] = "06948859";
		password[1] = "oracle1ORACLE";
		password[2] = "vmtest";
		password[3] = "";*/
		dbNum = 3;
		URLS = new String[dbNum];
		dbName = new String[dbNum];
		username = new String[dbNum];
		password = new String[dbNum];

		URLS[0] = "jdbc:mysql://162.105.71.128:3306/test";
		URLS[1] = "jdbc:oracle:thin:@162.105.71.128:1521:mytest";
		URLS[2] = "jdbc:hive2://162.105.71.247:10000/test";

		dbName[0] = "mysql";
		dbName[1] = "oracle";
		dbName[2] = "hive";


		username[0] = "root";
		username[1] = "SYSTEM";
		username[2] = "hadoop";


		password[0] = "06948859";
		password[1] = "oracle1ORACLE";
		password[2] = "";

	};
}