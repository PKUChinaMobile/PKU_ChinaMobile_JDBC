package com.pku.cis.PKU_ChinaMobile_JDBC.DatabaseVisitor;

/*
 * 获得函数信息的类
 */
public class MethodInformation {
	private String Alias;	//函数表示
	private String Type;		//函数类型，是函数或单目操作符或双目操作符。。。
	
	MethodInformation(String _alias, String _type){
		this.Alias = _alias;
		this.Type = _type;
	}
	
	public String getName()
	{
		return this.Alias;
	}
	
	public String getType()
	{
		return this.Type;
	}
}
