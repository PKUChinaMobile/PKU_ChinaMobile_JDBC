/**
 * 
 */
package com.pku.cis.PKU_ChinaMobile.DataSource;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * @author yinning
 * 实例如何找函数的对应关系
 */
public class Examples {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws BiffException 
	 */
	public static void main(String[] args) throws BiffException, IOException {
		// TODO Auto-generated method stub
		
		// 读取Oracle的函数名用于测试
		Workbook workbook = Workbook.getWorkbook(new File("SQLFunction.xls"));
		Sheet s=workbook.getSheet(0);
		int rsRows = s.getRows();

		for (int i=1;i<rsRows;i++)
		{
			String arg;
			Cell[] cell=s.getRow(i); 
			
			try {
				arg=cell[2].getContents().trim();
				if(arg.length()>0)
				{
					/////  查找的测试在这里
					String out="";
					Function f=FunctionMapping.getFuncByOracleFuncName(arg, DataSourceType.Oracle);
					if (f!=null){
						out+=f.name+", "+f.dataSrsType+", "+f.inputNum+"; ";
					}
					else
					{
						out+=", "+", "+"; ";
					}
					f=FunctionMapping.getFuncByOracleFuncName(arg, DataSourceType.TD);
					if (f!=null){
						out+=f.name+", "+f.dataSrsType+", "+f.inputNum+"; ";
					}
					else
					{
						out+=", "+", "+"; ";
					}
					f=FunctionMapping.getFuncByOracleFuncName(arg, DataSourceType.Hive);
					if (f!=null){
						out+=f.name+", "+f.dataSrsType+", "+f.inputNum+"; ";
					}
					else
					{
						out+=", "+", "+"; ";
					}
					System.out.println(out);
				}
			}
			catch (Exception e) {}
		}
	}
}
