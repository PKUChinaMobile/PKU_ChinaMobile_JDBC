package com.pku.cis.PKU_ChinaMobile_JDBC.examples;

import java.awt.Color;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.DriverManager;
import java.sql.SQLException;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import com.pku.cis.PKU_ChinaMobile_JDBC.Client.PKUConnection;
import com.pku.cis.PKU_ChinaMobile_JDBC.Client.PKUDriver;
import com.pku.cis.PKU_ChinaMobile_JDBC.Client.PKUResultSet;
import com.pku.cis.PKU_ChinaMobile_JDBC.Client.PKUResultSetMetaData;
import com.pku.cis.PKU_ChinaMobile_JDBC.Client.PKUStatement;

public class Test  {
	static JFrame f;
	static JComboBox jcb;
	static JButton btn;
	static TextArea t;
	
	static String sqls[] = {"SELECT * FROM PERSON",
			   "DROP TABLE PERSON2",
			   "CREATE TABLE PERSON2 (ID int, AGE int)",
			   "INSERT INTO PERSON2 VALUES(1, 18)",
			   "DELETE FROM PERSON2 WHERE ID = 1",
			   "UPDATE PERSON2 SET AGE = 19 WHERE ID = 1",
			   "SELECT * FROM PERSON2"
			   };
	
	static String userName ="C##MYTEST";
	static String userPasswd ="123456";
	static String tableName ="person";
	  
	static String urlPrefix = "jdbc:PKUDriver:";
	static String IP = "127.0.0.1"; 
	static PKUDriver d = new PKUDriver();
	public static void main(String args[])
	{
		
		   f = new JFrame("Test Window");
		   f.setSize(800, 600);
		   f.setLocation(500, 100);
		   f.setBackground(Color.GRAY);
		   f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		   
		   f.setLayout(null);
		   
		   jcb = new JComboBox(sqls);
		   jcb.setMaximumRowCount(7);
		   jcb.setSize(500,80);
		   jcb.setLocation(50, 30);
		   jcb.setFont(new Font("", Font.PLAIN, 19));
		   
		   btn = new JButton("Execute");
		   btn.setSize(180, 80);
		   btn.setLocation(570, 30);
		   btn.setFont(new Font("", Font.PLAIN, 19));
		   btn.addActionListener(new BtnActionAdapter());
		   t = new TextArea("",20,20, TextArea.SCROLLBARS_VERTICAL_ONLY);
		   t.setSize(700, 350);
		   t.setLocation(50, 150);
		   t.setEditable(false);
		   t.setFont(new Font("", Font.PLAIN, 20));
		   t.setBackground(Color.WHITE);
		   
		   
		   f.add(jcb);
		   f.add(btn);
		   f.add(t);
		   f.setVisible(true);
	}	
}
class BtnActionAdapter implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
			String sql = Test.jcb.getSelectedItem().toString();
			int index = Test.jcb.getSelectedIndex();
			Test.t.setText("");
			if(index == 0 || index == 6)
			{
				String fullURL = Test.urlPrefix + Test.IP;
				System.out.println("Attempt to connect " + fullURL);
				PKUConnection con;
				try {
					con = (PKUConnection)DriverManager.getConnection(fullURL, Test.userName, Test.userPasswd);
					System.out.println("Creating new Statement");
					PKUStatement stmt = null;
					stmt = (PKUStatement)con.createStatement();
					System.out.println("Executing " + sql);
					PKUResultSet rs = (PKUResultSet)stmt.executeQuery(sql);
					PKUResultSetMetaData rmeta = (PKUResultSetMetaData) rs.getMetaData();
					int numColumns = rmeta.getColumnCount();
					String temp = "";
					for( int i = 1;i<= numColumns;i++ ) {
				           if( i < numColumns )
				               temp += rmeta.getColumnName(i)+"->";
				           else
				               temp += rmeta.getColumnName(i)+"\r\n";
				       }
					   
				       while( rs.next() ){
				           for( int i = 1;i <= numColumns;i++ ){
				               if( i < numColumns ) 
				                   temp += new String((rs.getString(i).trim()).getBytes("ISO-8859-1")) + "->";
				               else
				                   temp += new String((rs.getString(i).trim()).getBytes("ISO-8859-1")) + "\r\n";
				           }
				       }
				       Test.t.setText(temp);
				       rs.close();
				       con.close();
				} catch (SQLException | UnsupportedEncodingException e1) {
					Writer result = new StringWriter();
			        PrintWriter printWriter = new PrintWriter(result);
			        e1.printStackTrace(printWriter);
			        Test.t.setText(result.toString());
				}
				
			}
			else if(index == 3 || index == 4 || index == 5)
			{
				String fullURL = Test.urlPrefix + Test.IP;
				System.out.println("Attempt to connect " + fullURL);
				PKUConnection con;
				try {
					con = (PKUConnection)DriverManager.getConnection(fullURL, Test.userName, Test.userPasswd);
					System.out.println("Creating new Statement");
					PKUStatement stmt = null;
					stmt = (PKUStatement)con.createStatement();
					System.out.println("Executing " + sql);
					stmt.executeUpdate(sql);
					Test.t.setText("done!");
				    con.close();
				} catch (SQLException  e1) {
					Writer result = new StringWriter();
			        PrintWriter printWriter = new PrintWriter(result);
			        e1.printStackTrace(printWriter);
			        Test.t.setText(result.toString());
				}	
			}
			else if(index == 1 || index == 2)
			{
				String fullURL = Test.urlPrefix + Test.IP;
				System.out.println("Attempt to connect " + fullURL);
				PKUConnection con;
				try {
					con = (PKUConnection)DriverManager.getConnection(fullURL, Test.userName, Test.userPasswd);
					System.out.println("Creating new Statement");
					PKUStatement stmt = null;
					stmt = (PKUStatement)con.createStatement();
					System.out.println("Executing " + sql);
					stmt.execute(sql);
					Test.t.setText("done!");
				    con.close();
				} catch (SQLException  e1) {
					Writer result = new StringWriter();
			        PrintWriter printWriter = new PrintWriter(result);
			        e1.printStackTrace(printWriter);
			        Test.t.setText(result.toString());
				}	
			}
	}
}
