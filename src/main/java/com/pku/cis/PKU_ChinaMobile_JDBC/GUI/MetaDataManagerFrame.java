package com.pku.cis.PKU_ChinaMobile_JDBC.GUI;

import com.pku.cis.PKU_ChinaMobile_JDBC.Client.PKUMetaDataManagement;
import org.datanucleus.metadata.MetaDataManager;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;


public class MetaDataManagerFrame extends JFrame {



    /*标签页1的组件*/
    public static int rowCount;
    public static int columnCount = 4;
    public static String data[][];
    public static String head[] = {"列1","列2","列3","列4"};
    public static JButton glbbutton;
    public static JButton glbbutton_1;
    public static JButton glbbutton_2;
    public static MyJTable glbtable;
    public static JTree tree;

    /*标签页2的组件*/
    public static int rowCount2;
    public static int columnCount2 = 6;
    public static String data2[][];
    public static String head2[] = {"UID","全局列名","LCID","Max","Min","Location"};
    public static JButton glbbutton2;
    public static JButton glbbutton2_1;
    public static JButton glbbutton2_2;
    public static MyJTable glbtable2;
    public static JTree tree2;
    public static JPanel panel;


    static final int FHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    static final int FWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MetaDataManagerFrame frame = new MetaDataManagerFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /*获取表格数据*/
    public void fetchData()
    {
        rowCount = 3;
        data = new String[rowCount][];
        data[0] = new String[columnCount];
        data[0][0] = "a";
        data[0][1] = "b";
        data[0][2] = "c";
        data[0][3] = "d";
        data[1] = new String[columnCount];
        data[1][0] = "a";
        data[1][1] = "b";
        data[1][2] = "c";
        data[1][3] = "d";
        data[2] = new String[columnCount];
        data[2][0] = "a";
        data[2][1] = "b";
        data[2][2] = "c";
        data[2][3] = "d";

    }
    /*更新表格*/
    public MyJTable updateTable()
    {
        fetchData();
        MyJTable t =  new MyJTable(new DefaultTableModel(data,head));
        t.setRowSelectionInterval(0, 0);
        t.setBackground(Color.WHITE);
        t.setBorder(null);
        t.setRowHeight(30);
        t.setEnabled(true);

        return t;
    }
    /*获取表格数据*/
    public void fetchData2()
    {
        rowCount2 = 2;
        data2 = new String[rowCount2][];
        data2[0] = new String[columnCount2];
        data2[0][0] = "a";
        data2[0][1] = "b";
        data2[0][2] = "c";
        data2[0][3] = "d";
        data2[0][4] = "c";
        data2[0][5] = "d";
        data2[1] = new String[columnCount2];
        data2[1][0] = "a";
        data2[1][1] = "b";
        data2[1][2] = "c";
        data2[1][3] = "d";
        data2[1][4] = "c";
        data2[1][5] = "d";


    }
    /*更新表格*/
    public MyJTable updateTable2()
    {
        fetchData2();
        MyJTable t =  new MyJTable(new DefaultTableModel(data2,head2));
        t.setRowSelectionInterval(0, 0);
        t.setBackground(Color.WHITE);
        t.setBorder(null);
        t.setRowHeight(30);
        t.setEnabled(true);

        return t;
    }
    /*更新文件树*/
    public void updateTree()
    {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("");
        PKUMetaDataManagement mm = new PKUMetaDataManagement();//mm使用时需要调用Init，结束时需要调用
        mm.Init();
        mm.showUTree();
        String[] tbName = mm.FetchUT();
        String[][] columnName = mm.FetchUC();
        mm.CloseCon();

        DefaultMutableTreeNode db = new DefaultMutableTreeNode("test");

        DefaultMutableTreeNode[] level1 = new DefaultMutableTreeNode[tbName.length];
        for(int i = 0; i < tbName.length; i++)
        {
            level1[i] = new DefaultMutableTreeNode(tbName[i]);
            DefaultMutableTreeNode[] level2 = new DefaultMutableTreeNode[columnName[i].length];
            for(int j = 0; j < columnName[i].length; j++)
            {
                level2[j] = new DefaultMutableTreeNode(columnName[i][j]);
                level1[i].add(level2[j]);
            }
            db.add(level1[i]);
        }
        root.add(db);
        tree = new JTree(root);
        tree.setRootVisible(false);
        tree.setShowsRootHandles(true);
        tree.addTreeSelectionListener(new Adapter_MetaDataManagerFrame(tree, panel));
    }
    public void updateTree2()
    {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("");
        PKUMetaDataManagement mm = new PKUMetaDataManagement();//mm使用时需要调用Init，结束时需要调用
        mm.Init();
        mm.showLTree();
        String[] datasourceName = mm.FetchLS(); //返回数据源数组
        String[][] dbName = mm.FetchLB(); //返回数据库名数组
        String[][][] tbName = mm.FetchLT();//返回表名数组
        String[][][][] columnName = mm.FetchLC(); //返回列名数组
        mm.CloseCon();

        DefaultMutableTreeNode[] level1 = new DefaultMutableTreeNode[datasourceName.length];
        for(int i = 0; i < datasourceName.length; i++)
        {
            level1[i] = new DefaultMutableTreeNode(datasourceName[i]);
            DefaultMutableTreeNode[] level2 = new DefaultMutableTreeNode[dbName[i].length];
            for(int j = 0; j < dbName[i].length; j++)
            {
                level2[j] = new DefaultMutableTreeNode(dbName[i][j]);
                DefaultMutableTreeNode[] level3 = new DefaultMutableTreeNode[tbName[i][j].length];
                for(int k = 0; k < tbName[i][j].length; k++)
                {
                    level3[k] = new DefaultMutableTreeNode(tbName[i][j][k]);
                    DefaultMutableTreeNode[] level4 = new DefaultMutableTreeNode[columnName[i][j][k].length];
                    for(int l = 0; l < columnName[i][j][k].length; l++)
                    {
                        level4[l] = new DefaultMutableTreeNode(columnName[i][j][k][l]);
                        level3[k].add(level4[l]);
                    }
                    level2[j].add(level3[k]);
                }
                level1[i].add(level2[j]);
            }
            root.add(level1[i]);

        }

        tree2 = new JTree(root);
        tree2.setRootVisible(false);
        tree2.setShowsRootHandles(true);
    }
    /**
     * Create the frame.
     */
    public MetaDataManagerFrame() {

        setTitle("透明网管系统——权限管理");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 400);
        getContentPane().setLayout(new GridLayout(1,1));

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);//标签页

        /*标签页1——全局映射*/
        JPanel tab1 = new JPanel();
        tab1.setBorder(new EmptyBorder(5, 5, 5, 5));
        tab1.setLayout(new CardLayout(1, 1));
        /*文件树*/
        updateTree();
        JScrollPane scrollPane = new JScrollPane(tree);//文件树包裹在可滚动模板里
        /*右侧模板*/
        JPanel panel = new JPanel();
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, scrollPane, panel);
        splitPane.setDividerLocation(200);//设置分割线位置
        splitPane.setOneTouchExpandable(true);//设置是否可展开
        splitPane.setDividerSize(10);//设置分隔线宽度的大小，以pixel为计算单位。
        splitPane.setResizeWeight(0.5);//设置改变大小时上下两部分改变的比例
        tab1.add(splitPane);

        /*标签页2——映射关系*/
        JPanel tab2 = new JPanel();
        tab2.setBorder(new EmptyBorder(5, 5, 5, 5));
        tab2.setLayout(new CardLayout(1, 1));
        /*文件树*/
        updateTree2();
        JScrollPane scrollPane2 = new JScrollPane(tree2);//文件树包裹在可滚动模板里
        /*右侧模板*/
        JPanel panel2 = new JPanel();
        JSplitPane splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, scrollPane2, panel2);
        splitPane2.setDividerLocation(200);//设置分割线位置
        splitPane2.setOneTouchExpandable(true);//设置是否可展开
        splitPane2.setDividerSize(10);//设置分隔线宽度的大小，以pixel为计算单位。
        splitPane2.setResizeWeight(0.5);//设置改变大小时上下两部分改变的比例
        tab2.add(splitPane2);

        tabbedPane.addTab("全局模式", tab1);
        tabbedPane.addTab("本地模式", tab2);
        getContentPane().add(tabbedPane);
    }
}
class Adapter_MetaDataManagerFrame implements TreeSelectionListener
{
    JTree tree;
    JPanel panel;
    JTable table;
    Adapter_MetaDataManagerFrame(JTree _tree, JPanel _panel)
    {
        tree = _tree;
        panel = _panel;
    }
    public void updateTable()
    {

    }
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
        int depth = selectedNode.getDepth();//0-列层，1-表层，2-数据库层
        if(depth == 2)
        {
            updateTable();
            JScrollPane scrollPane = new JScrollPane(table);//文件树包裹在可滚动模板里
            /*按钮区*/
            JPanel panel_1 = new JPanel();
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, scrollPane, panel);
            splitPane.setDividerLocation(200);//设置分割线位置
            splitPane.setOneTouchExpandable(true);//设置是否可展开
            splitPane.setDividerSize(10);//设置分隔线宽度的大小，以pixel为计算单位。
            splitPane.setResizeWeight(0.5);//设置改变大小时上下两部分改变的比例
            panel.setLayout(new CardLayout(1,1));
            panel.add(splitPane);
        }
        else if(depth == 1)
        {

        }
        else if(depth == 0)
        {

        }
    }
}