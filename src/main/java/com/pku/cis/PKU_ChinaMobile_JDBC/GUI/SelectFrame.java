package com.pku.cis.PKU_ChinaMobile_JDBC.GUI;

/**
 * 用户可输入查询窗口图形界面
 * Created by mrpen on 2015/5/21.
 */

import com.pku.cis.PKU_ChinaMobile_JDBC.Client.PKUConnection;
import com.pku.cis.PKU_ChinaMobile_JDBC.Client.PKUResultSet;
import com.pku.cis.PKU_ChinaMobile_JDBC.Client.PKUResultSetMetaData;
import com.pku.cis.PKU_ChinaMobile_JDBC.Client.PKUStatement;
import com.pku.cis.PKU_ChinaMobile_JDBC.Client.PKUDriver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.TextArea;


import javax.swing.*;

import java.awt.CardLayout;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.plaf.basic.BasicButtonUI;


public class SelectFrame extends JFrame {

    static PKUDriver d = new PKUDriver();
    public static TextArea textArea;//SQL语句输入框
    public static JLabel label_5;//底部状态框
    public static String[] tableHeader; //查询结果表头
    public static String[][] tableRow; //查询结果行
    public static JTable table;
    public static JScrollPane scrollPane;
    public static JLabel label_2;//结果栏
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SelectFrame frame = new SelectFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public SelectFrame() {
        setTitle("查询窗口");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 582, 402);
        getContentPane().setLayout(new CardLayout(0, 0));

		/*第一个工具栏*/
        JPanel panel = new JPanel();
        panel.setLayout(null);
        JButton btn = createBtn("Run","E:\\code\\java\\GUIDesigner\\icon0.png",10,0,65,25);//Run按钮
        btn.addActionListener(new Adapter_SelectFrame());
        panel.add(btn);
        JButton btn_2 = createBtn("Clear","E:\\code\\java\\GUIDesigner\\icon1.png",80,0,75,25);//Clear按钮
        panel.add(btn_2);

		/*SQL输入栏*/
        JPanel panel_1 = new JPanel(); //SQL输入栏
        panel_1.setLayout(new BorderLayout(0, 0));
        textArea = new TextArea("",20,20,TextArea.SCROLLBARS_VERTICAL_ONLY);
        textArea.setFont(new Font("Times New Roman",0, 20));
        panel_1.add(textArea, BorderLayout.CENTER);
        JLabel label = new JLabel(" ");
        panel_1.add(label, BorderLayout.WEST);
        JLabel label_1 = new JLabel(" ");
        panel_1.add(label_1, BorderLayout.EAST);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false, panel, panel_1);
        splitPane.setDividerLocation(25);//设置分割线位置
        splitPane.setOneTouchExpandable(true);//设置是否可展开
        splitPane.setDividerSize(0);//设置分隔线宽度的大小，以pixel为计算单位。
        splitPane.setOneTouchExpandable(false);

	    /*第二个工具栏*/
        JPanel panel_2 = new JPanel();
        panel_2.setLayout(null);
        JButton btn_3 = createBtn("Save","E:\\code\\java\\GUIDesigner\\icon2.png",10,0,65,25);//Save按钮
        panel_2.add(btn_3);

		/*结果栏*/
        JPanel panel_3 = new JPanel(); //结果栏
        panel_3.setLayout(new BorderLayout(0, 0));


        label_2 = new JLabel("");
        label_2.setOpaque(true);
        label_2.setBackground(Color.WHITE);
        scrollPane = new JScrollPane(label_2);
        panel_3.add(scrollPane, BorderLayout.CENTER);
        JLabel label_3 = new JLabel(" ");
        panel_3.add(label_3, BorderLayout.WEST);
        JLabel label_4 = new JLabel(" ");
        panel_3.add(label_4, BorderLayout.EAST);

        JSplitPane splitPane_1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false, panel_2, panel_3);
        splitPane_1.setDividerLocation(25);
        splitPane_1.setOneTouchExpandable(true);
        splitPane_1.setDividerSize(0);
        splitPane_1.setOneTouchExpandable(false);

        JSplitPane splitPane_2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false, splitPane, splitPane_1);
        splitPane_2.setDividerLocation(100);
        splitPane_2.setOneTouchExpandable(true);
        splitPane_2.setDividerSize(10);

	    /*底部状态栏*/
        JPanel panel_4 = new JPanel();
        panel_4.setLayout(new CardLayout(0, 0));
        label_5 = new JLabel("Status:");
        label_5.setFont(new Font("Times New Roman", Font.PLAIN, 15));// 按钮文本样式
        panel_4.add(label_5, "name_648620962732715");

        JSplitPane splitPane_3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false, splitPane_2, panel_4);


        splitPane_3.setDividerLocation(340);
        splitPane_3.setResizeWeight(1);//设置改变大小时上下两部分改变的比例
        splitPane_3.setOneTouchExpandable(false);
        splitPane_3.setDividerSize(0);

        getContentPane().add(splitPane_3, "name_565444981371158");
    }

    private static JButton createBtn(String text, String path, int x, int y, int w, int h)
    {
        JButton btn = new JButton(text);
        btn.setBorderPainted(false);// 隐藏边框
        btn.setFont(new Font("粗体", Font.PLAIN, 6));
        ImageIcon ico=new ImageIcon(path);
        Image temp=ico.getImage().getScaledInstance(h-4,h-4,ico.getImage().SCALE_DEFAULT);
        ico=new ImageIcon(temp);
        btn.setIcon(ico);
        btn.setUI(new BasicButtonUI());// 恢复基本视觉效果
        btn.setPreferredSize(new Dimension(80, 27));// 设置按钮大小
        btn.setContentAreaFilled(false);// 设置按钮透明
        btn.setFont(new Font("粗体", Font.PLAIN, 15));// 按钮文本样式
        btn.setMargin(new Insets(0, 0, 0, 0));// 按钮内容与边框距离
        btn.setBounds(x,y,w,h);
        btn.addMouseListener(new MyMouseListener());
        return btn;
    }
}
class Adapter_SelectFrame implements ActionListener
{
    public void actionPerformed(ActionEvent e)
    {
        /*获取查询结果*/
        String sql = SelectFrame.textArea.getText().toString();
        SelectFrame.label_5.setText("Execute: " + sql );
        String fullURL = Global.urlPrefix + Global.IP;
        System.out.println("Attempt to connect " + fullURL);
        PKUConnection con;
        try {
            con = (PKUConnection) DriverManager.getConnection(fullURL, Global.userName, Global.userPasswd);
            con.setDst(1);//0-连接所有数据库
            PKUStatement stmt;
            stmt = (PKUStatement)con.createStatement();
            System.out.println("Executing " + sql);
            PKUResultSet rs = (PKUResultSet)stmt.executeQuery(sql);
            PKUResultSetMetaData rmeta = (PKUResultSetMetaData) rs.getMetaData();
            int numColumns = rmeta.getColumnCount();
            int numRows = rs.getRows();
            SelectFrame.tableHeader = new String[numColumns];
            SelectFrame.tableRow = new String[numRows][];
            for(int i = 0; i < numRows; i++)
                SelectFrame.tableRow[i] = new String[numColumns];

            for( int i = 1;i<= numColumns;i++ ) //读取表头
                SelectFrame.tableHeader[i-1] = String.format("%-15s", rmeta.getColumnName(i));
            int j = 0;
            while( rs.next() ){
                for( int i = 1;i <= numColumns;i++ ){
                    SelectFrame.tableRow[j][i - 1] = String.format("%-15s",new String((rs.getString(i).trim())));
                }
                j++;
            }
            SelectFrame.label_5.setText("Success.");
            rs.close();
            con.close();
        /*绘制查询结果*/
            SelectFrame.table = new JTable(SelectFrame.tableRow, SelectFrame.tableHeader);
            SelectFrame.table.setPreferredScrollableViewportSize(new Dimension(numColumns*15,numRows*10));
            SelectFrame.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//某列改变或窗口改变时的调整
            SelectFrame.table.setOpaque(false);
            SelectFrame.scrollPane.add(SelectFrame.table);

        } catch (SQLException e1) {
            e1.printStackTrace();
            SelectFrame.label_5.setText("Failed.");
        }


    }
}
