package com.pku.cis.PKU_ChinaMobile.DataSource;

import java.io.IOException;
import java.util.Hashtable;

import jxl.read.biff.BiffException;

/*
 * 找Function 对应关系的函数
 */
public class FunctionMapping {
	
	static public int size()
	{
		if(instance==null)
			return -1;
		return instance.functionTable.size();
	}
	
	/**
	 * key=function name
	 */
	Hashtable<String,FunctionEquivalent> functionTable=new Hashtable<String,FunctionEquivalent>();
	
	static FunctionMapping instance=null;
	
	static boolean GetFunctionMapping()
	{
		if (instance==null)
		{
			try {
				instance=FunctionMappingAdapter.getMappingFromFile();
			} catch (BiffException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	class FunctionEquivalent
	{
		public Function oracleFunction;
		public Function tdFunction;
		public Function hiveFunction;
		
		public FunctionEquivalent(Function oracleFunction, Function tdFunction, Function hiveFunction)
		{
			this.oracleFunction=oracleFunction;
			this.tdFunction=tdFunction;
			this.hiveFunction=hiveFunction;
		}
	}
	
	/**
	 * 如果不存在对应函数，对应参数部分留null
	 * @param oracleFunction
	 * @param tdFunction
	 * @param hiveFunction
	 */
	void insertFunctions(Function oracleFunction, Function tdFunction, Function hiveFunction)
	{
		if (oracleFunction==null)
		{
			throw new IllegalArgumentException("oracleFunction=null");
		}
		else
		{
			this.functionTable.put(oracleFunction.name, new FunctionEquivalent(oracleFunction, tdFunction, hiveFunction));
		}
	}

	/**
	 * 优化，记录上次的查询结果
	 */
	private FunctionEquivalent lastLookupRes=null;
	
	/**
	 * 对应关系文件是放在最外层的SQLFunction.xls
	 * @param oracleFuncName as Name
	 * @param dataSourceType 要找对应函数的数据源的类型
	 * @return null 没找到，或者有异常
	 */
	public static Function getFuncByOracleFuncName(String oracleFuncName, DataSourceType dataSourceType)
	{
		if(!GetFunctionMapping())
		{
			return null;
		}
		// 上次查询不存在或者不匹配，重新进行查询
		if (instance.lastLookupRes==null || instance.lastLookupRes.oracleFunction.name.compareTo(oracleFuncName)!=0)
		{
			instance.lastLookupRes=instance.functionTable.get(oracleFuncName.toUpperCase());
		}
		if (instance==null)
		{
			return null;
		}
		else
		{
			switch (dataSourceType)
			{
				case Hive:
					return instance.lastLookupRes.hiveFunction;
				case Oracle:
					return instance.lastLookupRes.oracleFunction;
				case TD:
					return instance.lastLookupRes.tdFunction;
				default:
					return null;
			}
		}
	}
}

