/**
 * SQLTranslat - SQL翻译类
 * 接口：translate方法，其余和SQLParse对象商议。
 */
package com.pku.cis.PKU_ChinaMobile_JDBC.Server;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.pku.cis.PKU_ChinaMobile_JDBC.Server.Dialect.Hive.ToHiveOutputVisitor;
import com.pku.cis.PKU_ChinaMobile_JDBC.Server.Dialect.Oracle.ToOracleOutputVisitor;
import com.pku.cis.PKU_ChinaMobile_JDBC.Server.Dialect.Teradata.ToTeradataOutputVisitor;

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
            StringBuilder outOracle = new StringBuilder();
            ToOracleOutputVisitor visitorOracle = new ToOracleOutputVisitor(outOracle);
            stmt.accept(visitorOracle);
			out.append(outOracle);
			break;
		case Teradata:
            StringBuilder outTeradata = new StringBuilder();
            ToTeradataOutputVisitor visitorTeradata = new ToTeradataOutputVisitor(outTeradata);
            stmt.accept(visitorTeradata);
            out.append(outTeradata);
			break;
		case Hive:
            StringBuilder outHive = new StringBuilder();
            ToHiveOutputVisitor visitorHive = new ToHiveOutputVisitor(outHive);
            stmt.accept(visitorHive);
            out.append(outHive);
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
