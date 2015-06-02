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
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;


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
    public static DefaultMutableTreeNode db; //tree1最上层结点
    public static DefaultTreeModel dt; //tree1的模式
    public static String[] tbName;
    public static String[][] columnName;

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

    /*更新文件树*/
    public static void updateTree()
    {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("");
        PKUMetaDataManagement mm = new PKUMetaDataManagement();//mm使用时需要调用Init，结束时需要调用
        mm.Init();
        mm.showUTree();
        tbName = mm.FetchUT();
        columnName = mm.FetchUC();
        mm.CloseCon();

        db = new DefaultMutableTreeNode("test");

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
        dt = new DefaultTreeModel(root);
        tree = new JTree(dt);
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

        setTitle("透明网管系统——元数据管理");
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
        int level = selectedNode.getLevel();//3-列层，2-表层，1-数据库层
        if(level == 1) {
            fetchDBTable();
            dbtable.setModel(new DefaultTableModel(data, head));
        }
        else if(level == 2) {
            fetchTBTable();
            tbtable.setModel(new DefaultTableModel(data, head2));
        }
        else if(level == 3) {
            fetchColumnTable();
            columntable.setModel(new DefaultTableModel(data, head3));
        }
    }
    public void fetchDBTable()
    {
        PKUMetaDataManagement mm = new PKUMetaDataManagement();
        mm.Init();
        data = mm.showUTable();
        mm.CloseCon();
    }
    public void updateDBTable()
    {
        fetchDBTable();
        dbtable =  new MyJTable(new DefaultTableModel(data,head));
        if(dbtable.getRowCount() != 0) //表不为空时，设置默认选择第一列
            dbtable.setRowSelectionInterval(0, 0);
        dbtable.setBackground(Color.WHITE);
        dbtable.setBorder(null);
        dbtable.setRowHeight(30);
        dbtable.setEnabled(true);
        dbtable.updateUI();
    }
    public void fetchTBTable()
    {
        PKUMetaDataManagement mm = new PKUMetaDataManagement();
        mm.Init();
        String[][] temp = mm.showUTable();
        int UID = Integer.valueOf(temp[selectedNode.getParent().getIndex(selectedNode)][0]);
        data = mm.showUColumn(UID);
        mm.CloseCon();
    }
    public void updateTBTable()
    {
        fetchTBTable();
        tbtable =  new MyJTable(new DefaultTableModel(data,head2));
        if(tbtable.getRowCount() != 0)
            tbtable.setRowSelectionInterval(0, 0);
        tbtable.setBackground(Color.WHITE);
        tbtable.setBorder(null);
        tbtable.setRowHeight(30);
        tbtable.setEnabled(true);
        tbtable.updateUI();
    }
    public void fetchColumnTable()
    {
        PKUMetaDataManagement mm = new PKUMetaDataManagement();
        mm.Init();
        String[][] tmp = mm.showUTable();
        int UID = Integer.valueOf(tmp[selectedNode.getParent().getParent().getIndex(selectedNode.getParent())][0]);
        String[][] temp = mm.showUColumn(UID);
        int UCID = Integer.valueOf(temp[selectedNode.getParent().getIndex(selectedNode)][0]);
        data = mm.showMappingByU(UCID);
        mm.CloseCon();
    }
    public void updateColumnTable()
    {
        fetchColumnTable();
        columntable =  new MyJTable(new DefaultTableModel(data,head3));
        if(columntable.getRowCount() != 0)
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
            int level = selectedNode.getLevel(); //1-数据库层，2-表层，3-列层
            panel.removeAll();
            if (level == 1) {
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
                button.addActionListener(new Adapter_btn2_MetaDataManagerFrame(this));
                panel_1.add(button);

                button_1 = new JButton("删除");
                button_1.setBounds(6, 98, 135, 30);
                panel_1.add(button_1);
                button_1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        button.setEnabled(false);
                        button_1.setEnabled(false);
                        btnNewButton.setEnabled(false);
                        dbtable.setEnabled(false);
                        int index = dbtable.getSelectedRow(); //获取所选结点的Index
                        fetchDBTable(); //获取所选编辑表格的UID
                        int UID = Integer.valueOf(data[index][0]);
                        String tableName = data[index][1];
                        int result = JOptionPane.showConfirmDialog(null, "确定删除表 " + tableName + "？", "提示", JOptionPane.YES_NO_OPTION);
                        if (result == 0) {
                            PKUMetaDataManagement pm = new PKUMetaDataManagement();
                            pm.Init();
                            try {
                                if (pm.deleteUTable(UID)) {
                                    JOptionPane.showMessageDialog(null, "删除成功", "提示", JOptionPane.PLAIN_MESSAGE);
                                    //刷新文件树
                                    DefaultMutableTreeNode n = (DefaultMutableTreeNode) MetaDataManagerFrame.db.getChildAt(index);
                                    MetaDataManagerFrame.db.remove(index);
                                    MetaDataManagerFrame.tree.updateUI();
                                } else
                                    JOptionPane.showMessageDialog(null, "删除失败", "删除失败", JOptionPane.ERROR_MESSAGE);
                            } catch (Exception e1) {
                                JOptionPane.showMessageDialog(null, e1.getMessage(), "删除失败", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        button.setEnabled(true);
                        button_1.setEnabled(true);
                        btnNewButton.setEnabled(true);
                        try {
                            updateTable();
                        } catch (Exception e1) {
                            JOptionPane.showMessageDialog(null, e1.getMessage(), "获取数据失败", JOptionPane.ERROR_MESSAGE);
                        }
                        dbtable.updateUI(); //更新表
                        dbtable.setEnabled(true);

                    }
                });

                JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, scrollPane, panel_1);
                splitPane.setDividerLocation(300);//设置分割线位置
                splitPane.setOneTouchExpandable(true);//设置是否可展开
                splitPane.setDividerSize(10);//设置分隔线宽度的大小，以pixel为计算单位。
                splitPane.setResizeWeight(1);//设置改变大小时上下两部分改变的比例
                panel.setLayout(new CardLayout(1, 1));
                panel.add(splitPane);

            } else if (level == 2) {
                updateTBTable();
                JScrollPane scrollPane = new JScrollPane(tbtable);//文件树包裹在可滚动模板里
            /*按钮区*/
                JPanel panel_1 = new JPanel();
                panel_1.setLayout(null);
                btnNewButton = new JButton("添加");
                btnNewButton.setBounds(6, 16, 135, 30);
                btnNewButton.addActionListener(new Adapter_btn3_MetaDataManagerFrame(this));
                panel_1.add(btnNewButton);
                button = new JButton("编辑");
                button.setBounds(6, 58, 135, 30);
                button.addActionListener(new Adapter_btn4_MetaDataManagerFrame(this));
                panel_1.add(button);
                button_1 = new JButton("删除");
                button_1.setBounds(6, 98, 135, 30);
                button_1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        button.setEnabled(false);
                        button_1.setEnabled(false);
                        btnNewButton.setEnabled(false);
                        tbtable.setEnabled(false);
                        int index = tbtable.getSelectedRow(); //获取所选结点的Index
                        fetchTBTable(); //获取所选编辑列的TID
                        int TID = Integer.valueOf(data[index][0]);
                        String tableName = data[index][1];
                        int result = JOptionPane.showConfirmDialog(null, "确定删除表 " + tableName + "？", "提示", JOptionPane.YES_NO_OPTION);
                        if (result == 0) {
                            PKUMetaDataManagement pm = new PKUMetaDataManagement();
                            pm.Init();
                            try {
                                if (pm.deleteUColumn(TID)) {
                                    JOptionPane.showMessageDialog(null, "删除成功", "提示", JOptionPane.PLAIN_MESSAGE);
                                    //刷新文件树
                                    DefaultMutableTreeNode p = (DefaultMutableTreeNode)MetaDataManagerFrame.tree.getLastSelectedPathComponent();
                                    p.remove(index);
                                    MetaDataManagerFrame.tree.updateUI();
                                } else
                                    JOptionPane.showMessageDialog(null, "删除失败", "删除失败", JOptionPane.ERROR_MESSAGE);
                            } catch (Exception e1) {
                                JOptionPane.showMessageDialog(null, e1.getMessage(), "删除失败", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        button.setEnabled(true);
                        button_1.setEnabled(true);
                        btnNewButton.setEnabled(true);
                        try {
                            updateTable();
                        } catch (Exception e1) {
                            JOptionPane.showMessageDialog(null, e1.getMessage(), "获取数据失败", JOptionPane.ERROR_MESSAGE);
                        }
                        tbtable.updateUI(); //更新表
                        tbtable.setEnabled(true);

                    }
                });

                panel_1.add(button_1);

                JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, scrollPane, panel_1);
                splitPane.setDividerLocation(300);//设置分割线位置
                splitPane.setOneTouchExpandable(true);//设置是否可展开
                splitPane.setDividerSize(10);//设置分隔线宽度的大小，以pixel为计算单位。
                splitPane.setResizeWeight(1);//设置改变大小时上下两部分改变的比例
                panel.setLayout(new CardLayout(1, 1));
                panel.add(splitPane);
            } else if (level == 3) {
                updateColumnTable();
                JScrollPane scrollPane = new JScrollPane(columntable);//文件树包裹在可滚动模板里
            /*按钮区*/
                JPanel panel_1 = new JPanel();
                panel_1.setLayout(null);
                btnNewButton = new JButton("添加");
                btnNewButton.setBounds(6, 16, 135, 30);
                btnNewButton.addActionListener(new Adapter_btn5_MetaDataManagerFrame(this));
                panel_1.add(btnNewButton);
                button = new JButton("编辑");
                button.setBounds(6, 58, 135, 30);
                button.addActionListener(new Adapter_btn6_MetaDataManagerFrame(this));
                panel_1.add(button);
                button_1 = new JButton("删除");
                button_1.setBounds(6, 98, 135, 30);
                button_1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        button.setEnabled(false);
                        button_1.setEnabled(false);
                        btnNewButton.setEnabled(false);
                        columntable.setEnabled(false);
                        int index = columntable.getSelectedRow(); //获取所选结点的Index
                        fetchColumnTable(); //获取所选编辑列的TID
                        int UCID = Integer.valueOf(data[index][0]);
                        int result = JOptionPane.showConfirmDialog(null, "确定删除？", "提示", JOptionPane.YES_NO_OPTION);
                        if (result == 0) {
                            PKUMetaDataManagement pm = new PKUMetaDataManagement();
                            pm.Init();
                            try {
                                if (pm.deleteMap(UCID)) {
                                    JOptionPane.showMessageDialog(null, "删除成功", "提示", JOptionPane.PLAIN_MESSAGE);
                                } else
                                    JOptionPane.showMessageDialog(null, "删除失败", "删除失败", JOptionPane.ERROR_MESSAGE);
                            } catch (Exception e1) {
                                JOptionPane.showMessageDialog(null, e1.getMessage(), "删除失败", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        button.setEnabled(true);
                        button_1.setEnabled(true);
                        btnNewButton.setEnabled(true);
                        try {
                            updateTable();
                        } catch (Exception e1) {
                            JOptionPane.showMessageDialog(null, e1.getMessage(), "获取数据失败", JOptionPane.ERROR_MESSAGE);
                        }
                        columntable.updateUI(); //更新表
                        columntable.setEnabled(true);

                    }
                });

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
        if(dbtable.getRowCount() != 0)
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
        if(tbtable.getRowCount() != 0)
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
        if(columntable.getRowCount() != 0)
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
            int level = selectedNode.getLevel(); //1-数据源层，2-数据库层，3-表层，4-列层
            panel.removeAll();
            if(level == 1){
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
            else if (level == 2) {
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
            } else if (level == 3) {
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
            } else if (level == 4) {
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

/*界面1，第一层，添加按钮*/
class Adapter_btn_MetaDataManagerFrame implements ActionListener {
    Adapter_MetaDataManagerFrame p;
    JFrame f;
    JTextField textField;
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
        f.setBounds(100, 100, 329, 164);
        f.setLocation(PermissionManagerFrame.FWidth / 3, PermissionManagerFrame.FHeight / 3);
        f.setTitle("添加表");

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(16, 16, 297, 58);
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setLayout(null);

        JLabel label = new JLabel("表名：");
        label.setBounds(25, 15, 65, 26);
        panel.add(label);
        textField = new JTextField();
        textField.setBounds(90, 9, 160, 38);
        panel.add(textField);
        textField.setColumns(10);
        contentPane.add(panel);

        JButton button_1 = new JButton("取消");
        button_1.setBounds(188, 90, 117, 29);
        button_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p.btnNewButton.setEnabled(true);
                p.button.setEnabled(true);
                p.button_1.setEnabled(true);
                p.dbtable.setEnabled(true);
                f.dispose();
            }
        });
        contentPane.add(button_1);

        JButton button = new JButton("确认");
        button.setBounds(26, 90, 117, 29);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tableName = textField.getText();
                PKUMetaDataManagement pm = new PKUMetaDataManagement();
                pm.Init();
                try {
                    if (pm.addUT(tableName, "1")) {
                        JOptionPane.showMessageDialog(null, "添加成功", "添加成功", JOptionPane.PLAIN_MESSAGE);
                        //刷新表格
                        p.btnNewButton.setEnabled(true);
                        p.button.setEnabled(true);
                        p.button_1.setEnabled(true);
                        p.dbtable.setEnabled(true);
                        try {
                            p.updateTable();
                        }catch(Exception e1) {
                            JOptionPane.showMessageDialog(null, e1.getMessage(), "获取数据失败", JOptionPane.ERROR_MESSAGE);
                        }
                        p.dbtable.updateUI(); //更新表
                        p.dbtable.setEnabled(true);
                        //刷新文件树
                        DefaultMutableTreeNode n = new DefaultMutableTreeNode(tableName);
                        MetaDataManagerFrame.db.add(n);
                        MetaDataManagerFrame.tree.updateUI();
                        f.dispose();
                    } else
                        JOptionPane.showMessageDialog(null, "添加失败", "添加失败", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "添加失败", JOptionPane.ERROR_MESSAGE);
                }
                pm.CloseCon();
            }

        });
        contentPane.add(button);
        f.setContentPane(contentPane);
        f.setVisible(true);
    }
}

/*界面1，第一层，编辑按钮*/
class Adapter_btn2_MetaDataManagerFrame implements ActionListener {
    Adapter_MetaDataManagerFrame p;
    JFrame f;
    JTextField textField;
    int UID;
    Adapter_btn2_MetaDataManagerFrame(Adapter_MetaDataManagerFrame _p)
    {
        p = _p;
    }
    public void actionPerformed(ActionEvent e) {
        if(p.dbtable.getRowCount() == 0)
            JOptionPane.showMessageDialog(null, "无可编辑行", "编辑失败", JOptionPane.ERROR_MESSAGE);
        else{
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
            f.setBounds(100, 100, 329, 164);
            f.setLocation(PermissionManagerFrame.FWidth / 3, PermissionManagerFrame.FHeight / 3);
            f.setTitle("编辑表");

            JPanel contentPane = new JPanel();
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
            contentPane.setLayout(null);

            JPanel panel = new JPanel();
            panel.setBounds(16, 16, 297, 58);
            panel.setBorder(BorderFactory.createEtchedBorder());
            panel.setLayout(null);

            JLabel label = new JLabel("表名：");
            label.setBounds(25, 15, 65, 26);
            panel.add(label);
            textField = new JTextField();
            textField.setBounds(90, 9, 160, 38);
            panel.add(textField);
            textField.setColumns(10);
            p.fetchDBTable(); //获取所选编辑表格的UID和表名
            UID =Integer.valueOf(p.data[p.dbtable.getSelectedRow()][0]);
            textField.setText(p.data[p.dbtable.getSelectedRow()][1]); //获取原本表名
            contentPane.add(panel);

            JButton button_1 = new JButton("取消");
            button_1.setBounds(188, 90, 117, 29);
            button_1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    p.btnNewButton.setEnabled(true);
                    p.button.setEnabled(true);
                    p.button_1.setEnabled(true);
                    p.dbtable.setEnabled(true);
                    f.dispose();
                }
            });
            contentPane.add(button_1);

            JButton button = new JButton("确认");
            button.setBounds(26, 90, 117, 29);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String tableName = textField.getText();
                    int index = p.dbtable.getSelectedRow(); //获取所选结点的Index
                    PKUMetaDataManagement pm = new PKUMetaDataManagement();

                    pm.Init();
                    try {
                        if (pm.updateUTable_Name(UID, tableName)) {
                            JOptionPane.showMessageDialog(null, "编辑成功", "编辑成功", JOptionPane.PLAIN_MESSAGE);
                            //刷新表格
                            p.btnNewButton.setEnabled(true);
                            p.button.setEnabled(true);
                            p.button_1.setEnabled(true);
                            p.dbtable.setEnabled(true);
                            try {
                                p.updateTable();
                            }catch(Exception e1) {
                                JOptionPane.showMessageDialog(null, e1.getMessage(), "获取数据失败", JOptionPane.ERROR_MESSAGE);
                            }
                            p.dbtable.updateUI(); //更新表
                            p.dbtable.setEnabled(true);
                            //刷新文件树
                            DefaultMutableTreeNode n = (DefaultMutableTreeNode)MetaDataManagerFrame.db.getChildAt(index);
                            n.setUserObject(tableName);
                            MetaDataManagerFrame.tree.updateUI();
                            f.dispose();
                        } else
                            JOptionPane.showMessageDialog(null, "编辑失败", "编辑失败", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "编辑失败", JOptionPane.ERROR_MESSAGE);
                    }
                    pm.CloseCon();
                }

            });
            contentPane.add(button);
            f.setContentPane(contentPane);
            f.setVisible(true);
        }
    }
}

/*界面1，第二层，添加按钮*/
class Adapter_btn3_MetaDataManagerFrame implements ActionListener {
    Adapter_MetaDataManagerFrame p;
    JFrame f;
    JTextField textField;
    Adapter_btn3_MetaDataManagerFrame(Adapter_MetaDataManagerFrame _p)
    {
        p = _p;
    }
    public void actionPerformed(ActionEvent e) {
        f = new JFrame();
        p.btnNewButton.setEnabled(false);
        p.button.setEnabled(false);
        p.button_1.setEnabled(false);
        p.tbtable.setEnabled(false);
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
                p.tbtable.updateUI(); //更新表
                p.tbtable.setEnabled(true);
                f.dispose();
                super.windowClosing(e);
            }
        });
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setBounds(100, 100, 329, 164);
        f.setLocation(PermissionManagerFrame.FWidth / 3, PermissionManagerFrame.FHeight / 3);
        f.setTitle("添加列");

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(16, 16, 297, 58);
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setLayout(null);

        JLabel label = new JLabel("列名：");
        label.setBounds(25, 15, 65, 26);
        panel.add(label);
        textField = new JTextField();
        textField.setBounds(90, 9, 160, 38);
        panel.add(textField);
        textField.setColumns(10);
        contentPane.add(panel);

        JButton button_1 = new JButton("取消");
        button_1.setBounds(188, 90, 117, 29);
        button_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p.btnNewButton.setEnabled(true);
                p.button.setEnabled(true);
                p.button_1.setEnabled(true);
                p.tbtable.setEnabled(true);
                f.dispose();
            }
        });
        contentPane.add(button_1);

        JButton button = new JButton("确认");
        button.setBounds(26, 90, 117, 29);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String columnName = textField.getText();
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) MetaDataManagerFrame.tree.getLastSelectedPathComponent();
                int index = MetaDataManagerFrame.db.getIndex(selectedNode); //获取表结点的索引
                System.out.println(index);
                p.fetchDBTable();
                String TID = p.data[index][0]; //获取表ID,即上层的UID
                System.out.println(TID);
                PKUMetaDataManagement pm = new PKUMetaDataManagement();
                pm.Init();
                try {
                    if (pm.addUC(columnName, TID)) {
                        JOptionPane.showMessageDialog(null, "添加成功", "添加成功", JOptionPane.PLAIN_MESSAGE);
                        //刷新表格
                        p.btnNewButton.setEnabled(true);
                        p.button.setEnabled(true);
                        p.button_1.setEnabled(true);
                        p.tbtable.setEnabled(true);
                        try {
                            p.updateTable();
                        }catch(Exception e1) {
                            JOptionPane.showMessageDialog(null, e1.getMessage(), "获取数据失败", JOptionPane.ERROR_MESSAGE);
                        }
                        p.tbtable.updateUI(); //更新表
                        p.tbtable.setEnabled(true);
                        //刷新文件树
                        DefaultMutableTreeNode n = new DefaultMutableTreeNode(columnName);
                        selectedNode.add(n);
                        MetaDataManagerFrame.tree.updateUI();
                        f.dispose();
                    } else
                        JOptionPane.showMessageDialog(null, "添加失败", "添加失败", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "添加失败", JOptionPane.ERROR_MESSAGE);
                }
                pm.CloseCon();
            }

        });
        contentPane.add(button);
        f.setContentPane(contentPane);
        f.setVisible(true);
    }
}

/*界面1，第二层，编辑按钮*/
class Adapter_btn4_MetaDataManagerFrame implements ActionListener {
    Adapter_MetaDataManagerFrame p;
    JFrame f;
    JTextField textField;
    int UID;
    Adapter_btn4_MetaDataManagerFrame(Adapter_MetaDataManagerFrame _p)
    {
        p = _p;
    }
    public void actionPerformed(ActionEvent e) {
        if(p.tbtable.getRowCount() == 0)
            JOptionPane.showMessageDialog(null, "无可编辑行", "编辑失败", JOptionPane.ERROR_MESSAGE);
        else{
            f = new JFrame();
            p.btnNewButton.setEnabled(false);
            p.button.setEnabled(false);
            p.button_1.setEnabled(false);
            p.tbtable.setEnabled(false);
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
                    p.tbtable.updateUI(); //更新表
                    p.tbtable.setEnabled(true);
                    f.dispose();
                    super.windowClosing(e);
                }
            });
            f.setResizable(false);
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.setBounds(100, 100, 329, 164);
            f.setLocation(PermissionManagerFrame.FWidth / 3, PermissionManagerFrame.FHeight / 3);
            f.setTitle("编辑列");

            JPanel contentPane = new JPanel();
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
            contentPane.setLayout(null);

            JPanel panel = new JPanel();
            panel.setBounds(16, 16, 297, 58);
            panel.setBorder(BorderFactory.createEtchedBorder());
            panel.setLayout(null);

            JLabel label = new JLabel("列名：");
            label.setBounds(25, 15, 65, 26);
            panel.add(label);
            textField = new JTextField();
            textField.setBounds(90, 9, 160, 38);
            panel.add(textField);
            textField.setColumns(10);
            p.fetchTBTable(); //获取所选编辑表格的表名
            UID = Integer.valueOf(p.data[p.tbtable.getSelectedRow()][0]);
            textField.setText(p.data[p.tbtable.getSelectedRow()][1]); //获取原本表名
            contentPane.add(panel);

            JButton button_1 = new JButton("取消");
            button_1.setBounds(188, 90, 117, 29);
            button_1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    p.btnNewButton.setEnabled(true);
                    p.button.setEnabled(true);
                    p.button_1.setEnabled(true);
                    p.tbtable.setEnabled(true);
                    f.dispose();
                }
            });
            contentPane.add(button_1);

            JButton button = new JButton("确认");
            button.setBounds(26, 90, 117, 29);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String columnName = textField.getText();
                    int index = p.tbtable.getSelectedRow(); //获取所选结点的Index
                    PKUMetaDataManagement pm = new PKUMetaDataManagement();

                    pm.Init();
                    try {
                        if (pm.updateUColumn_Name(UID, columnName)) {
                            JOptionPane.showMessageDialog(null, "编辑成功", "编辑成功", JOptionPane.PLAIN_MESSAGE);
                            //刷新表格
                            p.btnNewButton.setEnabled(true);
                            p.button.setEnabled(true);
                            p.button_1.setEnabled(true);
                            p.tbtable.setEnabled(true);
                            try {
                                p.updateTable();
                            }catch(Exception e1) {
                                JOptionPane.showMessageDialog(null, e1.getMessage(), "获取数据失败", JOptionPane.ERROR_MESSAGE);
                            }
                            p.tbtable.updateUI(); //更新表
                            p.tbtable.setEnabled(true);
                            //刷新文件树
                            DefaultMutableTreeNode p = (DefaultMutableTreeNode)MetaDataManagerFrame.tree.getLastSelectedPathComponent();
                            DefaultMutableTreeNode n = (DefaultMutableTreeNode)p.getChildAt(index);
                            n.setUserObject(columnName);
                            MetaDataManagerFrame.tree.updateUI();
                            f.dispose();
                        } else
                            JOptionPane.showMessageDialog(null, "编辑失败", "编辑失败", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "编辑失败", JOptionPane.ERROR_MESSAGE);
                    }
                    pm.CloseCon();
                }

            });
            contentPane.add(button);
            f.setContentPane(contentPane);
            f.setVisible(true);
        }
    }
}

/*界面1，第三层，添加按钮*/
class Adapter_btn5_MetaDataManagerFrame implements ActionListener {
    Adapter_MetaDataManagerFrame p;
    JFrame f;
    JComboBox comboBox;
    int UCID;
    JTextField textField;
    JTextField textField2;
    JTextField textField3;
    int[] LCID;

    Adapter_btn5_MetaDataManagerFrame(Adapter_MetaDataManagerFrame _p)
    {
        p = _p;
    }
    public void actionPerformed(ActionEvent e) {
        f = new JFrame();
        p.btnNewButton.setEnabled(false);
        p.button.setEnabled(false);
        p.button_1.setEnabled(false);
        p.columntable.setEnabled(false);
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
                p.columntable.updateUI(); //更新表
                p.columntable.setEnabled(true);
                f.dispose();
                super.windowClosing(e);
            }
        });
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setBounds(100, 100, 329, 254);
        f.setLocation(PermissionManagerFrame.FWidth / 3, PermissionManagerFrame.FHeight / 3);
        f.setTitle("添加映射");

        PKUMetaDataManagement pm = new PKUMetaDataManagement();
        DefaultMutableTreeNode pa = (DefaultMutableTreeNode)MetaDataManagerFrame.tree.getLastSelectedPathComponent();
        int index = p.columntable.getSelectedRow();
        p.fetchColumnTable();
        UCID = Integer.valueOf(p.data[index][0]);
        System.out.println(index + "-" + UCID);
        pm.Init();
        pm.showNoMapLC(UCID);
        String[] SName = pm.FetchNoMapLSName();
        String[] BName = pm.FetchNoMapLBName();
        String[] TName = pm.FetchNoMapLTName();
        String[] CName = pm.FetchNoMapLCName();
        LCID = pm.FetchNoMapLCID();
        pm.CloseCon();

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(16, 16, 297, 148);
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setLayout(null);

        JLabel label = new JLabel("本地映射：");
        label.setBounds(25, 15, 65, 26);
        panel.add(label);
        comboBox = new JComboBox();
        for(int i = 0; i < SName.length; i++)
        {
            comboBox.addItem(SName[i] + "." + BName[i] + "." + TName[i] + "." + CName[i]);
        }
        comboBox.setBounds(90, 9, 160, 38);
        panel.add(comboBox);

        JLabel label1 = new JLabel("Max ：");
        label1.setBounds(25, 45, 65, 26);
        panel.add(label1);
        textField = new JTextField();
        textField.setBounds(90, 39, 160, 38);
        textField.setColumns(10);
        panel.add(textField);
        JLabel label2 = new JLabel("Min ：");
        label2.setBounds(25, 75, 65, 26);
        panel.add(label2);
        textField2 = new JTextField();
        textField2.setBounds(90, 69, 160, 38);
        textField2.setColumns(10);
        panel.add(textField2);
        JLabel label3 = new JLabel("Location ：");
        label3.setBounds(25, 105, 65, 26);
        panel.add(label3);
        textField3 = new JTextField();
        textField3.setBounds(90, 99, 160, 38);
        textField3.setColumns(10);
        panel.add(textField3);
        contentPane.add(panel);

        JButton button_1 = new JButton("取消");
        button_1.setBounds(188, 180, 117, 29);
        button_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p.btnNewButton.setEnabled(true);
                p.button.setEnabled(true);
                p.button_1.setEnabled(true);
                p.columntable.setEnabled(true);
                f.dispose();
            }
        });
        contentPane.add(button_1);

        JButton button = new JButton("确认");
        button.setBounds(26, 180, 117, 29);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int lcid = LCID[comboBox.getSelectedIndex()];
                int max = Integer.valueOf(textField.getText());
                int min = Integer.valueOf(textField2.getText());
                int location = Integer.valueOf(textField3.getText());
                PKUMetaDataManagement pm = new PKUMetaDataManagement();
                pm.Init();
                try {
                    if (pm.addMap(UCID, lcid, max, min, location)) {
                        JOptionPane.showMessageDialog(null, "添加成功", "添加成功", JOptionPane.PLAIN_MESSAGE);
                        //刷新表格
                        p.btnNewButton.setEnabled(true);
                        p.button.setEnabled(true);
                        p.button_1.setEnabled(true);
                        p.columntable.setEnabled(true);
                        try {
                            p.updateTable();
                        }catch(Exception e1) {
                            JOptionPane.showMessageDialog(null, e1.getMessage(), "获取数据失败", JOptionPane.ERROR_MESSAGE);
                        }
                        p.columntable.updateUI(); //更新表
                        p.columntable.setEnabled(true);
                        f.dispose();
                    } else
                        JOptionPane.showMessageDialog(null, "添加失败", "添加失败", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "添加失败", JOptionPane.ERROR_MESSAGE);
                }
                pm.CloseCon();
            }

        });
        contentPane.add(button);
        f.setContentPane(contentPane);
        f.setVisible(true);
    }
}

/*界面1，第三层，编辑按钮*/
class Adapter_btn6_MetaDataManagerFrame implements ActionListener {
    Adapter_MetaDataManagerFrame p;
    JFrame f;
    JComboBox comboBox;
    int UCID;
    JTextField textField;
    JTextField textField2;
    JTextField textField3;
    int max;
    int min;
    int location;
    Adapter_btn6_MetaDataManagerFrame(Adapter_MetaDataManagerFrame _p)
    {
        p = _p;
    }
    public void actionPerformed(ActionEvent e) {
        if(p.columntable.getRowCount() == 0)
            JOptionPane.showMessageDialog(null, "无可编辑行", "编辑失败", JOptionPane.ERROR_MESSAGE);
        else{
            f = new JFrame();
            p.btnNewButton.setEnabled(false);
            p.button.setEnabled(false);
            p.button_1.setEnabled(false);
            p.columntable.setEnabled(false);
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
                    p.columntable.updateUI(); //更新表
                    p.columntable.setEnabled(true);
                    f.dispose();
                    super.windowClosing(e);
                }
            });
            f.setResizable(false);
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.setBounds(100, 100, 329, 254);
            f.setLocation(PermissionManagerFrame.FWidth / 3, PermissionManagerFrame.FHeight / 3);
            f.setTitle("编辑映射");

            PKUMetaDataManagement pm = new PKUMetaDataManagement();
            DefaultMutableTreeNode pa = (DefaultMutableTreeNode)MetaDataManagerFrame.tree.getLastSelectedPathComponent();
            int index = pa.getParent().getIndex(pa);
            pm.Init();
            String[][] temp = pm.showUTable();
            int UID = Integer.valueOf(temp[p.selectedNode.getParent().getParent().getIndex(p.selectedNode.getParent())][0]);
            p.data = pm.showUColumn(UID);
            UCID = Integer.valueOf(p.data[index][0]);
            max = Integer.valueOf(p.data[index][2]);
            min = Integer.valueOf(p.data[index][3]);
            location = Integer.valueOf(p.data[index][4]);
            System.out.println(index + "-" + UCID);

            pm.CloseCon();

            JPanel contentPane = new JPanel();
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
            contentPane.setLayout(null);

            JPanel panel = new JPanel();
            panel.setBounds(16, 16, 297, 148);
            panel.setBorder(BorderFactory.createEtchedBorder());
            panel.setLayout(null);

            JLabel label = new JLabel("UCID：");
            label.setBounds(25, 15, 65, 26);
            panel.add(label);
            JTextField textField0 = new JTextField();
            textField0.setBounds(90, 39, 160, 38);
            textField0.setColumns(10);
            textField0.setText(UCID+"");
            textField0.setEditable(false);
            panel.add(textField0);
            JLabel label1 = new JLabel("Max ：");
            label1.setBounds(25, 45, 65, 26);
            panel.add(label1);
            textField = new JTextField();
            textField.setBounds(90, 39, 160, 38);
            textField.setColumns(10);
            textField.setText(max+"");
            panel.add(textField);
            JLabel label2 = new JLabel("Min ：");
            label2.setBounds(25, 75, 65, 26);
            panel.add(label2);
            textField2 = new JTextField();
            textField2.setBounds(90, 69, 160, 38);
            textField2.setColumns(10);
            textField2.setText(min+"");
            panel.add(textField2);
            JLabel label3 = new JLabel("Location ：");
            label3.setBounds(25, 105, 65, 26);
            panel.add(label3);
            textField3 = new JTextField();
            textField3.setBounds(90, 99, 160, 38);
            textField3.setColumns(10);
            textField3.setText(location+"");
            panel.add(textField3);
            contentPane.add(panel);

            JButton button_1 = new JButton("取消");
            button_1.setBounds(188, 180, 117, 29);
            button_1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    p.btnNewButton.setEnabled(true);
                    p.button.setEnabled(true);
                    p.button_1.setEnabled(true);
                    p.columntable.setEnabled(true);
                    f.dispose();
                }
            });
            contentPane.add(button_1);

            JButton button = new JButton("确认");
            button.setBounds(26, 180, 117, 29);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int max = Integer.valueOf(textField.getText());
                    int min = Integer.valueOf(textField2.getText());
                    int location = Integer.valueOf(textField3.getText());
                    PKUMetaDataManagement pm = new PKUMetaDataManagement();

                    pm.Init();
                    try {
                        if (pm.updateMap(UCID, max, min, location)) {
                            JOptionPane.showMessageDialog(null, "编辑成功", "编辑成功", JOptionPane.PLAIN_MESSAGE);
                            //刷新表格
                            p.btnNewButton.setEnabled(true);
                            p.button.setEnabled(true);
                            p.button_1.setEnabled(true);
                            p.columntable.setEnabled(true);
                            try {
                                p.updateTable();
                            }catch(Exception e1) {
                                JOptionPane.showMessageDialog(null, e1.getMessage(), "获取数据失败", JOptionPane.ERROR_MESSAGE);
                            }
                            p.columntable.updateUI(); //更新表
                            p.columntable.setEnabled(true);
                            f.dispose();
                        } else
                            JOptionPane.showMessageDialog(null, "编辑失败", "编辑失败", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "编辑失败", JOptionPane.ERROR_MESSAGE);
                    }
                    pm.CloseCon();
                }

            });
            contentPane.add(button);
            f.setContentPane(contentPane);
            f.setVisible(true);
        }
    }
}
