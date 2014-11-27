package com.pku.cis.PKU_ChinaMobile_JDBC.Server;

public enum DatabaseType {
	Oracle("Oracle"),
	Teradata("Teradata"),
	MySql("MySql"),
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
