package com.pku.cis.PKU_ChinaMobile_JDBC.Server;

import com.alibaba.druid.sql.ast.SQLObject;
import com.pku.cis.PKU_ChinaMobile_JDBC.DatabaseVisitor.MySqlOutputVisitor;
import com.pku.cis.PKU_ChinaMobile_JDBC.DatabaseVisitor.OracleOutputVisitor;
import com.pku.cis.PKU_ChinaMobile_JDBC.DatabaseVisitor.TeradataOutputVisitor;


public class SQLDialect {
	public static String toOracleString(SQLObject sqlObject){
		StringBuilder out = new StringBuilder();
		sqlObject.accept(new OracleOutputVisitor(out, false));
		
		String sql = out.toString();
		return sql;
	}
	
	public static String toTeradataString(SQLObject sqlObject){
		StringBuilder out = new StringBuilder();
		sqlObject.accept(new TeradataOutputVisitor(out, false));
		
		String sql = out.toString();
		return sql;
	}
	
	public static String toMySqlString(SQLObject sqlObject){
		StringBuilder out = new StringBuilder();
		sqlObject.accept(new MySqlOutputVisitor(out, false));
		
		String sql = out.toString();
		return sql;
	}
}
