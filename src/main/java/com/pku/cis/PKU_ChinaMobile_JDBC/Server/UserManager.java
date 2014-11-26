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
	String name;
	String pwd;
	User(String _name, String _pwd)
	{
		name = _name;
		pwd = _pwd;
		dbNum = 3;
		URLS = new String[3];
		dbName = new String[3];
		URLS[0] = "jdbc:mysql://localhost:3306/test";
		URLS[1] = "jdbc:oracle:thin:@localhost:1521:mytest";
		URLS[2] = "jdbc:hive2://162.105.71.61:10000/test";
		dbName[0] = "mysql/test";
		dbName[1] = "oracle/mytest";
		dbName[2] = "hive/test";
	};
}