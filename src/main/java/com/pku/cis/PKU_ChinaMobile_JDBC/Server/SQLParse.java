/**
 * SQLParse - SQL解析类，和JDBC接口部分只要求构造函数保存query语句，其余接口和SQLTranslate，ConnectionManager沟通
 */
package com.pku.cis.PKU_ChinaMobile_JDBC.Server;

public class SQLParse {
	String query;
	SQLParse(String _query)
	{
		query = _query;
	}
	public String getTableName()
	{
		return null;
	}
	public String getLocationHint()
	{
		return null;
	}
	public String getTimeHint()
	{
		return null;
	}
	public String getIPHint()
	{
		return null;
	}
}
