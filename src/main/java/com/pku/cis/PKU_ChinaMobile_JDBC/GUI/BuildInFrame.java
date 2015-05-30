package com.pku.cis.PKU_ChinaMobile_JDBC.GUI;

/**
 * Created by mrpen on 2015/5/23.
 * 内置查询界面
 */



import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.sql.DriverManager;
import java.sql.SQLException;

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


import java.awt.CardLayout;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.util.Vector;


public class BuildInFrame extends JFrame {

    static PKUDriver d = new PKUDriver();
    public static JComboBox comboBox;
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
                    BuildInFrame frame = new BuildInFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 读取配置文件
     * */
    public void fetchData()
    {
        comboBox.addItem("--选择查询语句--");
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream("res/BuildinSQLs.txt")));

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                comboBox.addItem(line);
            }
            br.close();
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null, "无法读取配置文件 BuildinSQLs.txt", "初始化失败", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     *
     */
    public void updateData()
    {
        try{
            FileWriter writer = new FileWriter("res/BuildinSQLs.txt");
            BufferedWriter bw = new BufferedWriter(writer);
            int n = comboBox.getItemCount();
            for(int i = 1; i < n; i++)
            {
                String str = comboBox.getItemAt(i).toString();
                bw.write(str);
                bw.newLine();
            }
            bw.close();
            writer.close();
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "无法写入配置文件 BuildinSQLs.txt", "添加语句失败", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * 更新下拉菜单
     * */
    public void updateComboBox()
    {
        comboBox.removeAllItems();
        fetchData();
    }
      /**
     * Create the frame.
     */
    public BuildInFrame() {

        setTitle("透明网关系统——查询窗口");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 582, 402);
        getContentPane().setLayout(new CardLayout(0, 0));

		/*第一个工具栏*/
        JPanel panel = new JPanel();
        panel.setLayout(null);
        JButton btn = createBtn("Run","res/image/icon0.png",10,0,65,25);//Run按钮
        btn.addActionListener(new Adapter_BuildInFrame());
        panel.add(btn);
        JButton btn_2 = createBtn("Add","res/image/icon1.png",80,0,65,25);//Add按钮
        panel.add(btn_2);
        btn_2.addActionListener(new Adapter2_BuildInFrame(this));

        JButton btn_4 = createBtn("Remove","res/image/icon3.png",150,0,110,25);//Remove按钮
        btn_4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = comboBox.getSelectedIndex();
                if(index != 0)
                {
                    comboBox.removeItemAt(index);
                    updateData();
                }
            }
        });
        panel.add(btn_4);


		/*SQL输入栏*/
        JPanel panel_1 = new JPanel(); //SQL输入栏
        panel_1.setLayout(new BorderLayout(0, 0));
        JPanel panel_5 = new JPanel(); //复选框
        panel_5.setLayout(new CardLayout(0, 0));
        comboBox = new JComboBox();
        fetchData();
        panel_5.add(comboBox);
        panel_1.add(panel_5, BorderLayout.CENTER);
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
        btn_3.addActionListener(new Adapter3_BuildInFrame());
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
class Adapter_BuildInFrame implements ActionListener
{
    public void actionPerformed(ActionEvent e)
    {
        /*获取查询结果*/
        int index = BuildInFrame.comboBox.getSelectedIndex();
        if(index != 0) {
            String sql = BuildInFrame.comboBox.getSelectedItem().toString();
            BuildInFrame.label_5.setText("Execute: " + sql);
            String fullURL = Global.urlPrefix + Global.IP;
            System.out.println("Attempt to connect " + fullURL);
            PKUConnection con;
            try {
                con = (PKUConnection) DriverManager.getConnection(fullURL, Global.userName, Global.userPasswd);
                con.setDst(0);//0-连接所有数据库
                PKUStatement stmt;
                stmt = (PKUStatement) con.createStatement();
                System.out.println("Executing " + sql);
                PKUResultSet rs = (PKUResultSet) stmt.executeQuery(sql);
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
                BuildInFrame.numColumns = numColumns;
                BuildInFrame.numRows = numRows;
                BuildInFrame.tableHeader = new String[numColumns];
                BuildInFrame.tableRow = new String[numRows][];
                for(int j = 0; j < numColumns; j++)
                    BuildInFrame.tableHeader[j] = temp.get(0)[j];
                for(int i = 0; i < numRows; i++) {
                    BuildInFrame.tableRow[i] = new String[numColumns];
                    for(int j = 0; j < numColumns; j++)
                        BuildInFrame.tableRow[i][j] = temp.get(i+1)[j];
                }
                temp.clear();
                BuildInFrame.label_5.setText("Success.");
                rs.close();
                con.close();
        /*绘制查询结果*/

                BuildInFrame.table = new MyJTable(new DefaultTableModel(BuildInFrame.tableRow, BuildInFrame.tableHeader));
                BuildInFrame.table.setPreferredScrollableViewportSize(new Dimension(numColumns * 20, numRows * 10));
                BuildInFrame.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//某列改变或窗口改变时的调整
                BuildInFrame.table.setBackground(Color.WHITE);
                BuildInFrame.panel_3.remove(BuildInFrame.scrollPane);
                BuildInFrame.scrollPane = new JScrollPane(BuildInFrame.table);
                BuildInFrame.panel_3.add(BuildInFrame.scrollPane, BorderLayout.CENTER);

            } catch (SQLException e1) {
                e1.printStackTrace();
                BuildInFrame.label_5.setText("Failed.");
                JOptionPane.showMessageDialog(null, e1.getMessage(), "连接失败", JOptionPane.ERROR_MESSAGE);
            }

        }
    }
}
class Adapter2_BuildInFrame implements ActionListener
{
    BuildInFrame f;
    Adapter2_BuildInFrame(BuildInFrame _f)
    {
        f = _f;
    }
    public void actionPerformed(ActionEvent e)
    {
        QueryGenerateFrame mf = new QueryGenerateFrame(f);
        mf.setVisible(true);
    }
}
/*Save按钮的相应事件*/
class Adapter3_BuildInFrame implements ActionListener
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
            out.write(BuildInFrame.tableHeader[0]);
            for(int i = 1; i < BuildInFrame.numColumns; i++)
                out.write(","+BuildInFrame.tableHeader[i]);
            out.write('\n');
            for(int i = 0; i < BuildInFrame.numRows; i++){
                out.write(BuildInFrame.tableRow[i][0]);
                for(int j = 1; j < BuildInFrame.numColumns; j++)
                {
                    out.write(","+BuildInFrame.tableRow[i][j]);
                }
                out.write('\n');
            }
            out.close();
        }
        catch(Exception e1){}
    }
}
