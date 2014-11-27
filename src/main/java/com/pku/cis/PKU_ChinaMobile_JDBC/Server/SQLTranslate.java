/**
 * SQLTranslat - SQL翻译类
 * 接口：translate方法，其余和SQLParse对象商议。
 */
package com.pku.cis.PKU_ChinaMobile_JDBC.Server;

import com.alibaba.druid.sql.ast.SQLStatement;

public class SQLTranslate {
	
	/*
	 * 初始化SQLTranslate类，包括载入数据库函数，语法映射关系文件等。
	 */
	static{
		
	}
	
	SQLTranslate()
	{
		
	}
	
	/**
	 * 根据query和SQLParse对象，返回需要的query语句
	 * @param query 统一SQL语句
	 * @param sp 语句对应的SQLParse对象
	 * @return query 特定SQL语句
	 */	
	public static String translate(String query, SQLParse sp)
	{
		System.out.println("Begin translate query "+query);
		
        StringBuilder out = new StringBuilder();
		SQLStatement stmt = sp.getAST();
		
		switch(getDatabaseType(sp)){
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
	private static DatabaseType getDatabaseType(SQLParse sp)
	{
		return DatabaseType.Oracle;
	}
}
