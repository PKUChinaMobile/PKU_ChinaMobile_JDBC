package com.pku.cis.PKU_ChinaMobile_JDBC.DatabaseVisitor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

import com.sun.tools.hat.internal.parser.Reader;

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
		
		try{
			File oracleFile = new File("res/OracleFunctions.txt");
			BufferedReader reader = new BufferedReader(new FileReader(oracleFile));
			String line = "";
			while((line=reader.readLine()) != null){
				String[] s = line.split(",");
				OracleAliasToFunctionKey.put(s[1], s[0]);
				OracleFunctions.put(s[0], new MethodInformation(s[1], s[2].toUpperCase()));
			}
		}catch(IOException e){
			System.out.println("Error occurs while reading file OracleFunctions.txt");
		}
		
//		OracleAliasToFunctionKey.put("ABS", "ABS");
//		OracleAliasToFunctionKey.put("MOD", "MOD");
//		OracleAliasToFunctionKey.put("CONCAT", "CONCAT");

//
//		OracleFunctions.put("ABS", new MethodInformation("ABS", "Function".toUpperCase()));
//		OracleFunctions.put("MOD", new MethodInformation("MOD", "Function".toUpperCase()));
//		OracleFunctions.put("CONCAT", new MethodInformation("CONCAT", "Function".toUpperCase()));

		try{
			File mysqlFile = new File("res/MySqlFunctions.txt");
			BufferedReader reader = new BufferedReader(new FileReader(mysqlFile));
			String line = "";
			while((line=reader.readLine()) != null){
				String[] s = line.split(",");
				MySqlFunctions.put(s[0], new MethodInformation(s[1], s[2].toUpperCase()));
			}
		}catch(IOException e){
			System.out.println("Error occurs while reading file MySqlFunctions.txt");
		}
		
//		MySqlFunctions.put("ABS", new MethodInformation("ABS", "Function".toUpperCase()));
//		MySqlFunctions.put("MOD", new MethodInformation("MOD", "Function".toUpperCase()));
//		MySqlFunctions.put("CONCAT", new MethodInformation("CONCAT", "Function".toUpperCase()));

		try{
			System.out.println("TeradataFunctions");
			File teradataFile = new File("res/TeradataFunctions.txt");
			BufferedReader reader = new BufferedReader(new FileReader(teradataFile));
			String line = "";
			while((line=reader.readLine()) != null){
				String[] s = line.split(",");
				TeradataFunctions.put(s[0], new MethodInformation(s[1], s[2].toUpperCase()));
			}
		}catch(IOException e){
			System.out.println("Error occurs while reading file TeradataFunctions.txt");
		}
		
//		TeradataFunctions.put("ABS", new MethodInformation("ABS", "Function".toUpperCase()));
//		TeradataFunctions.put("MOD", new MethodInformation(" MOD ", "2-Operation".toUpperCase()));
//		TeradataFunctions.put("CONCAT", new MethodInformation(" || ", "2-Operation".toUpperCase()));

		try{
			File hiveFile = new File("res/HiveFunctions.txt");
			BufferedReader reader = new BufferedReader(new FileReader(hiveFile));
			String line = "";
			while((line=reader.readLine()) != null){
				String[] s = line.split(",");
				HiveFunctions.put(s[0], new MethodInformation(s[1], s[2].toUpperCase()));
			}
		}catch(IOException e){
			System.out.println("Error occurs while reading file HiveFunctions.txt");
		}
		
//		HiveFunctions.put("ABS", new MethodInformation("ABS", "Function".toUpperCase()));
//		HiveFunctions.put("MOD", new MethodInformation(" % ", "2-Operation".toUpperCase()));
//		HiveFunctions.put("CONCAT", new MethodInformation("CONCAT", "Function".toUpperCase()));
		
		System.out.println("size of OracleAliasToFunctionKey:"+OracleAliasToFunctionKey.size());
		System.out.println("size of OracleFunctions:"+OracleFunctions.size());
		System.out.println("size of MySqlFunctions:"+MySqlFunctions.size());
		System.out.println("size of TeradataFunctions:"+TeradataFunctions.size());
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
