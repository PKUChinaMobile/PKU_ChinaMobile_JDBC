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

        /*右侧模板*/
        panel = new JPanel();//需要放在文件树监听器之前
         /*文件树*/
        updateTree();
        tree.addTreeSelectionListener(new Adapter_MetaDataManagerFrame(tree, panel));
        JScrollPane scrollPane = new JScrollPane(tree);//文件树包裹在可滚动模板里
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
        /*右侧模板*/
        JPanel panel2 = new JPanel();
        /*文件树*/
        updateTree2();
        tree2.addTreeSelectionListener(new Adapter2_MetaDataManagerFrame(tree2, panel2));
        JScrollPane scrollPane2 = new JScrollPane(tree2);//文件树包裹在可滚动模板里
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
    public static JPanel panel;
    DefaultMutableTreeNode selectedNode;
    MyJTable dbtable;
    MyJTable tbtable;
    MyJTable columntable;
    JButton btnNewButton;
    JButton button;
    JButton button_1;
    public static String data[][];
    public static String head[] = {"UID","TableName"};
    public static String head2[] = {"UID","ColumnName","Table ID"};
    public static String head3[] = {"UID","UCID","LCID","Max","Min","Location"};

    Adapter_MetaDataManagerFrame(JTree _tree, JPanel _panel)
    {
        tree = _tree;
        panel = _panel;

    }
    public void updateTable()
    {
        int depth = selectedNode.getDepth();//0-列层，1-表层，2-数据库层
        if(depth == 2)
            updateDBTable();
        else if(depth == 1)
            updateTBTable();
        else if(depth == 0)
            updateColumnTable();
    }
    public void updateDBTable()
    {
        PKUMetaDataManagement mm = new PKUMetaDataManagement();
        mm.Init();
        data = mm.showUTable();
        mm.CloseCon();
        dbtable =  new MyJTable(new DefaultTableModel(data,head));
        dbtable.setRowSelectionInterval(0, 0);
        dbtable.setBackground(Color.WHITE);
        dbtable.setBorder(null);
        dbtable.setRowHeight(30);
        dbtable.setEnabled(true);
        dbtable.updateUI();
    }
    public void updateTBTable()
    {
        PKUMetaDataManagement mm = new PKUMetaDataManagement();
        mm.Init();
        String[][] temp = mm.showUTable();
        int UID = Integer.valueOf(temp[selectedNode.getParent().getIndex(selectedNode)][0]);
        data = mm.showUColumn(UID);
        mm.CloseCon();
        tbtable =  new MyJTable(new DefaultTableModel(data,head2));
        tbtable.setRowSelectionInterval(0, 0);
        tbtable.setBackground(Color.WHITE);
        tbtable.setBorder(null);
        tbtable.setRowHeight(30);
        tbtable.setEnabled(true);
        tbtable.updateUI();
    }
    public void updateColumnTable()
    {
        PKUMetaDataManagement mm = new PKUMetaDataManagement();
        mm.Init();
        String[][] tmp = mm.showUTable();
        int UID = Integer.valueOf(tmp[selectedNode.getParent().getParent().getIndex(selectedNode.getParent())][0]);
        String[][] temp = mm.showUColumn(UID);
        int UCID = Integer.valueOf(temp[selectedNode.getParent().getIndex(selectedNode)][0]);
        data = mm.showMappingByU(UCID);
        mm.CloseCon();

        columntable =  new MyJTable(new DefaultTableModel(data,head3));
        columntable.setRowSelectionInterval(0, 0);
        columntable.setBackground(Color.WHITE);
        columntable.setBorder(null);
        columntable.setRowHeight(30);
        columntable.setEnabled(true);
        columntable.updateUI();
    }
    public void valueChanged(TreeSelectionEvent e) {
        selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (selectedNode != null) {
            int depth = selectedNode.getDepth();//0-列层，1-表层，2-数据库层
            panel.removeAll();
            if (depth == 2) {
                updateDBTable();
                JScrollPane scrollPane = new JScrollPane(dbtable);//文件树包裹在可滚动模板里
            /*按钮区*/
                JPanel panel_1 = new JPanel();
                panel_1.setLayout(null);
                btnNewButton = new JButton("添加");
                btnNewButton.setBounds(6, 16, 135, 30);
                btnNewButton.addActionListener(new Adapter_btn_MetaDataManagerFrame(this));
                panel_1.add(btnNewButton);
                button = new JButton("编辑");
                button.setBounds(6, 58, 135, 30);
                panel_1.add(button);
                button_1 = new JButton("删除");
                button_1.setBounds(6, 98, 135, 30);
                panel_1.add(button_1);

                JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, scrollPane, panel_1);
                splitPane.setDividerLocation(300);//设置分割线位置
                splitPane.setOneTouchExpandable(true);//设置是否可展开
                splitPane.setDividerSize(10);//设置分隔线宽度的大小，以pixel为计算单位。
                splitPane.setResizeWeight(1);//设置改变大小时上下两部分改变的比例
                panel.setLayout(new CardLayout(1, 1));
                panel.add(splitPane);
            } else if (depth == 1) {
                updateTBTable();
                JScrollPane scrollPane = new JScrollPane(tbtable);//文件树包裹在可滚动模板里
            /*按钮区*/
                JPanel panel_1 = new JPanel();
                panel_1.setLayout(null);
                btnNewButton = new JButton("添加");
                btnNewButton.setBounds(6, 16, 135, 30);
                panel_1.add(btnNewButton);
                button = new JButton("编辑");
                button.setBounds(6, 58, 135, 30);
                panel_1.add(button);
                button_1 = new JButton("删除");
                button_1.setBounds(6, 98, 135, 30);
                panel_1.add(button_1);

                JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, scrollPane, panel_1);
                splitPane.setDividerLocation(300);//设置分割线位置
                splitPane.setOneTouchExpandable(true);//设置是否可展开
                splitPane.setDividerSize(10);//设置分隔线宽度的大小，以pixel为计算单位。
                splitPane.setResizeWeight(1);//设置改变大小时上下两部分改变的比例
                panel.setLayout(new CardLayout(1, 1));
                panel.add(splitPane);
            } else if (depth == 0) {
                updateColumnTable();
                JScrollPane scrollPane = new JScrollPane(columntable);//文件树包裹在可滚动模板里
            /*按钮区*/
                JPanel panel_1 = new JPanel();
                panel_1.setLayout(null);
                btnNewButton = new JButton("添加");
                btnNewButton.setBounds(6, 16, 135, 30);
                panel_1.add(btnNewButton);
                button = new JButton("编辑");
                button.setBounds(6, 58, 135, 30);
                panel_1.add(button);
                button_1 = new JButton("删除");
                button_1.setBounds(6, 98, 135, 30);
                panel_1.add(button_1);

                JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, scrollPane, panel_1);
                splitPane.setDividerLocation(300);//设置分割线位置
                splitPane.setOneTouchExpandable(true);//设置是否可展开
                splitPane.setDividerSize(10);//设置分隔线宽度的大小，以pixel为计算单位。
                splitPane.setResizeWeight(1);//设置改变大小时上下两部分改变的比例
                panel.setLayout(new CardLayout(1, 1));
                panel.add(splitPane);
            }
            panel.updateUI();
        } else {
            panel.removeAll();
            panel.updateUI();
        }
    }
}
class Adapter2_MetaDataManagerFrame implements TreeSelectionListener
{
    JTree tree;
    public static JPanel panel;
    DefaultMutableTreeNode selectedNode;
    MyJTable dstable;
    MyJTable dbtable;
    MyJTable tbtable;
    MyJTable columntable;
    public static String data[][];
    public static String head0[] = {"UID","DBName","DSID"};
    public static String head[] = {"UID","TableName","DBID"};
    public static String head2[] = {"UID","TableID","ColumnName","ColumnType"};
    public static String head3[] = {"UID","UCID","LCID","Max","Min","Location"};

    Adapter2_MetaDataManagerFrame(JTree _tree, JPanel _panel)
    {
        tree = _tree;
        panel = _panel;

    }
    public void updateDSTable()
    {
        PKUMetaDataManagement mm = new PKUMetaDataManagement();
        int ID = selectedNode.getParent().getIndex(selectedNode) + 1;
        mm.Init();
        data = mm.showLDB(ID);
        mm.CloseCon();
        dstable =  new MyJTable(new DefaultTableModel(data,head0));
        dstable.setRowSelectionInterval(0, 0);
        dstable.setBackground(Color.WHITE);
        dstable.setBorder(null);
        dstable.setRowHeight(30);
        dstable.setEnabled(true);
        dstable.updateUI();
    }
    public void updateDBTable()
    {
        PKUMetaDataManagement mm = new PKUMetaDataManagement();
        int ID = selectedNode.getParent().getParent().getIndex(selectedNode.getParent()) + 1;
        mm.Init();
        String[][] temp = mm.showLDB(ID);
        int DBID = Integer.valueOf(temp[selectedNode.getParent().getIndex(selectedNode)][0]);
        data = mm.showLTable(DBID);
        mm.CloseCon();
        dbtable =  new MyJTable(new DefaultTableModel(data,head));
        dbtable.setRowSelectionInterval(0, 0);
        dbtable.setBackground(Color.WHITE);
        dbtable.setBorder(null);
        dbtable.setRowHeight(30);
        dbtable.setEnabled(true);
        dbtable.updateUI();
    }
    public void updateTBTable()
    {
        PKUMetaDataManagement mm = new PKUMetaDataManagement();
        mm.Init();
        int ID = selectedNode.getParent().getParent().getParent().
                getIndex(selectedNode.getParent().getParent()) + 1;
        String[][] temp = mm.showLDB(ID);
        int DBID = Integer.valueOf(temp[selectedNode.getParent().getParent().getIndex(selectedNode.getParent())][0]);
        String[][] tmp = mm.showLTable(DBID);
        int TBID = Integer.valueOf(tmp[selectedNode.getParent().getIndex(selectedNode)][0]);
        data = mm.showLColumn(TBID);
        mm.CloseCon();
        tbtable =  new MyJTable(new DefaultTableModel(data,head2));
        tbtable.setRowSelectionInterval(0, 0);
        tbtable.setBackground(Color.WHITE);
        tbtable.setBorder(null);
        tbtable.setRowHeight(30);
        tbtable.setEnabled(true);
        tbtable.updateUI();
    }
    public void updateColumnTable()
    {
        PKUMetaDataManagement mm = new PKUMetaDataManagement();
        mm.Init();
        int ID = selectedNode.getParent().getParent().getParent().getParent().
                getIndex(selectedNode.getParent().getParent().getParent()) + 1;
        String[][] temp = mm.showLDB(ID);
        int DBID = Integer.valueOf(temp[selectedNode.getParent().getParent().getParent().
                getIndex(selectedNode.getParent().getParent())][0]);
        String[][] tmp = mm.showLTable(DBID);
        int TBID = Integer.valueOf(tmp[selectedNode.getParent().getParent().getIndex(selectedNode.getParent())][0]);
        String[][] tp = mm.showLColumn(TBID);
        int LCID = Integer.valueOf(tp[selectedNode.getParent().getIndex(selectedNode)][0]);
        data = mm.showMappingByL(LCID);
        mm.CloseCon();

        columntable =  new MyJTable(new DefaultTableModel(data,head3));
        columntable.setRowSelectionInterval(0, 0);
        columntable.setBackground(Color.WHITE);
        columntable.setBorder(null);
        columntable.setRowHeight(30);
        columntable.setEnabled(true);
        columntable.updateUI();
    }
    public void valueChanged(TreeSelectionEvent e) {
        selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (selectedNode != null) {
            int depth = selectedNode.getDepth();//0-列层，1-表层，2-数据库层,3-数据源层
            panel.removeAll();
            if(depth == 3){
                updateDSTable();
                JScrollPane scrollPane = new JScrollPane(dstable);//文件树包裹在可滚动模板里
            /*按钮区*/
                JPanel panel_1 = new JPanel();
                panel_1.setLayout(null);
                JButton btnNewButton = new JButton("添加");
                btnNewButton.setBounds(6, 16, 135, 30);
                panel_1.add(btnNewButton);
                JButton button = new JButton("编辑");
                button.setBounds(6, 58, 135, 30);
                panel_1.add(button);
                JButton button_1 = new JButton("删除");
                button_1.setBounds(6, 98, 135, 30);
                panel_1.add(button_1);

                JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, scrollPane, panel_1);
                splitPane.setDividerLocation(300);//设置分割线位置
                splitPane.setOneTouchExpandable(true);//设置是否可展开
                splitPane.setDividerSize(10);//设置分隔线宽度的大小，以pixel为计算单位。
                splitPane.setResizeWeight(1);//设置改变大小时上下两部分改变的比例
                panel.setLayout(new CardLayout(1, 1));
                panel.add(splitPane);
            }
            else if (depth == 2) {
                updateDBTable();
                JScrollPane scrollPane = new JScrollPane(dbtable);//文件树包裹在可滚动模板里
            /*按钮区*/
                JPanel panel_1 = new JPanel();
                panel_1.setLayout(null);
                JButton btnNewButton = new JButton("添加");
                btnNewButton.setBounds(6, 16, 135, 30);
                panel_1.add(btnNewButton);
                JButton button = new JButton("编辑");
                button.setBounds(6, 58, 135, 30);
                panel_1.add(button);
                JButton button_1 = new JButton("删除");
                button_1.setBounds(6, 98, 135, 30);
                panel_1.add(button_1);

                JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, scrollPane, panel_1);
                splitPane.setDividerLocation(300);//设置分割线位置
                splitPane.setOneTouchExpandable(true);//设置是否可展开
                splitPane.setDividerSize(10);//设置分隔线宽度的大小，以pixel为计算单位。
                splitPane.setResizeWeight(1);//设置改变大小时上下两部分改变的比例
                panel.setLayout(new CardLayout(1, 1));
                panel.add(splitPane);
            } else if (depth == 1) {
                updateTBTable();
                JScrollPane scrollPane = new JScrollPane(tbtable);//文件树包裹在可滚动模板里
            /*按钮区*/
                JPanel panel_1 = new JPanel();
                panel_1.setLayout(null);
                JButton btnNewButton = new JButton("添加");
                btnNewButton.setBounds(6, 16, 135, 30);
                panel_1.add(btnNewButton);
                JButton button = new JButton("编辑");
                button.setBounds(6, 58, 135, 30);
                panel_1.add(button);
                JButton button_1 = new JButton("删除");
                button_1.setBounds(6, 98, 135, 30);
                panel_1.add(button_1);

                JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, scrollPane, panel_1);
                splitPane.setDividerLocation(300);//设置分割线位置
                splitPane.setOneTouchExpandable(true);//设置是否可展开
                splitPane.setDividerSize(10);//设置分隔线宽度的大小，以pixel为计算单位。
                splitPane.setResizeWeight(1);//设置改变大小时上下两部分改变的比例
                panel.setLayout(new CardLayout(1, 1));
                panel.add(splitPane);
            } else if (depth == 0) {
                updateColumnTable();
                JScrollPane scrollPane = new JScrollPane(columntable);//文件树包裹在可滚动模板里
            /*按钮区*/
                JPanel panel_1 = new JPanel();
                panel_1.setLayout(null);
                JButton btnNewButton = new JButton("添加");
                btnNewButton.setBounds(6, 16, 135, 30);
                panel_1.add(btnNewButton);
                JButton button = new JButton("编辑");
                button.setBounds(6, 58, 135, 30);
                panel_1.add(button);
                JButton button_1 = new JButton("删除");
                button_1.setBounds(6, 98, 135, 30);
                panel_1.add(button_1);

                JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, scrollPane, panel_1);
                splitPane.setDividerLocation(300);//设置分割线位置
                splitPane.setOneTouchExpandable(true);//设置是否可展开
                splitPane.setDividerSize(10);//设置分隔线宽度的大小，以pixel为计算单位。
                splitPane.setResizeWeight(1);//设置改变大小时上下两部分改变的比例
                panel.setLayout(new CardLayout(1, 1));
                panel.add(splitPane);
            }
            panel.updateUI();
        } else {
            panel.removeAll();
            panel.updateUI();
        }
    }
}

class Adapter_btn_MetaDataManagerFrame implements ActionListener {
    Adapter_MetaDataManagerFrame p;
    JFrame f;
    Adapter_btn_MetaDataManagerFrame(Adapter_MetaDataManagerFrame _p)
    {
        p = _p;
    }
    public void actionPerformed(ActionEvent e) {
        f = new JFrame();
        p.btnNewButton.setEnabled(false);
        p.button.setEnabled(false);
        p.button_1.setEnabled(false);
        p.dbtable.setEnabled(false);
        f.addWindowListener(new WindowAdapter() { //关闭窗口事件
            @Override
            public void windowClosing(WindowEvent e) {
                p.btnNewButton.setEnabled(true);
                p.button.setEnabled(true);
                p.button_1.setEnabled(true);
                try {
                    p.updateTable();
                }catch(Exception e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "获取数据失败", JOptionPane.ERROR_MESSAGE);
                }
                p.dbtable.updateUI(); //更新表
                p.dbtable.setEnabled(true);
                f.dispose();
                super.windowClosing(e);
            }
        });
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setBounds(100, 100, 329, 234);
        f.setLocation(PermissionManagerFrame.FWidth / 3, PermissionManagerFrame.FHeight / 3);
        f.setTitle("添加用户");


        f.setVisible(true);
    }
}