package com.pku.cis.PKU_ChinaMobile_JDBC.examples;
/**
 * 本测试样例用于往数据库创建或删除表哥
 * 本测试样例提供输入窗口，让用户输入drop table语句和create table语句进行查询，并返回结果或者错误信息；
 * 用户可选择在所有数据库上进行，也可以在某一单一数据库进行
 *
 *
 CREATE TABLE USERS (IMSI varchar(32), LOCATION int, GENDER int, AGE int)
 CREATE TABLE CallRecords (biSessID varchar(32), dualTime int, intYear int, intMonth int，intDay int, intHour int, intMinute int, vcCallingIMSI varchar(32), vcCalledIMSI varchar(32),intLocation int)
 * */
import java.awt.Color;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.io.Writer;
import java.sql.DriverManager;
import java.sql.SQLException;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import com.pku.cis.PKU_ChinaMobile_JDBC.Client.PKUConnection;
import com.pku.cis.PKU_ChinaMobile_JDBC.Client.PKUDriver;
import com.pku.cis.PKU_ChinaMobile_JDBC.Client.PKUStatement;


public class TestForDropAndCreate  {
    static JFrame f;
    static JComboBox dst;
    static JButton btn;
    static TextArea t;
    static TextField in;
    public static int index;

    static String databases[] = {"ALL","MYSQL","ORACLE","TERADATA","HIVE"};

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

        in = new TextField();
        in.setSize(400,80);
        in.setLocation(50, 30);
        in.setFont(new Font("", Font.PLAIN, 19));

        dst = new JComboBox(databases);
        dst.setMaximumRowCount(5);
        dst.setSize(100,80);
        dst.setLocation(460, 30);
        dst.setFont(new Font("", Font.PLAIN, 19));

        btn = new JButton("Execute");
        btn.setSize(180, 80);
        btn.setLocation(570, 30);
        btn.setFont(new Font("", Font.PLAIN, 19));
        btn.addActionListener(new Adapter_TestForDropAndCreate());
        t = new TextArea("",20,20, TextArea.SCROLLBARS_BOTH);
        t.setSize(700, 350);
        t.setLocation(50, 150);
        t.setEditable(false);
        t.setFont(new Font("", Font.PLAIN, 20));
        t.setBackground(Color.WHITE);


        f.add(in);
        f.add(dst);
        f.add(btn);
        f.add(t);
        f.setVisible(true);
    }
}
class Adapter_TestForDropAndCreate implements ActionListener
{
    public void actionPerformed(ActionEvent e)
    {
        String sql = TestForDropAndCreate.in.getText().toString();
        int index = TestForDropAndCreate.dst.getSelectedIndex();

        TestForDropAndCreate.t.setText("Execute: " + sql + "\n");
        String fullURL = Test.urlPrefix + Test.IP;
        System.out.println("Attempt to connect " + fullURL);
        PKUConnection con;
        try {
            con = (PKUConnection)DriverManager.getConnection(fullURL, Test.userName, Test.userPasswd);
            con.setDst(index);
            PKUStatement stmt;
            stmt = (PKUStatement)con.createStatement();
            System.out.println("Executing " + sql);
            stmt.execute(sql);
            TestForDropAndCreate.t.append("\ndone!");
            con.close();
        } catch (SQLException e1) {
            Writer result = new StringWriter();
            PrintWriter printWriter = new PrintWriter(result);
            e1.printStackTrace(printWriter);
            TestForDropAndCreate.t.setText(result.toString());
        }
    }
}
