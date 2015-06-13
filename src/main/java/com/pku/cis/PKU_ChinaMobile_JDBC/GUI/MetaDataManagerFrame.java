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
    public static JTree tree;
    public static JPanel panel;
    public static DefaultMutableTreeNode db; //tree1最上层结点
    public static DefaultTreeModel dt; //tree1的模式
    public static String[] tbName;
    public static String[][] columnName;

    /*标签页2的组件*/
    public static JTree tree2;
    public static JPanel panel2;
    public static DefaultMutableTreeNode root2; //tree1最上层结点
    public static String[] datasourceName2;
    public static String[][] dbName2;
    public static String[][][] tbName2;
    public static String[][][][] columnName2;

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
        root2 = new DefaultMutableTreeNode("");
        PKUMetaDataManagement mm = new PKUMetaDataManagement();//mm使用时需要调用Init，结束时需要调用
        mm.Init();
        mm.showLTree();
        datasourceName2 = mm.FetchLS(); //返回数据源数组
        dbName2 = mm.FetchLB(); //返回数据库名数组
        tbName2 = mm.FetchLT();//返回表名数组
        columnName2 = mm.FetchLC(); //返回列名数组
        mm.CloseCon();

        DefaultMutableTreeNode[] level1 = new DefaultMutableTreeNode[datasourceName2.length];
        for(int i = 0; i < datasourceName2.length; i++)
        {
            level1[i] = new DefaultMutableTreeNode(datasourceName2[i]);
            DefaultMutableTreeNode[] level2 = new DefaultMutableTreeNode[dbName2[i].length];
            for(int j = 0; j < dbName2[i].length; j++)
            {
                level2[j] = new DefaultMutableTreeNode(dbName2[i][j]);
                DefaultMutableTreeNode[] level3 = new DefaultMutableTreeNode[tbName2[i][j].length];
                for(int k = 0; k < tbName2[i][j].length; k++)
                {
                    level3[k] = new DefaultMutableTreeNode(tbName2[i][j][k]);
                    DefaultMutableTreeNode[] level4 = new DefaultMutableTreeNode[columnName2[i][j][k].length];
                    for(int l = 0; l < columnName2[i][j][k].length; l++)
                    {
                        level4[l] = new DefaultMutableTreeNode(columnName2[i][j][k][l]);
                        level3[k].add(level4[l]);
                    }
                    level2[j].add(level3[k]);
                }
                level1[i].add(level2[j]);
            }
            root2.add(level1[i]);

        }

        tree2 = new JTree(root2);
        tree2.setRootVisible(false);
        tree2.setShowsRootHandles(true);
    }
    /**
     * Create the frame.
     */
    public MetaDataManagerFrame() {

        setTitle("透明网关系统——元数据管理");
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
        panel2 = new JPanel();
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
                        if(index == -1) {
                            JOptionPane.showMessageDialog(null, "请选择一行", "删除失败", JOptionPane.ERROR_MESSAGE);
                        }
                        else {
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
                        }}
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
                        if(index == -1) {
                            JOptionPane.showMessageDialog(null, "请选择一行", "删除失败", JOptionPane.ERROR_MESSAGE);
                        }
                        else {
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
                        }}
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
                        if(index == -1) {
                            JOptionPane.showMessageDialog(null, "请选择一行", "删除失败", JOptionPane.ERROR_MESSAGE);
                        }
                        else {
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
                        }}
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
    JButton btnNewButton;
    JButton button;
    JButton button_1;
    public static String data[][];
    public static String head0[] = {"UID","DBName","DSID"};
    public static String head[] = {"UID","TableName","DBID"};
    public static String head2[] = {"UID","ColumnName","ColumnType","TableID"};
    public static String head3[] = {"UID","UCID","LCID","Max","Min","Location"};

    Adapter2_MetaDataManagerFrame(JTree _tree, JPanel _panel)
    {
        tree = _tree;
        panel = _panel;

    }
    public void updateTable()
    {
        int level = selectedNode.getLevel();//4-列层，3-表层，2-数据库层, 1-数据源层
        if(level == 1) {
            fetchDSTable();
            dstable.setModel(new DefaultTableModel(data, head0));
        }
        else if(level == 2) {
            fetchDBTable();
            dbtable.setModel(new DefaultTableModel(data, head));
        }
        else if(level == 3) {
            fetchTBTable();
            tbtable.setModel(new DefaultTableModel(data, head2));
        }
        else if(level == 4) {
            fetchColumnTable();
            columntable.setModel(new DefaultTableModel(data, head3));
        }
    }
    public void fetchDSTable()
    {
        PKUMetaDataManagement mm = new PKUMetaDataManagement();
        int ID = selectedNode.getParent().getIndex(selectedNode) + 1;
        mm.Init();
        data = mm.showLDB(ID);
        mm.CloseCon();
    }
    public void updateDSTable()
    {
        fetchDSTable();
        dstable =  new MyJTable(new DefaultTableModel(data,head0));
        dstable.setRowSelectionInterval(0, 0);
        dstable.setBackground(Color.WHITE);
        dstable.setBorder(null);
        dstable.setRowHeight(30);
        dstable.setEnabled(true);
        dstable.updateUI();
    }
    public void fetchDBTable()
    {
        PKUMetaDataManagement mm = new PKUMetaDataManagement();
        int ID = selectedNode.getParent().getParent().getIndex(selectedNode.getParent()) + 1;
        mm.Init();
        String[][] temp = mm.showLDB(ID);
        int DBID = Integer.valueOf(temp[selectedNode.getParent().getIndex(selectedNode)][0]);
        data = mm.showLTable(DBID);
        mm.CloseCon();
    }
    public void updateDBTable()
    {
        fetchDBTable();
        dbtable =  new MyJTable(new DefaultTableModel(data,head));
        if(dbtable.getRowCount() != 0)
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
        int ID = selectedNode.getParent().getParent().getParent().
                getIndex(selectedNode.getParent().getParent()) + 1;
        String[][] temp = mm.showLDB(ID);
        int DBID = Integer.valueOf(temp[selectedNode.getParent().getParent().getIndex(selectedNode.getParent())][0]);
        String[][] tmp = mm.showLTable(DBID);
        int TBID = Integer.valueOf(tmp[selectedNode.getParent().getIndex(selectedNode)][0]);
        data = mm.showLColumn(TBID);
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
            int level = selectedNode.getLevel(); //1-数据源层，2-数据库层，3-表层，4-列层
            panel.removeAll();
            if(level == 1){
                updateDSTable();
                JScrollPane scrollPane = new JScrollPane(dstable);//文件树包裹在可滚动模板里
            /*按钮区*/
                JPanel panel_1 = new JPanel();
                panel_1.setLayout(null);
                btnNewButton = new JButton("添加");
                btnNewButton.setBounds(6, 16, 135, 30);
                btnNewButton.addActionListener(new Adapter_btn7_MetaDataManagerFrame(this));
                panel_1.add(btnNewButton);
                button = new JButton("编辑");
                button.setBounds(6, 58, 135, 30);
                button.addActionListener(new Adapter_btn8_MetaDataManagerFrame(this));
                panel_1.add(button);
                button_1 = new JButton("删除");
                button_1.setBounds(6, 98, 135, 30);
                button_1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        button.setEnabled(false);
                        button_1.setEnabled(false);
                        btnNewButton.setEnabled(false);
                        dstable.setEnabled(false);
                        int index = dstable.getSelectedRow(); //获取所选结点的Index
                        if(index == -1) {
                            JOptionPane.showMessageDialog(null, "请选择一行", "删除失败", JOptionPane.ERROR_MESSAGE);
                        }
                        else {
                            fetchDSTable(); //获取所选编辑表格的UID
                            int UID = Integer.valueOf(data[index][0]);
                            String dbName = data[index][1];
                            int result = JOptionPane.showConfirmDialog(null, "确定删除表 " + dbName + "？", "提示", JOptionPane.YES_NO_OPTION);
                            if (result == 0) {
                                PKUMetaDataManagement pm = new PKUMetaDataManagement();
                                pm.Init();
                                try {
                                    if (pm.deleteLDB(UID)) {
                                        JOptionPane.showMessageDialog(null, "删除成功", "提示", JOptionPane.PLAIN_MESSAGE);
                                        //刷新文件树
                                        DefaultMutableTreeNode n = (DefaultMutableTreeNode) MetaDataManagerFrame.tree2.getLastSelectedPathComponent();
                                        n.remove(index);
                                        MetaDataManagerFrame.tree2.updateUI();
                                    } else
                                        JOptionPane.showMessageDialog(null, "删除失败", "删除失败", JOptionPane.ERROR_MESSAGE);
                                } catch (Exception e1) {
                                    JOptionPane.showMessageDialog(null, e1.getMessage(), "删除失败", JOptionPane.ERROR_MESSAGE);
                                }
                            }}
                        button.setEnabled(true);
                        button_1.setEnabled(true);
                        btnNewButton.setEnabled(true);
                        try {
                            updateTable();
                        } catch (Exception e1) {
                            JOptionPane.showMessageDialog(null, e1.getMessage(), "获取数据失败", JOptionPane.ERROR_MESSAGE);
                        }
                        dstable.updateUI(); //更新表
                        dstable.setEnabled(true);

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
            else if (level == 2) {
                updateDBTable();
                JScrollPane scrollPane = new JScrollPane(dbtable);//文件树包裹在可滚动模板里
            /*按钮区*/
                JPanel panel_1 = new JPanel();
                panel_1.setLayout(null);
                btnNewButton = new JButton("添加");
                btnNewButton.setBounds(6, 16, 135, 30);
                btnNewButton.addActionListener(new Adapter_btn9_MetaDataManagerFrame(this));
                panel_1.add(btnNewButton);
                button = new JButton("编辑");
                button.setBounds(6, 58, 135, 30);
                button.addActionListener(new Adapter_btn10_MetaDataManagerFrame(this));
                panel_1.add(button);
                button_1 = new JButton("删除");
                button_1.setBounds(6, 98, 135, 30);
                button_1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        button.setEnabled(false);
                        button_1.setEnabled(false);
                        btnNewButton.setEnabled(false);
                        dbtable.setEnabled(false);
                        int index = dbtable.getSelectedRow(); //获取所选结点的Index
                        if(index == -1) {
                            JOptionPane.showMessageDialog(null, "请选择一行", "删除失败", JOptionPane.ERROR_MESSAGE);
                        }
                        else {
                            fetchDBTable();
                            int UID = Integer.valueOf(data[index][0]);
                            String tableName = data[index][1];
                            int result = JOptionPane.showConfirmDialog(null, "确定删除表 " + tableName + "？", "提示", JOptionPane.YES_NO_OPTION);
                            if (result == 0) {
                                PKUMetaDataManagement pm = new PKUMetaDataManagement();
                                pm.Init();
                                try {
                                    if (pm.deleteLTable(UID)) {
                                        JOptionPane.showMessageDialog(null, "删除成功", "提示", JOptionPane.PLAIN_MESSAGE);
                                        //刷新文件树
                                        DefaultMutableTreeNode n = (DefaultMutableTreeNode) MetaDataManagerFrame.tree2.getLastSelectedPathComponent();
                                        n.remove(index);
                                        MetaDataManagerFrame.tree2.updateUI();
                                    } else
                                        JOptionPane.showMessageDialog(null, "删除失败", "删除失败", JOptionPane.ERROR_MESSAGE);
                                } catch (Exception e1) {
                                    JOptionPane.showMessageDialog(null, e1.getMessage(), "删除失败", JOptionPane.ERROR_MESSAGE);
                                }
                            }}
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
                btnNewButton = new JButton("添加");
                btnNewButton.setBounds(6, 16, 135, 30);
                btnNewButton.addActionListener(new Adapter_btn11_MetaDataManagerFrame(this));
                panel_1.add(btnNewButton);
                button = new JButton("编辑");
                button.setBounds(6, 58, 135, 30);
                button.addActionListener(new Adapter_btn12_MetaDataManagerFrame(this));
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
                        if(index == -1) {
                            JOptionPane.showMessageDialog(null, "请选择一行", "删除失败", JOptionPane.ERROR_MESSAGE);
                        }
                        else {
                            fetchTBTable();
                            int UID = Integer.valueOf(data[index][0]);
                            String columnName = data[index][1];
                            int result = JOptionPane.showConfirmDialog(null, "确定删除表 " + columnName + "？", "提示", JOptionPane.YES_NO_OPTION);
                            if (result == 0) {
                                PKUMetaDataManagement pm = new PKUMetaDataManagement();
                                pm.Init();
                                try {
                                    if (pm.deleteLColumn(UID)) {
                                        JOptionPane.showMessageDialog(null, "删除成功", "提示", JOptionPane.PLAIN_MESSAGE);
                                        //刷新文件树
                                        DefaultMutableTreeNode n = (DefaultMutableTreeNode) MetaDataManagerFrame.tree2.getLastSelectedPathComponent();
                                        n.remove(index);
                                        MetaDataManagerFrame.tree2.updateUI();
                                    } else
                                        JOptionPane.showMessageDialog(null, "删除失败", "删除失败", JOptionPane.ERROR_MESSAGE);
                                } catch (Exception e1) {
                                    JOptionPane.showMessageDialog(null, e1.getMessage(), "删除失败", JOptionPane.ERROR_MESSAGE);
                                }
                            }}
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
            } else if (level == 4) {
                updateColumnTable();
                JScrollPane scrollPane = new JScrollPane(columntable);//文件树包裹在可滚动模板里
            /*按钮区*/
                JPanel panel_1 = new JPanel();
                panel_1.setLayout(null);
                btnNewButton = new JButton("添加");
                btnNewButton.setBounds(6, 16, 135, 30);
                btnNewButton.addActionListener(new Adapter_btn13_MetaDataManagerFrame(this));
                panel_1.add(btnNewButton);
                button = new JButton("编辑");
                button.setBounds(6, 58, 135, 30);
                button.addActionListener(new Adapter_btn14_MetaDataManagerFrame(this));
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
                        if(index == -1) {
                            JOptionPane.showMessageDialog(null, "请选择一行", "删除失败", JOptionPane.ERROR_MESSAGE);
                        }
                        else {
                            fetchColumnTable();
                            int UID = Integer.valueOf(data[index][0]);
                            int result = JOptionPane.showConfirmDialog(null, "确定删除？", "提示", JOptionPane.YES_NO_OPTION);
                            if (result == 0) {
                                PKUMetaDataManagement pm = new PKUMetaDataManagement();
                                pm.Init();
                                try {
                                    if (pm.deleteMap(UID)) {
                                        JOptionPane.showMessageDialog(null, "删除成功", "提示", JOptionPane.PLAIN_MESSAGE);
                                    } else
                                        JOptionPane.showMessageDialog(null, "删除失败", "删除失败", JOptionPane.ERROR_MESSAGE);
                                } catch (Exception e1) {
                                    JOptionPane.showMessageDialog(null, e1.getMessage(), "删除失败", JOptionPane.ERROR_MESSAGE);
                                }
                            }}
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
        f.setLocation(MetaDataManagerFrame.FWidth / 3, MetaDataManagerFrame.FHeight / 3);
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
        else if(p.dbtable.getSelectedRow() == -1)
            JOptionPane.showMessageDialog(null, "请选择一行", "编辑失败", JOptionPane.ERROR_MESSAGE);
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
            f.setLocation(MetaDataManagerFrame.FWidth / 3, MetaDataManagerFrame.FHeight / 3);
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
        f.setLocation(MetaDataManagerFrame.FWidth / 3, MetaDataManagerFrame.FHeight / 3);
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
        else if(p.tbtable.getSelectedRow() == -1)
            JOptionPane.showMessageDialog(null, "请选择一行", "编辑失败", JOptionPane.ERROR_MESSAGE);
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
            f.setLocation(MetaDataManagerFrame.FWidth / 3, MetaDataManagerFrame.FHeight / 3);
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
        f.setBounds(100, 100, 430, 300);
        f.setLocation(MetaDataManagerFrame.FWidth / 3, MetaDataManagerFrame.FHeight / 3);
        f.setTitle("添加映射");

        PKUMetaDataManagement pm = new PKUMetaDataManagement();
        DefaultMutableTreeNode pa = (DefaultMutableTreeNode)MetaDataManagerFrame.tree.getLastSelectedPathComponent();
        int index = pa.getParent().getIndex(pa); //获取当前选择列结点的索引值
        pm.Init();

        String[][] temp = pm.showUTable(); //获取列数组，不能直接使用fetchcolumntable，是因为当前结点是列结点而不是表姐点
        int TID = Integer.valueOf(temp[p.selectedNode.getParent().getParent().getIndex(p.selectedNode.getParent())][0]);
        p.data = pm.showUColumn(TID);

        //不能直接通过选定行来找UCID，因为表可能为空
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
        panel.setBounds(16, 16, 400, 185);
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setLayout(null);

        JLabel label = new JLabel("本地映射：");
        label.setBounds(25, 13, 65, 26);
        panel.add(label);
        comboBox = new JComboBox();
        for(int i = 0; i < SName.length; i++)
        {
            comboBox.addItem(SName[i] + "." + BName[i] + "." + TName[i] + "." + CName[i]);
        }
        comboBox.setBounds(90, 7, 280, 30);
        panel.add(comboBox);

        JLabel label1 = new JLabel("Max ：");
        label1.setBounds(25, 53, 65, 26);
        panel.add(label1);
        textField = new JTextField();
        textField.setBounds(90, 47,280, 30);
        textField.setColumns(10);
        panel.add(textField);
        JLabel label2 = new JLabel("Min ：");
        label2.setBounds(25, 93, 65, 26);
        panel.add(label2);
        textField2 = new JTextField();
        textField2.setBounds(90, 87, 280, 30);
        textField2.setColumns(10);
        panel.add(textField2);
        JLabel label3 = new JLabel("Location ：");
        label3.setBounds(25, 133, 65, 26);
        panel.add(label3);
        textField3 = new JTextField();
        textField3.setBounds(90, 127, 280, 30);
        textField3.setColumns(10);
        panel.add(textField3);
        contentPane.add(panel);

        JButton button_1 = new JButton("取消");
        button_1.setBounds(228, 220, 117, 29);
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
        button.setBounds(26, 220, 117, 29);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int lcid = LCID[comboBox.getSelectedIndex()];
                String max = textField.getText();
                String min = textField2.getText();
                String location = textField3.getText();
                if(max.equals(""))
                    max = null;
                if(min.equals(""))
                    min = null;
                if(location.equals(""))
                    location = null;
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
    int UID;
    JTextField textField;
    JTextField textField2;
    JTextField textField3;
    String max;
    String min;
    String location;
    Adapter_btn6_MetaDataManagerFrame(Adapter_MetaDataManagerFrame _p)
    {
        p = _p;
    }
    public void actionPerformed(ActionEvent e) {
        if(p.columntable.getRowCount() == 0)
            JOptionPane.showMessageDialog(null, "无可编辑行", "编辑失败", JOptionPane.ERROR_MESSAGE);
        else if(p.columntable.getSelectedRow() == -1)
            JOptionPane.showMessageDialog(null, "请选择一行", "编辑失败", JOptionPane.ERROR_MESSAGE);
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
            f.setLocation(MetaDataManagerFrame.FWidth / 3, MetaDataManagerFrame.FHeight / 3);
            f.setTitle("编辑映射");

            int index = p.columntable.getSelectedRow();
            p.fetchColumnTable();
            UID = Integer.valueOf(p.data[index][0]);
            max = p.data[index][3];
            min = p.data[index][4];
            location = p.data[index][5];
            System.out.println(index + "-" + UID);

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
            textField0.setBounds(90, 11, 160, 28);
            textField0.setColumns(10);
            textField0.setText(UID+"");
            textField0.setEditable(false);
            panel.add(textField0);
            JLabel label1 = new JLabel("Max ：");
            label1.setBounds(25, 45, 65, 26);
            panel.add(label1);
            textField = new JTextField();
            textField.setBounds(90, 42, 160, 28);
            textField.setColumns(10);
            textField.setText(max+"");
            panel.add(textField);
            JLabel label2 = new JLabel("Min ：");
            label2.setBounds(25, 75, 65, 26);
            panel.add(label2);
            textField2 = new JTextField();
            textField2.setBounds(90, 73, 160, 28);
            textField2.setColumns(10);
            textField2.setText(min+"");
            panel.add(textField2);
            JLabel label3 = new JLabel("Location ：");
            label3.setBounds(25, 105, 65, 26);
            panel.add(label3);
            textField3 = new JTextField();
            textField3.setBounds(90, 104, 160, 28);
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
                    String max = textField.getText();
                    String min = textField2.getText();
                    String location = textField3.getText();
                    PKUMetaDataManagement pm = new PKUMetaDataManagement();
                    if(max.equals(""))
                        max = null;
                    if(min.equals(""))
                        min = null;
                    if(location.equals(""))
                        location = null;
                    pm.Init();
                    try {
                        if (pm.updateMap(UID, max, min, location)) {
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

/*界面2，第一层，添加按钮*/
class Adapter_btn7_MetaDataManagerFrame implements ActionListener {
    Adapter2_MetaDataManagerFrame p;
    JFrame f;
    JTextField textField;
    JTextField textField2;
    JTextField textField3;
    Adapter_btn7_MetaDataManagerFrame(Adapter2_MetaDataManagerFrame _p)
    {
        p = _p;
    }
    public void actionPerformed(ActionEvent e) {
        f = new JFrame();
        p.btnNewButton.setEnabled(false);
        p.button.setEnabled(false);
        p.button_1.setEnabled(false);
        p.dstable.setEnabled(false);
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
                p.dstable.updateUI(); //更新表
                p.dstable.setEnabled(true);
                f.dispose();
                super.windowClosing(e);
            }
        });
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setBounds(100, 100, 329, 245);
        f.setLocation(MetaDataManagerFrame.FWidth / 3, MetaDataManagerFrame.FHeight / 3);
        f.setTitle("添加数据库");

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(16, 16, 300, 145);
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setLayout(null);

        JLabel label = new JLabel("数据库名：");
        label.setBounds(25, 15, 65, 26);
        panel.add(label);
        textField = new JTextField();
        textField.setBounds(90, 9, 160, 38);
        panel.add(textField);
        textField.setColumns(10);
        contentPane.add(panel);

        JLabel label2 = new JLabel(" 用户名：");
        label2.setBounds(25, 57, 65, 26);
        panel.add(label2);
        textField2 = new JTextField();
        textField2.setBounds(90, 51, 160, 38);
        panel.add(textField2);
        textField2.setColumns(10);
        contentPane.add(panel);

        JLabel label3 = new JLabel("  表名：");
        label3.setBounds(25, 92, 65, 26);
        panel.add(label3);
        textField3 = new JTextField();
        textField3.setBounds(90, 96, 160, 38);
        panel.add(textField3);
        textField3.setColumns(10);
        contentPane.add(panel);


        JButton button_1 = new JButton("取消");
        button_1.setBounds(188, 180, 117, 29);
        button_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p.btnNewButton.setEnabled(true);
                p.button.setEnabled(true);
                p.button_1.setEnabled(true);
                p.dstable.setEnabled(true);
                f.dispose();
            }
        });
        contentPane.add(button_1);

        JButton button = new JButton("确认");
        button.setBounds(26, 180, 117, 29);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dbName = textField.getText();
                String usrName = textField2.getText();
                String psw = textField3.getText();
                PKUMetaDataManagement pm = new PKUMetaDataManagement();
                pm.Init();
                DefaultMutableTreeNode n = (DefaultMutableTreeNode)p.tree.getLastSelectedPathComponent();
                int index = n.getParent().getIndex(n);
                String[] temp;
                pm.showLTree();
                temp = pm.FetchLSID();
                String DSID = temp[index];
                try {
                    if (pm.addLDB(DSID, dbName, usrName, psw)) {
                        JOptionPane.showMessageDialog(null, "添加成功", "添加成功", JOptionPane.PLAIN_MESSAGE);
                        //刷新表格
                        p.btnNewButton.setEnabled(true);
                        p.button.setEnabled(true);
                        p.button_1.setEnabled(true);
                        p.dstable.setEnabled(true);
                        try {
                            p.updateTable();
                        }catch(Exception e1) {
                            JOptionPane.showMessageDialog(null, e1.getMessage(), "获取数据失败", JOptionPane.ERROR_MESSAGE);
                        }
                        p.dstable.updateUI(); //更新表
                        p.dstable.setEnabled(true);
                        //刷新文件树
                        DefaultMutableTreeNode tmp = new DefaultMutableTreeNode(dbName);
                        n.add(tmp);
                        MetaDataManagerFrame.tree2.updateUI();
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

/*界面2，第一层，编辑按钮*/
class Adapter_btn8_MetaDataManagerFrame implements ActionListener {
    Adapter2_MetaDataManagerFrame p;
    JFrame f;
    JTextField textField;
    int UID;
    Adapter_btn8_MetaDataManagerFrame(Adapter2_MetaDataManagerFrame _p)
    {
        p = _p;
    }
    public void actionPerformed(ActionEvent e) {
        if(p.dstable.getRowCount() == 0)
            JOptionPane.showMessageDialog(null, "无可编辑行", "编辑失败", JOptionPane.ERROR_MESSAGE);
        else if(p.dstable.getSelectedRow() == -1)
            JOptionPane.showMessageDialog(null, "请选择一行", "编辑失败", JOptionPane.ERROR_MESSAGE);
        else{
            f = new JFrame();
            p.btnNewButton.setEnabled(false);
            p.button.setEnabled(false);
            p.button_1.setEnabled(false);
            p.dstable.setEnabled(false);
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
                    p.dstable.updateUI(); //更新表
                    p.dstable.setEnabled(true);
                    f.dispose();
                    super.windowClosing(e);
                }
            });
            f.setResizable(false);
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.setBounds(100, 100, 329, 164);
            f.setLocation(MetaDataManagerFrame.FWidth / 3, MetaDataManagerFrame.FHeight / 3);
            f.setTitle("编辑数据库");

            JPanel contentPane = new JPanel();
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
            contentPane.setLayout(null);

            JPanel panel = new JPanel();
            panel.setBounds(16, 16, 297, 58);
            panel.setBorder(BorderFactory.createEtchedBorder());
            panel.setLayout(null);

            JLabel label = new JLabel("数据库名：");
            label.setBounds(25, 15, 65, 26);
            panel.add(label);
            textField = new JTextField();
            textField.setBounds(90, 9, 160, 38);
            panel.add(textField);
            textField.setColumns(10);
            p.fetchDSTable(); //获取所选编辑表格的UID和表名
            UID =Integer.valueOf(p.data[p.dstable.getSelectedRow()][0]);
            textField.setText(p.data[p.dstable.getSelectedRow()][1]); //获取原本表名
            contentPane.add(panel);

            JButton button_1 = new JButton("取消");
            button_1.setBounds(188, 90, 117, 29);
            button_1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    p.btnNewButton.setEnabled(true);
                    p.button.setEnabled(true);
                    p.button_1.setEnabled(true);
                    p.dstable.setEnabled(true);
                    f.dispose();
                }
            });
            contentPane.add(button_1);

            JButton button = new JButton("确认");
            button.setBounds(26, 90, 117, 29);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String dbName = textField.getText();
                    int index = p.dstable.getSelectedRow(); //获取所选结点的Index
                    PKUMetaDataManagement pm = new PKUMetaDataManagement();

                    pm.Init();
                    try {
                        if (pm.updateLDB_Name(UID, dbName)) {
                            JOptionPane.showMessageDialog(null, "编辑成功", "编辑成功", JOptionPane.PLAIN_MESSAGE);
                            //刷新表格
                            p.btnNewButton.setEnabled(true);
                            p.button.setEnabled(true);
                            p.button_1.setEnabled(true);
                            p.dstable.setEnabled(true);
                            try {
                                p.updateTable();
                            }catch(Exception e1) {
                                JOptionPane.showMessageDialog(null, e1.getMessage(), "获取数据失败", JOptionPane.ERROR_MESSAGE);
                            }
                            p.dstable.updateUI(); //更新表
                            p.dstable.setEnabled(true);
                            //刷新文件树
                            DefaultMutableTreeNode pa = (DefaultMutableTreeNode)p.tree.getLastSelectedPathComponent();
                            DefaultMutableTreeNode n = (DefaultMutableTreeNode)pa.getChildAt(index);
                            n.setUserObject(dbName);
                            MetaDataManagerFrame.tree2.updateUI();
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

/*界面2，第二层，添加按钮*/
class Adapter_btn9_MetaDataManagerFrame implements ActionListener {
    Adapter2_MetaDataManagerFrame p;
    JFrame f;
    JTextField textField;
    Adapter_btn9_MetaDataManagerFrame(Adapter2_MetaDataManagerFrame _p)
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
        f.setLocation(MetaDataManagerFrame.FWidth / 3, MetaDataManagerFrame.FHeight / 3);
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
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) MetaDataManagerFrame.tree2.getLastSelectedPathComponent();
                int index = selectedNode.getParent().getIndex(selectedNode); //获取表结点的索引
                int ID = selectedNode.getParent().getParent().getIndex(selectedNode.getParent()) + 1;
                p.data = pm.showLDB(ID);
                String DBID = p.data[index][0];

                try {
                    if (pm.addLT(DBID, tableName)) {
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
                        selectedNode.add(n);
                        MetaDataManagerFrame.tree2.updateUI();
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

/*界面2，第二层，编辑按钮*/
class Adapter_btn10_MetaDataManagerFrame implements ActionListener {
    Adapter2_MetaDataManagerFrame p;
    JFrame f;
    JTextField textField;
    int UID;
    Adapter_btn10_MetaDataManagerFrame(Adapter2_MetaDataManagerFrame _p)
    {
        p = _p;
    }
    public void actionPerformed(ActionEvent e) {
        if(p.dbtable.getRowCount() == 0)
            JOptionPane.showMessageDialog(null, "无可编辑行", "编辑失败", JOptionPane.ERROR_MESSAGE);
        else if(p.dbtable.getSelectedRow() == -1)
            JOptionPane.showMessageDialog(null, "请选择一行", "编辑失败", JOptionPane.ERROR_MESSAGE);
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
            f.setLocation(MetaDataManagerFrame.FWidth / 3, MetaDataManagerFrame.FHeight / 3);
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
            p.fetchDBTable(); //获取所选编辑表格的表名
            UID = Integer.valueOf(p.data[p.dbtable.getSelectedRow()][0]);
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
                        if (pm.updateLTable_Name(UID, tableName)) {
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
                            DefaultMutableTreeNode p = (DefaultMutableTreeNode)MetaDataManagerFrame.tree2.getLastSelectedPathComponent();
                            DefaultMutableTreeNode n = (DefaultMutableTreeNode)p.getChildAt(index);
                            n.setUserObject(tableName);
                            MetaDataManagerFrame.tree2.updateUI();
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

/*界面2，第三层，添加按钮*/
class Adapter_btn11_MetaDataManagerFrame implements ActionListener {
    Adapter2_MetaDataManagerFrame p;
    JFrame f;
    JTextField textField;
    JTextField textField2;

    Adapter_btn11_MetaDataManagerFrame(Adapter2_MetaDataManagerFrame _p)
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
        f.setBounds(100, 100, 329, 204);
        f.setLocation(MetaDataManagerFrame.FWidth / 3, MetaDataManagerFrame.FHeight / 3);
        f.setTitle("添加列");

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(16, 16, 297, 98);
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setLayout(null);

        JLabel label = new JLabel("列名：");
        label.setBounds(25, 15, 65, 26);
        panel.add(label);
        textField = new JTextField();
        textField.setBounds(90, 9, 160, 38);
        panel.add(textField);
        textField.setColumns(10);

        JLabel label2 = new JLabel("列类型：");
        label2.setBounds(25, 55, 65, 26);
        panel.add(label2);
        textField2 = new JTextField();
        textField2.setBounds(90, 49, 160, 38);
        panel.add(textField2);
        textField2.setColumns(10);
        contentPane.add(panel);

        JButton button_1 = new JButton("取消");
        button_1.setBounds(188, 130, 117, 29);
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
        button.setBounds(26, 130, 117, 29);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String columnName = textField.getText();
                String columnType = textField2.getText();

                PKUMetaDataManagement pm = new PKUMetaDataManagement();
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) MetaDataManagerFrame.tree2.getLastSelectedPathComponent();
                int index = selectedNode.getParent().getIndex(selectedNode); //获取表结点的索引
                int ID = selectedNode.getParent().getParent().getParent().getIndex(selectedNode.getParent().getParent()) + 1;
                pm.Init();
                String[][] temp = pm.showLDB(ID);
                int DBID = Integer.valueOf(temp[selectedNode.getParent().getParent().getIndex(selectedNode.getParent())][0]);
                p.data = pm.showLTable(DBID);
                String TID = p.data[index][0];

                try {
                    if (pm.addLC(TID, columnName, columnType)) {
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
                        MetaDataManagerFrame.tree2.updateUI();
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

/*界面2，第三层，编辑按钮*/
class Adapter_btn12_MetaDataManagerFrame implements ActionListener {
    Adapter2_MetaDataManagerFrame p;
    JFrame f;
    JTextField textField;
    int UID;
    Adapter_btn12_MetaDataManagerFrame(Adapter2_MetaDataManagerFrame _p)
    {
        p = _p;
    }
    public void actionPerformed(ActionEvent e) {
        if(p.tbtable.getRowCount() == 0)
            JOptionPane.showMessageDialog(null, "无可编辑行", "编辑失败", JOptionPane.ERROR_MESSAGE);
        else if(p.tbtable.getSelectedRow() == -1)
            JOptionPane.showMessageDialog(null, "请选择一行", "编辑失败", JOptionPane.ERROR_MESSAGE);
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
            f.setLocation(MetaDataManagerFrame.FWidth / 3, MetaDataManagerFrame.FHeight / 3);
            f.setTitle("编辑表");

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
                        if (pm.updateLColumn_Name(UID, columnName)) {
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
                            DefaultMutableTreeNode p = (DefaultMutableTreeNode)MetaDataManagerFrame.tree2.getLastSelectedPathComponent();
                            DefaultMutableTreeNode n = (DefaultMutableTreeNode)p.getChildAt(index);
                            n.setUserObject(columnName);
                            MetaDataManagerFrame.tree2.updateUI();
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

/*界面2，第四层，添加按钮*/
class Adapter_btn13_MetaDataManagerFrame implements ActionListener {
    Adapter2_MetaDataManagerFrame p;
    JFrame f;
    JComboBox comboBox;
    int LCID;
    JTextField textField;
    JTextField textField2;
    JTextField textField3;
    int[] UCID;


    Adapter_btn13_MetaDataManagerFrame(Adapter2_MetaDataManagerFrame _p)
    {
        p = _p;
    }
    public void actionPerformed(ActionEvent e) {
        if(p.columntable.getRowCount() >= 1)
            JOptionPane.showMessageDialog(null, "本地列只允许同时存在一个映射关系", "添加失败", JOptionPane.ERROR_MESSAGE);
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
            f.setBounds(100, 100, 430, 300);
            f.setLocation(MetaDataManagerFrame.FWidth / 3, MetaDataManagerFrame.FHeight / 3);
            f.setTitle("添加映射");

            PKUMetaDataManagement pm = new PKUMetaDataManagement();
            DefaultMutableTreeNode pa = (DefaultMutableTreeNode)MetaDataManagerFrame.tree2.getLastSelectedPathComponent();
            int index = pa.getParent().getIndex(pa); //获取当前选择列结点的索引值
            pm.Init();

            String[][] temp = pm.showUTable(); //获取列数组，不能直接使用fetchcolumntable，是因为当前结点是列结点而不是表姐点
            int TID = Integer.valueOf(temp[p.selectedNode.getParent().getParent().getIndex(p.selectedNode.getParent())][0]);
            p.data = pm.showUColumn(TID);

            //不能直接通过选定行来找UCID，因为表可能为空
            LCID = Integer.valueOf(p.data[index][0]);
            //System.out.println(index + "-" + LCID);

            pm.Init();
            pm.showNoMapUC(LCID);
            String[] TName = pm.FetchNoMapUTName();
            String[] CName = pm.FetchNoMapUCName();
            UCID = pm.FetchNoMapUCID();
            pm.CloseCon();

            JPanel contentPane = new JPanel();
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
            contentPane.setLayout(null);

            JPanel panel = new JPanel();
            panel.setBounds(16, 16, 400, 185);
            panel.setBorder(BorderFactory.createEtchedBorder());
            panel.setLayout(null);

            JLabel label = new JLabel("本地映射：");
            label.setBounds(25, 13, 65, 26);
            panel.add(label);
            comboBox = new JComboBox();
            for(int i = 0; i < TName.length; i++)
            {
                comboBox.addItem(TName[i] + "." + CName[i]);
            }
            comboBox.setBounds(90, 7, 280, 30);
            panel.add(comboBox);

            JLabel label1 = new JLabel("Max ：");
            label1.setBounds(25, 53, 65, 26);
            panel.add(label1);
            textField = new JTextField();
            textField.setBounds(90, 47,280, 30);
            textField.setColumns(10);
            panel.add(textField);
            JLabel label2 = new JLabel("Min ：");
            label2.setBounds(25, 93, 65, 26);
            panel.add(label2);
            textField2 = new JTextField();
            textField2.setBounds(90, 87, 280, 30);
            textField2.setColumns(10);
            panel.add(textField2);
            JLabel label3 = new JLabel("Location ：");
            label3.setBounds(25, 133, 65, 26);
            panel.add(label3);
            textField3 = new JTextField();
            textField3.setBounds(90, 127, 280, 30);
            textField3.setColumns(10);
            panel.add(textField3);
            contentPane.add(panel);

            JButton button_1 = new JButton("取消");
            button_1.setBounds(228, 220, 117, 29);
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
            button.setBounds(26, 220, 117, 29);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int ucid = UCID[comboBox.getSelectedIndex()];
                    String max = textField.getText();
                    String min = textField2.getText();
                    String location = textField3.getText();
                    if(max.equals(""))
                        max = null;
                    if(min.equals(""))
                        min = null;
                    if(location.equals(""))
                        location = null;
                    PKUMetaDataManagement pm = new PKUMetaDataManagement();
                    pm.Init();
                    try {
                        if (pm.addMap(ucid, LCID, max, min, location)) {
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
}

/*界面2，第四层，编辑按钮*/
class Adapter_btn14_MetaDataManagerFrame implements ActionListener {
    Adapter2_MetaDataManagerFrame p;
    JFrame f;
    JComboBox comboBox;
    int UID;
    JTextField textField;
    JTextField textField2;
    JTextField textField3;
    String max;
    String min;
    String location;
    Adapter_btn14_MetaDataManagerFrame(Adapter2_MetaDataManagerFrame _p)
    {
        p = _p;
    }
    public void actionPerformed(ActionEvent e) {
        if(p.columntable.getRowCount() == 0)
            JOptionPane.showMessageDialog(null, "无可编辑行", "编辑失败", JOptionPane.ERROR_MESSAGE);
        else if(p.columntable.getSelectedRow() == -1)
            JOptionPane.showMessageDialog(null, "请选择一行", "编辑失败", JOptionPane.ERROR_MESSAGE);
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
            f.setLocation(MetaDataManagerFrame.FWidth / 3, MetaDataManagerFrame.FHeight / 3);
            f.setTitle("编辑映射");

            int index = p.columntable.getSelectedRow();
            p.fetchColumnTable();
            UID = Integer.valueOf(p.data[index][0]);
            max = p.data[index][3];
            min = p.data[index][4];
            location =p.data[index][5];
            //System.out.println(index + "-" + LCID);

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
            textField0.setBounds(90, 11, 160, 28);
            textField0.setColumns(10);
            textField0.setText(UID+"");
            textField0.setEditable(false);
            panel.add(textField0);
            JLabel label1 = new JLabel("Max ：");
            label1.setBounds(25, 45, 65, 26);
            panel.add(label1);
            textField = new JTextField();
            textField.setBounds(90, 42, 160, 28);
            textField.setColumns(10);
            textField.setText(max+"");
            panel.add(textField);
            JLabel label2 = new JLabel("Min ：");
            label2.setBounds(25, 75, 65, 26);
            panel.add(label2);
            textField2 = new JTextField();
            textField2.setBounds(90, 73, 160, 28);
            textField2.setColumns(10);
            textField2.setText(min+"");
            panel.add(textField2);
            JLabel label3 = new JLabel("Location ：");
            label3.setBounds(25, 105, 65, 26);
            panel.add(label3);
            textField3 = new JTextField();
            textField3.setBounds(90, 104, 160, 28);
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
                    String max = textField.getText();
                    String min = textField2.getText();
                    String location = textField3.getText();
                    PKUMetaDataManagement pm = new PKUMetaDataManagement();
                    if(max.equals(""))
                        max = null;
                    if(min.equals(""))
                        min = null;
                    if(location.equals(""))
                        location = null;
                    pm.Init();
                    try {
                        if (pm.updateMap(UID, min, max, location)) {
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