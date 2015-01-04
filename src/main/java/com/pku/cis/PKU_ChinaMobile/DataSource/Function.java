/**
 * 
 */
package com.pku.cis.PKU_ChinaMobile.DataSource;

/**
 * @author yinning
 * 函数
 */
public class Function {
	
	public String name;
	public String description;
	public DataSourceType dataSrsType;
	public FunctionType funcType=FunctionType.notspecified;
	public int inputNum=1;
	
	public Function(String name, String description,
			DataSourceType dataSrsType, FunctionType funcType, int inputNum) {
		super();
		this.name = name;
		this.description = description;
		this.dataSrsType = dataSrsType;
		this.funcType = funcType;
		this.inputNum = inputNum;
	}

	public Function() {
		// TODO Auto-generated constructor stub
	}

}
