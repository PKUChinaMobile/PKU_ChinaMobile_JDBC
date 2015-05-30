package com.pku.cis.PKU_ChinaMobile_JDBC.GUI;

/**
 * 用户可输入查询窗口图形界面
 * Created by mrpen on 2015/5/21.
 * 现在是只连接mysql，等数据库配置好了记得改回来
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
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import javax.swing.*;

import java.awt.CardLayout;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.plaf.basic.BasicButtonUI;


public class SelectFrame extends JFrame {

    static PKUDriver d = new PKUDriver();
    public static TextArea textArea;//SQL语句输入框
    public static JLabel label_5;   //底部状态框
    public static int numRows; //表行数
    public static int numColumns; //表列数
    public static String[] tableHeader;//查询结果表头
    public static String[][] tableRow; //查询结果行
    public static MyJTable table; //自定义表对象
    public static JScrollPane scrollPane; //滚动条栏，包含表对象
    public static JPanel panel_3; //下半部面板
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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 582, 402);
        getContentPane().setLayout(new CardLayout(0, 0));

		/*第一个工具栏*/
        JPanel panel = new JPanel();
        panel.setLayout(null);
        JButton btn = createBtn("Run","res/image/icon0.png",10,0,65,25);//Run按钮
        btn.addActionListener(new Adapter_SelectFrame());
        panel.add(btn);
        JButton btn_2 = createBtn("Clear","res/image/icon1.png",80,0,75,25);//Clear按钮
        panel.add(btn_2);
        btn_2.addActionListener(new Adapter2_SelectFrame());

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
        JButton btn_3 = createBtn("Save","res/image/icon2.png",10,0,65,25);//Save按钮
        btn_3.addActionListener(new Adapter3_SelectFrame());
        panel_2.add(btn_3);

		/*结果栏*/
        panel_3 = new JPanel(); //结果栏
        panel_3.setLayout(new BorderLayout(0, 0));
        JLabel label_2 = new JLabel("");
        label_2.setOpaque(true);
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
/*Run按钮的响应事件*/
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
            con.setDst(0);//0-连接所有数据库
            PKUStatement stmt;
            stmt = (PKUStatement)con.createStatement();
            System.out.println("Executing " + sql);
            PKUResultSet rs = (PKUResultSet)stmt.executeQuery(sql);
            PKUResultSetMetaData rmeta = (PKUResultSetMetaData) rs.getMetaData();
            int numColumns = rmeta.getColumnCount();

            Vector<String[]> temp = new Vector<String[]>();
            String[] tempRow;
            tempRow = new String[numColumns]; //先将结果读取到vector，为了得知行数
            for(int i = 1; i <= numColumns; i++) //读取表头
                tempRow[i-1] =  String.format("%-15s", rmeta.getColumnName(i));
            temp.add(tempRow);
            while(rs.next()){
                tempRow = new String[numColumns];
                for(int i = 1; i <= numColumns; i++)
                    tempRow[i-1] = String.format("%-15s",new String((rs.getString(i).trim())));
                temp.add(tempRow);
            }
            int numRows = temp.size()-1;
            SelectFrame.numColumns = numColumns;
            SelectFrame.numRows = numRows;
            SelectFrame.tableHeader = new String[numColumns];
            SelectFrame.tableRow = new String[numRows][];
            for(int j = 0; j < numColumns; j++)
                SelectFrame.tableHeader[j] = temp.get(0)[j];
            for(int i = 0; i < numRows; i++) {
                SelectFrame.tableRow[i] = new String[numColumns];
                for(int j = 0; j < numColumns; j++)
                    SelectFrame.tableRow[i][j] = temp.get(i+1)[j];
            }
            temp.clear();
            SelectFrame.label_5.setText("Success.");
            rs.close();
            con.close();
        /*绘制查询结果*/

            SelectFrame.table = new MyJTable(new DefaultTableModel(SelectFrame.tableRow, SelectFrame.tableHeader));
            SelectFrame.table.setPreferredScrollableViewportSize(new Dimension(numColumns*20,numRows*10));
            SelectFrame.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//某列改变或窗口改变时的调整
            SelectFrame.table.setBackground(Color.WHITE);
            SelectFrame.panel_3.remove(SelectFrame.scrollPane);
            SelectFrame.scrollPane  = new JScrollPane(SelectFrame.table);
            SelectFrame.panel_3.add(SelectFrame.scrollPane, BorderLayout.CENTER);

        } catch (SQLException e1) {
            e1.printStackTrace();
            SelectFrame.label_5.setText("Failed.");
            JOptionPane.showMessageDialog(null,e1.getMessage(),"连接失败", JOptionPane.ERROR_MESSAGE);
        }


    }
}
/*Clear按钮的响应事件*/
class Adapter2_SelectFrame implements ActionListener
{
    public void actionPerformed(ActionEvent e)
    {
        SelectFrame.textArea.setCaretPosition(0);
        SelectFrame.textArea.setText("");
    }
}
/*Save按钮的相应事件*/
class Adapter3_SelectFrame implements ActionListener
{
    public void actionPerformed(ActionEvent e)
    {

        JFileChooser jf = new JFileChooser();
        String saveType[] = {"txt","csv"};  //
        jf.setFileFilter(new FileNameExtensionFilter("TXT & CSV FILE", saveType));
        jf.setDialogTitle("导出结果");     //自定义选择框标题
        jf.setSelectedFile(new File("result.txt")); //设置默认文件名
        jf.showDialog(null, "保存文件");//这行代码取代showOpenDialog和showSaveDialog
        File fi = jf.getSelectedFile();
        String f = fi.getAbsolutePath();
        try{
            FileWriter out = new FileWriter(f);
            out.write(SelectFrame.tableHeader[0]);
            for(int i = 1; i < SelectFrame.numColumns; i++)
                out.write(","+SelectFrame.tableHeader[i]);
            out.write('\n');
            for(int i = 0; i < SelectFrame.numRows; i++){
                out.write(SelectFrame.tableRow[i][0]);
                for(int j = 1; j < SelectFrame.numColumns; j++)
                {
                    out.write(","+SelectFrame.tableRow[i][j]);
                }
                out.write('\n');
            }
            out.close();
        }
        catch(Exception e1){
            JOptionPane.showMessageDialog(null,e1.getMessage(),"保存失败", JOptionPane.ERROR_MESSAGE);

        }

    }
}