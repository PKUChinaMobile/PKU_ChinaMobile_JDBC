package com.pku.cis.PKU_ChinaMobile_JDBC.examples;
/**本测试样例用于生成数据；
 * 其中测试用表的表名为callRecords，模拟移动公司的通信数据记录，具体结构如下：
 * biSessID varchar(32) 	- 通话ID
 * dualTime int 			- 通话时长
 * intYear int 				- 通话发生的年
 * intMonth int 			- 通话发生的月
 * intDay int 				- 通话发生的日 
 * intHour int 				- 通话发生的小时
 * intMinute int 			- 通话发生的分钟
 * vcCallingIMSI varchar(32)- 主叫用户id- 前两位代表地域
 * vcCalledIMSI varchar(32) - 被叫用户id
 * intLocation int 			-通话地点	（0~32）
 * 
 * 输入起始年月和终止年月，目标数据库，地点，总记录数即可生成数据
 *
 * 另数据库各自已经创建USERS表，模拟用户信息
 * IMSI varchar(32)  -用户ID
 * LOCATION int		 -用户地点
 * GENDER int		 -用户性别
 * AGE int			-用户年龄

 * */
import java.awt.Color;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;



public class DataGenerate {
	static JFrame f;
	static JButton btn;
	static JTextField num;
	static JTextField location;
	static JTextField s_mon;
	static JTextField e_mon;
	static JTextField s_year;
	static JTextField e_year;
	static JComboBox dst;
	static TextArea board;
	static JLabel l_dst;
	static JLabel l_smon;
	static JLabel l_emon;
	static JLabel l_syear;
	static JLabel l_eyear;
	static JLabel l_num;
	static JLabel l_location;
	static int userNo = 100000;
	static int days[] = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	static String databases[] = {"MYSQL",
		   "ORACLE",
		   "TERADATA",
		   "HIVE"
		   };
	public void init()
	{
		try {
			System.out.println("Loading JDBC Driver...");
			Class.forName("oracle.jdbc.OracleDriver").newInstance();
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Class.forName("org.apache.hive.jdbc.HiveDriver").newInstance();
		} catch (Exception e) {
			System.err.println("Could not found JDBC Driver!");
			System.err.println("Error Message is " + e.getMessage());
		} 
	}
	public static void main(String[] args)
	{
		DataGenerate d = new DataGenerate();
		d.init();
		 f = new JFrame("Test Window");
		 f.setSize(800, 600);
		 f.setLocation(500, 100);
		 f.setBackground(Color.GRAY);
		 f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		   
		 f.setLayout(null);
		 
		 l_dst = new JLabel("Choose Objective Database:");
		 l_dst.setSize(300, 80);
		 l_dst.setLocation(50, 30);
		 l_dst.setFont(new Font("", Font.PLAIN, 19));
		 dst = new JComboBox(databases);
		 dst.setMaximumRowCount(4);
		 dst.setSize(300,80);
		 dst.setLocation(350, 30);
		 dst.setFont(new Font("", Font.PLAIN, 19));
		 
		 l_syear = new JLabel("Start Year:");
		 l_syear.setSize(100, 60);
		 l_syear.setLocation(50, 120);
		 l_syear.setFont(new Font("", Font.PLAIN, 19));
		 s_year = new JTextField();
		 s_year.setSize(100, 60);
		 s_year.setLocation(150, 120);
		 s_year.setFont(new Font("", Font.PLAIN, 19));
		 l_smon = new JLabel("Start Mon:");
		 l_smon.setSize(100, 60);
		 l_smon.setLocation(300, 120);
		 l_smon.setFont(new Font("", Font.PLAIN, 19));
		 s_mon = new JTextField();
		 s_mon.setSize(100, 60);
		 s_mon.setLocation(400, 120);
		 s_mon.setFont(new Font("", Font.PLAIN, 19));
		 
		 l_eyear = new JLabel("End Year:");
		 l_eyear.setSize(100, 60);
		 l_eyear.setLocation(50, 200);
		 l_eyear.setFont(new Font("", Font.PLAIN, 19));
		 e_year = new JTextField();
		 e_year.setSize(100, 60);
		 e_year.setLocation(150, 200);
		 e_year.setFont(new Font("", Font.PLAIN, 19));
		 l_emon = new JLabel("End Mon:");
		 l_emon.setSize(100, 60);
		 l_emon.setLocation(300, 200);
		 l_emon.setFont(new Font("", Font.PLAIN, 19));
		 e_mon = new JTextField();
		 e_mon.setSize(100, 60);
		 e_mon.setLocation(400, 200);
		 e_mon.setFont(new Font("", Font.PLAIN, 19));
		 
		 l_location = new JLabel("Location(0~34):");
		 l_location.setSize(140, 60);
		 l_location.setLocation(10, 280);
		 l_location.setFont(new Font("", Font.PLAIN, 19));
		 location = new JTextField();
		 location.setSize(100, 60);
		 location.setLocation(150, 280);
		 location.setFont(new Font("", Font.PLAIN, 19));
		 l_num = new JLabel("Counts:");
		 l_num.setSize(100, 60);
		 l_num.setLocation(300, 280);
		 l_num.setFont(new Font("", Font.PLAIN, 19));
		 num = new JTextField();
		 num.setSize(100, 60);
		 num.setLocation(400, 280);
		 num.setFont(new Font("", Font.PLAIN, 19));
		 
		 JButton btn = new JButton("EXECUTE");
		 btn.setSize(200, 60);
		 btn.setLocation(50, 360);
		 btn.setFont(new Font("", Font.PLAIN, 19));
		 btn.addActionListener(new Adapter_DataGenerate());
		 
		 board = new TextArea("",20,20, TextArea.SCROLLBARS_VERTICAL_ONLY);
		 board.setSize(300, 60);
		 board.setLocation(350, 360);
		 board.setEditable(false);
		 board.setFont(new Font("", Font.PLAIN, 20));
		 board.setBackground(Color.WHITE);
		 
		 f.add(l_dst);
		 f.add(dst);
		 f.add(l_syear);
		 f.add(s_year);
		 f.add(l_smon);
		 f.add(s_mon);
		 f.add(l_eyear);
		 f.add(e_year);
		 f.add(l_emon);
		 f.add(e_mon);
		 f.add(l_location);
		 f.add(location);
		 f.add(l_num);
		 f.add(num);
		 f.add(btn);
		 f.add(board);
		 f.setVisible(true);
	}
}
class Adapter_DataGenerate implements ActionListener
{
	static String tbName = "CallRecords";
	static int index;
	static String URLS[];
	static String dbName[];
	static int dbNum;
	static String username[];
	static String password[];
	static Connection conn;

	File file;
	FileWriter fw = null;
	BufferedWriter writer = null;

	public void init()
	{
		dbNum = 4;
		URLS = new String[dbNum];
		dbName = new String[dbNum];
		username = new String[dbNum];
		password = new String[dbNum];
		
		URLS[0] = "jdbc:mysql://162.105.71.102:3306/test";
		URLS[1] = "jdbc:oracle:thin:@162.105.71.102:1521:mytest";
		URLS[2] = "jdbc:teradata://162.105.71.205/vmtest";
		URLS[3] = "jdbc:hive2://162.105.71.61:10000/test";
		
		dbName[0] = "mysql";
		dbName[1] = "oracle";
		dbName[2] = "teradata";
		dbName[3] = "hive";
		
		username[0] = "root";
		username[1] = "SYSTEM";
		username[2] = "vmtest";
		username[3] = "hadoop";
		
		password[0] = "06948859";
		password[1] = "oracle1ORACLE";
		password[2] = "vmtest";
		password[3] = "";
		
		
		try{
			conn = (Connection)DriverManager.getConnection(URLS[index], username[index], password[index]);
		}catch(SQLException e){
			System.err.println("Connection for "+dbName[index]+" failed.");
			System.err.println("Error Message is " + e.getMessage());
		}
		System.out.println("Connect done!");
		/*建表语句
		 * String sql = "CREATE TABLE CallRecords (biSessID varchar(32), dualTime int, intYear int, intMonth int"
				+ ",intDay int, intHour int, intMinute int, vcCallingIMSI varchar(32), vcCalledIMSI varchar(32),"
				+ "intLocation int);";
		
		*/

		if(index == 3)
		{
			file = new File("data.txt");
			try {
				fw = new FileWriter(file);
				writer = new BufferedWriter(fw);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("init done!");
	}
	public void actionPerformed(ActionEvent e)
	{
		int smon, emon, syear, eyear, lct, n;
		smon = Integer.parseInt(DataGenerate.s_mon.getText());
		emon = Integer.parseInt(DataGenerate.e_mon.getText());
		syear = Integer.parseInt(DataGenerate.s_year.getText());
		eyear = Integer.parseInt(DataGenerate.e_year.getText());
		n = Integer.parseInt(DataGenerate.num.getText());
		lct = Integer.parseInt(DataGenerate.location.getText());
		index = DataGenerate.dst.getSelectedIndex();
		
		init();

		Date dt= new Date(); 
		Long headID= dt.getTime();
		int tailID = 0; 
		int spanMon = (eyear - syear) * 12 + emon - smon + 1;
		int RecordPerMon = n / spanMon;
		int cnt = 0, total = 0;
		for(int y = syear, m = smon, i = 0; i < spanMon; i++, m++)
		{
			if(m > 12)
			{
				m = 1;
				y += 1;
			}
			int upBound;
			if(i != spanMon - 1)
				upBound = RecordPerMon;
			else
				upBound = RecordPerMon + n % spanMon;
			int RecordPerDay = upBound / DataGenerate.days[m - 1] + 1, k = 0, day = 1;
			for(int j = 0; j < upBound; j++,cnt++,k++)
			{
				String sessID = headID.toString() + (tailID+"");
				tailID++;
				if(tailID == 10000)
				{
					tailID = 0;
					headID += 1;
				}
				if(k == RecordPerDay)
				{
					k = 0;
					day++;
				}
				if(cnt == 100)
				{
					total += 100;
					cnt = 0;
					DataGenerate.board.setText(total + " / " + n);
				}
				String hour = (int)(Math.random() * 24)+"";
				String minute = (int)(Math.random() * 60)+"";
				String dualTime = (int)(Math.random() * 1000000)+"";
				String location = lct + "";
				String IMSI1 = (int)(Math.random() * 1000) + lct * 10000000 + "";
				String IMSI2 = (int)(Math.random() * 1000) + lct * 10000000 + "";
				String sql = "INSERT INTO " + tbName + "(biSessID, dualTime, intYear, intMonth, intDay, intHour, intMinute, "
						+ "vcCallingIMSI, vcCalledIMSI, intLocation) VALUES('" + sessID+"','"+dualTime+"','"+y+"','"+m+"','"+
						day+"','"+hour+"','"+minute+"','"+IMSI1+"','"+IMSI2+"','"+location+"')";
				if(index == 3)
					insertIntoFile(sessID + "\001" + dualTime +"\001" +y + "\001" + m + "\001"
							+ day + "\001" + hour + "\001" + minute + "\001" + IMSI1 + "\001" +
							IMSI2 + "\001" + location);
				else
					insertIntoDb(sql);
				
			}
			
		}
/*
		for(int i = 0; i < 1000; i++) {
			String IMSI1 = lct * 10000000 + i + "";
			String gender = (int) (Math.random() * 2) + "";
			String age = 18 + (int) (Math.random() * 60) + "";
			String sql = "INSERT INTO USERS(IMSI,LOCATION,GENDER,AGE) VALUES('" + IMSI1 + "','" + lct + "','" + gender + "','" + age + "')";
			if(index == 3)
				insertIntoFile(IMSI1 + "\001" + lct + "\001" + gender + "\001" + age);
			else
				insertIntoDb(sql);
		}
*/
			DataGenerate.board.setText("done!");
		if(index == 3)
		{
			try {
				writer.close();
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
	}
	public void insertIntoFile(String str)
	{


		try {
			writer.write(str);
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public void insertIntoDb(String sql)
	{
		System.out.println(sql);
			try {
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(sql);
			} catch (SQLException  e1) {
				Writer result = new StringWriter();
		        PrintWriter printWriter = new PrintWriter(result);
		        e1.printStackTrace(printWriter);
		        DataGenerate.board.setText(result.toString());
			}	
	}
}
