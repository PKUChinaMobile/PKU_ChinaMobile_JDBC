package com.pku.cis.PKU_ChinaMobile_JDBC.DatabaseVisitor;

import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

/*
 * 通过表格文件获得不同数据库之间方法的映射关系
 */
public class MethodNameMapping {
	static private MethodNameMapping singletonMapping = null;
	
	private Map<String, String> OracleAliasToFunctionKey;
	private Map<String, MethodInformation> OracleFunctions;
	private Map<String, MethodInformation> MySqlFunctions;
	private Map<String, MethodInformation> TeradataFunctions;
	private Map<String, MethodInformation> HiveFunctions;
	
	static public MethodNameMapping getSingleton(){
		if(singletonMapping == null)
			singletonMapping = new MethodNameMapping();
		return singletonMapping;
	}
	
	MethodNameMapping(){
		System.out.println("Initializing MethodNameMapping...");
		
		OracleAliasToFunctionKey = new HashMap<String, String>();
		OracleFunctions = new HashMap<String, MethodInformation>();
		MySqlFunctions = new HashMap<String, MethodInformation>();
		TeradataFunctions = new HashMap<String, MethodInformation>();
		HiveFunctions = new HashMap<String, MethodInformation>();
		
		OracleAliasToFunctionKey.put("ABS", "ABS");
		OracleAliasToFunctionKey.put("MOD", "MOD");
		OracleAliasToFunctionKey.put("CONCAT", "CONCAT");
		System.out.println("size of OracleAliasToFunctionKey:"+OracleAliasToFunctionKey.size());

		OracleFunctions.put("ABS", new MethodInformation("ABS", "Function".toUpperCase()));
		OracleFunctions.put("MOD", new MethodInformation("MOD", "Function".toUpperCase()));
		OracleFunctions.put("CONCAT", new MethodInformation("CONCAT", "Function".toUpperCase()));
		System.out.println("size of OracleFunctions:"+OracleFunctions.size());
		
		MySqlFunctions.put("ABS", new MethodInformation("ABS", "Function".toUpperCase()));
		MySqlFunctions.put("MOD", new MethodInformation("MOD", "Function".toUpperCase()));
		MySqlFunctions.put("CONCAT", new MethodInformation("CONCAT", "Function".toUpperCase()));
		System.out.println("size of MySqlFunctions:"+MySqlFunctions.size());

		TeradataFunctions.put("ABS", new MethodInformation("ABS", "Function".toUpperCase()));
		TeradataFunctions.put("MOD", new MethodInformation(" MOD ", "2-Operation".toUpperCase()));
		TeradataFunctions.put("CONCAT", new MethodInformation(" || ", "2-Operation".toUpperCase()));
		System.out.println("size of TeradataFunctions:"+TeradataFunctions.size());

		HiveFunctions.put("ABS", new MethodInformation("ABS", "Function".toUpperCase()));
		HiveFunctions.put("MOD", new MethodInformation(" % ", "2-Operation".toUpperCase()));
		HiveFunctions.put("CONCAT", new MethodInformation("CONCAT", "Function".toUpperCase()));
		System.out.println("size of HiveFunctions:"+HiveFunctions.size());
	}
	
	public MethodInformation ToOracleFunction(String key){
		return OracleFunctions.get(key);
	}
	
	public MethodInformation ToTeradataFunction(String key){
		return TeradataFunctions.get(key);
	}

	public MethodInformation ToMySqlFunction(String key){
		return MySqlFunctions.get(key);
	}

	public MethodInformation ToHiveFunction(String key){
		return HiveFunctions.get(key);
	}
}
