package com.pku.cis.PKU_ChinaMobile_JDBC.Server;

/*
 * 用于表示数据库类型的枚举类
 */

public enum DatabaseType {
	Oracle("Oracle"),
	Teradata("Teradata"),
	MySql("MySql"), 
	Hive("Hive"),
	;
	
	public final String name;
	DatabaseType(){
		this(null);
	}
	
	DatabaseType(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
}
