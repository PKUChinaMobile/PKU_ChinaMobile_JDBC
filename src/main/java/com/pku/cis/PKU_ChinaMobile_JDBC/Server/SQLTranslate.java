/**
 * SQLTranslat - SQL翻译类
 * 接口：translate方法，其余和SQLParse对象商议。
 */
package com.pku.cis.PKU_ChinaMobile_JDBC.Server;

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
	public static String translate(String query, SQLParse sp)
	{
		return query;
	}
}
