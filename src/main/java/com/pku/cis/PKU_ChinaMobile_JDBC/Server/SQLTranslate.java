/**
 * SQLTranslat - SQL翻译类
 * 接口：translate方法，其余和SQLParse对象商议。
 */
package com.pku.cis.PKU_ChinaMobile_JDBC.Server;

import com.alibaba.druid.sql.ast.SQLStatement;

public class SQLTranslate {
	
	SQLTranslate()
	{
		
	}
	
	/**
	 * 根据query和SQLParse对象，返回需要的query语句
	 * @param query 统一SQL语句
	 * @param sp 语句对应的SQLParse对象
	 * @return query 特定SQL语句
	 */	
	public static String translate(SQLParse sp, String dbType)
	{
		System.out.println("Begin translate query.");
		
        StringBuilder out = new StringBuilder();
		SQLStatement stmt = sp.getAST();
		
		switch(getDatabaseType(dbType)){
		case Oracle:
			out.append(SQLDialect.toOracleString(stmt));
			break;
		case MySql:
			out.append(SQLDialect.toMySqlString(stmt));
			break;
		case Teradata:
			out.append(SQLDialect.toTeradataString(stmt));
			break;
		case Hive:
			out.append(SQLDialect.toHiveString(stmt));
			break;
		}
		
		System.out.println("Finish translate, execute "+out.toString());
		return out.toString();
	}
	
	/**
	 * 根据SQLParse提供的信息，解析出SQL语句属于哪种数据库
	 * @param sp 语句对应的SQLParse对象
	 * @return 数据库类型
	 */	
	private static DatabaseType getDatabaseType(String dbType)
	{
		if(dbType.toUpperCase().equals("MYSQL"))
			return DatabaseType.MySql;
		if(dbType.toUpperCase().equals("TERADATA"))
			return DatabaseType.Teradata;
		if(dbType.toUpperCase().equals("HIVE"))
			return DatabaseType.Hive;
		return DatabaseType.Oracle;
	}
}
