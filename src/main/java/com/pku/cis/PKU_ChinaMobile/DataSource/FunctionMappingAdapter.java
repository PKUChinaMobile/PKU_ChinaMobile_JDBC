/**
 * 
 */
package com.pku.cis.PKU_ChinaMobile.DataSource;

import java.io.File; 
import java.io.IOException;

import jxl.*; 
import jxl.read.biff.BiffException;

/**
 * @author yinning
 * 将配置文件读取到对象中
 */
class FunctionMappingAdapter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		try {
//			FunctionMapping fm=getMappingFromFile();
//			
//			for(FunctionEquivalent fe:fm.functionTable.values())
//			{
//				String out="";
//				if (fe.oracleFunction!=null && fe.oracleFunction.name!=null)
//				{
//					out+=fe.oracleFunction.name;
//				}
//				out+=", ";
//				if (fe.tdFunction!=null && fe.tdFunction.name!=null)
//				{
//					out+=fe.tdFunction.name;
//				}
//				out+=", ";
//				if (fe.hiveFunction!=null && fe.hiveFunction.name!=null)
//				{
//					out+=fe.hiveFunction.name;
//				}
//				System.out.println(out);
//			}
//			
//		} catch (BiffException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	static FunctionMapping getMappingFromFile() throws BiffException, IOException
	{
		FunctionMapping.instance=new FunctionMapping();
			
			Workbook workbook = Workbook.getWorkbook(new File("SQLFunction.xls"));
			Sheet s=workbook.getSheet(0);
			int rsRows = s.getRows();

			for (int i=1;i<rsRows;i++)
			{
				String arg;
				Function oracle=null,td=null,hive=null;
				Cell[] cell=s.getRow(i); 
				
				try {
				arg=cell[2].getContents().trim();
				if(arg.length()>0)
				{
					oracle=new Function();
					oracle.dataSrsType=DataSourceType.Oracle;
					oracle.name=arg.toUpperCase();
					arg=cell[5].getContents().trim();
					oracle.funcType=FunctionType.valueOf(arg);
					arg=cell[6].getContents().trim();
					oracle.inputNum=Integer.valueOf(arg);
				}
				}
				catch (Exception e) {}
				
				try {
				arg=cell[9].getContents().trim();
				if(arg.length()>0)
				{
					td=new Function();
					td.dataSrsType=DataSourceType.TD;
					td.name=arg.toUpperCase();
					arg=cell[10].getContents().trim();
					td.funcType=FunctionType.valueOf(arg);
					arg=cell[11].getContents().trim();
					td.inputNum=Integer.valueOf(arg);
				}
				}
				catch (Exception e) {}
				
				try {
				arg=cell[14].getContents().trim();
				if(arg.length()>0)
				{
					hive=new Function();
					hive.dataSrsType=DataSourceType.Hive;
					hive.name=arg.toUpperCase();
					arg=cell[15].getContents().trim();
					hive.funcType=FunctionType.valueOf(arg);
					arg=cell[16].getContents().trim();
					hive.inputNum=Integer.valueOf(arg);
				}
				}
				catch (Exception e) {}
				
				FunctionMapping.instance.insertFunctions(oracle, td, hive);
				
//				System.out.println(FunctionMapping.size());
			}
					
		return FunctionMapping.instance;
	}

}
