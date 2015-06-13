package com.pku.cis.PKU_ChinaMobile_JDBC.Server;

import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.SQLStatement;
//import com.pku.cis.PKU_ChinaMobile_JDBC.DatabaseVisitor.HiveOutputVisitor;
//import com.pku.cis.PKU_ChinaMobile_JDBC.DatabaseVisitor.MySqlOutputVisitor;
//import com.pku.cis.PKU_ChinaMobile_JDBC.DatabaseVisitor.OracleOutputVisitor;
//import com.pku.cis.PKU_ChinaMobile_JDBC.DatabaseVisitor.TeradataOutputVisitor;
import com.pku.cis.PKU_ChinaMobile_JDBC.Server.Dialect.Teradata.ToTeradataOutputVisitor;
import com.pku.cis.PKU_ChinaMobile_JDBC.Server.Dialect.Oracle.ToOracleOutputVisitor;
import com.pku.cis.PKU_ChinaMobile_JDBC.Server.Dialect.Hive.ToHiveOutputVisitor;

/*
 * 封装了通过访问AST树实现SQL翻译的工具类
 */
public class SQLDialect {
	public static String toOracleString(SQLObject sqlObject){
		StringBuilder out = new StringBuilder();
		//sqlObject.accept(new OracleOutputVisitor(out, false));
		((SQLStatement)sqlObject).accept(new ToOracleOutputVisitor(out));

		String sql = out.toString().replace("\n", " ").replace(";", "");
		System.out.println("Oracle[" + sql + "]");
		return sql;
	}
	
	public static String toTeradataString(SQLObject sqlObject){
		StringBuilder out = new StringBuilder();
		//sqlObject.accept(new TeradataOutputVisitor(out, false));
		((SQLStatement)sqlObject).accept(new ToTeradataOutputVisitor(out));

		String sql = out.toString().replace("\n", " ").replace(";", "");
		System.out.println("TD[" + sql + "]");
		return sql;
	}
	
	public static String toMySqlString(SQLObject sqlObject){
		StringBuilder out = new StringBuilder();
		//sqlObject.accept(new MySqlOutputVisitor(out, false));
		//((SQLStatement)sqlObject).accept(new ToMySqlOutputVisitor(out, false));
		
		String sql = out.toString().replace("\n", " ").replace(";", "");
		System.out.println("MySql[" + sql + "]");
		return sql;
	}
	
	public static String toHiveString(SQLObject sqlObject){
		StringBuilder out = new StringBuilder();
		//sqlObject.accept(new HiveOutputVisitor(out, false));
		((SQLStatement)sqlObject).accept(new ToHiveOutputVisitor(out));

		String sql = out.toString().replace("\n", " ").replace(";", "");

		System.out.println("Hive[" + sql + "]");
		return sql;
	}
}
